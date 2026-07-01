document.addEventListener("DOMContentLoaded", () => {
    listarPagos();
});


function listarPagos() {

    fetch("PagoController?action=listarPagosAdmin")
        .then(res => res.json())
        .then(data => {

            if (!data.success) {
                Swal.fire("Error", data.message, "error");
                return;
            }

            const tbody = document.querySelector("#tabla-pagos tbody");
            tbody.innerHTML = "";

            data.data.forEach(p => {

                let badge = "";

                switch (p.estadoPago) {

                    case "PENDIENTE":
                        badge = `
                        <span class="badge rounded-pill"
                              style="
                                background:#fff4d4;
                                color:#8a6500;
                                border:1px solid #ffd35a;
                              ">
                            Pendiente
                        </span>`;
                        break;

                    case "VALIDADO":
                        badge = `
                        <span class="badge rounded-pill"
                              style="
                                background:#e9f8e7;
                                color:#2f6d2f;
                                border:1px solid #8bc98b;
                              ">
                            Validado
                        </span>`;
                        break;

                    default:
                        badge = `
                        <span class="badge rounded-pill"
                              style="
                                background:#fdeaea;
                                color:#b43b3b;
                                border:1px solid #f3a4a4;
                              ">
                            Rechazado
                        </span>`;
                }

                tbody.innerHTML += `

                <tr>

                    <td><b>#${p.idPedido}</b></td>

                    <td>${p.metodo}</td>

                    <td>${badge}</td>

                    <td>${p.fechaPago || "-"}</td>

                    <td>

                        <img
                            src="${p.comprobante}"
                            width="75"
                            class="rounded shadow-sm"
                            style="
                                cursor:pointer;
                                border:2px solid #ffe7a4;
                                transition:.25s;
                            "
                            onclick="verComprobante('${p.comprobante}')">

                    </td>

                    <td>

                        <button
                            class="btn btn-burger-success btn-sm me-2"
                            onclick="validarPago(${p.idPago}, 'VALIDADO')">

                            <i "></i>
                            Aprobar

                        </button>

                        <button
                            class="btn btn-burger-danger btn-sm"
                            onclick="validarPago(${p.idPago}, 'RECHAZADO')">

                            <i "></i>
                            Rechazar

                        </button>

                    </td>

                </tr>

                `;

            });

        })

        .catch(error => {

            console.error(error);

            Swal.fire(
                "Error",
                "No fue posible cargar los pagos.",
                "error"
            );

        });

}



function verComprobante(img){

    Swal.fire({

        title:"Comprobante de pago",

        imageUrl:img,

        imageWidth:450,

        imageAlt:"Comprobante",

        background:"#fffdf8",

        confirmButtonColor:"#ffc01b",

        confirmButtonText:"Cerrar"

    });

}


function validarPago(idPago, estado){

    fetch("PagoController?action=validarPago",{

        method:"POST",

        headers:{
            "Content-Type":"application/x-www-form-urlencoded"
        },

        body:`idPago=${idPago}&estado=${estado}`

    })

    .then(res=>res.json())

    .then(data=>{

        if(data.success){

            Swal.fire({

                icon:"success",

                title:"Pago actualizado",

                text:`Estado: ${estado}`,

                confirmButtonColor:"#ffc01b"

            });

            listarPagos();

        }else{

            Swal.fire("Error",data.message,"error");

        }

    });

}