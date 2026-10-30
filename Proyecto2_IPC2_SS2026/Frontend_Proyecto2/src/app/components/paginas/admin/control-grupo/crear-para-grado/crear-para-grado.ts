import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Menu } from '../../componentes/menu/menu';
import { Header } from '../../../../header/header';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { AñoLectivo } from '../../../../modelos/admin/año-lectivo';
import { Grado } from '../../../../modelos/admin/grado';
import { SuperAdminService } from '../../../../../servicios/super-admin-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Seccion } from '../../../../modelos/seccion-enum';
import { KeyValuePipe } from '@angular/common';
import { GrupoSeccionRequest } from '../../../../modelos/admin/grupo-seccion-request';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Menu, Header, ReactiveFormsModule, KeyValuePipe, RouterLink],
  selector: 'app-crear-para-grado',
  styles: ``,
  templateUrl: './crear-para-grado.html',
})
export class CrearParaGrado implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  operacionCompleatada = signal<boolean>(false);
  mensajeError = signal<string>('');
  anios = signal<AñoLectivo[]>([]);
  grados = signal<Grado[]>([]);
  grupoForm!: FormGroup;
  secciones =  Seccion;

  constructor(
    private añosService: SuperAdminService, 
    private grupoService: GrupoService, 
    private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.grupoForm = this.formBuilder.group({
      anioLectivo: [null, [Validators.required]],
      grado: [null, [Validators.required]], 
      seccion: [null, [Validators.required]]
    });

    this.añosService.getAñosUtiles().subscribe({
      next: (anios: AñoLectivo[]) => {
        this.anios.set(anios);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
    this.grupoService.getGradosNormales().subscribe({
      next: (grados: Grado[]) => {
        this.grados.set(grados);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setAnio(anio: number): void {
    this.grupoForm.patchValue({ anioLectivo: anio })
  }

  setGrado(id: number): void {
    this.grupoForm.patchValue({ grado: id });
  }

  submit(): void {
    this.operacionCompleatada.set(false);
    this.mensajeError.set('');
    const grupo: GrupoSeccionRequest = this.grupoForm.value;
    this.grupoService.crearGrupoGrado(grupo).subscribe({
      next: () => {
        this.operacionCompleatada.set(true);
        this.grupoForm.reset();
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
