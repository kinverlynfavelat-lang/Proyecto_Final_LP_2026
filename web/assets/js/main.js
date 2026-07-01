
async function loadComponent(id, file) {
    const response = await fetch(file);
    const data = await response.text();
    document.getElementById(id).innerHTML = data;
}


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

        await loadComponent('head-placeholder', 'head.html');
        await loadComponent('header-placeholder', 'header.html');
        await loadComponent('footer-placeholder', 'footer.html');

        await loadScript('assets/js/jquery-3.6.0.min.js');
        await loadScript('assets/js/jquery.dataTables.min.js');
        await loadScript('assets/js/dataTables.bootstrap5.min.js');
        await loadScript('assets/js/bootstrap.bundle.min.js');
        await loadScript('https://cdn.jsdelivr.net/npm/sweetalert2@11');

        await loadScript("assets/js/auth.js");
        await loadScript("assets/js/tienda.js");

        await new Promise(resolve => setTimeout(resolve, 400));

        if (typeof verificarSesion === 'function') {
            verificarSesion();
        }

        if (typeof cargarProductos === 'function') {
            cargarProductos();
        }

        if (typeof inicializarEventosAuth === 'function') {
            inicializarEventosAuth();
        }

        if (typeof cargarTablaAdmin === 'function') {
            cargarTablaAdmin();
        }

        if (typeof refrescarCarrito === 'function') {
            refrescarCarrito();
        }

        if (typeof cargarHistorial === 'function') {
            cargarHistorial();
        }

    } catch (error) {
        console.error("Error cargando la aplicación:", error);
    }
}

init();
