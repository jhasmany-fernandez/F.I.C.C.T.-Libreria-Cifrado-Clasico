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

## 📥 **Clonación e Instalación en Otro Equipo**

### **📋 Prerrequisitos**

Antes de clonar el proyecto, asegúrate de tener instalado:

1. **Java Development Kit (JDK)**
   ```bash
   # Verificar versión (requiere JDK 21 o superior)
   java --version
   javac --version

   # Si no tienes JDK 21, descargar desde:
   # https://adoptium.net/temurin/releases/
   ```

2. **Apache Maven**
   ```bash
   # Verificar versión (requiere Maven 3.6 o superior)
   mvn --version

   # Si no tienes Maven, instalar:
   # Ubuntu/Debian: sudo apt install maven
   # Windows: descargar desde https://maven.apache.org/download.cgi
   # macOS: brew install maven
   ```

3. **Git**
   ```bash
   # Verificar Git
   git --version
   ```

### **🚀 Pasos de Instalación**

#### **1. Clonar el Repositorio**
```bash
# Opción 1: HTTPS
git clone https://github.com/usuario/F.I.C.C.T.-Libreria-Cifrado-Clasico.git

# Opción 2: SSH (si tienes configurada la clave SSH)
git clone git@github.com:usuario/F.I.C.C.T.-Libreria-Cifrado-Clasico.git

# Navegar al directorio del proyecto
cd F.I.C.C.T.-Libreria-Cifrado-Clasico
```

#### **2. Verificar la Estructura del Proyecto**
```bash
# Listar archivos principales
ls -la

# Verificar que existe pom.xml
cat pom.xml | head -10
```

#### **3. Instalar Dependencias**
```bash
# Limpiar compilaciones anteriores y descargar dependencias
mvn clean install

# Verificar que no hay errores
mvn compile
```

#### **4. Ejecutar Tests (Opcional)**
```bash
# Ejecutar todos los tests para verificar que todo funciona
mvn test

# Deberías ver:
# Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
# [INFO] BUILD SUCCESS
```

#### **5. Iniciar la Aplicación**
```bash
# Opción A: Usar Jetty (Recomendado para desarrollo)
mvn jetty:run

# La aplicación estará disponible en:
# http://localhost:9090/crypto

# Opción B: Generar WAR para Tomcat
mvn package
# El archivo WAR se genera en: target/crypto-classic-jsp-1.0-SNAPSHOT.war
```

### **🔧 Solución de Problemas Comunes**

#### **❌ Error: "JAVA_HOME not set"**
```bash
# Linux/macOS
export JAVA_HOME=/path/to/your/jdk
echo 'export JAVA_HOME=/path/to/your/jdk' >> ~/.bashrc

# Windows
set JAVA_HOME=C:\path\to\your\jdk
# O configurar en Variables de Entorno del Sistema
```

#### **❌ Error: "Maven command not found"**
```bash
# Verificar PATH
echo $PATH

# Agregar Maven al PATH
export PATH=$PATH:/path/to/maven/bin
```

#### **❌ Error: "Port 9090 already in use"**
```bash
# Encontrar proceso usando el puerto
lsof -i :9090  # Linux/macOS
netstat -ano | findstr :9090  # Windows

# Terminar el proceso o cambiar puerto en pom.xml
```

#### **❌ Error de compilación**
```bash
# Limpiar y recompilar
mvn clean
mvn compile

# Si persiste, verificar versión de Java
java --version
```

### **🌐 Configuración de Puertos**

Si necesitas cambiar el puerto por defecto:

1. **Para Jetty (puerto 9090):**
   ```xml
   <!-- En pom.xml, buscar jetty-maven-plugin -->
   <configuration>
       <httpConnector>
           <port>8080</port>  <!-- Cambiar aquí -->
       </httpConnector>
   </configuration>
   ```

2. **Para Tomcat (puerto 8080):**
   ```xml
   <!-- En server.xml de Tomcat -->
   <Connector port="8080" protocol="HTTP/1.1"
              connectionTimeout="20000"
              redirectPort="8443" />
   ```

### **📱 Acceso desde Otros Dispositivos**

Para acceder desde otros dispositivos en la misma red:

```bash
# Encontrar tu IP local
ip addr show  # Linux
ipconfig      # Windows
ifconfig      # macOS

# Acceder desde otro dispositivo:
# http://TU_IP_LOCAL:9090/crypto
# Ejemplo: http://192.168.1.100:9090/crypto
```

### **🔄 Actualizar el Proyecto**

Para obtener la última versión:

