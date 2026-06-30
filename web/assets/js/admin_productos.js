/*
 ====================================
 ADMIN PRODUCTOS
 ====================================
 */

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

<tr>

<td>
<img src="${producto.imagen}"
width="70"
class="rounded shadow">
</td>

<td>${producto.nombre}</td>

<td>${producto.descripcion}</td>

<td>S/ ${producto.precio}</td>

<td>

<span class="badge bg-${producto.estadoProducto === 'ACTIVO' ? 'success' : 'secondary'}">

${producto.estadoProducto}

</span>

</td>

<td>

<button
class="btn btn-warning btn-sm"
onclick="editarProducto(${producto.idProducto})">

Editar

</button>

<button
class="btn btn-${producto.estadoProducto === 'ACTIVO' ? 'danger' : 'success'} btn-sm"

onclick="cambiarEstado(${producto.idProducto},
'${producto.estadoProducto}')">

${producto.estadoProducto === 'ACTIVO'
                            ? 'Inactivar'
                            : 'Activar'}

</button>

</td>

</tr>

`;

                });

            })

            .catch(error => {

                console.error(error);

            });

}
// ======================================
// EDITAR PRODUCTO
// ======================================

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

    console.log(idProducto, estadoActual);

}
// ================================
// ABRIR MODAL NUEVO PRODUCTO
// ================================
function abrirModalNuevo() {

    document.getElementById("formProducto").reset();

    document.getElementById("action").value = "guardar";

    document.getElementById("idProducto").value = "";

    document.getElementById("tituloModal").textContent = "Nuevo Producto";

    $("#modalProducto").modal("show");

}
// ======================================
// GUARDAR PRODUCTO
// ======================================

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