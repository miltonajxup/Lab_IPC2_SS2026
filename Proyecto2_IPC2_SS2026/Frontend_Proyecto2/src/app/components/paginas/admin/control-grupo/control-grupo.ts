import { Component, OnInit } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Menu } from '../componentes/menu/menu';
import { Header } from '../../../header/header';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Menu, Header, RouterLink],
  selector: 'app-control-grupo',
  styles: ``,
  templateUrl: './control-grupo.html',
})
export class ControlGrupo {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
