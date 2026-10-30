import { Component, signal } from '@angular/core';
import { GrupoSeccionDB } from '../../../modelos/admin/grupo-seccion-db';
import { Solvencia } from '../../../modelos/admin/reporte/solvencia';
import { GrupoService } from '../../../../servicios/grupo-service';
import { ReporteSercvice } from '../../../../servicios/reporte-service';

@Component({
  imports: [],
  selector: 'app-reporte-solvencia',
  styles: ``,
  templateUrl: './reporte-solvencia.html',
})
export class ReporteSolvencia {
  pagina = signal<number>(1);
  paginaSolvencia = signal<number>(1);
  mensajeError = signal<string>('');
  mensaje = signal<string>('');
  gruposAnuales = signal<GrupoSeccionDB[]>([]);
  solvencias = signal<Solvencia[]>([]);
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
    this.grupoService.getGruposDelAño(pagina).subscribe({
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
    this.mensaje.set('');
    this.idGrupo = grupo.id;
    this.paginaSolvencia.set(1);
    this.llamarReportes(this.paginaSolvencia());
  }

  cambiarPaginaSolvencia(paso: number): void {
    const pagina = this.paginaSolvencia() + paso;
    if (pagina < 1) {
      return;
    }
    this.llamarReportes(pagina);
  }

  private llamarReportes(pagina: number): void {
    this.reporteService.getReporteSolventes(this.idGrupo, pagina).subscribe({
      next: (solvencias: Solvencia[]) => {
        this.solvencias.set(solvencias);
        if (solvencias.length === 0) {
          this.paginaSolvencia.set(1);
          this.mensaje.set('No hay estudianes en esta seccion');
        } else {
          this.paginaSolvencia.set(pagina);
        }
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  exportar(): string {
    return this.reporteService.exportarSolvencias(this.idGrupo, this.paginaSolvencia());
  }

}
