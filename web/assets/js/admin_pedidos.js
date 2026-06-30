document.addEventListener("DOMContentLoaded", () => {
    listarPedidos();
});

// =========================
// LISTAR PEDIDOS
// =========================
function listarPedidos() {

    fetch("AppController?action=listarPedidosAdmin")
        .then(res => res.json())
        .then(data => {

            if (!data.success) {
                Swal.fire("Error", data.message, "error");
                return;
            }

            const tbody = document.querySelector("#tabla-pedidos tbody");
            tbody.innerHTML = "";

            data.data.forEach(p => {

                tbody.innerHTML += `
                    <tr>

                        <td>${p.codigo}</td>
                        <td>${p.cliente}</td>
                        <td>${p.fecha}</td>
                        <td>${p.metodoPago}</td>
                        <td>S/ ${p.total}</td>

                        <td>
                            <span class="badge 
                                ${p.estado === 'RECIBIDO' ? 'bg-secondary' :
                                  p.estado === 'EN_PREPARACION' ? 'bg-warning text-dark' :
                                  p.estado === 'LISTO' ? 'bg-primary' :
                                  'bg-success'}">

                                ${p.estado}
                            </span>
                        </td>

                        <td>

                            <button class="btn btn-primary btn-sm"
                                onclick="verDetalle(${p.idPedido})">
                                Ver
                            </button>

                            <div class="dropdown d-inline">

                                <button class="btn btn-warning btn-sm dropdown-toggle"
                                    data-bs-toggle="dropdown">
                                    Estado
                                </button>

                                <ul class="dropdown-menu">

                                    <li><a class="dropdown-item" onclick="setEstado(${p.idPedido}, 'RECIBIDO')">Recibido</a></li>

                                    <li><a class="dropdown-item" onclick="setEstado(${p.idPedido}, 'EN_PREPARACION')">En preparación</a></li>

                                    <li><a class="dropdown-item" onclick="setEstado(${p.idPedido}, 'LISTO')">Listo</a></li>

                                    <li><a class="dropdown-item" onclick="setEstado(${p.idPedido}, 'ENTREGADO')">Entregado</a></li>

                                </ul>

                            </div>

                        </td>

                    </tr>
                `;
            });

        })
        .catch(err => {
            console.error(err);
            Swal.fire("Error", "No se pudieron cargar los pedidos", "error");
        });
}

// =========================
// VER DETALLE
// =========================
function verDetalle(id) {

    fetch("AppController?action=detallePedido&idPedido=" + id)
        .then(res => res.json())
        .then(data => {

            if (!data.success) {
                Swal.fire("Error", data.message, "error");
                return;
            }

            let p = data.data;

            let html = `
                <p><b>Código:</b> ${p.codigo}</p>
                <p><b>Cliente:</b> ${p.nombreCliente}</p>
                <p><b>DNI:</b> ${p.dni}</p>
                <p><b>Teléfono:</b> ${p.telefono}</p>
                <p><b>Dirección:</b> ${p.direccionEntrega}</p>

                <hr>

                <h5>Productos</h5>
            `;

            p.detalles.forEach(d => {
                html += `
                    <p>
                        ${d.producto} - 
                        ${d.cantidad} x S/ ${d.precioUnitario}
                        = S/ ${d.subTotal}
                    </p>
                `;
            });

            html += `<hr><h5>Total: S/ ${p.total}</h5>`;

            document.getElementById("contenidoPedido").innerHTML = html;

            new bootstrap.Modal(document.getElementById("modalPedido")).show();
        });
}

// =========================
// CAMBIAR ESTADO (PRO)
// =========================
function setEstado(idPedido, estado) {

    fetch("AppController?action=actualizarEstadoPedido", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `idPedido=${idPedido}&estado=${estado}`
    })
    .then(res => res.json())
    .then(data => {

        if (data.success) {

            Swal.fire({
                icon: "success",
                title: "Estado actualizado",
                text: estado
            });

            listarPedidos();

        } else {
            Swal.fire("Error", data.message, "error");
        }

    })
    .catch(err => {
        console.error(err);
        Swal.fire("Error", "No se pudo actualizar", "error");
    });
}