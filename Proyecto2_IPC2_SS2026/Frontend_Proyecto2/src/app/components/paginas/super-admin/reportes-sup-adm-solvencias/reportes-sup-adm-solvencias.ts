import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { ReporteSolvencia } from '../../reportes/reporte-solvencia/reporte-solvencia';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, Menu, ReporteSolvencia],
  selector: 'app-reportes-sup-adm-solvencias',
  styles: ``,
  templateUrl: './reportes-sup-adm-solvencias.html',
})
export class ReportesSupAdmSolvencias {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
}
