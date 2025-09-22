# F.I.C.C.T. - Librería de Cifrado Clásico

**Facultad de Ingeniería en Ciencias de la Computación y Telecomunicaciones**  
**Universidad Autónoma Gabriel René Moreno**

## ✅ **Proyecto Completamente Implementado**

Una librería web completa para el estudio y aplicación de algoritmos de cifrado clásicos, desarrollada con tecnologías Java Enterprise y diseño moderno.

## 🛠️ **Tecnologías Utilizadas**

* **Java**: OpenJDK 21.0.8 (LTS)
* **Build Tool**: Apache Maven 3.9.11
* **Servidor Web**: Apache Tomcat/10.1.45 + Eclipse Jetty 11.0.15
* **Frontend**: Bootstrap 5, JavaScript ES6, CSS3
* **Testing**: JUnit 5 para pruebas unitarias

## 📊 **Estado del Proyecto**

### **✅ Estructura Completa Implementada**

- **22 archivos Java** creados con implementación completa
- **4 archivos JSP** para la interfaz web
- Estructura Maven estándar para aplicación web WAR
- **100% funcional y listo para despliegue**

## 🔧 **Componentes Principales Implementados**

### **🏗️ Core (Base del Sistema)**
- `CipherAlgorithm.java` - Interfaz común para todos los cifrados
- `Alphabet.java` - Manejo de alfabetos español/inglés/personalizado
- `TextOptions.java` - Configuración de procesamiento de texto  
- `TextPreprocessor.java` - Normalización y limpieza de texto
- `Keys.java` - Validación y manejo de claves

### **🔐 Algoritmos de Cifrado Implementados**
- **CaesarCipher** - Cifrado César con desplazamiento
- **VigenereCipher** - Cifrado Vigenère polialfabético
- **SimpleSubstitutionCipher** - Sustitución monoalfabética
- **AtbashCipher** - Cifrado Atbash por inversión
- **RailFenceCipher** - Cifrado en zigzag
- **ColumnarTranspositionCipher** - Transposición columnar
- **PlayfairCipher** - Cifrado Playfair de pares
- **XorCipher** - Operación XOR

### **📊 Herramientas de Análisis y Configuración**
- **FrequencyAnalyzer** - Análisis de frecuencias con índice de coincidencia
- **Custom Alphabets** - Soporte para configuración de alfabetos personalizados (números, símbolos, espacios)

### **🌐 Controladores Web (Servlets)**
- **CipherController** (`/cipher`) - Cifrado/descifrado via POST/GET
- **AnalyzeController** (`/analyze`) - Análisis de frecuencias y herramientas de análisis
- **AlphabetController** (`/alphabet`) - Gestión de alfabetos

### **⚙️ Servicios**
- **CryptoService** - Orquestador principal del sistema
- **JsonUtil** - Utilidades para respuestas JSON

### **🎨 Interfaz Web Completa**
- **index.jsp** - Interfaz principal con formularios Bootstrap 5
- **header.jspf/footer.jspf** - Plantillas reutilizables
- **error.jsp** - Página de errores personalizada
- **app.css** - Estilos personalizados modernos
- **app.js** - JavaScript para interactividad AJAX

### **🧪 Tests Unitarios**
- **CaesarCipherTest** - Pruebas del cifrado César
- **CryptoServiceTest** - Pruebas del servicio principal

## 🚀 **Características del Sistema**

1. **Interfaz Web Moderna** - Bootstrap 5, responsive, intuitiva
2. **8 Algoritmos de Cifrado** implementados y funcionales  
3. **Análisis Criptográfico** - Frecuencias e índice de coincidencia
4. **Configuración de Alfabetos** - Soporte para alfabetos personalizados con números, símbolos y espacios
5. **Configuración de Texto** - Preservar espacios, acentos, mayúsculas
6. **API REST** - Endpoints JSON para integración
7. **Validación Robusta** - Claves y parámetros validados
8. **Manejo de Errores** - Páginas y respuestas de error personalizadas

## 📁 **Estructura de Directorios Implementada**

