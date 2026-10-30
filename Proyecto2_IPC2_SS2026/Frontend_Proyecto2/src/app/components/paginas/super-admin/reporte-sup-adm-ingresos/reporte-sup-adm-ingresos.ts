import { Component } from '@angular/core';
import { Menu } from '../componentes/menu/menu';
import { Header } from '../../../header/header';
import { ReporteIngresos } from '../../reportes/ingresos/ingresos';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Menu, Header, ReporteIngresos],
  selector: 'app-reporte-sup-adm-ingresos',
  styles: ``,
  templateUrl: './reporte-sup-adm-ingresos.html',
})
export class ReporteSupAdmIngresos {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
}
