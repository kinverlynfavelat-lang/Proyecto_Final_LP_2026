

async function cargarProductos() {

    const contenedor = document.getElementById("lista-productos");

    if (!contenedor)
        return;

    contenedor.innerHTML = `
        <div class="text-center py-5">
            <div class="spinner-border text-warning"></div>
            <p>Cargando hamburguesas...</p>
        </div>
    `;

    try {

        const response = await fetch("ProductoController?action=listarActivos");
        const result = await response.json();

        if (!result.success) {
            contenedor.innerHTML = "<p>Error cargando productos</p>";
            return;
        }

        contenedor.innerHTML = "";

        result.data.forEach(p => {

            contenedor.innerHTML += `
                <div class="col-6 col-md-3 mb-4">

    <div class="card product-card h-100 shadow-sm border-0">

        <img src="${p.imagen}"
             class="card-img-top product-img">

        <div class="card-body d-flex flex-column">

            <h6 class="fw-bold mb-1 text-truncate">
                ${p.nombre}
            </h6>

            <small class="text-muted mb-2 small-desc">
                ${p.descripcion}
            </small>

            <h6 class="product-price mb-3">
    S/ ${p.precio}
</h6>

            <button class="btn btn-sm w-100 mt-auto add-btn"
                    onclick="agregarAlCarrito(${p.idProducto})">

                Agregar

            </button>

        </div>

    </div>

</div>
            `;
        });

    } catch (error) {
        console.error("Error productos:", error);
        contenedor.innerHTML = "<p>Error de conexión</p>";
    }
}


async function agregarAlCarrito(idProducto) {

    const usuario = JSON.parse(sessionStorage.getItem("usuario"));

    if (!usuario) {
        new bootstrap.Modal(document.getElementById("modalLogin")).show();
        return;
    }

    const res = await fetch(
            "AppController?action=agregarCarrito&idProducto=" + idProducto,
            {method: "POST"}
    );

    const data = await res.json();

    if (data.success) {
        actualizarNavbarCarrito();
        refrescarCarrito(); 
    }
}

function cargarCarrito() {

    const tabla = document.querySelector("#tabla-carrito tbody");
    if (!tabla)
        return;

    fetch("AppController?action=listarCarrito", {
        credentials: "include"
    })
            .then(res => res.json())
            .then(data => {

                tabla.innerHTML = "";

                const carrito = data.carrito || [];

                if (carrito.length === 0) {

                    tabla.innerHTML = `
                <tr>
                    <td colspan="6" class="text-center py-4">
                        El carrito está vacío
                    </td>
                </tr>
            `;

                    actualizarUI(0, 0);
                    return;
                }

                let total = 0;

                carrito.forEach((item, index) => {

                    total += item.subTotal;

                    tabla.innerHTML += `
                <tr>
                    <td>${index + 1}</td>

                    <td>
                        <img src="${item.producto.imagen}" width="50">
                        ${item.producto.nombre}
                    </td>

                    <td>S/ ${item.precioCompra.toFixed(2)}</td>

                    
                        <td>
    ${item.cantidad}
</td>
                   

                    <td>S/ ${item.subTotal.toFixed(2)}</td>

           <td>
    <button class="btn btn-danger btn-sm btn-trash"
        onclick="eliminarItem(${item.producto.idProducto})">

        <i class="bi bi-trash"></i>
    </button>
</td>
                </tr>
            `;
                });

                actualizarUI(total, carrito.length);

            })
            .catch(err => console.error("Error carrito:", err));
}


function cambiarCantidad(idProducto, cambio) {

    let accion = cambio === 1 ? "incrementarCantidad" : "disminuirCantidad";

    fetch(`AppController?action=${accion}&idProducto=${idProducto}`, {
        method: 'POST',
        credentials: "include"
    })
            .then(res => res.json())
            .then(data => {

                if (data.success) {
                    refrescarCarrito();
                } else {
                    alert(data.message);
                }

            });
}


function eliminarItem(idProducto) {

    fetch(`AppController?action=quitarProducto&idProducto=${idProducto}`, {
        credentials: "include"
    })
            .then(res => res.json())
            .then(data => {

                if (data.success) {
                    refrescarCarrito();
                } else {
                    alert(data.message);
                }

            });
}


async function actualizarNavbarCarrito() {

    try {

        const res = await fetch("AppController?action=listarCarrito", {
            credentials: "include"
        });

        const data = await res.json();

        const carrito = data.carrito || [];

        let total = 0;

        carrito.forEach(item => {
            total += item.subTotal;
        });

        actualizarUI(total, carrito.length);

    } catch (error) {
        console.error("Error navbar:", error);
    }
}



function actualizarUI(total, cantidad) {

    const totalEl = document.getElementById("total-carrito");
    const countEl = document.getElementById("cart-count");

    if (totalEl) {
        totalEl.textContent = "S/ " + total.toFixed(2);
    }

    if (countEl) {
        countEl.textContent = cantidad;
    }
}



