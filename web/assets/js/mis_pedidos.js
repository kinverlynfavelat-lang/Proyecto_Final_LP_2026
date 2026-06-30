document.addEventListener("DOMContentLoaded", () => {
    cargarMisPedidos();
});

function cargarMisPedidos() {

    fetch("AppController?action=historialPedidos")
        .then(res => res.json())
        .then(data => {

            if (!data.success) {
                Swal.fire("Error", data.message, "error");
                return;
            }

            const tbody = document.getElementById("tabla-pedidos");
            tbody.innerHTML = "";

            data.data.forEach(p => {

                tbody.innerHTML += `
                    <tr>
                        <td>${p.codigo}</td>
                        <td>${p.fecha}</td>
                        <td>${p.estadoPedido}</td>
                        <td>${p.metodoPago}</td>
                        <td>S/ ${p.total}</td>
                    </tr>
                `;
            });
        })
        .catch(err => {
            console.error(err);
            Swal.fire("Error", "No se pudieron cargar los pedidos", "error");
        });
}