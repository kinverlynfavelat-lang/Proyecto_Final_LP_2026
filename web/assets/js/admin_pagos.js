document.addEventListener("DOMContentLoaded", () => {
    listarPagos();
});

// =========================
// LISTAR PAGOS
// =========================
function listarPagos() {

    fetch("PagoController?action=listarPagosAdmin")
            .then(res => res.json())
            .then(data => {

                const tbody = document.querySelector("#tabla-pagos tbody");
                tbody.innerHTML = "";

                if (!data.success) {
                    Swal.fire("Error", data.message, "error");
                    return;
                }

                data.data.forEach(p => {

                    tbody.innerHTML += `
                    <tr>

                        <td>${p.idPedido}</td>
                        <td>${p.metodo}</td>

                        <td>
                            <span class="badge 
                                ${p.estadoPago === 'PENDIENTE' ? 'bg-warning text-dark' :
                            p.estadoPago === 'VALIDADO' ? 'bg-success' :
                            'bg-danger'}">

                                ${p.estadoPago}

                            </span>
                        </td>

                        <td>${p.fechaPago || ''}</td>

                        <td>
                            <img src="${p.comprobante}"
     width="70"
     class="rounded shadow"
     style="cursor:pointer"
     onclick="verComprobante('${p.comprobante}')">
                        </td>

                        <td>

                            <button class="btn btn-success btn-sm"
                                onclick="validarPago(${p.idPago}, 'VALIDADO')">
                                Aprobar
                            </button>

                            <button class="btn btn-danger btn-sm"
                                onclick="validarPago(${p.idPago}, 'RECHAZADO')">
                                Rechazar
                            </button>

                        </td>

                    </tr>
                `;
                });

            });
}

// =========================
// VER IMAGEN
// =========================
function verComprobante(img) {

    Swal.fire({
        imageUrl: img,
        imageWidth: 400,
        imageAlt: "Comprobante"
    });

}

// =========================
// VALIDAR PAGO
// =========================
function validarPago(idPago, estado) {

    fetch("PagoController?action=validarPago", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `idPago=${idPago}&estado=${estado}`
    })
            .then(res => res.json())
            .then(data => {

                if (data.success) {
                    Swal.fire("Correcto", "Pago actualizado", "success");
                    listarPagos();
                } else {
                    Swal.fire("Error", data.message, "error");
                }

            });

}
function verComprobante(img) {

    Swal.fire({
        imageUrl: img,
        imageWidth: 500,
        imageHeight: 600,
        showConfirmButton: false
    });

}