function refrescarCarrito() {
    cargarCarrito();
    actualizarNavbarCarrito();
}



window.addEventListener("load", () => {
    refrescarCarrito();
});

function iniciarCompra() {

    fetch("AppController?action=listarCarrito")
            .then(res => res.json())
            .then(data => {

                if (!data.carrito || data.carrito.length === 0) {
                    Swal.fire("Carrito vacío");
                    return;
                }

                bootstrap.Modal.getOrCreateInstance(document.getElementById("modalDatosPedido")).show();
            })
            .catch(() => {
                Swal.fire("Error", "No se pudo cargar el carrito");
            });
}


function pasarMetodoPago() {

    const pedido = {
        nombre: $('#nombreCliente').val(),
        dni: $('#dni').val(),
        telefono: $('#telefono').val(),
        direccionEntrega: $('#direccion').val()
    };

    if (!pedido.nombre || !pedido.dni || !pedido.telefono || !pedido.direccionEntrega) {
        Swal.fire("Completa todos los campos");
        return;
    }

    sessionStorage.setItem("pedidoTemp", JSON.stringify(pedido));

    bootstrap.Modal.getOrCreateInstance(document.getElementById("modalDatosPedido")).hide();
    bootstrap.Modal.getOrCreateInstance(document.getElementById("modalMetodoPago")).show();
}


function confirmarMetodoPago(metodo) {

    const pedido = JSON.parse(sessionStorage.getItem("pedidoTemp"));

    if (!pedido) {
        Swal.fire("Error", "No hay datos del pedido");
        return;
    }

    const data = {
        nombreCliente: pedido.nombre,
        dni: pedido.dni,
        telefono: pedido.telefono,
        direccionEntrega: pedido.direccionEntrega,
        metodoPago: metodo
    };

    fetch("AppController?action=generarPedido", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: new URLSearchParams(data)
    })
            .then(res => res.json())
            .then(data => {

                if (!data.success) {
                    console.log("ERROR BACKEND:", data.message);
                    Swal.fire("Error", data.message);
                    return;
                }

                const pedidoFinal = {
                    ...pedido,
                    metodoPago: metodo,
                    idPedido: data.idPedido,
                    codigo: data.codigo
                };

                sessionStorage.setItem("pedidoActual", JSON.stringify(pedidoFinal));

                bootstrap.Modal.getOrCreateInstance(document.getElementById("modalMetodoPago")).hide();
                mostrarResumenPedido(pedidoFinal);
            });
}

// ==========================
// 4. RESUMEN PEDIDO
// ==========================
function mostrarResumenPedido(pedidoFinal) {

    $('#res-metodo').text(pedidoFinal.metodoPago);
    $('#res-codigo').text(pedidoFinal.codigo);

    const total = pedidoFinal.total || $('#total-carrito').text();
    $('#res-total').text(total);

    bootstrap.Modal.getOrCreateInstance(document.getElementById("modalResumenPedido")).show();
}


// ==========================
// 5. ABRIR COMPROBANTE
// ==========================
function abrirModalComprobante() {

    bootstrap.Modal.getOrCreateInstance(document.getElementById("modalResumenPedido")).hide();
    bootstrap.Modal.getOrCreateInstance(document.getElementById("modalComprobante")).show();
}


// ==========================
// 6. ENVIAR COMPROBANTE (PagoController)
// ==========================
async function enviarComprobante() {

    const form = document.getElementById("form-comprobante");
    const formData = new FormData(form);

    const pedido = JSON.parse(sessionStorage.getItem("pedidoActual"));

    if (!pedido) {
        Swal.fire("Error", "No hay pedido activo");
        return;
    }

    if (!form.querySelector("input[type='file']").files.length) {
        Swal.fire("Error", "Debes subir un comprobante");
        return;
    }

    formData.append("idPedido", pedido.idPedido);
    formData.append("metodo", pedido.metodoPago);
    formData.append("action", "registrarPago");

    try {

        const res = await fetch("PagoController", {
            method: "POST",
            body: formData
        });

        const data = await res.json();

        if (data.success) {

            bootstrap.Modal.getOrCreateInstance(document.getElementById("modalComprobante")).hide();
            bootstrap.Modal.getOrCreateInstance(document.getElementById("modalFinal")).show();

            $('#final-codigo').text(pedido.codigo);
            $('#final-metodo').text(pedido.metodoPago);
            $('#final-total').text($('#total-carrito').text());

            // limpiar flujo
            sessionStorage.removeItem("pedidoTemp");
            sessionStorage.removeItem("pedidoActual");

        } else {
            Swal.fire("Error", data.message);
        }

    } catch (err) {
        Swal.fire("Error", "Error de conexión");
    }
}


function volverAlMenu() {

    sessionStorage.removeItem("pedidoTemp");
    sessionStorage.removeItem("pedidoActual");

    $('#cart-count').text("0");

    window.location.href = "index.html";
}



