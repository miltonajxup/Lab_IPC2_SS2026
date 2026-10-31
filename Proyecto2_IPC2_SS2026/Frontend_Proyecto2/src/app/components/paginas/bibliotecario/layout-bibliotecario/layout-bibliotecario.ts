import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { MenuBibliotecario } from '../menu-bibliotecario/menu-bibliotecario';

@Component({
  imports: [Header, MenuBibliotecario,],
  selector: 'app-layout-bibliotecario',
  styles: ``,
  templateUrl: './layout-bibliotecario.html',
})
export class LayoutBibliotecario {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
}
