<%@ include file="layout/header.jspf" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-danger text-white">
                <h3><i class="bi bi-exclamation-triangle"></i> Error</h3>
            </div>
            <div class="card-body">
                <h4>¡Ups! Algo salió mal</h4>
                
                <% 
                    String errorCode = String.valueOf(response.getStatus());
                    String errorMessage = "";
                    
                    switch(errorCode) {
                        case "404":
                            errorMessage = "La página que buscas no existe.";
                            break;
                        case "500":
                            errorMessage = "Error interno del servidor. Por favor, inténtalo más tarde.";
                            break;
                        default:
                            errorMessage = "Se ha producido un error inesperado.";
                    }
                %>
                
                <div class="alert alert-danger" role="alert">
                    <h5>Error <%= errorCode %></h5>
                    <p><%= errorMessage %></p>
                </div>
                
                <div class="mt-4">
                    <h6>¿Qué puedes hacer?</h6>
                    <ul>
                        <li>Verificar que la URL esté escrita correctamente</li>
                        <li>Regresar a la <a href="${pageContext.request.contextPath}/">página principal</a></li>
                        <li>Intentar más tarde si es un error del servidor</li>
                    </ul>
                </div>
                
                <div class="mt-4">
                    <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                        <i class="bi bi-house"></i> Volver al Inicio
                    </a>
                    <button onclick="history.back()" class="btn btn-secondary">
                        <i class="bi bi-arrow-left"></i> Página Anterior
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="layout/footer.jspf" %>