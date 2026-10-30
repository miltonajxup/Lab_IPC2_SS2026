import { Component } from '@angular/core';
import { Menu } from '../componentes/menu/menu';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Menu, Header],
  selector: 'app-layout-super-admin',
  styles: ``,
  templateUrl: './layout-super-admin.html',
})
export class LayoutSuperAdmin {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
}
