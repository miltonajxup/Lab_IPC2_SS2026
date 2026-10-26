import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { Curso } from '../../../../modelos/admin/curso';
import { CursoService } from '../../../../../servicios/curso-service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormCurso } from '../form-curso/form-curso';

@Component({
  imports: [Header, Menu, FormCurso, RouterLink],
  selector: 'app-modificar-curso',
  styles: ``,
  templateUrl: './modificar-curso.html',
})
export class ModificarCurso implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  idCurso!: number;
  cursoParaActualizar = signal<Curso | undefined>(undefined);
  mensajeError = signal<string>('');

  constructor(private router: ActivatedRoute, private cursoService: CursoService) {}

  ngOnInit(): void {
    this.idCurso = this.router.snapshot.params['id'];
    this.cursoService.getCursoPorId(this.idCurso).subscribe({
      next: (curso: Curso) => {
        this.cursoParaActualizar.set(curso);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
