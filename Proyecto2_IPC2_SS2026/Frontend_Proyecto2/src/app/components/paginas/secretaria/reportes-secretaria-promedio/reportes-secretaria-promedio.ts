import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { ReportePromedios } from '../../reportes/reporte-promedios/reporte-promedios';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, MenuSecretaria, ReportePromedios],
  selector: 'app-reportes-secretaria-promedio',
  styles: ``,
  templateUrl: './reportes-secretaria-promedio.html',
})
export class ReportesSecretariaPromedio {
  rol: RolUsuario = RolUsuario.SECRETARIA;
}
