import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { Grado } from '../../../../modelos/admin/grado';
import { CursoService } from '../../../../../servicios/curso-service';
import { Curso } from '../../../../modelos/admin/curso';
import { CurriculoRequest } from '../../../../modelos/admin/curriculo-request';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, Menu, ReactiveFormsModule, RouterLink],
  selector: 'app-crear-para-grado',
  styles: ``,
  templateUrl: './crear-para-grado.html',
})
export class CrearCurriculoGrado implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  paginaCursos = signal<number>(1);
  operacionCompleatada = signal<boolean>(false);
  mensajeError = signal<string>('');
  grados = signal<Grado[]>([]);
  cursos = signal<Curso[]>([]);
  curriculoForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder, 
    private grupoService: GrupoService, 
    private cursoService: CursoService) {}

  ngOnInit(): void {
    this.curriculoForm = this.formBuilder.group({
      cursoId: [null, Validators.required], 
      gradoId: [null, Validators.required]
    });
    this.grupoService.getGradosNormales().subscribe({
      next: (grados: Grado[]) => {
        this.grados.set(grados);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
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

  setGrado(id: number): void {
    this.curriculoForm.patchValue({ gradoId: id });
  }

  setCurso(id: number): void {
    this.curriculoForm.patchValue({ cursoId: id });
  }


  moverPaginaCursos(paso: number): void {
    const pagina = this.paginaCursos() + paso;
    console.log(pagina);
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
      }
    })
  }

  submit(): void {
    this.operacionCompleatada.set(false);
    this.mensajeError.set('');
    const curriculo: CurriculoRequest = this.curriculoForm.value;
    this.grupoService.agregarCurriculoGrado(curriculo).subscribe({
      next: () => {
        this.curriculoForm.reset();
        this.operacionCompleatada.set(true);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
