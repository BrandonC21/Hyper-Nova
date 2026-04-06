// Endpoints
const API_CLIENTE = '/api/clientes';
const API_VEHICULO = '/api/vehiculos';
const API_CONTRATO = '/api/contratos'; // Necesitarás este controlador en tu backend
const visualizar = 'visualizar-contrato.html'; // Página para mostrar el contrato generado
let idVehiculoGlobal = null; // Guardamos el ID del vehículo a nivel global

document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modal');
    const openModal = document.getElementById('btnAbrirModal'); 
    const closeModal = document.getElementById('closeModalBtn');
    const btnBuscar = document.getElementById('btnBuscar');
    const formReserva = document.getElementById('form-reserva'); // Corregido al ID del HTML
    
    const parametrosURL = new URLSearchParams(window.location.search);
    idVehiculoGlobal = parametrosURL.get('id');

    if (idVehiculoGlobal) {
        cargarDatosVehiculo(idVehiculoGlobal);
    } else {
        alert("No se especificó un vehículo. Regresa al catálogo y selecciona uno.");
    }

    // --- EVENTOS DEL MODAL ---
    if (openModal) openModal.addEventListener('click', () => modal.style.display = 'flex');
    if (closeModal) closeModal.addEventListener('click', () => modal.style.display = 'none');

    // --- EVENTO DE BÚSQUEDA ---
    if (btnBuscar) {
        btnBuscar.addEventListener('click', function (){
           const rfcCliente = document.getElementById('rfcBuscar').value;
           if(rfcCliente.trim() !== ''){
               buscarPorRFC(rfcCliente);
           } else {
               alert("Debes ingresar el RFC a buscar"); 
           }
        });
    }

    // --- EVENTOS PARA CALCULAR TOTALES DINÁMICAMENTE ---
    const inputsCalculo = ['fechaInicio', 'fechaFin', 'seguro'];
    inputsCalculo.forEach(id => {
        const elemento = document.getElementById(id);
        if(elemento) elemento.addEventListener('change', calcularTotales);
    });

    // --- EVENTO PRINCIPAL: CONFIRMAR RESERVACIÓN ---
    if (formReserva) {
        formReserva.addEventListener('submit', (evento) => {
            evento.preventDefault();
            procesarReservacion(evento);
        });
    }
});


// -----------------------------------------
// FUNCIONES DE BÚSQUEDA Y LLENADO (Vehículo y Cliente)
// -----------------------------------------

async function buscarPorRFC(rfcCliente) {
    try {
        const response = await fetch(`${API_CLIENTE}/${rfcCliente}`);
        if (response.ok) {
            const cliente = await response.json();
            // ¡AGREGA ESTA LÍNEA!
            console.log("Datos del cliente desde Java:", cliente);
            llenarFormularioCliente(cliente);
            document.getElementById('modal').style.display = 'none';          
        } else if (response.status === 404) {
            alert("El RFC no existe, por favor llena tus datos para registrarte.");
        }
    } catch (error) {
        console.log("Error de conexión:", error);
    }
}

function llenarFormularioCliente(cliente) {
    document.getElementById('nombre').value = cliente.nombre || '';
    document.getElementById('apellidoPaterno').value = cliente.apellidoP || '';
    document.getElementById('apellidoMaterno').value = cliente.apellidoM || '';
    document.getElementById('numLicencia').value = cliente.noLicencia || '';
    document.getElementById('telefono').value = cliente.celular || '';
    document.getElementById('email').value = cliente.email || '';
    document.getElementById('RFC').value = cliente.rfc || '';
    if (cliente.direccion) {
        document.getElementById('calle').value = cliente.direccion.calle || '';
        document.getElementById('colonia').value = cliente.direccion.colonia || '';
        document.getElementById('codigoPostal').value = cliente.direccion.codigoPostal || '';
        document.getElementById('delegacion').value = cliente.direccion.delegacion || '';
    }
}

async function cargarDatosVehiculo(id) {
    try {
        const response = await fetch(`${API_VEHICULO}/${id}`);
        if (response.ok) {
            const vehiculo = await response.json();
            document.getElementById('marca').value = `${vehiculo.marca} - ${vehiculo.modelo} - ${vehiculo.clasificacion} - Modelo ${vehiculo.anio}`;
            document.getElementById('placa').value = vehiculo.placa;
            document.getElementById('costoDia').value = vehiculo.costoDia;
            // Guardamos el costo en un input oculto para calcular
            document.getElementById('costoDia').dataset.precio = vehiculo.costoDia; 
        }
    } catch (error) {
        console.log("Error al cargar los datos del vehículo:", error);
    }
}


// -----------------------------------------
// LÓGICA DE NEGOCIO: CALCULAR Y PROCESAR
// -----------------------------------------

