const API_LOGIN_RUL = '/api/empleados/login';

//Validamos el usuario
function validarForm() {
    const formLogin = document.getElementById('formLogin');
    const mensajeError = document.getElementById('mensaje-error');

    event.preventDefault();
    mensajeError.style.display = 'none';
    const formData = new FormData(formLogin);
    //Hacemos la peticion
    fetch(API_LOGIN_RUL, {
        method: 'POST',
        body: formData,
    })
    //Si la respuesta es correcta
    .then(response => response.json())
    .then(datos => {
        if(datos.exito === true){
            // Si el rol es admin carga la ruta del controlador
            if (datos.rol === 'ADMINISTRADOR') {
                window.location.href = '/admin/panel';

            } else if(datos.rol === 'EMPLEADO') {
                window.location.href = '/empleados/registrar-auto';

            } else {
                window.location.href = '/catalogo';
            }
        }else{
            mensajeError.innerText = datos.mensaje;
            mensajeError.style.display = 'block';
        }
    }).catch(error => {
        console.log("error en el form: " + error);
        mensajeError.innerText = "Error de conexion con la base de datos.";
        mensajeError.style.display = 'block';
    });
    return false;
}