import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { ReportePromedios } from '../../reportes/reporte-promedios/reporte-promedios';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, Menu, ReportePromedios],
  selector: 'app-reportes-sup-adm-promedio',
  styles: ``,
  templateUrl: './reportes-sup-adm-promedio.html',
})
export class ReportesSupAdmPromedio {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
}
