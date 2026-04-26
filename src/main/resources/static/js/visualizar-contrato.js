document.addEventListener('DOMContentLoaded', async () => {
            // Obtener el ID del contrato de la URL (ej. visualizar-contrato.html?id=5)
            const parametrosURL = new URLSearchParams(window.location.search);
            const idContrato = parametrosURL.get('id');

            if (!idContrato) {
                alert("No se encontró el número de contrato.");
                return;
            }

            try {
                // Hacemos GET a tu nuevo endpoint del controlador
                const response = await fetch(`/api/contratos/${idContrato}`);
                
                if (response.ok) {
                    const contrato = await response.json();
                    
                    // Llenamos la información en el HTML
                    document.getElementById('folioContrato').textContent = "HN-" + contrato.id.toString().padStart(6, '0');
                    document.getElementById('fechaEmision').textContent = new Date().toLocaleDateString();

                    // Cliente (Asegúrate de que tus getters devuelven estos nombres)
                    document.getElementById('cNombre').textContent = `${contrato.cliente.nombre} ${contrato.cliente.apellidoP} ${contrato.cliente.apellidoM || ''}`;
                    document.getElementById('cRfc').textContent = contrato.cliente.rfc;
                    document.getElementById('cLicencia').textContent = contrato.cliente.noLicencia;
                    document.getElementById('cTelefono').textContent = contrato.cliente.celular;

                    // Vehículo
                    document.getElementById('vMarcaModelo').textContent = `${contrato.vehiculo.marca} ${contrato.vehiculo.modelo}`;
                    document.getElementById('vPlaca').textContent = contrato.vehiculo.placa;
                    
                    // Renta
                    document.getElementById('vSeguro').textContent = contrato.tipoSeguro.replace('_', ' ');
                    document.getElementById('vAseguradora').textContent = contrato.aseguradora;
                    document.getElementById('rInicio').textContent = contrato.fechaInicio;
                    document.getElementById('rFin').textContent = contrato.fechaFin;
                    document.getElementById('rDias').textContent = contrato.diasTotales;
                    document.getElementById('rPago').textContent = "Tarjeta - " + contrato.metodoPago;
                    document.getElementById('totalFinal').textContent = contrato.costoTotal.toLocaleString('es-MX', {minimumFractionDigits: 2});

                } else {
                    alert("No se pudo cargar el contrato.");
                }
            } catch (error) {
                console.error("Error al obtener el contrato:", error);
            }
        });