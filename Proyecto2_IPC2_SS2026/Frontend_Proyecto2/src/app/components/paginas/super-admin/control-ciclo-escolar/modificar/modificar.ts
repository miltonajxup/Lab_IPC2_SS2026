import { Component, Input } from '@angular/core';
import { Form } from '../form/form';
import { AñoLectivo } from '../../../../modelos/admin/año-lectivo';
import { Menu } from '../../componentes/menu/menu';
import { RouterLink } from '@angular/router';
import { Header } from '../../../../header/header';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';

@Component({
  imports: [Form, Menu, Header, RouterLink],
  selector: 'app-modificar',
  styles: ``,
  templateUrl: './modificar.html',
})
export class Modificar {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
  @Input()
  anioParaActuailzar!: AñoLectivo;

}
