import { Component, OnInit, signal } from '@angular/core';
import { GrupoSeccionDB } from '../../../modelos/admin/grupo-seccion-db';
import { GrupoService } from '../../../../servicios/grupo-service';
import { ReporteSercvice } from '../../../../servicios/reporte-service';
import { Promedio } from '../../../modelos/admin/reporte/promedio';

@Component({
  imports: [],
  selector: 'app-reporte-promedios',
  styles: ``,
  templateUrl: './reporte-promedios.html',
})
export class ReportePromedios implements OnInit {
  pagina = signal<number>(1);
  mensajeError = signal<string>('');
  mensaje = signal<string>('');
  gruposAnuales = signal<GrupoSeccionDB[]>([]);
  promedios = signal<Promedio[]>([]);
  idGrupo!: number;
  
  constructor(private grupoService: GrupoService, private reporteService: ReporteSercvice) {}

  ngOnInit(): void {
    this.grupoService.getTodosLosGrupos(this.pagina()).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        this.gruposAnuales.set(grupos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  cambiarPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.grupoService.getTodosLosGrupos(pagina).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        if (grupos.length === 0) {
          return;
        }
        this.gruposAnuales.set(grupos);
        this.pagina.set(pagina);
      }
    });
  }

  buscar(grupo: GrupoSeccionDB): void {
    this.mensajeError.set('');
    this.idGrupo = grupo.id;
    this.reporteService.getReportePromedio(this.idGrupo).subscribe({
      next: (promedio: Promedio[]) => {
        this.promedios.set(promedio);
        this.mensaje.set( 'Promedio de' + grupo.nombreGrado + " " + grupo.nivelAcademico + " Seccion: " + grupo.seccion);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  exportar(): string {
    if (this.idGrupo === null) {
      return '';
    }
    return this.reporteService.exportarPromedios(this.idGrupo);
  }
}
