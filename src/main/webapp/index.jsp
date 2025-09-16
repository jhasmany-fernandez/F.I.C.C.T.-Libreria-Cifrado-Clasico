<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/jsp/layout/header.jspf" %>

<div class="row">
    <div class="col-12">
        <div class="jumbotron bg-primary text-white p-5 rounded mb-4">
            <h1 class="display-4">F.I.C.C.T.</h1>
            <p class="lead">Librería de Cifrado Clásico - Herramientas de Criptografía Histórica</p>
            <hr class="my-4">
            <p>Explora y experimenta con algoritmos de cifrado clásicos utilizados a lo largo de la historia.</p>
        </div>
    </div>
</div>

<div class="row" id="cipher-section">
    <div class="col-lg-6">
        <div class="card">
            <div class="card-header">
                <h3>Cifrar/Descifrar Texto</h3>
            </div>
            <div class="card-body">
                <form id="cipher-form">
                    <div class="mb-3">
                        <label for="algorithm" class="form-label">Algoritmo de Cifrado</label>
                        <select class="form-select" id="algorithm" name="algorithm">
                            <option value="caesar">César</option>
                            <option value="vigenere">Vigenère</option>
                            <option value="substitution">Sustitución Simple</option>
                            <option value="atbash">Atbash</option>
                            <option value="railfence">Rail Fence</option>
                            <option value="columnar">Transposición Columnar</option>
                            <option value="playfair">Playfair</option>
                            <option value="xor">XOR</option>
                        </select>
                    </div>
                    
                    <div class="mb-3">
                        <label for="operation" class="form-label">Operación</label>
                        <div>
                            <input type="radio" class="form-check-input" id="encrypt" name="operation" value="encrypt" checked>
                            <label class="form-check-label me-3" for="encrypt">Cifrar</label>
                            <input type="radio" class="form-check-input" id="decrypt" name="operation" value="decrypt">
                            <label class="form-check-label" for="decrypt">Descifrar</label>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="text" class="form-label">Texto</label>
                        <textarea class="form-control" id="text" name="text" rows="4" 
                                  placeholder="Ingrese el texto a procesar..."></textarea>
                    </div>
                    
                    <div class="mb-3">
                        <label for="key" class="form-label">Clave</label>
                        <input type="text" class="form-control" id="key" name="key" 
                               placeholder="Ingrese la clave...">
                    </div>
                    
                    <div class="mb-3">
                        <label for="alphabet" class="form-label">Alfabeto</label>
                        <select class="form-select" id="alphabet" name="alphabet">
                            <option value="english">Inglés (26 letras)</option>
                            <option value="spanish">Español (27 letras con Ñ)</option>
                            <option value="full_ascii">ASCII Completo (con símbolos y espacio)</option>
                        </select>
                    </div>

                    
                    <div class="mb-3">
                        <label class="form-label">Opciones de Texto</label>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="preserveCase" name="preserveCase" value="true">
                            <label class="form-check-label" for="preserveCase">Preservar mayúsculas/minúsculas</label>
                        </div>
                        <div class="form-check">
                            <input type="checkbox" class="form-check-input" id="preserveSpaces" name="preserveSpaces" value="true">
                            <label class="form-check-label" for="preserveSpaces">Preservar espacios</label>
                        </div>
                    </div>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">Procesar</button>
                        <button type="button" class="btn btn-secondary" id="clear-form">Limpiar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <div class="col-lg-6">
        <div class="card">
            <div class="card-header">
                <h3>Resultado</h3>
            </div>
            <div class="card-body">
                <div id="cipher-result">
                    <p class="text-muted">El resultado aparecerá aquí...</p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row mt-4" id="analysis-section">
    <div class="col-lg-6">
        <div class="card">
            <div class="card-header">
                <h3>Análisis de Frecuencias</h3>
            </div>
            <div class="card-body">
                <form id="frequency-form">
                    <div class="mb-3">
                        <label for="frequency-text" class="form-label">Texto a Analizar</label>
                        <textarea class="form-control" id="frequency-text" name="text" rows="4"
                                  placeholder="Ingrese el texto para análisis de frecuencias..."></textarea>
                    </div>
                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-info">Analizar Frecuencias</button>
                        <button type="button" class="btn btn-secondary" id="clear-frequency">Limpiar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <div class="col-lg-6">
        <div class="card">
            <div class="card-header">
                <h3>Criptoanálisis</h3>
            </div>
            <div class="card-body">
                <!-- Pestañas para diferentes tipos de criptoanálisis -->
                <ul class="nav nav-tabs" id="cryptanalysisTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="caesar-break-tab" data-bs-toggle="tab" data-bs-target="#caesar-break" type="button" role="tab">César</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="vigenere-break-tab" data-bs-toggle="tab" data-bs-target="#vigenere-break" type="button" role="tab">Vigenère</button>
                    </li>
                </ul>

                <div class="tab-content mt-3" id="cryptanalysisTabContent">
                    <!-- Criptoanálisis de César -->
                    <div class="tab-pane fade show active" id="caesar-break" role="tabpanel">
                        <form id="caesar-break-form">
                            <div class="mb-3">
                                <label for="caesar-ciphertext" class="form-label">Texto Cifrado (César)</label>
                                <textarea class="form-control" id="caesar-ciphertext" name="ciphertext" rows="4"
                                         placeholder="Ingrese el texto cifrado con César para romper..."></textarea>
                                <div class="form-text">Ataque de fuerza bruta para encontrar todas las posibles claves.</div>
                            </div>
                            <div class="mb-3">
                                <label for="caesar-alphabet-break" class="form-label">Alfabeto</label>
                                <select class="form-select" id="caesar-alphabet-break" name="alphabet">
                                    <option value="english">Inglés (26 letras)</option>
                                    <option value="spanish">Español (27 letras con Ñ)</option>
                                    <option value="full_ascii">ASCII Completo</option>
                                </select>
                            </div>
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-warning">Romper Cifrado César</button>
                                <button type="button" class="btn btn-secondary" id="clear-caesar">Limpiar</button>
                            </div>
                        </form>
                    </div>

                    <!-- Criptoanálisis de Vigenère -->
                    <div class="tab-pane fade" id="vigenere-break" role="tabpanel">
                        <form id="vigenere-break-form">
                            <div class="mb-3">
                                <label for="vigenere-ciphertext" class="form-label">Texto Cifrado (Vigenère)</label>
                                <textarea class="form-control" id="vigenere-ciphertext" name="ciphertext" rows="4"
                                         placeholder="Ingrese el texto cifrado con Vigenère para analizar..."></textarea>
                                <div class="form-text">Análisis de frecuencias para determinar la longitud de clave.</div>
                            </div>
                            <div class="mb-3">
                                <label for="vigenere-alphabet-break" class="form-label">Alfabeto</label>
                                <select class="form-select" id="vigenere-alphabet-break" name="alphabet">
                                    <option value="english">Inglés (26 letras)</option>
                                    <option value="spanish">Español (27 letras con Ñ)</option>
                                    <option value="full_ascii">ASCII Completo</option>
                                </select>
                            </div>
                            <div class="d-flex gap-2">
                                <button type="submit" class="btn btn-warning">Analizar Vigenère</button>
                                <button type="button" class="btn btn-secondary" id="clear-vigenere">Limpiar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row mt-4">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <h3>Resultados de Análisis</h3>
            </div>
            <div class="card-body">
                <div id="analysis-result">
                    <p class="text-muted">Los resultados del análisis aparecerán aquí...</p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/jsp/layout/footer.jspf" %>
