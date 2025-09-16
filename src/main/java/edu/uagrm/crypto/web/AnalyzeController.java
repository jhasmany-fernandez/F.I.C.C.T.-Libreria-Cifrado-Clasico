package edu.uagrm.crypto.web;

import edu.uagrm.crypto.service.CryptoService;
import edu.uagrm.crypto.analysis.CaesarBreaker;
import edu.uagrm.crypto.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/analyze")
public class AnalyzeController extends HttpServlet {
    
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
        
        // Validar parámetros
        String validationError = validateAnalysisParameters(request);
        if (validationError != null) {
            response.getWriter().write(JsonUtil.createErrorResponse(validationError));
            return;
        }

        String analysisType = request.getParameter("type");
        String text = request.getParameter("text").trim();
        
        try {
            if ("frequency".equals(analysisType)) {
                handleFrequencyAnalysis(text, response);
            } else if ("caesar".equals(analysisType)) {
                handleCaesarBreak(text, response);
            } else {
                response.getWriter().write(JsonUtil.createErrorResponse("Tipo de análisis no válido"));
            }
            
        } catch (Exception e) {
            response.getWriter().write(JsonUtil.createErrorResponse("Error interno: " + e.getMessage()));
        }
    }
    
    private void handleFrequencyAnalysis(String text, HttpServletResponse response) throws IOException {
        CryptoService.AnalysisResult result = cryptoService.analyzeFrequencies(text);
        
        if (result.isSuccess()) {
            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            data.put("frequencies", result.getFrequencies());
            data.put("indexOfCoincidence", result.getIndexOfCoincidence());
            data.put("report", result.getReport());
            data.put("message", result.getMessage());
            
            response.getWriter().write(JsonUtil.toJson(data));
        } else {
            response.getWriter().write(JsonUtil.createErrorResponse(result.getMessage()));
        }
    }
    
    private void handleCaesarBreak(String text, HttpServletResponse response) throws IOException {
        CryptoService.CaesarBreakResult result = cryptoService.breakCaesar(text);
        
        if (result.isSuccess()) {
            Map<String, Object> data = new HashMap<>();
            data.put("success", true);
            data.put("message", result.getMessage());
            
            CaesarBreaker.CaesarCandidate best = result.getBestCandidate();
            if (best != null) {
                Map<String, Object> bestData = new HashMap<>();
                bestData.put("shift", best.getShift());
                bestData.put("text", best.getText());
                bestData.put("score", best.getScore());
                data.put("bestCandidate", bestData);
            }
            
            Map<String, Object> candidates = new HashMap<>();
            int count = 0;
            for (CaesarBreaker.CaesarCandidate candidate : result.getAllCandidates()) {
                if (count >= 5) break;
                Map<String, Object> candidateData = new HashMap<>();
                candidateData.put("shift", candidate.getShift());
                candidateData.put("text", candidate.getText());
                candidateData.put("score", candidate.getScore());
                candidates.put("candidate" + count, candidateData);
                count++;
            }
            data.put("topCandidates", candidates);
            
            response.getWriter().write(JsonUtil.toJson(data));
        } else {
            response.getWriter().write(JsonUtil.createErrorResponse(result.getMessage()));
        }
    }

    private String validateAnalysisParameters(HttpServletRequest request) {
        String analysisType = request.getParameter("type");
        String text = request.getParameter("text");

        // Validar tipo de análisis
        if (analysisType == null || analysisType.trim().isEmpty()) {
            return "El tipo de análisis es obligatorio";
        }
        if (!"frequency".equals(analysisType) && !"caesar".equals(analysisType)) {
            return "Tipo de análisis no válido. Use 'frequency' o 'caesar'";
        }

        // Validar texto
        if (text == null || text.trim().isEmpty()) {
            return "El texto es obligatorio";
        }

        text = text.trim();

        // Validaciones específicas según el tipo de análisis
        if ("frequency".equals(analysisType)) {
            if (text.length() < 10) {
                return "El texto debe tener al menos 10 caracteres para un análisis de frecuencias significativo";
            }
            if (text.length() > 50000) {
                return "El texto no puede exceder 50,000 caracteres para análisis de frecuencias";
            }
        } else if ("caesar".equals(analysisType)) {
            if (text.length() < 20) {
                return "El texto debe tener al menos 20 caracteres para un criptoanálisis efectivo";
            }
            if (text.length() > 10000) {
                return "El texto no puede exceder 10,000 caracteres para criptoanálisis";
            }
        }

        return null; // Sin errores
    }
}