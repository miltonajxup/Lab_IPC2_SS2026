/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
var claseMostrada;

document.addEventListener("DOMContentLoaded", () => {
        ocultarElemento('sucursales-en-usuario');
});

function ocultarElemento(id) {
    const elemento = document.getElementById(id);
    if (elemento) {
        elemento.style.display = 'none';
    }
}

function mostrarOpciones(id) {
    if (claseMostrada) {
        document.getElementById(claseMostrada).setAttribute('style', 'display: none');
    }
    claseMostrada = id;
    document.getElementById(id).setAttribute('style', 'display: block');
    
    ocultarElemento('usuarios-admin-sucursal');
}

function mostrarSucursalesEnUsuario(contenedor) {
    const sucursales = document.getElementById('sucursales-en-usuario');
    if (contenedor.value === 'ADMINISTRADOR_SUCURSAL') {
        sucursales.style.display = 'block';
    } else {
        sucursales.style.display = 'none';
    }
}

function agregarElemento(valor, idInput, identificador, idInputElegido) {
    document.getElementById(idInput).value = valor;
    document.getElementById(idInputElegido).value = identificador;
}
