document.addEventListener("DOMContentLoaded", () => {
    listarPedidos();
});

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

<tr class="fila-producto">

    <td>
        <span class="fw-semibold">
            ${p.codigo}
        </span>
    </td>

    <td>

        <div class="fw-semibold">
            ${p.cliente}
        </div>

    </td>

    <td>${p.fecha}</td>

    <td>

        <span class="badge-metodo">

            ${p.metodoPago}

        </span>

    </td>

    <td>

        <span class="precio-admin">

            S/ ${Number(p.total).toFixed(2)}

        </span>

    </td>

    <td>

        <span class="badge ${
            p.estado === "RECIBIDO"
                ? "badge-recibido"
                : p.estado === "EN_PREPARACION"
                    ? "badge-preparacion"
                    : p.estado === "LISTO"
                        ? "badge-listo"
                        : "badge-entregado"
        }">

            ${
                p.estado === "RECIBIDO"
                    ? "Recibido"
                    : p.estado === "EN_PREPARACION"
                        ? "En preparación"
                        : p.estado === "LISTO"
                            ? "Listo"
                            : "Entregado"
            }

        </span>

    </td>

    <td>

        <div class="d-flex gap-2">

            <button
                class="btn btn-editar"

                title="Ver detalle"

                onclick="verDetalle(${p.idPedido})">

                <i class="bi bi-eye"></i>

            </button>

            <div class="dropdown">

                <button
                    class="btn btn-inactivar dropdown-toggle"

                    data-bs-toggle="dropdown">

                    Estado

                </button>

                <ul class="dropdown-menu shadow border-0 rounded-3">

                    <li>

                        <a class="dropdown-item"

                           onclick="setEstado(${p.idPedido},'RECIBIDO')">

                            Recibido

                        </a>

                    </li>

                    <li>

                        <a class="dropdown-item"

                           onclick="setEstado(${p.idPedido},'EN_PREPARACION')">

                            En preparación

                        </a>

                    </li>

                    <li>

                        <a class="dropdown-item"

                           onclick="setEstado(${p.idPedido},'LISTO')">

                            Listo

                        </a>

                    </li>

                    <li>

                        <a class="dropdown-item"

                           onclick="setEstado(${p.idPedido},'ENTREGADO')">

                            Entregado

                        </a>

                    </li>

                </ul>

            </div>

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