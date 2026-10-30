import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { ReporteSolvencia } from '../../reportes/reporte-solvencia/reporte-solvencia';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, Menu, ReporteSolvencia],
  selector: 'app-reportes-admin-solvencia',
  styles: ``,
  templateUrl: './reportes-admin-solvencia.html',
})
export class ReportesAdminSolvencia {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
