const API_EMPLEADO ='/api/empleados';
const API_VEHICULO = '/api/vehiculos';
const API_CONTRATO = '/api/contratos';
const volver = '/catalogo';
const btnVolver = document.getElementById('btn-salir');
const btnReparar = document.getElementById('btnReparar');
const btnDisponible = document.getElementById('btnDisponible');
const btnEliminar = document.getElementById('btnEliminar');
const btnFinalizar = document.getElementById('btnFinalizar');

let idVehiculoSeleccionado = null; 


document.addEventListener('DOMContentLoaded', () => {
    const btnEmpleados = document.getElementById('btn-empleados');
    const btnFlota = document.getElementById('btn-flota');
    const formRegistro = document.getElementById('RegistarEmp');
    
    if (btnVolver) {
        btnVolver.addEventListener('click', () => {
            window.location.href = volver;
        });
    }

    //Logiaca para abrir el modal
    const modal = document.getElementById('modal');
    const closeModal = document.getElementById('closeModalBtn');
    const tablaContenedor = document.getElementById('tabla');
    if (modal) {
        modal.style.display = 'none';
    }
    //Abrir el modal
    if(tablaContenedor){
        tablaContenedor.addEventListener('click', async (evento) => {
            if(evento.target.classList.contains('abrir-modal-btn')){
                modal.style.display = 'flex';
                idVehiculoSeleccionado = evento.target.getAttribute('data-id');
                console.log("Vehículo seleccionado:", idVehiculoSeleccionado);
            }
            //Entrega del auto
            if(evento.target.classList.contains('btn-accion2')){
                idVehiculoSeleccionado = evento.target.getAttribute('data-id');
                console.log(idVehiculoSeleccionado)
                const idContrato = await contratoID(idVehiculoSeleccionado);
                await entregarVehiculo(idContrato);
            }
        });
    }
    if (btnReparar) {
        btnReparar.addEventListener('click', () => {
            if (idVehiculoSeleccionado) {
                marcarComoMantenimiento(idVehiculoSeleccionado);
                modal.style.display = 'none';
            }
        });
    }

    if (btnDisponible) {
        btnDisponible.addEventListener('click', () => {
            if (idVehiculoSeleccionado) {
                marcarComoDisponible(idVehiculoSeleccionado);
                modal.style.display = 'none';
            }
        });
    }

    if (btnEliminar) {
        btnEliminar.addEventListener('click', () => {
            if (idVehiculoSeleccionado) {
                eliminarAuto(idVehiculoSeleccionado);
                modal.style.display = 'none';
            }
        });
    }
    if (btnFinalizar) {
        btnFinalizar.addEventListener('click', async (evento) => {
            evento.preventDefault(); 
            if (idVehiculoSeleccionado) {
                const idC = await contratoID(idVehiculoSeleccionado);
                if (idC === 0) {
                    alert("Este vehículo no tiene un contrato activo para finalizar.");
                    return; 
                }
                const observaciones = document.getElementById('descripcionDelAuto').value; // El Select
                const montoInput = document.getElementById('monto').value;
                const descripcionExtra = document.getElementById('descripcion').value; // El texto
                const montoExtra = montoInput ? parseFloat(montoInput) : 0;
                await finalizarContrato(idC, observaciones, montoExtra, descripcionExtra);
                modal.style.display = 'none';
                document.getElementById('finalizarContratoForm').reset();
            } else {
                alert("No se ha seleccionado ningún vehículo.");
            }
        });
    }
    
    //Cerrar el modal
    if (closeModal) {
        closeModal.addEventListener('click', () => {
            modal.style.display = 'none';
        });
    }

    detalleVehiculo();
    btnEmpleados.addEventListener('click', (evento) => {
        mostrarSeccion('seccion-empleados', evento.currentTarget);
    });

    btnFlota.addEventListener('click', (evento) => {
        mostrarSeccion('seccion-flota', evento.currentTarget);
    });
    formRegistro.addEventListener('submit', (evento) => {
        evento.preventDefault();
        registrarEmpleado(evento);
    });
});

function mostrarSeccion(idSeccion, botonPresionado) {
    document.getElementById('seccion-empleados').style.display = 'none';
    document.getElementById('seccion-flota').style.display = 'none';
    document.getElementById(idSeccion).style.display = 'block';
    const botones = document.querySelectorAll('.btn-nav');
    botones.forEach(btn => btn.classList.remove('active'));
    botonPresionado.classList.add('active');
}

async function registrarEmpleado(evento) {
    const form = evento.target;
    const rolSelecionado = document.getElementById('rol').value;
    //Validamos el rol
    if (rolSelecionado === "tipo") {
        alert("Seleccione el Tipo del Empleado");
        return;
    }

    const empleado = {
        nombre: document.getElementById('nombre').value,
        apellidoP: document.getElementById('apellidoP').value,
        apellidoM: document.getElementById('apellidoM').value,
        celular: document.getElementById('celular').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        rol: rolSelecionado,
        direccion:{
            calle: document.getElementById('calle').value,
            colonia: document.getElementById('colonia').value,
            codigoPostal: document.getElementById('codigoPostal').value,
            delegacion: document.getElementById('delegacion').value,
         }
    };
    const res = await fetch(API_EMPLEADO,{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'},
             body: JSON.stringify(empleado),
    });
        if(res.ok){
            alert("Empleado Registrado correctamente");
            form.reset();
        }else {
            alert('Error al registrar un vehiculo con el mismo correo');
        }

}

