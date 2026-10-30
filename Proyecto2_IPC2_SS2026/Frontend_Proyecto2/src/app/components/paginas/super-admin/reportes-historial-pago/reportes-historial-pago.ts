import { Component } from '@angular/core';
import { ReporteHistorialPagosEstudiante } from '../../reportes/historial-pagos-estudiante/historial-pagos-estudiante';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [ReporteHistorialPagosEstudiante, Header, Menu],
  selector: 'app-reportes-historial-pago',
  styles: ``,
  templateUrl: './reportes-historial-pago.html',
})
export class ReportSuperAdminHistorialPago {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
}
