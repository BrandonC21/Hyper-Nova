const API_VEHICULO = '/api/vehiculos/disponibles'; 
const miEnlace = document.getElementById('accion-link')
const API_BUSQUEDA = '/api/vehiculos/buscar'; 
const login = '/login';
const btnBuscar = document.getElementById('btn-buscar');


    
document.addEventListener('DOMContentLoaded', () => {
    cargarCatalogo();
    if (miEnlace) {
        miEnlace.addEventListener('click', () => {
            window.location.href = login;
        });
    }
    if(btnBuscar){
        btnBuscar.addEventListener('click', () => {
            const input = document.getElementById('marca').value;
        if(input.trim() !== ''){
            busqueda(input);
        }else{
            alert("Debes ingresar el Modelo o Marca");
        }
      })
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

//Funcion para buscar por modelo o marca
async function busqueda(nombre){
    try{
        const responce = await fetch(`${API_BUSQUEDA}/${nombre}`);
        if(responce.ok){
          //Convertir la respuesta en json
          const vehiclos = await responce.json();
          console.log("Vehiculos encontrados"+vehiclos);
          imprimirVehiculo(vehiclos);  
        }
        else if(responce.status === 404){
            mostrarMensaje();
        }
    }catch{
        console.log("Error de conexio");
    }
}

function mostrarMensaje(){
    const contenedor = document.getElementById('catalogo-grid');
   contenedor.innerHTML = '';
   const tarjeta = `
   <div class="mensaje">
        <h2>El vehiculo ingresado no existe</h2>    
    </div>
    `;
    contenedor.innerHTML = tarjeta;     
    
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