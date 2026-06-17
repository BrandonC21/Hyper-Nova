//endopoint
const API_CONTRATOS = '/api/contratos/folio';
const API_CANCELAR ='/api/contratos/cancelar';
const API_BUSCAR = '/api/clientes/obtener';
const btnBuscar = document.getElementById('btn-buscar');
const btnCancelar  = document.getElementById('btn-cancelar');
const btnRecuperar = document.getElementById('btn-recuperar');
const catalogo = document.getElementById('v-catalogo');
const vista = '/catalogo';
let idContratoActual = null;

document.addEventListener('DOMContentLoaded', () => {
    if(btnBuscar){
        btnBuscar.addEventListener('click', function () {
            const folio = document.getElementById('folio').value;
            if(folio.trim() !== ''){
                obtnerContratoFolio(folio);
                console.log("Precionado")
            }else{
                alert("Debes ingresar el folio")
            }
        });
    }
    if(btnCancelar){
        btnCancelar.addEventListener('click', function () {
            if (!idContratoActual) {
                alert("No hay ningún contrato seleccionado para cancelar.");
                return;
            }

            if (confirm("¿Estás seguro de que deseas cancelar esta reservación? En base a las políticas, se cobrará el depósito.")) {
                cancelarContrato(idContratoActual);
            }
        });
    }
    if(btnRecuperar){
        btnRecuperar.addEventListener('click', function (){
            const RFC = document.getElementById('RFC').value;
            if(RFC.trim() !== ''){
                buscarFolio(RFC);
                console.log("Precionado")
            }else{
                alert("Debes ingresar el RFC")
            }
        });
    }
    if(catalogo){
        catalogo.addEventListener('click', () =>{
           window.location.href=vista;
        });
    }
});
async function obtnerContratoFolio(folio){
    try{
        const  responce = await fetch(`${API_CONTRATOS}/${folio}`);
        if (responce.ok){
            const contrato = await responce.json();
            mostarInformacionV(contrato);
            document.getElementById('informacion').style.display = 'block';
            idContratoActual = contrato.idContrato;
        }
        else if(responce.status === 404){
            alert("El Folio es incorrecto")
        }else{
            console.log("Error en la busqueda")
        }
    }catch (error){
        console.log("Error al hacer la peticion")
    }
}
async function cancelarContrato(idContrato){
    try{
        const responce = await fetch(`${API_CANCELAR}/${idContrato}`, {
            method: 'PUT'
        });

        if (responce.ok) {
            alert("El contrato ha sido cancelado exitosamente, se le enviara por correo su nota.");
            location.reload();

        } else {
            const errorMensaje = await responce.text();
            alert("Error al cancelar: " + errorMensaje);
        }
    }catch (error){
        console.log("Error al hacer la peticion de cancelacion");
    }
}

async function buscarFolio(RFC){
    try {
        const response = await fetch(`${API_BUSCAR}/${RFC}`);
        if (response.ok){
            alert("Se le ah enviado a su correo");
        } else if(response.status === 404){
            alert("Es incorrecto el rfc o no cuenta con un contrato");
        }
    } catch (error){
        console.log("Error", error);
    }
}
function mostarInformacionV(contrato){
    //Detalle de la imegen
    const img = document.getElementById('detalle-imagen');
    const msjImgElement = document.getElementById('detalle-mensaje-img');
    if(contrato.vehiculo.urlImagen){
        img.src = contrato.vehiculo.urlImagen;
        img.style.display = 'block';
        msjImgElement.style.display = 'none';
    }else {
        msjImgElement.textContent = 'Imagen no disponible para este vehículo.';
    }
    //informacion del vehiculo
    document.getElementById('estado').textContent = contrato.estadoContrato;
    document.getElementById('vMarca').textContent = contrato.vehiculo.marca;
    document.getElementById('vModelo').textContent = contrato.vehiculo.modelo;
    document.getElementById('vClasificacion').textContent = contrato.vehiculo.clasificacion;
    document.getElementById('Version').textContent = contrato.vehiculo.version;
    document.getElementById('vAnio').textContent = contrato.vehiculo.anio;
    document.getElementById('vPlaca').textContent = contrato.vehiculo.placa;

    //Fechas de entrega
    document.getElementById('fecha-inicio').textContent = contrato.fechaInicio;
    document.getElementById('fecha-final').textContent = contrato.fechaFin;


    //Datos del seguro
    document.getElementById('sCobertura').textContent = contrato.seguro.tipoSeguro;
    document.getElementById('sCosto').textContent = contrato.seguro.costo;
    document.getElementById('vPrecio').textContent = contrato.vehiculo.costoDia;
    document.getElementById('cTotal').textContent = contrato.costoFinal;

}





