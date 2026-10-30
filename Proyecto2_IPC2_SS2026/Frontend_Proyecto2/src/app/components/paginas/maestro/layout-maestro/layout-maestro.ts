import { Component } from '@angular/core';
import { MenuMaestro } from '../componentes/menu-maestro/menu-maestro';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [MenuMaestro, Header],
  selector: 'app-layout-maestro',
  styles: ``,
  templateUrl: './layout-maestro.html',
})
export class LayoutMaestro {
  rol: RolUsuario = RolUsuario.MAESTRO;
}
