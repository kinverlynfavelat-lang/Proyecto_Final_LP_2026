
// ==========================
// CARGAR CARRITO (TABLA)
// ==========================
function cargarCarrito() {

    const tabla = document.querySelector("#tabla-carrito tbody");
    if (!tabla) return;

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
                        <button onclick="cambiarCantidad(${item.producto.idProducto}, -1)">-</button>
                        ${item.cantidad}
                        <button onclick="cambiarCantidad(${item.producto.idProducto}, 1)">+</button>
                    </td>

                    <td>S/ ${item.subTotal.toFixed(2)}</td>

                    <td>
                        <button class="btn btn-danger btn-sm"
                            onclick="eliminarItem(${item.producto.idProducto})">
                            X
                        </button>
                    </td>
                </tr>
            `;
        });

        actualizarUI(total, carrito.length);

    })
    .catch(err => console.error("Error carrito:", err));
}


// ==========================
// CAMBIAR CANTIDAD
// ==========================
function cambiarCantidad(idProducto, cambio) {

    fetch(`AppController?action=actualizarCantidad&idProducto=${idProducto}&cantidad=${cambio}`, {
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


// ==========================
// ELIMINAR ITEM
// ==========================
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


// ==========================
// ACTUALIZAR HEADER (TOTAL + COUNT)
// ==========================
async function actualizarHeaderCarrito() {

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
        console.error("Error header carrito:", error);
    }
}


// ==========================
// ACTUALIZAR UI CENTRALIZADO
// ==========================
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


// ==========================
// REFRESCAR TODO
// ==========================
function refrescarCarrito() {
    cargarCarrito();
    actualizarHeaderCarrito();
}


// ==========================
// INIT
// ==========================
document.addEventListener("DOMContentLoaded", () => {
    refrescarCarrito();
});