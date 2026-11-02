import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Libro } from '../../../modelos/biblioteca/libro';
import { LibroService } from '../../../../servicios/libro-service';
import { MenuBibliotecario } from '../menu-bibliotecario/menu-bibliotecario';
import { RouterLink } from '@angular/router';
import { ListaLibros } from '../lista-libros/lista-libros';

@Component({
  imports: [Header, MenuBibliotecario, RouterLink, ListaLibros],
  selector: 'app-control-libro',
  styles: ``,
  templateUrl: './control-libro.html',
})
export class ControlLibro {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
}
