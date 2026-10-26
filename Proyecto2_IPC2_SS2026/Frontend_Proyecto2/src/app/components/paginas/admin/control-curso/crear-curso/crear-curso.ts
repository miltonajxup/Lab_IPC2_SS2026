import { Component } from '@angular/core';
import { FormCurso } from '../form-curso/form-curso';
import { Menu } from '../../componentes/menu/menu';
import { Header } from '../../../../header/header';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { RouterLink } from '@angular/router';

@Component({
  imports: [FormCurso, Menu, Header, RouterLink],
  selector: 'app-crear-curso',
  styles: ``,
  templateUrl: './crear-curso.html',
})
export class CrearCurso {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
