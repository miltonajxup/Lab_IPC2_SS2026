import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { ReporteIngresos } from '../../reportes/ingresos/ingresos';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, Menu, ReporteIngresos],
  selector: 'app-reporte-admin-ingresos',
  styles: ``,
  templateUrl: './reporte-admin-ingresos.html',
})
export class ReporteAdminIngresos {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
