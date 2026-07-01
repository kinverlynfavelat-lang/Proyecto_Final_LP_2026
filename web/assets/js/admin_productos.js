
document.addEventListener("DOMContentLoaded", () => {

    if (document.getElementById("tabla-productos")) {

        cargarTablaProductos();

    }

});

function cargarTablaProductos() {

fetch("ProductoController?action=listar")
        .then(res => res.json())

            .then(respuesta => {

                if (!respuesta.success) {

                    Swal.fire("Error", respuesta.message, "error");
                    return;

                }

                const tbody = document.querySelector("#tabla-productos tbody");

                tbody.innerHTML = "";

                respuesta.data.forEach(producto => {

                    tbody.innerHTML += `

<tr class="fila-producto">

    <td>
        <img
            src="${producto.imagen}"
            class="img-admin-producto">
    </td>

    <td>

        <div class="fw-semibold">
            ${producto.nombre}
        </div>

    </td>

    <td class="text-muted">

        ${producto.descripcion}

    </td>

    <td>

        <span class="precio-admin">
            S/ ${Number(producto.precio).toFixed(2)}
        </span>

    </td>

    <td>

        <span class="badge ${
            producto.estadoProducto === "ACTIVO"
                ? "badge-activo"
                : "badge-inactivo"
        }">

            ${
                producto.estadoProducto === "ACTIVO"
                    ? "● Activo"
                    : "● Inactivo"
            }

        </span>

    </td>

    <td>

        <div class="d-flex gap-2">

            <button
                class="btn btn-editar"

                onclick="editarProducto(${producto.idProducto})">

                <i class="bi bi-pencil-square"></i>

            </button>

            <button

                class="btn ${
                    producto.estadoProducto === "ACTIVO"
                        ? "btn-inactivar"
                        : "btn-activar"
                }"

                onclick="cambiarEstado(${producto.idProducto}, '${producto.estadoProducto}')">

                <i class="bi ${
                    producto.estadoProducto === "ACTIVO"
                        ? "bi-eye-slash"
                        : "bi-eye"
                }"></i>

            </button>

        </div>

    </td>

</tr>

`;

                });

            })

            .catch(error => {

                console.error(error);

            });

}

function editarProducto(idProducto) {

    fetch("ProductoController?action=buscar&idProducto=" + idProducto)

            .then(res => res.json())

            .then(producto => {

                document.getElementById("action").value = "editar";

                document.getElementById("tituloModal").textContent = "Editar Producto";

                document.getElementById("idProducto").value = producto.idProducto;

                document.getElementById("nombre").value = producto.nombre;

                document.getElementById("descripcion").value = producto.descripcion;

                document.getElementById("precio").value = producto.precio;

                $("#modalProducto").modal("show");

            })

            .catch(error => {

                console.error(error);

                Swal.fire("Error", "No fue posible obtener el producto", "error");

            });

}

function cambiarEstado(idProducto, estadoActual) {

    const nuevoEstado =
            estadoActual === "ACTIVO"
            ? "INACTIVO"
            : "ACTIVO";

    Swal.fire({
        title: "¿Deseas continuar?",
        text: "Se cambiará el estado del producto.",
        icon: "question",
        showCancelButton: true,
        confirmButtonText: "Sí",
        cancelButtonText: "Cancelar"

    }).then((result) => {

        if (!result.isConfirmed)
            return;

        fetch("ProductoController", {

            method: "POST",

            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },

            body:
                    "action=cambiarEstado"
                    + "&idProducto=" + idProducto
                    + "&estadoProducto=" + nuevoEstado

        })

                .then(res => res.json())

                .then(data => {

                    console.log(data);

                    if (data.success) {

                        Swal.fire(
                                "Correcto",
                                data.message,
                                "success"
                                );

                        cargarTablaProductos();

                    } else {

                        Swal.fire(
                                "Error",
                                data.message,
                                "error"
                                );

                    }

                })

                .catch(error => {

                    console.error(error);

                });

    });

}

function abrirModalNuevo() {

    document.getElementById("formProducto").reset();

    document.getElementById("action").value = "guardar";

    document.getElementById("idProducto").value = "";

    document.getElementById("tituloModal").textContent = "Nuevo Producto";

    $("#modalProducto").modal("show");

}
// guardar producto

document.getElementById("formProducto").addEventListener("submit", function (e) {

    e.preventDefault();

    const formData = new FormData(this);

    fetch("ProductoController", {

        method: "POST",

        body: formData

    })

            .then(res => res.json())

            .then(data => {
        
                if (data.success) {

                    Swal.fire({

                        icon: "success",

                        title:
                                formData.get("action") === "guardar"
                                ? "Producto registrado"
                                : "Producto actualizado",

                        text: data.message

                    });
                    $("#modalProducto").modal("hide");

                    cargarTablaProductos();

                } else {

                    Swal.fire("Error", data.message, "error");

                }

            })

            .catch(error => {

                console.error(error);

                Swal.fire("Error", "Ocurrió un error.", "error");

            });

});