```bash
crypto-classic-jsp/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/edu/uagrm/crypto/
│  │  │  ├─ algorithms/              # ✅ 8 algoritmos implementados
│  │  │  │  ├─ CaesarCipher.java
│  │  │  │  ├─ VigenereCipher.java
│  │  │  │  ├─ ColumnarTranspositionCipher.java
│  │  │  │  ├─ SimpleSubstitutionCipher.java
│  │  │  │  ├─ AtbashCipher.java
│  │  │  │  ├─ PlayfairCipher.java
│  │  │  │  ├─ RailFenceCipher.java
│  │  │  │  └─ XorCipher.java
│  │  │  ├─ core/                    # ✅ Clases base implementadas
│  │  │  │  ├─ CipherAlgorithm.java
│  │  │  │  ├─ Alphabet.java
│  │  │  │  ├─ TextOptions.java
│  │  │  │  ├─ TextPreprocessor.java
│  │  │  │  └─ Keys.java
│  │  │  ├─ analysis/                # ✅ Análisis criptográfico
│  │  │  │  ├─ FrequencyAnalyzer.java
│  │  │  │  └─ AlphabetConfiguration.java
│  │  │  ├─ service/                 # ✅ Servicios principales
│  │  │  │  └─ CryptoService.java
│  │  │  ├─ web/                     # ✅ Controladores web
│  │  │  │  ├─ CipherController.java
│  │  │  │  ├─ AnalyzeController.java
│  │  │  │  └─ AlphabetController.java
│  │  │  └─ util/                    # ✅ Utilidades
│  │  │     └─ JsonUtil.java
│  │  ├─ resources/                  # ✅ Recursos
│  │  │  └─ messages.properties
│  │  └─ webapp/                     # ✅ Interfaz web completa
│  │     ├─ WEB-INF/
│  │     │  ├─ web.xml              # ✅ Servlet 4.0
│  │     │  └─ jsp/
│  │     │     ├─ layout/
│  │     │     │  ├─ header.jspf    # ✅ Plantillas
│  │     │     │  └─ footer.jspf
│  │     │     └─ error.jsp         # ✅ Página de errores
│  │     ├─ index.jsp               # ✅ UI principal
│  │     └─ assets/
│  │        ├─ css/app.css          # ✅ Estilos modernos
│  │        └─ js/app.js            # ✅ JavaScript AJAX
│  └─ test/java/edu/uagrm/crypto/   # ✅ Tests unitarios
│     ├─ algorithms/CaesarCipherTest.java
│     └─ service/CryptoServiceTest.java
```

## 🐳 **Despliegue con Docker (RECOMENDADO)**

### **📋 Prerrequisitos para Docker**
- Docker Engine 20.0+
- Docker Compose v2.0+
- Puertos 8080 disponible

### **🚀 ESCENARIO 1: Clonar desde GitHub y Levantar con Docker**

#### **Paso 1: Clonar el Repositorio**
```bash
# Abrir terminal y navegar al directorio deseado
cd /home/usuario/proyectos/  # Cambiar por tu directorio preferido

# Clonar el repositorio (reemplazar con la URL real)
git clone https://github.com/usuario/F.I.C.C.T.-Libreria-Cifrado-Clasico.git

# Navegar al directorio del proyecto
cd F.I.C.C.T.-Libreria-Cifrado-Clasico
```

#### **Paso 2: Verificar Estructura del Proyecto**
```bash
# Verificar que los archivos principales existen
ls -la

# Deberías ver:
# - Dockerfile
# - docker-compose.yml
# - pom.xml
# - src/
# - README.md
```

#### **Paso 3: Verificar Docker**
```bash
# Verificar que Docker está instalado y funcionando
docker --version
docker compose version

# Verificar que el puerto 8080 está libre
sudo netstat -tlnp | grep :8080
# (No debe mostrar nada si está libre)
```

#### **Paso 4: Construir y Levantar la Aplicación**
```bash
# Construir y levantar el servicio Docker
docker compose up ficct-crypto --build -d

# El proceso hará:
# ✅ Descargar imagen base de Tomcat 9 + Java 21
# ✅ Descargar dependencias de Maven
# ✅ Compilar el código Java
# ✅ Ejecutar tests (20 tests)
# ✅ Generar archivo WAR
# ✅ Iniciar servidor Tomcat
```