function calcularTotales() {
    const fInicioStr = document.getElementById('fechaInicio').value;
    const fFinStr = document.getElementById('fechaFin').value;
    const seguroSelect = document.getElementById('seguro').value;
  



    // Si aún no elige fechas, no calculamos
    if(!fInicioStr || !fFinStr) return; 

    const fInicio = new Date(fInicioStr);
    const fFin = new Date(fFinStr);

    if(fFin < fInicio) {
        alert("La fecha de devolución no puede ser antes de la recogida");
        document.getElementById('fechaFin').value = '';
        return;
    }

    // Calcular días
    const diffTiempo = Math.abs(fFin - fInicio);
    let diasTotales = Math.ceil(diffTiempo / (1000 * 60 * 60 * 24));
    if(diasTotales === 0) diasTotales = 1; // Mínimo 1 día de renta
    document.getElementById('diasTotales').textContent = diasTotales;

    // Obtener costos
    const costoDiaVehiculo = parseFloat(document.getElementById('costoDia').dataset.precio || 0);
    let deposito = (costoDiaVehiculo * diasTotales) * 0.20; // Depossito es el 20% del de la renta total del vehículo
    document.getElementById('deposito').value = deposito.toFixed(2);
    document.getElementById('depositoMostrar').value = `$ ${deposito.toFixed(2)}`;

    
    
    let costoSeguroDia = 0;

    switch(seguroSelect) {
        case 'COBERTURA_TERCEROS': costoSeguroDia = 129.35; break;
        case 'COBERTURA_AMPLIA': costoSeguroDia = 189.99; break;
        case 'COBERTURA_LIMITADA': costoSeguroDia = 249.65; break;
        case 'COBERTURA_PREMIUM': costoSeguroDia = 385.65; break;
    }

    // Calcular el total: (Costo Vehículo x Días) + (Costo Seguro x Días) + Depósito
    const total = (costoDiaVehiculo * diasTotales) + (costoSeguroDia * diasTotales) + deposito;
    
    document.getElementById('costoFinal').value = total.toFixed(2);
}

// Lógica principal de Reservación
async function procesarReservacion() {
    const rfc = document.getElementById('RFC').value;

    // 1. Armamos el objeto Cliente
    const datosCliente = {
        nombre: document.getElementById('nombre').value,
        apellidoP: document.getElementById('apellidoPaterno').value,
        apellidoM: document.getElementById('apellidoMaterno').value,
        noLicencia: document.getElementById('numLicencia').value,
        celular: document.getElementById('telefono').value,
        email: document.getElementById('email').value,
        rfc: rfc,
        direccion: {
            calle: document.getElementById('calle').value,
            colonia: document.getElementById('colonia').value,
            codigoPostal: document.getElementById('codigoPostal').value,
            delegacion: document.getElementById('delegacion').value,
        }
    };

    try {
        let idClienteFinal = null;

        // 2. VERIFICAMOS SI EL CLIENTE EXISTE
        const resVerificar = await fetch(`${API_CLIENTE}/${rfc}`);
        
        if (resVerificar.ok) {
            // EL CLIENTE EXISTE -> Lo actualizamos
            // NOTA: Asumo que en tu Controlador de SpringBoot tienes un endpoint PUT o POST para actualizar
            console.log("El cliente existe, actualizando datos...");
            const resActualizar = await fetch(`${API_CLIENTE}/actualizar`, { 
                method: 'PUT', // o POST dependiendo de tu controlador
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datosCliente)
            });
            const clienteActualizado = await resActualizar.json();
            idClienteFinal = clienteActualizado.idCliente;
        } else {
            // EL CLIENTE ES NUEVO -> Lo creamos
            console.log("Cliente nuevo, registrando...");
            const resCrear = await fetch(API_CLIENTE, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(datosCliente)
            });
            const clienteNuevo = await resCrear.json();
            idClienteFinal = clienteNuevo.idCliente; 
        }

        // 3. ARMAMOS Y ENVIAMOS EL CONTRATO
        // Ya tenemos el idClienteFinal, ahora creamos el contrato
        const datosContrato = {
            cliente: { idCliente: idClienteFinal }, // Vinculamos el ID del cliente
            vehiculo: { idVehiculo: idVehiculoGlobal }, // Vinculamos el ID del vehículo
            fechaInicio: document.getElementById('fechaInicio').value,
            fechaFin: document.getElementById('fechaFin').value,
            seguro:{
                nombreAseguradora: document.getElementById('nombreAseguradora').value,
                tipoSeguro: document.getElementById('seguro').value,

            },
            metodoPago: {
                nombreBanco: document.getElementById('nombreBanco').value,
                numTarjeta: document.getElementById('numTarjeta').value,
                vigencia: document.getElementById('vigencia').value,
                cvs: document.getElementById('cvs').value,
                cliente: { idCliente: idClienteFinal }
            },
            costoFinal: parseFloat(document.getElementById('costoFinal').value),
            diasTotales: parseInt(document.getElementById('diasTotales').textContent)
        
        };

        const resContrato = await fetch(API_CONTRATO, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(datosContrato)
        });

        if (resContrato.ok) {
            const contratoGenerado = await resContrato.json();
            alert("¡Reservación confirmada exitosamente!");
            
            // 4. REDIRECCIÓN A LA PÁGINA DEL CONTRATO
            // Se asume que el backend devuelve el contrato con su ID
            window.location.href = `${visualizar}?id=${contratoGenerado.idContrato}`;
        } else {
            alert("Error al generar el contrato.");
        }

    } catch (error) {
        console.error("Error en el proceso de reservación:", error);
        alert("Ocurrió un problema de conexión al procesar la reserva.");
    }
}