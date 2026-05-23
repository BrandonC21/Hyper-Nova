//Endpoint
const API_CONTRATO = '/api/contratos';
const catalogo = '/catalogo';
const volverCatalogo = document.getElementById('catalogo');

document.addEventListener('DOMContentLoaded', async () => {
            const parametrosURL = new URLSearchParams(window.location.search);
            const idContrato = parametrosURL.get('id');
            const fecha = new Date();
            const formatoLocal = fecha.toLocaleDateString('es-MX');

            if (!idContrato) {
                alert("No se encontró el número de contrato.");
                console.log(idContrato)
                console.log(window.location.search);
                return;
            }
            if(catalogo){
                volverCatalogo.addEventListener('click', () =>{
                    window.location.href = catalogo;
                });
            }

            try {
                console.log(window.location.search);
                const response = await fetch(`${API_CONTRATO}/${idContrato}`);
                if (response.ok) {
                    const contrato = await response.json();
                    document.getElementById('folioContrato').textContent = contrato.folio;
                    document.getElementById('fechaEmision').textContent = formatoLocal;

                    //Datos de cliente
                    document.getElementById('cNombre').textContent = `${contrato.cliente.nombre} ${contrato.cliente.apellidoP} ${contrato.cliente.apellidoM || ''}`;
                    document.getElementById('cRfc').textContent = contrato.cliente.rfc;
                    document.getElementById('cLicencia').textContent = contrato.cliente.noLicencia;
                    document.getElementById('cTelefono').textContent = contrato.cliente.celular;
                    document.getElementById('cEmail').textContent = contrato.cliente.email;

                    // Datos del vehiculo
                    document.getElementById('vMarcaModelo').textContent = contrato.vehiculo.marca;
                        //`${contrato.vehiculo.marca} ${contrato.vehiculo.modelo}`;
                    document.getElementById('vModelo').textContent = contrato.vehiculo.modelo;
                    document.getElementById('vClasificacion').textContent = contrato.vehiculo.clasificacion;
                    document.getElementById('Version').textContent = contrato.vehiculo.version;
                    document.getElementById('vAnio').textContent = contrato.vehiculo.anio;
                    document.getElementById('vPlaca').textContent = contrato.vehiculo.placa;

                    //Datos del seguro
                    document.getElementById('sCompania').textContent = contrato.seguro.nombreAseguradora;
                    document.getElementById('sCobertura').textContent = contrato.seguro.tipoSeguro;
                    document.getElementById('sCostoDia').textContent = contrato.seguro.costo;


                    // Renta
                    document.getElementById('rInicio').textContent = contrato.fechaInicio;
                    document.getElementById('rFin').textContent = contrato.fechaFin;
                    document.getElementById('rDias').textContent = contrato.diasTotales;
                    document.getElementById('rPago').textContent = "Tarjeta - " + contrato.metodoPago.nombreBanco;
                    document.getElementById('cDeposito').textContent = contrato.deposito;
                    document.getElementById('totalFinal').textContent = contrato.costoFinal.toLocaleString('es-MX', {minimumFractionDigits: 2});

                } else {
                    alert("No se pudo cargar el contrato.");
                }
            } catch (error) {
                console.error("Error al obtener el contrato:", error);
            }
        });