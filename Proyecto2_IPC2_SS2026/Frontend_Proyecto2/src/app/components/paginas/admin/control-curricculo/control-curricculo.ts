import { Component } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, Menu, RouterLink],
  selector: 'app-control-curricculo',
  styles: ``,
  templateUrl: './control-curricculo.html',
})
export class ControlCurricculo {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
