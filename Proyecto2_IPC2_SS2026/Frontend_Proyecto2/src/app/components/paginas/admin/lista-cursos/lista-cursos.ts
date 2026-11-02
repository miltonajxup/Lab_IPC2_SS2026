import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { Curso } from '../../../modelos/admin/curso';
import { CursoService } from '../../../../servicios/curso-service';

@Component({
  imports: [],
  selector: 'app-lista-cursos',
  styles: ``,
  templateUrl: './lista-cursos.html',
})
export class ListaCursos implements OnInit {
  @Input()
  idCurso!: number;

  @Output()
  cursoSeleccionado = new EventEmitter<number>();

  cursos = signal<Curso[]>([]);
  paginaCursos = signal<number>(1);
  mensajeError = signal<string>('');

  constructor(private cursoService: CursoService) {}
  
  ngOnInit(): void {
    this.cursoService.getCursos(this.paginaCursos()).subscribe({
      next: (cursos: Curso[]) => {
        this.cursos.set(cursos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  avanzarPaginaCursos(avanzar: number): void {
    const pagina = this.paginaCursos() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.cursoService.getCursos(pagina).subscribe({
      next: (cursos: Curso[]) => {
        if (cursos.length === 0) {
          return;
        }
        this.cursos.set(cursos);
        this.paginaCursos.set(pagina);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  elegirCurso(id: number): void {
    this.cursoSeleccionado.emit(id);
  }
}
