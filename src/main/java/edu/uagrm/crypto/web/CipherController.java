package edu.uagrm.crypto.web;

import edu.uagrm.crypto.service.CryptoService;
import edu.uagrm.crypto.core.TextOptions;
import edu.uagrm.crypto.util.JsonUtil;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cipher")
public class CipherController extends HttpServlet {
    
    private CryptoService cryptoService;
    
    @Override
    public void init() throws ServletException {
        cryptoService = new CryptoService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Validar y obtener parámetros
        String validationError = validateRequestParameters(request);
        if (validationError != null) {
            response.getWriter().write(JsonUtil.createErrorResponse(validationError));
            return;
        }

        String operation = request.getParameter("operation");
        String algorithm = request.getParameter("algorithm");
        String text = request.getParameter("text").trim();
        String key = request.getParameter("key") != null ? request.getParameter("key").trim() : "";
        String alphabetType = request.getParameter("alphabet") != null ?
                             request.getParameter("alphabet") : "english";
        String customAlphabet = request.getParameter("customAlphabet");

        TextOptions options = createTextOptions(request);
        
        try {
            CryptoService.CryptoResult result;
            
            if ("encrypt".equals(operation)) {
                result = cryptoService.encrypt(algorithm, text, key, options, alphabetType, customAlphabet);
            } else if ("decrypt".equals(operation)) {
                result = cryptoService.decrypt(algorithm, text, key, options, alphabetType, customAlphabet);
            } else {
                response.getWriter().write(JsonUtil.createErrorResponse("Operación no válida"));
                return;
            }
            
            if (result.isSuccess()) {
                response.getWriter().write(JsonUtil.createSuccessResponse(result.getResult(), result.getMessage()));
            } else {
                response.getWriter().write(JsonUtil.createErrorResponse(result.getMessage()));
            }
            
        } catch (Exception e) {
            response.getWriter().write(JsonUtil.createErrorResponse("Error interno: " + e.getMessage()));
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, String> algorithms = cryptoService.getAvailableAlgorithms();
        StringBuilder json = new StringBuilder();
        json.append("{\"algorithms\":{");
        
        boolean first = true;
        for (Map.Entry<String, String> entry : algorithms.entrySet()) {
            if (!first) json.append(",");
            first = false;
            json.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
        }
        
        json.append("}}");
        response.getWriter().write(json.toString());
    }
    
    private TextOptions createTextOptions(HttpServletRequest request) {
        TextOptions options = new TextOptions();

        options.setPreserveCase("true".equals(request.getParameter("preserveCase")));
        options.setPreserveSpaces("true".equals(request.getParameter("preserveSpaces")));
        options.setPreserveAccents("true".equals(request.getParameter("preserveAccents")));
        options.setPreservePunctuation("true".equals(request.getParameter("preservePunctuation")));

        return options;
    }

    private String validateRequestParameters(HttpServletRequest request) {
        String operation = request.getParameter("operation");
        String algorithm = request.getParameter("algorithm");
        String text = request.getParameter("text");
        String key = request.getParameter("key");
        String alphabetType = request.getParameter("alphabet");

        // Validar operación
        if (operation == null || operation.trim().isEmpty()) {
            return "La operación es obligatoria";
        }
        if (!"encrypt".equals(operation) && !"decrypt".equals(operation)) {
            return "Operación no válida. Use 'encrypt' o 'decrypt'";
        }

        // Validar algoritmo
        if (algorithm == null || algorithm.trim().isEmpty()) {
            return "El algoritmo es obligatorio";
        }
        String[] validAlgorithms = {"caesar", "vigenere", "substitution", "atbash",
                                   "railfence", "columnar", "playfair", "xor"};
        boolean algorithmValid = false;
        for (String validAlg : validAlgorithms) {
            if (validAlg.equals(algorithm)) {
                algorithmValid = true;
                break;
            }
        }
        if (!algorithmValid) {
            return "Algoritmo no válido";
        }

        // Validar texto
        if (text == null || text.trim().isEmpty()) {
            return "El texto es obligatorio";
        }
        if (text.length() > 10000) {
            return "El texto no puede exceder 10,000 caracteres";
        }

        // Validar alfabeto
        if (alphabetType != null && !isValidAlphabetType(alphabetType)) {
            return "Tipo de alfabeto no válido. Use: 'english', 'spanish' o 'full_ascii'";
        }

        // Validar alfabeto personalizado si es necesario
        if ("custom".equals(alphabetType)) {
            String customAlphabet = request.getParameter("customAlphabet");
            if (customAlphabet == null || customAlphabet.trim().isEmpty()) {
                return "El alfabeto personalizado es obligatorio cuando se selecciona 'custom'";
            }
            if (customAlphabet.trim().length() < 2) {
                return "El alfabeto personalizado debe tener al menos 2 caracteres";
            }
            // Verificar caracteres únicos
            String alphabet = customAlphabet.trim().toUpperCase();
            for (int i = 0; i < alphabet.length(); i++) {
                if (alphabet.indexOf(alphabet.charAt(i), i + 1) != -1) {
                    return "El alfabeto personalizado no puede contener caracteres duplicados";
                }
            }
        }

        // Validar clave según el algoritmo
        return validateKeyForAlgorithm(algorithm, key, alphabetType);
    }

    private String validateKeyForAlgorithm(String algorithm, String key, String alphabetType) {
        if (key == null) key = "";
        key = key.trim();

        switch (algorithm) {
            case "caesar":
                if (key.isEmpty()) {
                    return "La clave es obligatoria para el cifrado César";
                }
                try {
                    int caesarKey = Integer.parseInt(key);
                    if (caesarKey < 0 || caesarKey > 25) {
                        return "La clave César debe ser un número entre 0 y 25";
                    }
                } catch (NumberFormatException e) {
                    return "La clave César debe ser un número válido";
                }
                break;

            case "vigenere":
            case "playfair":
            case "columnar":
                if (key.isEmpty()) {
                    return "La clave es obligatoria para " + algorithm;
                }
                String validChars = "spanish".equals(alphabetType) ?
                    "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ" : "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                for (char c : key.toUpperCase().toCharArray()) {
                    if (validChars.indexOf(c) == -1) {
                        String alphabetName = "spanish".equals(alphabetType) ? "español" : "inglés";
                        return "La clave debe contener solo letras válidas del alfabeto " + alphabetName;
                    }
                }
                break;

            case "substitution":
                if (key.isEmpty()) {
                    return "La clave es obligatoria para la sustitución simple";
                }
                int expectedLength = "spanish".equals(alphabetType) ? 27 : 26;
                if (key.length() != expectedLength) {
                    return "La clave de sustitución debe tener exactamente " + expectedLength + " caracteres";
                }
                // Validar caracteres únicos
                String subValidChars = "spanish".equals(alphabetType) ?
                    "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ" : "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                boolean[] used = new boolean[subValidChars.length()];
                for (char c : key.toUpperCase().toCharArray()) {
                    int index = subValidChars.indexOf(c);
                    if (index == -1) {
                        return "La clave contiene caracteres no válidos para el alfabeto seleccionado";
                    }
                    if (used[index]) {
                        return "La clave de sustitución no puede contener caracteres duplicados";
                    }
                    used[index] = true;
                }
                break;

            case "railfence":
                if (key.isEmpty()) {
                    return "El número de rieles es obligatorio";
                }
                try {
                    int rails = Integer.parseInt(key);
                    if (rails < 2 || rails > 100) {
                        return "El número de rieles debe estar entre 2 y 100";
                    }
                } catch (NumberFormatException e) {
                    return "El número de rieles debe ser un número válido";
                }
                break;

            case "xor":
                if (key.isEmpty()) {
                    return "La clave es obligatoria para el cifrado XOR";
                }
                break;

            case "atbash":
                // Atbash no requiere clave
                break;

            default:
                return "Algoritmo no reconocido para validación de clave";
        }

        return null; // Sin errores
    }

    private boolean isValidAlphabetType(String alphabetType) {
        return "english".equals(alphabetType) ||
               "spanish".equals(alphabetType) ||
               "full_ascii".equals(alphabetType);
    }
}