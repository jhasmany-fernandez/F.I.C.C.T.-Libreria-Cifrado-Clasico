# CRIPTOGRAFICA - Desarrollo-Libreria-Cifrado-Clasico

## Datos.
* openjdk 21.0.8 2025-07-15
* Apache Maven 3.9.11
* Server version: Apache Tomcat/10.1.45



## Tomcat.
```bash
catalina.sh start

catalina.sh stop

```

## Estructura de carpetas (Maven + Tomcat)
```bash
crypto-classic-jsp/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ edu/uagrm/crypto/
│  │  │     ├─ algorithms/              # Implementaciones de cifrados
│  │  │     │  ├─ CaesarCipher.java
│  │  │     │  ├─ VigenereCipher.java
│  │  │     │  ├─ ColumnarTranspositionCipher.java
│  │  │     │  ├─ SimpleSubstitutionCipher.java
│  │  │     │  ├─ AtbashCipher.java              (opcional)
│  │  │     │  ├─ PlayfairCipher.java
│  │  │     │  ├─ RailFenceCipher.java
│  │  │     │  ├─ PermutationCipher.java
│  │  │     │  ├─ HillCipher.java
│  │  │     │  ├─ AutokeyCipher.java
│  │  │     │  └─ XorCipher.java                 (opcional)
│  │  │     ├─ core/
│  │  │     │  ├─ CipherAlgorithm.java           # interfaz común (encrypt/decrypt)
│  │  │     │  ├─ Alphabet.java                  # alfabeto configurable
│  │  │     │  ├─ TextOptions.java               # mayúsc/minúsc/acentos, espacios, etc.
│  │  │     │  ├─ TextPreprocessor.java          # normalización/limpieza
│  │  │     │  └─ Keys.java                      # validación/construcción de claves
│  │  │     ├─ analysis/
│  │  │     │  ├─ FrequencyAnalyzer.java         # análisis de frecuencias
│  │  │     │  └─ CaesarBreaker.java             # fuerza bruta / heurística
│  │  │     ├─ service/
│  │  │     │  └─ CryptoService.java             # orquesta: prepara texto, invoca algoritmo
│  │  │     ├─ web/
│  │  │     │  ├─ CipherController.java          # /cipher (POST) cifrar/descifrar
│  │  │     │  ├─ AnalyzeController.java         # /analyze (POST) frecuencias / romper César
│  │  │     │  └─ AlphabetController.java        # /alphabet (GET/POST) gestión de alfabetos
│  │  │     └─ util/
│  │  │        └─ JsonUtil.java                  # helpers para respuestas JSON
│  │  ├─ resources/
│  │  │  └─ messages.properties                  # textos/i18n opcional
│  │  └─ webapp/
│  │     ├─ META-INF/
│  │     ├─ WEB-INF/
│  │     │  ├─ web.xml                           # (o usa @WebServlet)
│  │     │  └─ jsp/
│  │     │     ├─ layout/
│  │     │     │  ├─ header.jspf
│  │     │     │  └─ footer.jspf
│  │     │     ├─ index.jsp                      # UI principal
│  │     │     ├─ result.jsp                     # muestra resultados HTML
│  │     │     ├─ analyze.jsp                    # tabla de frecuencias
│  │     │     └─ error.jsp
│  │     ├─ assets/
│  │     │  ├─ css/app.css
│  │     │  └─ js/app.js                         # opcional: llamar endpoints vía fetch/AJAX
│  │     └─ favicon.ico
│  └─ test/
│     └─ java/edu/uagrm/crypto/
│        ├─ algorithms/CaesarCipherTest.java     # JUnit para cada algoritmo
│        └─ service/CryptoServiceTest.java

```

## Plan de trabajo.
```bash

1. Crear proyecto Maven war (o usar maven-archetype-webapp).

2. Implementar core (Alphabet, TextOptions, Preprocessor).

3. Implementar algoritmos uno por uno con tests (César → Vigenère → Columnar → …).

4. Implementar CryptoService y controllers.

5. Construir JSP (index/result/analyze) + CSS básico.

6. Agregar tests JUnit y casos ejemplo (incluye pruebas de borde: letras no en alfabeto, claves inválidas, etc.).

7. Empaquetar war y desplegar en Tomcat.
```