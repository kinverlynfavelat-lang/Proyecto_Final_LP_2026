/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


// Función para cargar archivos HTML
async function loadComponent(id, file) {
    const response = await fetch(file);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;
}

// Función para cargar scripts secuencialmente
function loadScript(src) {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = src;
        script.onload = resolve;
        script.onerror = reject;
        document.body.appendChild(script);
    });
}

async function init() {
    try {
        // 1. Cargar los componentes HTML
        await loadComponent('head-placeholder', 'head.html');
        await loadComponent('header-placeholder', 'header.html');
        await loadComponent('footer-placeholder', 'footer.html');

        // 2. Cargar las librerías en ORDEN ESTRICTO
        await loadScript('assets/js/jquery-3.6.0.min.js');

        // --- AÑADIR ESTOS DOS ---
        await loadScript('assets/js/jquery.dataTables.min.js');
        await loadScript('assets/js/dataTables.bootstrap5.min.js');
        // ------------------------

        await loadScript('assets/js/bootstrap.bundle.min.js');
        await loadScript('https://cdn.jsdelivr.net/npm/sweetalert2@11');

        // 3. Cargar nuestra lógica al final
        await loadScript('assets/js/tienda.js');

        // 4. Ejecutar funciones iniciales
        setTimeout(() => {
            if (typeof verificarSesion === 'function')
                verificarSesion();
            if (typeof cargarProductos === 'function')
                cargarProductos();
            if (typeof inicializarEventosAuth === 'function')
                inicializarEventosAuth();
            if (typeof cargarTablaAdmin === 'function')
                cargarTablaAdmin();

            if (typeof cargarCarrito === 'function')
                cargarCarrito();
            if (typeof actualizarContadorCarrito === 'function')
                actualizarContadorCarrito();
            
            if (typeof cargarHistorial === 'function')
                cargarHistorial();

        }, 200);

    } catch (error) {
        console.error("Error cargando la aplicación:", error);
    }
}

init();
