# 🐳 F.I.C.C.T. - Guía de Despliegue con Docker

## 📋 Problemas Resueltos

Esta versión incluye las siguientes correcciones críticas:

### ✅ **Problemas Solucionados:**
1. **Tomcat 10 → Tomcat 9**: Compatibilidad con `javax.servlet` (Java EE)
2. **Context Path**: APIs funcionan en `/` en lugar de `/crypto/`
3. **Health Check**: Corregido para usar endpoint correcto
4. **JavaScript**: Rutas de APIs actualizadas
5. **Nginx**: Configuración de assets corregida

---

## 🚀 Despliegue Rápido

### **Requisitos Previos:**
- Docker Engine 20.0+
- Docker Compose v2.0+
- Puertos 8080 y 80 disponibles

### **Comandos de Despliegue:**

```bash
# 1. Clonar/actualizar el repositorio
git clone <repository-url>
cd F.I.C.C.T.-Libreria-Cifrado-Clasico

# 2. Levantar aplicación (PRODUCCIÓN)
docker compose up ficct-crypto --build -d

# 3. Verificar que está funcionando
curl http://localhost:8080/cipher

# 4. Acceder a la interfaz web
# http://localhost:8080
```

### **Comandos Alternativos:**

```bash
# Desarrollo (con Jetty en puerto 9090)
docker compose --profile development up ficct-dev --build -d

# Con Nginx Proxy (puerto 80)
docker compose --profile proxy up --build -d

# Ver logs
docker compose logs ficct-crypto -f

# Parar servicios
docker compose down
```

---

## 🔧 Configuraciones Disponibles

### **1. Producción (Recomendado)**
- **Puerto**: 8080
- **Servidor**: Tomcat 9.0 + Java 21
- **Contexto**: `/` (raíz)
- **APIs**: `/cipher`, `/analyze`, `/alphabet`

```bash
docker compose up ficct-crypto --build -d
```

### **2. Desarrollo**
- **Puerto**: 9090
- **Servidor**: Jetty (hot reload)
- **Contexto**: `/crypto`

```bash
docker compose --profile development up ficct-dev --build -d
```

### **3. Con Proxy Nginx**
- **Puerto**: 80 (HTTP)
- **Funciones**: Load balancing, SSL ready, caching

```bash
docker compose --profile proxy up --build -d
```

---

## 🧪 Verificación de Funcionamiento

### **1. Health Check Automático**
Docker incluye health checks automáticos:
```bash
docker compose ps
# STATUS debe mostrar "healthy"
```

### **2. Pruebas de API**

```bash
# Listar algoritmos disponibles
curl http://localhost:8080/cipher

# Cifrar texto con César
curl -X POST http://localhost:8080/cipher \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "operation=encrypt&algorithm=caesar&text=HOLA&key=3"

# Respuesta esperada:
# {"success":true,"result":"KROD","message":"Texto cifrado exitosamente con César (Alfabeto english)"}
```

### **3. Interfaz Web**
1. Abrir: http://localhost:8080
2. Probar formulario de cifrado:
   - Algoritmo: César
   - Texto: "HOLA MUNDO"
   - Clave: 3
   - Resultado esperado: "KROD PXQGR"

---

## 📁 Estructura de Archivos Importantes

```
├── Dockerfile                 # ✅ Corregido (Tomcat 9)
├── docker-compose.yml         # ✅ Health check actualizado
├── nginx/nginx.conf           # ✅ Assets path corregido
└── src/main/webapp/assets/js/app.js  # ✅ APIs paths corregidos
```

---

## 🐛 Solución de Problemas

### **Error: "Unexpected token '<'"**
- **Causa**: Context path incorrecto en JavaScript
- **Solución**: ✅ Ya corregido en esta versión

### **Error: APIs devuelven 404**
- **Causa**: Incompatibilidad Tomcat 10 + javax.servlet
- **Solución**: ✅ Ya corregido (usa Tomcat 9)

### **Error: Health check failed**
- **Causa**: Health check apuntaba a `/crypto/`
- **Solución**: ✅ Ya corregido (apunta a `/`)

### **Error: Puertos ocupados**
```bash
# Verificar qué está usando el puerto
sudo netstat -tlnp | grep :8080

# Cambiar puerto en docker-compose.yml si es necesario
ports:
  - "8081:8080"  # Usar puerto 8081 externamente
```

---

## 📦 Algoritmos Incluidos

La aplicación incluye 8 algoritmos de cifrado clásico:

1. **César** - Cifrado por desplazamiento
2. **Vigenère** - Cifrado polialfabético
3. **Sustitución Simple** - Reemplazo monoalfabético
4. **Atbash** - Inversión de alfabeto
5. **Rail Fence** - Transposición en zigzag
6. **Columnar** - Transposición columnar
7. **Playfair** - Cifrado de pares
8. **XOR** - Operación lógica exclusiva

---

## 🔒 Notas de Seguridad

- Aplicación lista para producción
- Health checks configurados
- Logs persistentes en `./logs/`
- Sin credenciales hardcodeadas
- HTTPS ready (configurar SSL en nginx)

---

## 📞 Soporte

Si encuentras problemas:

1. Verificar logs: `docker compose logs ficct-crypto -f`
2. Verificar puertos: `docker compose ps`
3. Probar APIs directamente con curl
4. Revisar este documento

**¡El despliegue debería funcionar sin problemas en cualquier máquina con Docker!**