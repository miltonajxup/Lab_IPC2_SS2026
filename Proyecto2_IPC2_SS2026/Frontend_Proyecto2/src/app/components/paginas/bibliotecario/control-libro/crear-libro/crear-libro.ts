import { Component } from '@angular/core';
import { Header } from '../../../../header/header';
import { MenuBibliotecario } from '../../menu-bibliotecario/menu-bibliotecario';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { FormLibro } from '../form/form';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, MenuBibliotecario, FormLibro, RouterLink],
  selector: 'app-crear-libro',
  styles: ``,
  templateUrl: './crear-libro.html',
})
export class CrearLibro {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
}
