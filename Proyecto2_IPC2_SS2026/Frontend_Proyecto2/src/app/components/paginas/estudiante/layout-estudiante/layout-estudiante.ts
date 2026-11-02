import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuEstudiante } from '../menu-estudiante/menu-estudiante';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, MenuEstudiante],
  selector: 'app-layout-estudiante',
  styles: ``,
  templateUrl: './layout-estudiante.html',
})
export class LayoutEstudiante {
  rol: RolUsuario = RolUsuario.ESTUDIANTE;
}
