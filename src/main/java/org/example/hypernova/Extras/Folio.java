package org.example.hypernova.Extras;

import java.security.SecureRandom;

public class Folio {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    //Generar un folio aleatorio de 5 caracteres
    public static String generarFolio() {
        StringBuilder folio = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(CARACTERES.length());
            folio.append(CARACTERES.charAt(index));
        }
        return folio.toString();
    }
}