#### **Paso 5: Verificar que está Funcionando**
```bash
# Verificar estado del contenedor
docker compose ps
# STATUS debe mostrar "Up" y "healthy"

# Probar la API
curl http://localhost:8080/cipher
# Debe devolver JSON con los algoritmos disponibles

# Probar cifrado César
curl -X POST http://localhost:8080/cipher \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "operation=encrypt&algorithm=caesar&text=HOLA&key=3"
# Debe devolver: {"success":true,"result":"KROD","message":"..."}
```

#### **Paso 6: Acceder a la Interfaz Web**
```bash
# Abrir navegador y acceder a:
# 🌐 http://localhost:8080

# ✅ Deberías ver la interfaz de F.I.C.C.T.
# ✅ Puedes probar cifrar/descifrar texto
# ✅ Todas las funcionalidades están disponibles
```

---

### **🔄 ESCENARIO 2: Cerrar y Volver a Levantar el Proyecto**

#### **Cuando necesites cerrar el proyecto:**

```bash
# Navegar al directorio del proyecto
cd F.I.C.C.T.-Libreria-Cifrado-Clasico

# Detener y eliminar contenedores
docker compose down

# Verificar que se detuvo correctamente
docker compose ps
# No debe mostrar contenedores activos
```

#### **Para volver a levantar el proyecto:**

```bash
# Opción A: Levantar sin rebuild (más rápido)
docker compose up ficct-crypto -d

# Opción B: Levantar con rebuild (si hubo cambios en el código)
docker compose up ficct-crypto --build -d

# Opción C: Actualizar desde Git y rebuild
git pull origin main
docker compose up ficct-crypto --build -d
```

#### **Verificar que volvió a funcionar:**
```bash
# Esperar unos segundos y verificar
sleep 10
docker compose ps

# Probar la aplicación
curl http://localhost:8080/cipher

# Acceder a la web: http://localhost:8080
```

---

### **🛠️ Comandos Útiles de Docker**

```bash
# Ver logs de la aplicación
docker compose logs ficct-crypto -f

# Ver estado de contenedores
docker compose ps

# Entrar al contenedor (para debugging)
docker exec -it ficct-crypto-app bash

# Limpiar todo (contenedores, imágenes, cache)
docker system prune -a

# Solo limpiar contenedores parados
docker compose down --remove-orphans
```

### **🔧 Solución de Problemas Docker**

#### **❌ Error: "Port already in use"**
```bash
# Encontrar qué está usando el puerto 8080
sudo netstat -tlnp | grep :8080

# Detener el proceso o cambiar puerto en docker-compose.yml:
ports:
  - "8081:8080"  # Usar puerto 8081 externamente
```

#### **❌ Error: "Docker daemon not running"**
```bash
# Linux
sudo systemctl start docker
sudo systemctl enable docker

# Windows/macOS: Iniciar Docker Desktop
```

#### **❌ Error: "Cannot connect to the Docker daemon"**
```bash
# Agregar usuario al grupo docker (Linux)
sudo usermod -aG docker $USER
# Cerrar sesión y volver a iniciar
```

#### **❌ Error en el build**
```bash
# Limpiar cache y rebuild
docker compose down
docker system prune -f
docker compose up ficct-crypto --build -d
```

### **⚡ Ventajas del Despliegue con Docker**

✅ **Sin instalación de dependencias**: No necesitas Java, Maven, ni Tomcat
✅ **Funciona en cualquier OS**: Linux, Windows, macOS
✅ **Configuración automática**: Todo preconfigurado y optimizado
✅ **Isolation**: No afecta tu sistema host
✅ **Reproducible**: Mismo resultado en cualquier máquina
✅ **Health checks**: Monitoreo automático del estado
✅ **Easy scaling**: Fácil agregar nginx, balanceadores, etc.

---

## **🌐 Calculadora Online**.
> [Caesar-cipher.com/](https://caesar-cipher.com/)<br>
> [Vegenere.planetcalc](https://es.planetcalc.com/2468/)