// F.I.C.C.T. - Librería de Cifrado Clásico - JavaScript

document.addEventListener('DOMContentLoaded', function() {
    
    // Elementos del DOM
    const cipherForm = document.getElementById('cipher-form');
    const frequencyForm = document.getElementById('frequency-form');
    const caesarBreakForm = document.getElementById('caesar-break-form');
    const cipherResult = document.getElementById('cipher-result');
    const analysisResult = document.getElementById('analysis-result');
    
    // Manejador del formulario de cifrado/descifrado
    if (cipherForm) {
        cipherForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const formData = new FormData(cipherForm);
            const operation = formData.get('operation');
            
            // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }
            
            showLoading(cipherResult);
            
            try {
                const response = await fetch('/crypto/cipher', {
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
            
            const formData = new FormData(frequencyForm);
            formData.append('type', 'frequency');
            
            // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }
            
            showLoading(analysisResult);
            
            try {
                const response = await fetch('/crypto/analyze', {
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
            
            const formData = new FormData(caesarBreakForm);
            formData.append('type', 'caesar');
            
            // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }
            
            showLoading(analysisResult);
            
            try {
                const response = await fetch('/crypto/analyze', {
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
});