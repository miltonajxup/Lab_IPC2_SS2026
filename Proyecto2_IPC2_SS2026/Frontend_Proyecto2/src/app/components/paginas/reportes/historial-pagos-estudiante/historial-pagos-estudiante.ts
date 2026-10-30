import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReporteSercvice } from '../../../../servicios/reporte-service';
import { HistorialPagoEstudiante } from '../../../modelos/admin/reporte/historial-pago-estudiante';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-historial-pagos-estudiante',
  styles: ``,
  templateUrl: './historial-pagos-estudiante.html',
})
export class ReporteHistorialPagosEstudiante implements OnInit {
  mensajeError = signal<string>('');
  mensaje = signal<string>('');
  busquedaForm!: FormGroup;
  pagina = signal<number>(1);
  historial = signal<HistorialPagoEstudiante | undefined>(undefined);
  idEstudiante!: string;
  fechaInicio!: Date;
  fechaFin!: Date;

  constructor(private formBuilder: FormBuilder, private reporteService: ReporteSercvice) {}

  ngOnInit(): void {
    this.busquedaForm = this.formBuilder.group({
      idEstudiante: [null, Validators.required],
      fechaInicial: [null], 
      fechaFin: [null]
    });
  }

  buscar(): void {
    this.mensajeError.set('');
    this.mensaje.set('');
    this.pagina.set(1);
    this.idEstudiante = this.busquedaForm.value.idEstudiante;
    this.fechaInicio = this.busquedaForm.value.fechaInicial;
    this.fechaFin = this.busquedaForm.value.fechaFin;
    this.decidirLlamada(this.pagina());
  }

  private decidirLlamada(pagina: number): void {
    if (this.fechaInicio === null || this.fechaFin === null) {
      this.llamarHistorialSinFechas(pagina);
    } else {
      this.llamarHistorialConFechas(pagina);
    }
  }

  moverPagina(paso: number): void {
    this.mensajeError.set('');
    const pagina: number = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.decidirLlamada(pagina);
  }

  private llamarHistorialSinFechas(pagina: number): void {
    this.reporteService.getReportePagosSinFecha(this.idEstudiante, pagina).subscribe({
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

  private llamarHistorialConFechas(pagina: number): void {
    this.reporteService.getReportePagosFechas(this.idEstudiante, this.fechaInicio, this.fechaFin, pagina).subscribe({
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
    })
  }

  exportar(): string {
    if (this.fechaInicio == null || this.fechaFin == null) {
      return this.reporteService.exportarPagosSinFecha(this.idEstudiante, this.pagina());
    } else {
      return this.reporteService.exportarPagosEntreFechas(this.idEstudiante, this.fechaInicio, this.fechaFin, this.pagina());
    }
  }

}
