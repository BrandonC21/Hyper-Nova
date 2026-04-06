const API_VEHICULO_DETALLE = '/api/vehiculos/';
const catalogo ='catalogo';
const formulario = 'nuevo-contrato.html';
const miEnlace = document.getElementById('accion-link');

document.addEventListener('DOMContentLoaded', () => {
    // 1. Extraer el ID del vehículo desde la URL (ej. ?id=5)
    const parametrosURL = new URLSearchParams(window.location.search);
    const idVehiculo = parametrosURL.get('id');
    if(miEnlace){
        miEnlace.addEventListener('click', () => {
            window.location.href = catalogo;
        });
    }
    if (idVehiculo) {
        // Si hay un ID, vamos a buscar sus datos
        cargarDetallesVehiculo(idVehiculo);
    } else {
        // Si alguien entra a la página sin ID, le avisamos
        document.getElementById('detalle-titulo').textContent = 'Error: Vehículo no especificado';
        document.getElementById('detalle-mensaje-img').textContent = 'Por favor, regresa al catálogo y selecciona un vehículo.';
    }
});

// Función para ir al backend (Java) a traer los datos
async function cargarDetallesVehiculo(id) {
    try {
        const response = await fetch(API_VEHICULO_DETALLE + id);

        if (response.ok) {
            const vehiculo = await response.json();
            mostrarDetalles(vehiculo);
        } else {
            console.log('Error al obtener el vehículo. Status:', response.status);
            document.getElementById('detalle-titulo').textContent = 'Vehículo no encontrado';
            document.getElementById('detalle-mensaje-img').textContent = 'El vehículo no existe en la base de datos.';
        }
    } catch (error) {
        console.log("Error de conexión con el servidor:", error);
    }
}

// Función para inyectar los datos en tu HTML
function mostrarDetalles(vehiculo) {
    // 1. Llenamos el texto (usamos textContent para mayor seguridad)
    document.getElementById('detalle-titulo').textContent = `${vehiculo.marca} ${vehiculo.modelo}`;
    document.getElementById('detalle-precio').textContent = vehiculo.costoDia.toFixed(2);
    document.getElementById('detalle-color').textContent = vehiculo.color;
    document.getElementById('detalle-clasificacion').textContent = vehiculo.clasificacion;
    document.getElementById('detalle-ocupantes').textContent = vehiculo.ocupantes;
    document.getElementById('detalle-transmicion').textContent = vehiculo.transmicion;

    // 2. Manejamos la imagen
    const imgElement = document.getElementById('detalle-imagen');
    const msjImgElement = document.getElementById('detalle-mensaje-img');

    if (vehiculo.urlImagen) {
        imgElement.src = vehiculo.urlImagen; // Le ponemos la ruta de la imagen
        imgElement.style.display = 'block';  // Hacemos visible la etiqueta <img>
        msjImgElement.style.display = 'none'; // Ocultamos el texto "Cargando imagen..."
    } else {
        msjImgElement.textContent = 'Imagen no disponible para este vehículo.';
    }

    const btnRenta = document.getElementById('realizarRenta');
    if (btnRenta) {
        btnRenta.addEventListener('click', () => {
            window.location.href = `${formulario}?id=${vehiculo.idVehiculo}`;
        });
    }
}