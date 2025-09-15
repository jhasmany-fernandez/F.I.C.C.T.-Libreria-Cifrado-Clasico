package edu.uagrm.crypto.web;

import edu.uagrm.crypto.core.Alphabet;
import edu.uagrm.crypto.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/alphabet")
public class AlphabetController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> data = new HashMap<>();
        data.put("spanish", Alphabet.SPANISH);
        data.put("english", Alphabet.ENGLISH);
        data.put("numbers", Alphabet.NUMBERS);
        
        response.getWriter().write(JsonUtil.toJson(data));
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String customAlphabet = request.getParameter("alphabet");
        
        if (customAlphabet == null || customAlphabet.isEmpty()) {
            response.getWriter().write(JsonUtil.createErrorResponse("Alfabeto no puede estar vacío"));
            return;
        }
        
        try {
            Alphabet alphabet = new Alphabet(customAlphabet);
            
            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            data.put("alphabet", alphabet.getChars());
            data.put("size", alphabet.size());
            data.put("message", "Alfabeto personalizado creado exitosamente");
            
            response.getWriter().write(JsonUtil.toJson(data));
            
        } catch (Exception e) {
            response.getWriter().write(JsonUtil.createErrorResponse("Error creando alfabeto: " + e.getMessage()));
        }
    }
}