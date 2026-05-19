const API_VEHICULO = '/api/vehiculos';
const catalogo = '/catalogo';
const formVehiculo = document.getElementById('registrarVehiculo');
const btnvolver = document.getElementById('btn-volver');

//Cargamos el select de marcas 
document.addEventListener('DOMContentLoaded', () => {
    //Marcas de automoviles
    const selectMarca = document.getElementById('marca');
    for (const marca in marcas) {
        const option  = document.createElement('option');
        option.value = marca; 
        option.textContent = marcas[marca];
        selectMarca.appendChild(option);
    }
    //Evento para llenar el select de modelos dependiendo de la marca seleccionada
    const selectModelo = document.getElementById('modelo');
    const selectVersion = document.getElementById('version');
    selectMarca.addEventListener('change', () => {
        const marcaSeleccionada = selectMarca.value;
        llenarSelectModelos(marcaSeleccionada,selectModelo, selectVersion);
      
    });
   

});


btnvolver.addEventListener('click', () => {
        window.location.href = catalogo;
});

formVehiculo.addEventListener('submit', (evento) => {
    evento.preventDefault();
    enviarVehiculo(evento);
});

//Agregar Vehiculos
async function enviarVehiculo(evento) {
    const form = evento.target;
    const marcaC = document.getElementById('marca').value;
    const clas = document.getElementById('clasificacion').value;
    const colorV = document.getElementById('color').value
    const modeloC = document.getElementById('modelo').value;
    const versionC = document.getElementById('version').value;

    //Validamos que el empleado agregue el modelo
    if(modeloC === "Select"){
        alert('Seleccione el modelo del vehiculo'); 
        return; 
    }
    //Validamos que el empleado agregue la marca
    if(marcaC === "Select"){
        alert('Seleccione la marca del vehiculo'); 
        return; 
    }
    //Validamos que el empleado agregue la clasificacion
    if(clas === "Selec") {
        alert('Seleccione la clasificacion del vehiculo');
        return;
    }
    //Validacion de que el usuario agrege el color del vehiculo
    if(colorV === "Selec"){
        alert('Debes Seleccionar Un Color')
        return;
    }
    //Validamos el numero de serie
    const numSerie = document.getElementById('numSerie').value;
    if (!numSerie) {
        alert('Debes ingresar el VIN');
        return;
    }
    const vehiculo = {
        marca: marcaC,
        modelo: modeloC,
        version: versionC,
        clasificacion: clas,
        transmicion: document.getElementById('transmicion').value,
        ocupantes: parseInt(document.getElementById('ocupantes').value),
        placa: document.getElementById('placa').value,
        //color: document.getElementById('color').value,
        color:colorV,
        anio: document.getElementById('anio').value,
        costoDia: document.getElementById('costoDia').value,
        numSerie: numSerie
    };
    const urlImagen = document.getElementById('urlImagen');
    const archivo = urlImagen.files[0];
    if(!archivo) {
        alert('Debes ingresar un archivo');
        return;
    }
    const formData = new FormData();
    formData.append('vehiculo', new Blob([JSON.stringify(vehiculo)], {type: 'application/json'}));
    formData.append('imagen',archivo);
    try {
        const res = await fetch(API_VEHICULO, {
            method: 'POST',
            body: formData,
        });

        if(res.ok){
            alert('Vehiculo Registrado Con exito');
            evento.target.reset();
        } else {
            const errorMsg = await res.text();
            alert('Error al registrar el vehiculo: ' + errorMsg);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("No se pudo conectar con el servidor.");
    }

}