//Solicitar los detalles del vehiculo
async function detalleVehiculo(){
    try {
        const responce = await fetch(API_VEHICULO);
        if (responce.ok) {
            const vehiculo = await responce.json();
            llenarTabla(vehiculo);
        } else{
            console.log("Error al ver la info del vahiculo");
        }
    }catch (error) {
        console.log("Error al cargar el vehiculo");
    }
}
async function llenarTabla(vehiculos){
    const table = document.getElementById('tabla');
    //Limpia el contenedor
    table.innerHTML = '';
    for (const vehiculo of vehiculos) {
        const fechaContrato = await mostrarFechaContrato(vehiculo.idVehiculo);
        const tabla = `
          <tr>
            <td>${vehiculo.idVehiculo}</td>
            <td><strong>${vehiculo.marca} ${vehiculo.modelo}</strong> <strong>Modelo:</strong> ${vehiculo.anio}</td>
            <td>${vehiculo.placa}</td>
            <td><span class="badge badge-renta">${vehiculo.estado}</span></td>
            <td>${fechaContrato}</td>
            <td><button class="btn-accion abrir-modal-btn" data-id="${vehiculo.idVehiculo}">Ver Detalles</button></td>
            <td><button class="btn-accion2" data-id="${vehiculo.idVehiculo}">Entregar Auto</button></td>
           </tr>   
        `;
        table.innerHTML += tabla;
    }

}
// Marcar como en mantenimiento
async function marcarComoMantenimiento(idVehiculo) {
    try {
        const url = `${API_VEHICULO}/${idVehiculo}/mantenimiento`; // Ej: /api/vehiculos/5/mantenimiento
        
        const response = await fetch(url, {
            method: 'PUT'
        });

        if (response.ok) {
            alert("Vehiculo enviado a mantenimiento exitosamente.");
            detalleVehiculo();
        } else {
            alert("Error al actualizar el estado del vehiculo.");
        }
    } catch (error) {
        console.error("Error en la petición:", error);
        alert("Error de conexión al servidor.");
    }
}

//Entrega de vehiculo
async function entregarVehiculo(idContrato) {
    try{
        const url = `${API_CONTRATO}/${idContrato}/actualizar`;
        const responce = await fetch(url, {
            method: 'PUT'
        });
        if(responce.ok){
            alert("Vehiculo entregado Existosamente")
            detalleVehiculo();
        }else{
            alert("Error al actualizar la entrega")
        }
    }catch{
        alert("Error en la promesa")
    }
}

// Marcar como disponible
async function marcarComoDisponible(idVehiculo) {
    try {
        const url = `${API_VEHICULO}/${idVehiculo}/disponible`; 
        
        const response = await fetch(url, {
            method: 'PUT'
        });

        if (response.ok) {
            alert("El vehiculo ahora está disponible.");
            detalleVehiculo();
        } else {
            alert("Error al actualizar el estado del vehiculo.");
        }
    } catch (error) {
        console.error("Error en la petición:", error);
        alert("Error de conexión al servidor.");
    }
}

async function eliminarAuto(idVehiculo) {
    try {
        const url = `${API_VEHICULO}/${idVehiculo}`; // Queda como: /api/vehiculos/1   
        const response = await fetch(url, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert("Vehículo eliminado correctamente");
            detalleVehiculo(); // Recargamos la tabla
            document.getElementById('modal').style.display = 'none'; // Cerramos modal
        } else {
            alert("No se pudo eliminar el vehículo, esta en mantenimiento o tiene un contrato en curso.");
        }
    } catch (error) {
        console.error("Error al eliminar:", error);
    }
}

//Mostar fecha de contrato
async function mostrarFechaContrato(idVehiculo) {
    try {
        const responce = await fetch(`${API_VEHICULO}/${idVehiculo}/fecha`);
        if (responce.ok) {
            const fecha = await responce.text();
            console.log("Fecha del contrato:", fecha);
            return fecha;
        }
    } catch (error) {
        console.error("Error:", error);
    }
    return "Sin Constrtato";
}

//Finalizar el contrato con los datos extra 
async function finalizarContrato(idContrato, observaciones, montoExtra, descripcionExtra) {

    const datosFinalizacion = {
        observaciones: observaciones,
        montoExtra: montoExtra,
        descripcionExtra: descripcionExtra
    };              
    try {
        const url = `${API_CONTRATO}/${idContrato}/finalizar`;
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datosFinalizacion)
        }); 
            if (response.ok) {
                alert("Contrato finalizado correctamente.");
                detalleVehiculo(); //Actualizamos la tabla para reflejar el cambio
            } else {
                alert("Error al finalizar el contrato.");
            }
    }
        catch (error) {
        console.error("Error al finalizar el contrato:", error);
    }   
}

//obtener id del contrato 
async function contratoID(idVehiculo) {
    try {
        const url = `${API_CONTRATO}/vehiculo/${idVehiculo}/activo`;
        const responce = await fetch(url);
        if(responce.ok){
           const idContrato = await responce.text();
           const id = parseInt(idContrato);
           return id; 
        }else{
            console.log("No hay contratos")
        }
        
    } catch (error) {
        console.error("Error al obtener el ID del contrato:", error);
    }
    return 0;
}
