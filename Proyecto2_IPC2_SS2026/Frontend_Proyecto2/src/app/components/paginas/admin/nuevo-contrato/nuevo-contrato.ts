import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { FormBuilder, FormGroup, Validators, ɵInternalFormsSharedModule, ReactiveFormsModule } from '@angular/forms';
import { EmpleadoService } from '../../../../servicios/empleado-service';
import { KeyValuePipe } from '@angular/common';
import { Empleado } from '../../../modelos/empleado';

@Component({
  imports: [Header, Menu, ɵInternalFormsSharedModule, KeyValuePipe, ReactiveFormsModule],
  selector: 'app-nuevo-contrato',
  styles: ``,
  templateUrl: './nuevo-contrato.html',
})
export class NuevoContrato implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  operacionCompletada = signal<boolean>(false);
  mensajeError = signal<string>('');
  empleadoForm!: FormGroup;
  roles = RolUsuario;
  
  constructor(private empleadoService: EmpleadoService, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.empleadoForm = this.formBuilder.group({
      dpi: [null, [Validators.required, Validators.maxLength(13)]], 
      nombre: [null, [Validators.required, Validators.maxLength(40)]], 
      rol: [null, [Validators.required]],
      salario: [null, [Validators.required, Validators.min(1)]]
    })
  }

  submit(): void {
    this.operacionCompletada.set(false);
    if (this.empleadoForm.valid) {
      const empleado: Empleado = this.empleadoForm.value;
      this.empleadoService.crearYContratar(empleado).subscribe({
        next: () => {
          this.operacionCompletada.set(true);
          this.empleadoForm.reset();
        }, 
        error: (error: any) => {
          this.mensajeError.set(error.error);
        }
      });
    }
  }
}
