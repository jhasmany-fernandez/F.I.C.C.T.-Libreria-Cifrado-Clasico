#!/bin/bash

# Script de despliegue para F.I.C.C.T. - Librería de Cifrado Clásico
# Autor: F.I.C.C.T. UAGRM
# Versión: 1.0

set -e  # Salir si cualquier comando falla

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Función para imprimir mensajes con colores
print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Verificar que Docker esté instalado
check_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker no está instalado. Por favor instale Docker primero."
        exit 1
    fi

    if ! command -v docker-compose &> /dev/null; then
        print_error "Docker Compose no está instalado. Por favor instale Docker Compose primero."
        exit 1
    fi

    print_success "Docker y Docker Compose están disponibles"
}

# Función para construir la imagen
build_image() {
    print_info "Construyendo imagen Docker..."
    docker build -t ficct/crypto-classic:latest .
    print_success "Imagen construida exitosamente"
}

# Función para ejecutar tests
run_tests() {
    print_info "Ejecutando tests en contenedor..."
    docker run --rm ficct/crypto-classic:latest /bin/bash -c "cd /app && mvn test"
    print_success "Tests ejecutados exitosamente"
}

# Función para despliegue en producción
deploy_production() {
    print_info "Desplegando en modo producción..."
    docker-compose down 2>/dev/null || true
    docker-compose up -d ficct-crypto
    print_success "Aplicación desplegada en modo producción"
    print_info "Aplicación disponible en: http://localhost:8080/crypto"
}

# Función para despliegue en desarrollo
deploy_development() {
    print_info "Desplegando en modo desarrollo..."
    docker-compose --profile development down 2>/dev/null || true
    docker-compose --profile development up -d ficct-dev
    print_success "Aplicación desplegada en modo desarrollo"
    print_info "Aplicación disponible en: http://localhost:9090/crypto"
}

# Función para despliegue con proxy
deploy_with_proxy() {
    print_info "Desplegando con Nginx proxy..."
    docker-compose --profile proxy down 2>/dev/null || true
    docker-compose --profile proxy up -d
    print_success "Aplicación desplegada con proxy"
    print_info "Aplicación disponible en: http://localhost/crypto"
}

# Función para detener servicios
stop_services() {
    print_info "Deteniendo servicios..."
    docker-compose down
    docker-compose --profile development down 2>/dev/null || true
    docker-compose --profile proxy down 2>/dev/null || true
    print_success "Servicios detenidos"
}

# Función para limpiar recursos
cleanup() {
    print_info "Limpiando recursos Docker..."
    docker-compose down -v 2>/dev/null || true
    docker system prune -f
    print_success "Recursos limpiados"
}

# Función para mostrar logs
show_logs() {
    local service=${1:-ficct-crypto}
    print_info "Mostrando logs del servicio: $service"
    docker-compose logs -f $service
}

# Función para mostrar estado
show_status() {
    print_info "Estado de los servicios:"
    docker-compose ps
}

# Función de ayuda
show_help() {
    echo "Script de despliegue para F.I.C.C.T. - Librería de Cifrado Clásico"
    echo ""
    echo "Uso: $0 [COMANDO]"
    echo ""
    echo "Comandos disponibles:"
    echo "  build       - Construir imagen Docker"
    echo "  test        - Ejecutar tests en contenedor"
    echo "  prod        - Desplegar en modo producción (puerto 8080)"
    echo "  dev         - Desplegar en modo desarrollo (puerto 9090)"
    echo "  proxy       - Desplegar con Nginx proxy (puerto 80)"
    echo "  stop        - Detener todos los servicios"
    echo "  cleanup     - Limpiar recursos Docker"
    echo "  logs [svc]  - Mostrar logs (servicio opcional)"
    echo "  status      - Mostrar estado de servicios"
    echo "  help        - Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0 build && $0 prod    # Construir y desplegar"
    echo "  $0 dev                 # Modo desarrollo"
    echo "  $0 logs ficct-crypto   # Ver logs de producción"
    echo "  $0 stop && $0 cleanup  # Limpiar todo"
}

# Función principal
main() {
    check_docker

    case ${1:-help} in
        build)
            build_image
            ;;
        test)
            run_tests
            ;;
        prod|production)
            build_image
            deploy_production
            ;;
        dev|development)
            deploy_development
            ;;
        proxy)
            build_image
            deploy_with_proxy
            ;;
        stop)
            stop_services
            ;;
        cleanup)
            cleanup
            ;;
        logs)
            show_logs $2
            ;;
        status)
            show_status
            ;;
        help|--help|-h)
            show_help
            ;;
        *)
            print_error "Comando no reconocido: $1"
            show_help
            exit 1
            ;;
    esac
}

# Ejecutar función principal con todos los argumentos
main "$@"