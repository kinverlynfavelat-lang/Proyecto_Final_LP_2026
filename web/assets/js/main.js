
// ==========================
// CARGAR COMPONENTES HTML
// ==========================
async function loadComponent(id, file) {
    const response = await fetch(file);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;
}


// ==========================
// CARGAR SCRIPTS EN ORDEN
// ==========================
function loadScript(src) {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = src;
        script.onload = resolve;
        script.onerror = reject;
        document.body.appendChild(script);
    });
}


// ==========================
// INIT PRINCIPAL
// ==========================
async function init() {

    try {

        // 1. COMPONENTES HTML
        await loadComponent('head-placeholder', 'head.html');
        await loadComponent('header-placeholder', 'header.html');
        await loadComponent('footer-placeholder', 'footer.html');

        // 2. LIBRERÍAS
        await loadScript('assets/js/jquery-3.6.0.min.js');
        await loadScript('assets/js/jquery.dataTables.min.js');
        await loadScript('assets/js/dataTables.bootstrap5.min.js');
        await loadScript('assets/js/bootstrap.bundle.min.js');
        await loadScript('https://cdn.jsdelivr.net/npm/sweetalert2@11');

        // 3. LÓGICA
        await loadScript("assets/js/auth.js");
        await loadScript("assets/js/tienda.js");

        // 4. ESPERA SEGURA (DOM READY REAL)
        await new Promise(resolve => setTimeout(resolve, 400));

        // 🔐 SESIÓN
        if (typeof verificarSesion === 'function') {
            verificarSesion();
        }

        // 🛍️ PRODUCTOS
        if (typeof cargarProductos === 'function') {
            cargarProductos();
        }

        // 🎯 AUTH EVENTS
        if (typeof inicializarEventosAuth === 'function') {
            inicializarEventosAuth();
        }

        // 📊 ADMIN
        if (typeof cargarTablaAdmin === 'function') {
            cargarTablaAdmin();
        }

        // 🛒 CARRITO
        if (typeof refrescarCarrito === 'function') {
            refrescarCarrito();
        }

        // 📜 HISTORIAL
        if (typeof cargarHistorial === 'function') {
            cargarHistorial();
        }

    } catch (error) {
        console.error("Error cargando la aplicación:", error);
    }
}

// INIT
init();

// ==========================
// INICIAR APP
// ==========================
