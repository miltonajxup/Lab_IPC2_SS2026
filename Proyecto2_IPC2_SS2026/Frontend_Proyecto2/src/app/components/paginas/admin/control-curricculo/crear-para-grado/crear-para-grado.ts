import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { CurriculoRequest } from '../../../../modelos/admin/curriculo-request';
import { RouterLink } from '@angular/router';
import { ListaGradosNormales } from '../../lista-grados-normales/lista-grados-normales';
import { ListaCursos } from '../../lista-cursos/lista-cursos';

@Component({
  imports: [Header, Menu, ReactiveFormsModule, RouterLink, ListaGradosNormales, ListaCursos],
  selector: 'app-crear-para-grado',
  styles: ``,
  templateUrl: './crear-para-grado.html',
})
export class CrearCurriculoGrado implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  operacionCompleatada = signal<boolean>(false);
  mensajeError = signal<string>('');
  
  curriculoForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder, 
    private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.curriculoForm = this.formBuilder.group({
      cursoId: [null, Validators.required], 
      gradoId: [null, Validators.required]
    });
  }

  setGrado(id: number): void {
    this.curriculoForm.patchValue({ gradoId: id });
  }

  setCurso(id: number): void {
    this.curriculoForm.patchValue({ cursoId: id });
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
