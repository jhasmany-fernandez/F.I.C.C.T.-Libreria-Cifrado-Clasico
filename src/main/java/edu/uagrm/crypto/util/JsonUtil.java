package edu.uagrm.crypto.util;

import java.util.Map;

public class JsonUtil {
    
    public static String toJson(Map<String, Object> data) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        
        boolean first = true;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            json.append("\"").append(entry.getKey()).append("\":");
            json.append(formatValue(entry.getValue()));
        }
        
        json.append("}");
        return json.toString();
    }
    
    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "\"" + escapeString((String) value) + "\"";
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> mapValue = (Map<String, Object>) value;
            return toJson(mapValue);
        } else {
            return "\"" + escapeString(value.toString()) + "\"";
        }
    }
    
    private static String escapeString(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    public static String createSuccessResponse(String result, String message) {
        return "{"
                + "\"success\":true,"
                + "\"result\":\"" + escapeString(result) + "\","
                + "\"message\":\"" + escapeString(message) + "\""
                + "}";
    }
    
    public static String createErrorResponse(String message) {
        return "{"
                + "\"success\":false,"
                + "\"message\":\"" + escapeString(message) + "\""
                + "}";
    }
}