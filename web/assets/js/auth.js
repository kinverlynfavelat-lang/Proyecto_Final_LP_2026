/*
 ==========================================
 AUTH.JS - BURGERBUILDER
 ==========================================
 Login, registro, sesión y logout
 ==========================================
 */

// ==========================
// INICIALIZAR EVENTOS
// ==========================
function inicializarEventosAuth() {

    // ==========================
    // LOGIN
    // ==========================
    $(document).on('submit', '#form-login', function (e) {
        e.preventDefault();

        const datos = $(this).serialize();

        fetch('AuthController?action=login', {
            method: 'POST',
            body: new URLSearchParams(datos)
        })
                .then(res => res.json())
                .then(data => {

                    if (data.success) {

                        // Guardar usuario en sesión del navegador
                        sessionStorage.setItem("usuario", JSON.stringify(data.userData));

                        Swal.fire({
                            icon: 'success',
                            title: 'Bienvenido',
                            text: data.message,
                            timer: 1500,
                            showConfirmButton: false
                        });

                        setTimeout(() => {

                            if (data.userData.rol === "ADMIN") {
                                window.location.href = "admin_productos.html";
                            } else {
                                window.location.href = "index.html";
                            }

                        }, 1200);

                    } else {
                        Swal.fire("Error", data.message, "error");
                    }
                })
                .catch(err => console.error("Error login:", err));
    });

    // ==========================
    // REGISTER
    // ==========================
    $(document).on('submit', '#form-register', function (e) {
        e.preventDefault();

        const datos = $(this).serialize();

        fetch('AuthController?action=register', {
            method: 'POST',
            body: new URLSearchParams(datos)
        })
                .then(res => res.json())
                .then(data => {

                    if (data.success) {

                        Swal.fire({
                            icon: 'success',
                            title: 'Registro exitoso',
                            text: 'Ahora puedes iniciar sesión'
                        }).then(() => {
                            const loginEl = document.getElementById("modalLogin");
const registerEl = document.getElementById("modalRegister");

bootstrap.Modal.getOrCreateInstance(registerEl).hide();

setTimeout(() => {
    bootstrap.Modal.getOrCreateInstance(loginEl).show();
}, 200);

                        });

                    } else {
                        Swal.fire("Error", data.message, "error");
                    }
                })
                .catch(err => console.error("Error register:", err));
    });
}

// ==========================
// VERIFICAR SESIÓN
// ==========================
function verificarSesion() {

    const usuario = JSON.parse(sessionStorage.getItem("usuario"));

    if (!usuario)
        return;

    // Mostrar usuario en UI
    document.getElementById("btn-login-modal")?.classList.add("d-none");
    document.getElementById("user-profile")?.classList.remove("d-none");

    const nombre = document.getElementById("user-name");
    if (nombre) {
        nombre.textContent = usuario.nombreCompleto;
    }

  
    if (usuario.rol === "ADMIN") {

        document.getElementById("link-admin-productos")?.classList.remove("d-none");
        document.getElementById("link-admin-pedidos")?.classList.remove("d-none");
        document.getElementById("link-admin-pagos")?.classList.remove("d-none");
        document.getElementById("separator-admin")?.classList.remove("d-none");

        document.getElementById("link-mis-pedidos")?.classList.add("d-none");

        document.getElementById("navbar-total")?.classList.add("d-none");

        document.getElementById("navbar-carrito")?.classList.add("d-none");
        
        document.getElementById("panel-admin")?.classList.remove("d-none");
    }
}

// ==========================
// LOGOUT
// ==========================
function logout() {

    fetch('AuthController?action=logout', {method: 'POST'})
            .then(() => {

                sessionStorage.clear();
                window.location.href = "index.html";

            })
            .catch(err => console.error("Error logout:", err));
}
function abrirRegistro() {

    const loginEl = document.getElementById("modalLogin");
    const registerEl = document.getElementById("modalRegister");

    const login = bootstrap.Modal.getInstance(loginEl);

    if (!login) {
        bootstrap.Modal.getOrCreateInstance(registerEl).show();
        return;
    }

    login.hide();

    loginEl.addEventListener("hidden.bs.modal", () => {
        bootstrap.Modal.getOrCreateInstance(registerEl).show();
    }, { once: true });
}

function abrirLogin() {

    const registerEl = document.getElementById("modalRegister");
    const loginEl = document.getElementById("modalLogin");

    const register = bootstrap.Modal.getInstance(registerEl);

    if (!register) {
        bootstrap.Modal.getOrCreateInstance(loginEl).show();
        return;
    }

    register.hide();

    registerEl.addEventListener("hidden.bs.modal", () => {
        bootstrap.Modal.getOrCreateInstance(loginEl).show();
    }, { once: true });
}