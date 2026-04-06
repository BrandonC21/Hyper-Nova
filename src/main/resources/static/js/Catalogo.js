const API_VEHICULO = '/api/vehiculos/disponibles'; // Endpoint para obtener vehículos disponibles
const login = '/login';
const miEnlace = document.getElementById('accion-link')
document.addEventListener('DOMContentLoaded', () => {
    cargarCatalogo();
    if (miEnlace) {
        miEnlace.addEventListener('click', () => {
            window.location.href = login;
        });
    }else{
        console.log('No se encuentra encuentra el index');
    }
});

async function cargarCatalogo() {
    try {
        //Se realiza la peticion fetc
        const responce = await fetch(API_VEHICULO);
        if (responce.ok) {
        //Convertir la respuesta en un json
            const vehiculos = await responce.json();
            imprimirVehiculo(vehiculos);
        }else{
            console.log('Error en la vehiculos');
        }
    }catch (error) {
        console.log("Error al traer los vehiculos",error);
    }
}

function imprimirVehiculo(vehiculos) {
    const contenedor = document.getElementById('catalogo-grid');
    //Limpiar el contenedor
    contenedor.innerHTML = '';
    
    vehiculos.forEach((vehiculo) => {
        console.log("Datos del vehiculo:", vehiculo);
        const tarjeta = ` 
            <div class="card-vehiculo">
                <a href="/detalle-vehiculo.html?id=${vehiculo.idVehiculo}" class="card-link">
                    <img src="${vehiculo.urlImagen}" alt="Vehículo" class="card-imagen">
                    <div class="card-info">
                        <h3> ${vehiculo.marca} ${vehiculo.modelo} </h3>
                        <p>Modelo ${vehiculo.anio} • Numero de ocupantes ${vehiculo.ocupantes}</p>       
                        <p>Kilometraje ilimitado • Transmición ${vehiculo.transmicion}</p>
                        <p>Categoria ${vehiculo.clasificacion}</p>
                        
                        <div class="precio">
                            <h3>Precio desde por dia</h3>
                            <span>$ ${vehiculo.costoDia.toFixed(2)}</span>
                        </div>
                    </div>
                </a>
            </div>
        `;
        contenedor.innerHTML += tarjeta;
    });
}