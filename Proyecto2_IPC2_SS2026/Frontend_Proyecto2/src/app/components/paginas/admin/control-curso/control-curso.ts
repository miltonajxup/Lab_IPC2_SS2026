import { Component, OnInit, signal } from '@angular/core';
import { Menu } from '../componentes/menu/menu';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Curso } from '../../../modelos/admin/curso';
import { CursoService } from '../../../../servicios/curso-service';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Menu, Header, RouterLink],
  selector: 'app-control-curso',
  styles: ``,
  templateUrl: './control-curso.html',
})
export class ControlCurso implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  mensajeError = signal<string>('');
  pagina = signal<number>(1);
  cursos = signal<Curso[]>([]);

  constructor(private cursoService: CursoService) {}

  ngOnInit(): void {
    this.cursoService.getCursos(this.pagina()).subscribe({
      next: (cursos: Curso[]) => {
        this.cursos.set(cursos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  avanzarPagina(avanzar: number): void  {
    const pagina = this.pagina() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.cursoService.getCursos(pagina).subscribe({
      next: (cursos: Curso[]) => {
        if (cursos.length === 0) {
          return;
        }
        this.cursos.set(cursos);
        this.pagina.set(pagina);
      }
    });
  }
}
