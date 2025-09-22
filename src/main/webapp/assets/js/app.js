// F.I.C.C.T. - Librería de Cifrado Clásico - JavaScript

document.addEventListener('DOMContentLoaded', function() {
    
    // Elementos del DOM
    const cipherForm = document.getElementById('cipher-form');
    const frequencyForm = document.getElementById('frequency-form');
    const alphabetConfigForm = document.getElementById('alphabet-config-form');
    const caesarBreakForm = document.getElementById('caesar-break-form');
    const vigenereBreakForm = document.getElementById('vigenere-break-form');
    const cipherResult = document.getElementById('cipher-result');
    const analysisResult = document.getElementById('analysis-result');
    
    // Manejador del formulario de cifrado/descifrado
    if (cipherForm) {
        cipherForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Validar formulario antes de enviar
            if (!validateCipherForm()) {
                return;
            }

            const formData = new FormData(cipherForm);
            const operation = formData.get('operation');

            // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }

            showLoading(cipherResult);

            try {
                const response = await fetch('/cipher', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params
                });

                const result = await response.json();
                displayCipherResult(result);

            } catch (error) {
                displayError(cipherResult, 'Error de conexión: ' + error.message);
            }
        });
    }
    
    // Manejador del análisis de frecuencias
    if (frequencyForm) {
        frequencyForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Validar formulario
            if (!validateFrequencyForm()) {
                return;
            }

            const formData = new FormData(frequencyForm);
            formData.append('type', 'frequency');

            // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }

            showLoading(analysisResult);

            try {
                const response = await fetch('/analyze', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params
                });

                const result = await response.json();
                displayFrequencyResult(result);

            } catch (error) {
                displayError(analysisResult, 'Error de conexión: ' + error.message);
            }
        });
    }
    
    // Manejador del criptoanálisis de César
    if (caesarBreakForm) {
        caesarBreakForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Validar formulario
            if (!validateCaesarBreakForm()) {
                return;
            }

            const formData = new FormData(caesarBreakForm);
            formData.append('type', 'caesar');

            // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }

            showLoading(analysisResult);

            try {
                const response = await fetch('/analyze', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params
                });

                const result = await response.json();
                displayCaesarBreakResult(result);

            } catch (error) {
                displayError(analysisResult, 'Error de conexión: ' + error.message);
            }
        });
    }

    // Manejador del criptoanálisis de Vigenère
    if (vigenereBreakForm) {
        vigenereBreakForm.addEventListener('submit', async function(e) {
            e.preventDefault();

            // Validar formulario
            if (!validateVigenereBreakForm()) {
                return;
            }

            const formData = new FormData(vigenereBreakForm);
            formData.append('type', 'vigenere');

            // Convertir FormData a URLSearchParams
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }

            showLoading(analysisResult);

            try {
                const response = await fetch('/analyze', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: params
                });

                const result = await response.json();

                if (response.ok) {
                    displayVigenereBreakResult(result);
                } else {
                    displayError(analysisResult, result.message || 'Error en el análisis');
                }
            } catch (error) {
                displayError(analysisResult, 'Error de conexión: ' + error.message);
            }
        });
    }

    // Función para mostrar loading
    function showLoading(element) {
        element.innerHTML = `
            <div class="text-center">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Procesando...</span>
                </div>
                <p class="mt-2">Procesando...</p>
            </div>
        `;
    }
    
    // Función para mostrar errores
    function displayError(element, message) {
        element.innerHTML = `
            <div class="error-section">
                <h5><i class="bi bi-exclamation-triangle"></i> Error</h5>
                <p>${message}</p>
            </div>
        `;
    }
    
    // Función para mostrar resultado de cifrado/descifrado
    function displayCipherResult(result) {
        if (result.success) {
            cipherResult.innerHTML = `
                <div class="success-section fade-in">
                    <h5><i class="bi bi-check-circle"></i> ${result.message}</h5>
                    <div class="mt-3">
                        <label class="form-label">Resultado:</label>
                        <textarea class="form-control text-monospace" rows="4" readonly>${result.result}</textarea>
                        <button class="btn btn-sm btn-outline-primary mt-2 copy-button" onclick="copyToClipboard('${result.result}')">
                            Copiar
                        </button>
                    </div>
                </div>
            `;
        } else {
            displayError(cipherResult, result.message);
        }
    }
    
    // Función para mostrar resultado de análisis de frecuencias
    function displayFrequencyResult(result) {
        if (result.success) {
            let frequencyHtml = '<div class="success-section fade-in">';
            frequencyHtml += '<h5><i class="bi bi-bar-chart"></i> Análisis de Frecuencias</h5>';
            
            // Tabla de frecuencias
            frequencyHtml += '<div class="analysis-chart mt-3">';
            frequencyHtml += '<table class="table table-sm frequency-table">';
            frequencyHtml += '<thead><tr><th>Carácter</th><th>Frecuencia (%)</th><th>Visualización</th></tr></thead><tbody>';
            
            for (const [char, freq] of Object.entries(result.frequencies)) {
                if (freq > 0) {
                    const barWidth = (freq / Math.max(...Object.values(result.frequencies))) * 100;
                    frequencyHtml += `
                        <tr>
                            <td><strong>${char}</strong></td>
                            <td>${freq.toFixed(2)}%</td>
                            <td><div class="frequency-bar" style="width: ${barWidth}%"></div></td>
                        </tr>
                    `;
                }
            }
            
            frequencyHtml += '</tbody></table></div>';
            
            // Índice de coincidencia
            frequencyHtml += `
                <div class="mt-3">
                    <strong>Índice de Coincidencia:</strong> ${result.indexOfCoincidence.toFixed(4)}
                </div>
            `;
            
            frequencyHtml += '</div>';
            analysisResult.innerHTML = frequencyHtml;
        } else {
            displayError(analysisResult, result.message);
        }
    }
    
    // Función para mostrar resultado de criptoanálisis de César
    function displayCaesarBreakResult(result) {
        if (result.success) {
            let html = '<div class="success-section fade-in">';
            html += '<h5><i class="bi bi-key"></i> Criptoanálisis de César</h5>';
            
            // Mejor candidato
            if (result.bestCandidate) {
                html += `
                    <div class="candidate-item best-candidate mt-3">
                        <h6>Mejor Candidato (Desplazamiento ${result.bestCandidate.shift})</h6>
                        <p class="text-monospace">${result.bestCandidate.text}</p>
                        <small>Puntuación: ${result.bestCandidate.score.toFixed(2)}</small>
                        <button class="btn btn-sm btn-outline-success ms-2 copy-button" onclick="copyToClipboard('${result.bestCandidate.text}')">
                            Copiar
                        </button>
                    </div>
                `;
            }
            
            // Top candidatos
            if (result.topCandidates) {
                html += '<h6 class="mt-4">Otros Candidatos:</h6>';
                
                for (const [key, candidate] of Object.entries(result.topCandidates)) {
                    const preview = candidate.text.length > 100 ? candidate.text.substring(0, 100) + '...' : candidate.text;
                    html += `
                        <div class="candidate-item">
                            <strong>Desplazamiento ${candidate.shift}</strong> (Score: ${candidate.score.toFixed(2)})
                            <p class="text-monospace small">${preview}</p>
                            <button class="btn btn-sm btn-outline-primary copy-button" onclick="copyToClipboard('${candidate.text}')">
                                Copiar
                            </button>
                        </div>
                    `;
                }
            }
            
            html += '</div>';
            analysisResult.innerHTML = html;
        } else {
            displayError(analysisResult, result.message);
        }
    }

    function displayVigenereBreakResult(result) {
        if (result.success) {
            let html = '<div class="success-section fade-in">';
            html += '<h5><i class="bi bi-graph-up"></i> Análisis de Vigenère</h5>';

            if (result.keyLengthAnalysis) {
                html += '<h6 class="mt-3">Análisis de Longitud de Clave:</h6>';
                html += '<div class="row">';

                for (const [length, score] of Object.entries(result.keyLengthAnalysis)) {
                    html += `
                        <div class="col-md-4 mb-2">
                            <div class="card">
                                <div class="card-body p-2">
                                    <h6>Longitud ${length}</h6>
                                    <p class="mb-0">Score: ${score.toFixed(3)}</p>
                                </div>
                            </div>
                        </div>
                    `;
                }
                html += '</div>';
            }

            if (result.bestCandidate) {
                html += `
                    <div class="candidate-item best-candidate mt-3">
                        <h6>Mejor Candidato (Clave: "${result.bestCandidate.key}")</h6>
                        <p class="text-monospace">${result.bestCandidate.text}</p>
                        <small>Puntuación: ${result.bestCandidate.score.toFixed(2)}</small>
                        <button class="btn btn-sm btn-outline-success ms-2 copy-button" onclick="copyToClipboard('${result.bestCandidate.text}')">
                            Copiar
                        </button>
                    </div>
                `;
            }

            html += '</div>';
            analysisResult.innerHTML = html;
        } else {
            displayError(analysisResult, result.message);
        }
    }

    // Función para copiar al portapapeles
    window.copyToClipboard = function(text) {
        navigator.clipboard.writeText(text).then(function() {
            // Mostrar mensaje de éxito
            const toast = document.createElement('div');
            toast.className = 'position-fixed top-0 end-0 p-3';
            toast.style.zIndex = '1050';
            toast.innerHTML = `
                <div class="toast show" role="alert">
                    <div class="toast-header">
                        <strong class="me-auto">Éxito</strong>
                        <button type="button" class="btn-close" onclick="this.parentElement.parentElement.parentElement.remove()"></button>
                    </div>
                    <div class="toast-body">
                        Texto copiado al portapapeles
                    </div>
                </div>
            `;
            document.body.appendChild(toast);
            
            setTimeout(() => {
                if (toast.parentElement) {
                    toast.remove();
                }
            }, 3000);
        });
    };
    
    // Actualizar información del algoritmo al cambiar selección
    const algorithmSelect = document.getElementById('algorithm');
    if (algorithmSelect) {
        algorithmSelect.addEventListener('change', function() {
            updateAlgorithmInfo(this.value);
        });
        
        // Mostrar info inicial
        updateAlgorithmInfo(algorithmSelect.value);
    }
    
    function updateAlgorithmInfo(algorithm) {
        const algorithmInfos = {
            'caesar': 'Cifrado por desplazamiento. Clave: número del 0-25',
            'vigenere': 'Cifrado polialfabético. Clave: palabra o frase',
            'substitution': 'Sustitución monoalfabética. Clave: permutación del alfabeto',
            'atbash': 'Cifrado por inversión. No requiere clave',
            'railfence': 'Cifrado por transposición. Clave: número de rieles',
            'columnar': 'Transposición columnar. Clave: palabra para ordenar columnas',
            'playfair': 'Cifrado por sustitución de pares. Clave: palabra o frase',
            'xor': 'Operación XOR. Clave: cualquier texto'
        };
        
        const info = algorithmInfos[algorithm] || 'Seleccione un algoritmo';
        
        // Buscar o crear elemento de información
        let infoElement = document.querySelector('.algorithm-info');
        if (!infoElement) {
            infoElement = document.createElement('div');
            infoElement.className = 'algorithm-info';
            algorithmSelect.parentElement.insertAdjacentElement('afterend', infoElement);
        }
        
        infoElement.innerHTML = `<small>${info}</small>`;
    }

    // Función para validar el formulario de cifrado
    function validateCipherForm() {
        clearValidationErrors();

        const algorithm = document.getElementById('algorithm').value;
        const text = document.getElementById('text').value.trim();
        const key = document.getElementById('key').value.trim();
        const alphabet = document.getElementById('alphabet').value;

        let isValid = true;

        // Validar texto
        if (!text) {
            showFieldError('text', 'El texto es obligatorio');
            isValid = false;
        } else if (text.length > 10000) {
            showFieldError('text', 'El texto no puede exceder 10,000 caracteres');
            isValid = false;
        }

        // Validar clave según el algoritmo
        switch (algorithm) {
            case 'caesar':
                if (!key) {
                    showFieldError('key', 'La clave es obligatoria para el cifrado César');
                    isValid = false;
                } else if (!isValidCaesarKey(key)) {
                    showFieldError('key', 'La clave debe ser un número entre 0 y 25');
                    isValid = false;
                }
                break;

            case 'vigenere':
                if (!key) {
                    showFieldError('key', 'La clave es obligatoria para el cifrado Vigenère');
                    isValid = false;
                } else if (!isValidVigenereKey(key, alphabet)) {
                    const alphabetType = alphabet === 'spanish' ? 'español' :
                                    alphabet === 'full_ascii' ? 'ASCII completo' : 'inglés';
                    showFieldError('key', `La clave debe contener solo letras válidas del alfabeto ${alphabetType}`);
                    isValid = false;
                }
                break;

            case 'substitution':
                if (!key) {
                    showFieldError('key', 'La clave es obligatoria para la sustitución simple');
                    isValid = false;
                } else if (!isValidSubstitutionKey(key, alphabet)) {
                    const expectedLength = alphabet === 'spanish' ? 27 : 26;
                    showFieldError('key', `La clave debe tener exactamente ${expectedLength} caracteres únicos del alfabeto`);
                    isValid = false;
                }
                break;

            case 'railfence':
                if (!key) {
                    showFieldError('key', 'El número de rieles es obligatorio');
                    isValid = false;
                } else if (!isValidRailFenceKey(key)) {
                    showFieldError('key', 'El número de rieles debe ser un entero mayor a 1');
                    isValid = false;
                }
                break;

            case 'columnar':
                if (!key) {
                    showFieldError('key', 'La clave es obligatoria para la transposición columnar');
                    isValid = false;
                } else if (!isValidColumnarKey(key, alphabet)) {
                    showFieldError('key', 'La clave debe contener solo letras válidas del alfabeto');
                    isValid = false;
                }
                break;

            case 'playfair':
                if (!key) {
                    showFieldError('key', 'La clave es obligatoria para el cifrado Playfair');
                    isValid = false;
                } else if (!isValidPlayfairKey(key, alphabet)) {
                    showFieldError('key', 'La clave debe contener solo letras válidas del alfabeto');
                    isValid = false;
                }
                break;

            case 'xor':
                if (!key) {
                    showFieldError('key', 'La clave es obligatoria para el cifrado XOR');
                    isValid = false;
                }
                break;

            case 'atbash':
                // Atbash no requiere clave
                break;

            default:
                showFieldError('algorithm', 'Seleccione un algoritmo válido');
                isValid = false;
        }

        return isValid;
    }

    // Funciones de validación específicas
    function isValidCaesarKey(key) {
        const num = parseInt(key);
        return !isNaN(num) && num >= 0 && num <= 25;
    }

    function isValidVigenereKey(key, alphabet) {
        if (!key) return false;
        let validChars;

        switch(alphabet) {
            case 'spanish':
                validChars = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ';
                break;
            case 'full_ascii':
                validChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{}|;\':",./<>? ';
                break;
            default: // english
                validChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        }

        return key.toUpperCase().split('').every(char => validChars.includes(char));
    }

    function isValidSubstitutionKey(key, alphabet) {
        let expectedLength, validChars;

        switch(alphabet) {
            case 'spanish':
                expectedLength = 27;
                validChars = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ';
                break;
            case 'full_ascii':
                expectedLength = 67; // 26 letters + 10 numbers + 30 symbols + 1 space
                validChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{}|;\':",./<>? ';
                break;
            default: // english
                expectedLength = 26;
                validChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        }

        if (key.length !== expectedLength) return false;

        const keyUpper = key.toUpperCase();
        const uniqueChars = new Set(keyUpper);

        return uniqueChars.size === expectedLength &&
               keyUpper.split('').every(char => validChars.includes(char));
    }

    function isValidRailFenceKey(key) {
        const num = parseInt(key);
        return !isNaN(num) && num > 1 && num <= 100;
    }

    function isValidColumnarKey(key, alphabet) {
        if (!key) return false;
        const validChars = alphabet === 'spanish' ?
            'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ' :
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

        return key.toUpperCase().split('').every(char => validChars.includes(char));
    }

    function isValidPlayfairKey(key, alphabet) {
        if (!key) return false;
        const validChars = alphabet === 'spanish' ?
            'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ' :
            'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

        return key.toUpperCase().split('').every(char => validChars.includes(char));
    }

    // Validación para análisis de frecuencias
    function validateFrequencyForm() {
        const text = document.getElementById('frequency-text').value.trim();

        if (!text) {
            showFieldError('frequency-text', 'El texto para análisis es obligatorio');
            return false;
        }

        if (text.length < 10) {
            showFieldError('frequency-text', 'El texto debe tener al menos 10 caracteres para un análisis significativo');
            return false;
        }

        if (text.length > 50000) {
            showFieldError('frequency-text', 'El texto no puede exceder 50,000 caracteres');
            return false;
        }

        return true;
    }

    // Validación para criptoanálisis de César
    function validateCaesarBreakForm() {
        const text = document.getElementById('caesar-ciphertext').value.trim();

        if (!text) {
            showFieldError('caesar-ciphertext', 'El texto cifrado es obligatorio');
            return false;
        }

        if (text.length < 20) {
            showFieldError('caesar-ciphertext', 'El texto debe tener al menos 20 caracteres para un criptoanálisis efectivo');
            return false;
        }

        if (text.length > 10000) {
            showFieldError('caesar-ciphertext', 'El texto no puede exceder 10,000 caracteres');
            return false;
        }

        return true;
    }

    // Validación para criptoanálisis de Vigenère
    function validateVigenereBreakForm() {
        const text = document.getElementById('vigenere-ciphertext').value.trim();

        if (!text) {
            showFieldError('vigenere-ciphertext', 'El texto cifrado es obligatorio');
            return false;
        }

        if (text.length < 50) {
            showFieldError('vigenere-ciphertext', 'El texto debe tener al menos 50 caracteres para un análisis de frecuencias efectivo');
            return false;
        }

        if (text.length > 10000) {
            showFieldError('vigenere-ciphertext', 'El texto no puede exceder 10,000 caracteres');
            return false;
        }

        return true;
    }

    // Funciones para mostrar errores de validación
    function showFieldError(fieldId, message) {
        const field = document.getElementById(fieldId);
        field.classList.add('is-invalid');

        // Remover error previo si existe
        const existingError = field.parentElement.querySelector('.invalid-feedback');
        if (existingError) {
            existingError.remove();
        }

        // Crear nuevo mensaje de error
        const errorDiv = document.createElement('div');
        errorDiv.className = 'invalid-feedback';
        errorDiv.textContent = message;
        field.parentElement.appendChild(errorDiv);
    }

    function clearValidationErrors() {
        // Remover clases de error
        document.querySelectorAll('.is-invalid').forEach(element => {
            element.classList.remove('is-invalid');
        });

        // Remover mensajes de error
        document.querySelectorAll('.invalid-feedback').forEach(element => {
            element.remove();
        });
    }

    // Limpiar errores cuando el usuario empiece a escribir
    document.querySelectorAll('#cipher-form input, #cipher-form textarea, #cipher-form select, #frequency-form textarea, #alphabet-config-form input, #alphabet-config-form select').forEach(element => {
        element.addEventListener('input', function() {
            this.classList.remove('is-invalid');
            const errorMsg = this.parentElement.querySelector('.invalid-feedback');
            if (errorMsg) {
                errorMsg.remove();
            }
        });
    });

    // Funcionalidad del botón limpiar
    const clearButton = document.getElementById('clear-form');
    if (clearButton) {
        clearButton.addEventListener('click', function() {
            console.log('Clear button clicked!');

            // Limpiar formulario principal
            if (cipherForm) {
                console.log('Cipher form found, resetting...');
                cipherForm.reset();

                // Restaurar valores por defecto
                const algorithmEl = document.getElementById('algorithm');
                const operationEl = document.getElementById('operation');
                const alphabetEl = document.getElementById('alphabet');

                if (algorithmEl) algorithmEl.value = 'caesar';
                if (operationEl) operationEl.checked = true; // Cifrar
                if (alphabetEl) alphabetEl.value = 'english';

                // Limpiar resultado
                if (cipherResult) {
                    cipherResult.innerHTML = '<p class="text-muted">El resultado aparecerá aquí...</p>';
                }

                // Limpiar errores de validación
                clearValidationErrors();

                // Actualizar información del algoritmo
                updateAlgorithmInfo('caesar');

                console.log('Form cleared successfully!');
            } else {
                console.log('Cipher form not found!');
            }
        });
    } else {
        console.log('Clear button not found!');
    }

    // Funcionalidad del botón limpiar para análisis de frecuencias
    const clearFrequencyButton = document.getElementById('clear-frequency');
    if (clearFrequencyButton) {
        clearFrequencyButton.addEventListener('click', function() {
            console.log('Clear frequency button clicked!');

            const frequencyTextEl = document.getElementById('frequency-text');
            if (frequencyTextEl) {
                frequencyTextEl.value = '';
            }

            // Limpiar resultado del análisis
            if (analysisResult) {
                analysisResult.innerHTML = '<p class="text-muted">Los resultados del análisis aparecerán aquí...</p>';
            }

            // Limpiar errores de validación
            clearValidationErrors();

            console.log('Frequency form cleared successfully!');
        });
    }

    // Funcionalidad del botón limpiar para criptoanálisis de César
    const clearCaesarButton = document.getElementById('clear-caesar');
    if (clearCaesarButton) {
        clearCaesarButton.addEventListener('click', function() {
            console.log('Clear Caesar button clicked!');

            const caesarTextEl = document.getElementById('caesar-ciphertext');
            const caesarAlphabetEl = document.getElementById('caesar-alphabet-break');

            if (caesarTextEl) caesarTextEl.value = '';
            if (caesarAlphabetEl) caesarAlphabetEl.value = 'english';

            // Limpiar resultado del análisis
            if (analysisResult) {
                analysisResult.innerHTML = '<p class="text-muted">Los resultados del análisis aparecerán aquí...</p>';
            }

            // Limpiar errores de validación
            clearValidationErrors();

            console.log('Caesar form cleared successfully!');
        });
    }

    // Funcionalidad del botón limpiar para criptoanálisis de Vigenère
    const clearVigenereButton = document.getElementById('clear-vigenere');
    if (clearVigenereButton) {
        clearVigenereButton.addEventListener('click', function() {
            console.log('Clear Vigenere button clicked!');

            const vigenereTextEl = document.getElementById('vigenere-ciphertext');
            const vigenereAlphabetEl = document.getElementById('vigenere-alphabet-break');

            if (vigenereTextEl) vigenereTextEl.value = '';
            if (vigenereAlphabetEl) vigenereAlphabetEl.value = 'english';

            // Limpiar resultado del análisis
            if (analysisResult) {
                analysisResult.innerHTML = '<p class="text-muted">Los resultados del análisis aparecerán aquí...</p>';
            }

            // Limpiar errores de validación
            clearValidationErrors();

            console.log('Vigenere form cleared successfully!');
        });
    }

    // Manejador para mostrar/ocultar campo de alfabeto personalizado
    const alphabetSelect = document.getElementById('alphabet');
    if (alphabetSelect) {
        alphabetSelect.addEventListener('change', function() {
            const customAlphabetDiv = document.getElementById('custom-alphabet-div');
            if (this.value === 'custom') {
                customAlphabetDiv.style.display = 'block';
            } else {
                customAlphabetDiv.style.display = 'none';
                document.getElementById('custom-alphabet').value = '';
            }
        });
    }

    // Manejador para demostración de alfabetos
    const alphabetDemoSelect = document.getElementById('alphabet-demo');
    if (alphabetDemoSelect) {
        alphabetDemoSelect.addEventListener('change', function() {
            const customDemoDiv = document.getElementById('custom-demo-div');
            if (this.value === 'custom') {
                customDemoDiv.style.display = 'block';
            } else {
                customDemoDiv.style.display = 'none';
            }
        });
    }

    // Botón para mostrar caracteres del alfabeto
    const demoAlphabetButton = document.getElementById('demo-alphabet');
    if (demoAlphabetButton) {
        demoAlphabetButton.addEventListener('click', function() {
            const alphabetType = document.getElementById('alphabet-demo').value;
            const customAlphabet = document.getElementById('custom-demo').value;

            let alphabetChars = '';

            switch(alphabetType) {
                case 'english':
                    alphabetChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
                    break;
                case 'spanish':
                    alphabetChars = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ';
                    break;
                case 'full_ascii':
                    alphabetChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{}|;\':",./<>? ';
                    break;
                case 'custom':
                    alphabetChars = customAlphabet.toUpperCase();
                    break;
                default:
                    alphabetChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
            }

            const resultHtml = `
                <div class="alert alert-info">
                    <h5>Alfabeto ${alphabetType.charAt(0).toUpperCase() + alphabetType.slice(1)}:</h5>
                    <p><strong>Caracteres (${alphabetChars.length}):</strong></p>
                    <p class="font-monospace bg-light p-2 border rounded">${alphabetChars}</p>
                    <small class="text-muted">Este alfabeto se puede usar para cifrar texto que incluye estos caracteres específicos.</small>
                </div>
            `;

            analysisResult.innerHTML = resultHtml;
        });
    }

});