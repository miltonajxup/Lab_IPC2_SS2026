import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { ReporteHistorialPagosEstudiante } from '../../reportes/historial-pagos-estudiante/historial-pagos-estudiante';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, Menu, ReporteHistorialPagosEstudiante],
  selector: 'app-reportes-admin-historial-pago',
  styles: ``,
  templateUrl: './reportes-admin-historial-pago.html',
})
export class ReportesAdminHistorialPago {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
}
