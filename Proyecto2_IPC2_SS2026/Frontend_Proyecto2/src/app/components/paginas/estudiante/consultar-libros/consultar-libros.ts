import { Component } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Header } from '../../../header/header';
import { MenuEstudiante } from '../menu-estudiante/menu-estudiante';
import { ListaLibros } from '../../bibliotecario/lista-libros/lista-libros';

@Component({
  imports: [Header, MenuEstudiante, ListaLibros],
  selector: 'app-consultar-libros',
  styles: ``,
  templateUrl: './consultar-libros.html',
})
export class ConsultarLibros {
  rol: RolUsuario = RolUsuario.ESTUDIANTE;
}
