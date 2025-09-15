package edu.uagrm.crypto.web;

import edu.uagrm.crypto.service.CryptoService;
import edu.uagrm.crypto.core.TextOptions;
import edu.uagrm.crypto.util.JsonUtil;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        
        String operation = request.getParameter("operation");
        String algorithm = request.getParameter("algorithm");
        String text = request.getParameter("text");
        String key = request.getParameter("key");
        String alphabetType = request.getParameter("alphabet");
        
        // Debug temporal
        System.out.println("DEBUG - operation: " + operation);
        System.out.println("DEBUG - algorithm: " + algorithm);
        System.out.println("DEBUG - text: " + text);
        System.out.println("DEBUG - key: " + key);
        System.out.println("DEBUG - alphabet: " + alphabetType);
        
        if (operation == null || algorithm == null || text == null || key == null) {
            response.getWriter().write(JsonUtil.createErrorResponse("Parámetros faltantes - operation: " + operation + ", algorithm: " + algorithm + ", text: " + text + ", key: " + key));
            return;
        }
        
        // Permitir valores vacíos, pero convertir null a vacío
        text = text != null ? text : "";
        key = key != null ? key : "";
        alphabetType = alphabetType != null ? alphabetType : "spanish";
        
        TextOptions options = createTextOptions(request);
        
        try {
            CryptoService.CryptoResult result;
            
            if ("encrypt".equals(operation)) {
                result = cryptoService.encrypt(algorithm, text, key, options, alphabetType);
            } else if ("decrypt".equals(operation)) {
                result = cryptoService.decrypt(algorithm, text, key, options, alphabetType);
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
}