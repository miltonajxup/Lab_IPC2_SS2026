import { Component, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { AñoLectivo } from '../../../../modelos/admin/año-lectivo';
import { Carrera } from '../../../../modelos/admin/carrera';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Seccion } from '../../../../modelos/seccion-enum';
import { SuperAdminService } from '../../../../../servicios/super-admin-service';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { CarreraService } from '../../../../../servicios/carrera-service';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { RouterLink } from '@angular/router';
import { GrupoSeccionRequest } from '../../../../modelos/admin/grupo-seccion-request';
import { KeyValuePipe } from '@angular/common';
import { Grado } from '../../../../modelos/admin/grado';

@Component({
  imports: [Header, Menu, ReactiveFormsModule, RouterLink, KeyValuePipe],
  selector: 'app-crear-para-carrera',
  styles: ``,
  templateUrl: './crear-para-carrera.html',
})
export class CrearParaCarrera {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  pagina = signal<number>(1);
  operacionCompleatada = signal<boolean>(false);
  mensajeError = signal<string>('');
  anios = signal<AñoLectivo[]>([]);
  grados = signal<Grado[]>([]);
  carreras = signal<Carrera[]>([]);
  grupoForm!: FormGroup;
  secciones =  Seccion;

  constructor(
    private añosService: SuperAdminService, 
    private grupoService: GrupoService, 
    private carreraService: CarreraService, 
    private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.grupoForm = this.formBuilder.group({
      anioLectivo: [null, [Validators.required]],
      grado: [null, [Validators.required]], 
      carrera: [null, Validators.required], 
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
    this.carreraService.getCarrerasActivas(this.pagina()).subscribe({
      next: (carreras: Carrera[]) => {
        this.carreras.set(carreras);
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

  setCarrera(carrera: Carrera): void {
    this.grupoForm.patchValue({ carrera: carrera.codigo, grado: null });
    this.grados.set(carrera.grados!)
  }

  moverPaginaCarrera(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.carreraService.getCarrerasActivas(pagina).subscribe({
      next: (carreras: Carrera[]) => {
        if (carreras.length === 0) {
          return;
        }
        this.carreras.set(carreras);
        this.pagina.set(pagina);
      }
    })
  }

  submit(): void {
    const grupo: GrupoSeccionRequest = this.grupoForm.value;
    this.mensajeError.set('');
    this.operacionCompleatada.set(false);
    this.grupoService.crearGrupoCarrera(grupo).subscribe({
      next: () => {
        this.operacionCompleatada.set(true);
        this.grupoForm.reset();
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
