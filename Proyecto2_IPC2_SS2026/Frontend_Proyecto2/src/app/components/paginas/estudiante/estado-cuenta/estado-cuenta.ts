import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuEstudiante } from '../menu-estudiante/menu-estudiante';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { HistorialPagoEstudiante } from '../../../modelos/admin/reporte/historial-pago-estudiante';
import { ReporteSercvice } from '../../../../servicios/reporte-service';
import { AuthService } from '../../../../servicios/auth-service';
import { TablaHisEstudiante } from '../../reportes/historial-pagos-estudiante/tabla-his-estudiante/tabla-his-estudiante';

@Component({
  imports: [Header, MenuEstudiante, TablaHisEstudiante],
  selector: 'app-estado-cuenta',
  styles: ``,
  templateUrl: './estado-cuenta.html',
})
export class EstadoCuenta implements OnInit {
  rol: RolUsuario = RolUsuario.ESTUDIANTE;
  historial = signal<HistorialPagoEstudiante | undefined>(undefined);
  pagina = signal<number>(1);
  mensajeError = signal<string>('');

  constructor(private historialService: ReporteSercvice, private authService: AuthService) {}

  ngOnInit(): void {
    this.llamarHistorial(this.pagina());
  }

  moverPagina(paso: number): void {
    this.mensajeError.set('');
    const pagina: number = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.llamarHistorial(pagina);
  }

  private llamarHistorial(pagina: number): void {
    this.historialService.getReportePagosSinFecha(this.authService.getUserId()!, pagina).subscribe({
      next: (historial: HistorialPagoEstudiante) => {
        if (historial.historial.length === 0) {
          return;
        }
        this.historial.set(historial);
        this.pagina.set(pagina);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
