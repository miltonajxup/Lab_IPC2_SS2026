import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Menu } from '../componentes/menu/menu';
import { ReportePromedios } from '../../reportes/reporte-promedios/reporte-promedios';

@Component({
  imports: [Header, Menu, ReportePromedios],
  selector: 'app-reportes-admin',
  styles: ``,
  templateUrl: './reportes-admin.html',
})
export class ReportesAdmin {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
