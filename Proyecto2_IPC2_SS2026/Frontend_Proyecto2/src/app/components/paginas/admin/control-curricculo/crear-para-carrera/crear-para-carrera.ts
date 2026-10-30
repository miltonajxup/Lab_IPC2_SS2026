import { Component, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { Grado } from '../../../../modelos/admin/grado';
import { Carrera } from '../../../../modelos/admin/carrera';
import { Curso } from '../../../../modelos/admin/curso';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { CursoService } from '../../../../../servicios/curso-service';
import { CarreraService } from '../../../../../servicios/carrera-service';
import { CurriculoRequest } from '../../../../modelos/admin/curriculo-request';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, Menu, ReactiveFormsModule, RouterLink],
  selector: 'app-crear-para-carrera',
  styles: ``,
  templateUrl: './crear-para-carrera.html',
})
export class CrearCurriculoCarrera {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  paginaCursos = signal<number>(1);
  paginaCarreras = signal<number>(1);
  operacionCompleatada = signal<boolean>(false);
  mensajeError = signal<string>('');
  grados = signal<Grado[]>([]);
  carreras = signal<Carrera[]>([]);
  cursos = signal<Curso[]>([]);
  curriculoForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder, 
    private grupoService: GrupoService, 
    private cursoService: CursoService,
    private carreraService: CarreraService) {}

  ngOnInit(): void {
    this.curriculoForm = this.formBuilder.group({
      cursoId: [null, Validators.required], 
      gradoId: [null, Validators.required], 
      carreraId: [null, Validators.required]
    });
    this.carreraService.getCarrerasActivas(this.paginaCarreras()).subscribe({
      next: (carreras: Carrera[]) => {
        this.carreras.set(carreras);
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

  avanzarPaginaCarreras(avanzar: number): void {
    const pagina = this.paginaCarreras() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.carreraService.getCarrerasActivas(pagina).subscribe({
      next: (carreras: Carrera[]) => {
        if (carreras.length === 0) {
          return;
        }
        this.carreras.set(carreras);
        this.paginaCursos.set(pagina);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setCarrera(carrera: Carrera): void {
    this.curriculoForm.patchValue({
      carreraId: carrera.codigo,
      gradoId: null
    });
    this.grados.set(carrera.grados!);
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
    this.grupoService.agregarCurriculoCarrera(curriculo).subscribe({
      next: () => {
        this.curriculoForm.reset();
        this.operacionCompleatada.set(true);
        this.grados.set([]);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
