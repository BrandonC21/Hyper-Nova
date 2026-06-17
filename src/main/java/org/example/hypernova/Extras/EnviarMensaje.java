package org.example.hypernova.Extras;

import java.util.Properties;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;

public class EnviarMensaje {
    private static final String usuario ="pruebasCorreoJavaDoc33@gmail.com";
    private static final String  pasword = "jcslahnhdogjeuwe";

    public void enviarCorreo(String to, String asunto, String cuerpo) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");// Serviddor SMTP de Gmail
        props.put("mail.smtp.port", "587"); //Puerto 587
        props.put("mail.smtp.auth", "true");//Autenticacion requerida
        props.put("mail.smtp.starttls.enable", "true"); //Activado de el cifrado TLS para mayor seguridad
        //Se crea la sesion del correo
        //Valida que las credenciales sean correctas y se establece la conexion con el servidor SMTP de Gmail
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, pasword);
            }

        });

        System.out.println("Sesión inicializada: ");
        try {
            //MinMensage objeto que representa el correo
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(usuario)); //Define el remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Define el destinatario
            mensaje.setSubject(asunto); // Asusnto del correo elctronico
            mensaje.setText(cuerpo); // Cuerpo del mensaje plano
            Transport.send(mensaje); // se envvia el mensaje si es correcto las credenciales
            System.out.println("Correo enviado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

}