```bash
# Actualizar desde el repositorio
git pull origin main

# Reinstalar dependencias si hay cambios en pom.xml
mvn clean install

# Reiniciar la aplicación
mvn jetty:run
```

## 🚀 **Instrucciones de Despliegue**

### **Opción 1: Ejecutar con Jetty (Recomendado para desarrollo)**
```bash
# Compilar y ejecutar
mvn clean compile
mvn jetty:run

# Acceder a la aplicación
# http://localhost:9090/crypto
```

### **Opción 2: Desplegar en Tomcat**
```bash
# 1. Generar WAR
mvn clean package

# 2. Copiar WAR
cp target/crypto-classic-jsp.war $TOMCAT_HOME/webapps/

# 3. Iniciar Tomcat
catalina.sh start

# 4. Acceder a la aplicación
# http://localhost:8080/crypto-classic-jsp
```

### **Ejecutar Tests**
```bash
mvn test
```

## 🔧 **Configuración y Dependencias**

El proyecto incluye todas las dependencias necesarias en el `pom.xml`:

```xml
<!-- Servlet API -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>4.0.1</version>
    <scope>provided</scope>
</dependency>

<!-- JSP API -->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>javax.servlet.jsp-api</artifactId>
    <version>2.3.3</version>
    <scope>provided</scope>
</dependency>

<!-- JSTL -->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>

<!-- Jetty Plugin para desarrollo -->
<plugin>
    <groupId>org.eclipse.jetty</groupId>
    <artifactId>jetty-maven-plugin</artifactId>
    <version>11.0.15</version>
</plugin>
```

## 📚 **Uso del Sistema**

1. **Cifrado/Descifrado**: Seleccionar algoritmo, ingresar texto y clave
2. **Análisis de Frecuencias**: Analizar distribución de caracteres
3. **Configuración de Alfabetos**: Definir alfabetos personalizados con números, símbolos y espacios
4. **Configuración**: Personalizar opciones de procesamiento de texto

## 🎯 **APIs Disponibles**

- `POST /cipher` - Cifrar/descifrar texto
- `GET /cipher` - Obtener algoritmos disponibles
- `POST /analyze` - Análisis de frecuencias y herramientas de análisis
- `GET/POST /alphabet` - Gestión de alfabetos

## ✅ **Estado de Completitud**

- [x] ✅ **Proyecto Maven WAR configurado**
- [x] ✅ **Core implementado** (Alphabet, TextOptions, Preprocessor)
- [x] ✅ **8 Algoritmos implementados** y probados
- [x] ✅ **CryptoService y controladores** funcionales
- [x] ✅ **Interfaz JSP con Bootstrap 5** moderna
- [x] ✅ **Tests JUnit 5** - 20 pruebas pasando exitosamente
- [x] ✅ **Empaquetado WAR** optimizado para producción
- [x] ✅ **Plugin Jetty** configurado para desarrollo
- [x] ✅ **Errores corregidos** - Compilación sin warnings

## 🧪 **Resultados de Tests**

```
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

**🎉 El proyecto está 100% completo, probado y listo para despliegue inmediato.**

## ⚡ **Inicio Rápido (Para Desarrolladores)**

```bash
# 1. Clonar repositorio
git clone https://github.com/usuario/F.I.C.C.T.-Libreria-Cifrado-Clasico.git
cd F.I.C.C.T.-Libreria-Cifrado-Clasico

# 2. Verificar prerrequisitos
java --version    # Requiere JDK 21+
mvn --version     # Requiere Maven 3.6+

# 3. Instalar y ejecutar
mvn clean install
mvn jetty:run

# 4. Acceder a la aplicación
# 🌐 http://localhost:9090/crypto
```

### **🎯 Funcionalidades Principales Disponibles:**

1. **🔐 Cifrado/Descifrado**
   - 8 algoritmos clásicos implementados
   - Soporte para 3 tipos de alfabeto (Inglés, Español, ASCII Completo)
   - Validación de claves en tiempo real

2. **📊 Criptoanálisis**
   - Ataque de fuerza bruta para César
   - Análisis de frecuencias para Vigenère
   - Resultados con puntuaciones de probabilidad

3. **📈 Análisis de Frecuencias**
   - Distribución de caracteres
   - Índice de coincidencia
   - Gráficos interactivos

4. **⚙️ Configuración Avanzada**
   - Alfabetos personalizados
   - Opciones de procesamiento de texto
   - Preservación de espacios y acentos

## Online.
[Caesar-cipher.com/](https://caesar-cipher.com/)<br>
