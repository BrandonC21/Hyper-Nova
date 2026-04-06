const API_VEHICULO = '/api/vehiculos';
const catalogo = '/catalogo';
const formVehiculo = document.getElementById('registrarVehiculo');
const btnvolver = document.getElementById('btn-volver');
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
    const clas = document.getElementById('clasificacion').value;
    //Validamos que el empleado agregue la clasificacion
    if(clas === "Selec") {
        alert('Seleccione la clasificacion');
        return;
    }
    //Validamos el numero de serie
    const numSerie = document.getElementById('numSerie').value;
    if (!numSerie) {
        alert('Debes ingresar el VIN');
        return;
    }
    const vehiculo = {
        marca: document.getElementById('marca').value,
        modelo: document.getElementById('modelo').value,
        clasificacion: clas,
        transmicion: document.getElementById('transmicion').value,
        ocupantes: parseInt(document.getElementById('ocupantes').value),
        placa: document.getElementById('placa').value,
        color: document.getElementById('color').value,
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