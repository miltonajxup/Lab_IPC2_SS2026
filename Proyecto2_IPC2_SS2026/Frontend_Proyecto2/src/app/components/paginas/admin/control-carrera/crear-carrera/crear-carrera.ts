import { Component } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Menu } from '../../componentes/menu/menu';
import { Header } from '../../../../header/header';
import { FormCarrera } from '../form-carrera/form-carrera';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Menu, Header, FormCarrera, RouterLink],
  selector: 'app-crear-carrera',
  styles: ``,
  templateUrl: './crear-carrera.html',
})
export class CrearCarrera {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
