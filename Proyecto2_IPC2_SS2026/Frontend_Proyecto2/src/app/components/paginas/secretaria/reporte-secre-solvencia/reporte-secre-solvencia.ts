import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { ReporteSolvencia } from '../../reportes/reporte-solvencia/reporte-solvencia';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, MenuSecretaria, ReporteSolvencia],
  selector: 'app-reporte-secre-solvencia',
  styles: ``,
  templateUrl: './reporte-secre-solvencia.html',
})
export class ReporteSecreSolvencia {
  rol: RolUsuario = RolUsuario.SECRETARIA;
}
