import { Component, signal } from '@angular/core';
import { Ingreso } from '../../../modelos/admin/reporte/ingreso';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ReporteSercvice } from '../../../../servicios/reporte-service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-reporte-ingresos',
  styles: ``,
  templateUrl: './ingresos.html',
})
export class ReporteIngresos {
  busquedaForm!: FormGroup;
  ingresos = signal<Ingreso[]>([]);
  mensajeError = signal<string>('');
  fechaInicial!: Date;
  fechaFin!: Date;

  constructor(private formBuilder: FormBuilder, private reporteService: ReporteSercvice) {}

  ngOnInit(): void {
    this.busquedaForm = this.formBuilder.group({
      fechaInicial: [null], 
      fechaFin: [null]
    });
  }

  buscar(): void {
    this.fechaInicial = this.busquedaForm.value.fechaInicial;
    this.fechaFin = this.busquedaForm.value.fechaFin;
    if (this.fechaInicial == null || this.fechaFin == null) {
      this.reporteService.getIngresosHistoricos().subscribe({
        next: (ingresos: Ingreso[]) => {
          this.ingresos.set(ingresos);
        }, 
        error: (error: any) =>{
          this.mensajeError.set(error.error);
        }
      });
    } else {
      this.reporteService.getIngresosPorFecha(this.fechaInicial, this.fechaFin).subscribe({
        next: (ingresos: Ingreso[]) => {
          this.ingresos.set(ingresos);
        }, 
        error: (error: any) =>{
          this.mensajeError.set(error.error);
        }
      });
    }
  }

  exportar(): string {
    if (this.fechaInicial == null || this.fechaFin == null) {
      return this.reporteService.exportarIngresos();
    } else {
      return this.reporteService.exportarIngresosFechas(this.fechaInicial, this.fechaFin);
    }
  }

}
