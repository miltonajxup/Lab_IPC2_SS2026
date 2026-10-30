import { Component, Input, OnInit, signal } from '@angular/core';
import { AñoLectivo } from '../../../../modelos/admin/año-lectivo';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { SuperAdminService } from '../../../../../servicios/super-admin-service';
import { Menu } from '../../componentes/menu/menu';
import { RouterLink } from '@angular/router';
import { Header } from '../../../../header/header';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, Menu, ReactiveFormsModule, RouterLink],
  selector: 'app-form',
  styles: ``,
  templateUrl: './form.html',
})
export class Form implements OnInit {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
  nuevoAnio!: AñoLectivo;
  isEditMode: boolean = false;
  operacionCompletada = signal<boolean>(false);
  mensaje = signal<string>('');

  @Input()
  anioParaActualizar!: AñoLectivo
  @Input()
  anioForm!: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private superAdminService: SuperAdminService
  ) {}

  ngOnInit(): void {
    this.anioForm = this.formBuilder.group({
      anio: [null, [Validators.required, Validators.min(2024)]],
      fechaInicio: [null, [Validators.required]], 
      fechaFin: [null, [Validators.required]],
    })
  }

  submit(): void {
    if (this.anioForm.valid) {
      if (this.isEditMode) {
        this.actualizarAnio();
      } else {
        this.agregarAnio();
      }
    }
  }

  private agregarAnio(): void {
    this.nuevoAnio = this.anioForm.value as AñoLectivo;
    this.superAdminService.agregarAñoLectivo(this.nuevoAnio).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      }, 
      error: (error: any) => {
        this.mensaje.set(error.error);
      }
    });
  }

  private actualizarAnio(): void {
    const {anio, ...modificado} = this.anioForm.value;
    this.superAdminService.modificarAño(modificado, anio).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      }, 
      error: (error: any) => {
        this.mensaje.set(error.error);
      }
    })
  }

  reset(): void {
    if (this.isEditMode) {
      this.resetParaActualizar();
    } else {
      this.resetParaAgregar();
    }
    this.operacionCompletada.set(false);
    this.mensaje.set('');
  }

  private resetParaAgregar(): void {
    this.anioForm.reset();
  }

  private resetParaActualizar(): void {
    this.anioForm.reset(this.anioParaActualizar);
  }

}
