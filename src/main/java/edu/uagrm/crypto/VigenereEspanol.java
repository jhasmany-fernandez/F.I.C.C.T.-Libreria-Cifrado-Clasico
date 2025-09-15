package edu.uagrm.crypto;

public class VigenereEspanol {
    
    // Alfabeto español con Ñ
    private static final String ALFABETO_ESPANOL = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private static final String ALFABETO_INGLES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    // Método para cifrar o descifrar
    public static String procesar(String texto, String clave, boolean cifrar, boolean usarEspanol) {
        String alfabeto = usarEspanol ? ALFABETO_ESPANOL : ALFABETO_INGLES;
        int mod = alfabeto.length();
        
        StringBuilder resultado = new StringBuilder();
        clave = clave.toUpperCase();  // normalizamos clave
        int j = 0; // índice de la clave

        for (int i = 0; i < texto.length(); i++) {
            char ch = Character.toUpperCase(texto.charAt(i));

            // Si es letra del alfabeto
            if (alfabeto.indexOf(ch) != -1) {
                int posTexto = alfabeto.indexOf(ch);
                int posClave = alfabeto.indexOf(clave.charAt(j % clave.length()));

                int nuevaPos;
                if (cifrar) {
                    nuevaPos = (posTexto + posClave) % mod;
                } else {
                    nuevaPos = (posTexto - posClave + mod) % mod;
                }

                char nuevoChar = alfabeto.charAt(nuevaPos);
                resultado.append(nuevoChar);

                j++; // avanzar solo si era letra
            } else {
                // Si es espacio o símbolo, se conserva
                resultado.append(texto.charAt(i));
            }
        }

        return resultado.toString();
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        String mensaje = "CRIPTOGRAMAS RESUELTOS";
        String clave = "MEGA";

        System.out.println("=== ALFABETO ESPAÑOL CON MEGA ===");
        String cifradoEsp = procesar(mensaje, clave, true, true);
        String descifradoEsp = procesar(cifradoEsp, clave, false, true);

        System.out.println("Texto original: " + mensaje);
        System.out.println("Cifrado: " + cifradoEsp);
        System.out.println("Descifrado: " + descifradoEsp);
        System.out.println("Esperado: ÑVKPFSIRMPCSDIUUPOVOE");
        System.out.println("¿Coincide? " + cifradoEsp.replace(" ", "").equals("ÑVKPFSIRMPCSDIUUPOVOE"));
        
        System.out.println("\n=== ALFABETO ESPAÑOL CON MECA ===");
        String cifradoMeca = procesar(mensaje, "MECA", true, true);
        String descifradoMeca = procesar(cifradoMeca, "MECA", false, true);

        System.out.println("Texto original: " + mensaje);
        System.out.println("Cifrado: " + cifradoMeca);
        System.out.println("Descifrado: " + descifradoMeca);
    }
}