import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InscripcionService } from '../../../../servicios/inscripcion-service';
import { ReinscripcionRequest } from '../../../modelos/secretaria/reinscripcion-request';
import { ListaGrupos } from '../lista-grupos/lista-grupos';

@Component({
  imports: [Header, MenuSecretaria, ReactiveFormsModule, ListaGrupos],
  selector: 'app-reinscripcion',
  styles: ``,
  templateUrl: './reinscripcion.html',
})
export class Reinscripcion implements OnInit {
  rol: RolUsuario = RolUsuario.SECRETARIA;
  mensajeError = signal<string>('');
  mensaje = signal<string>('');
  estudianteForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private inscripcionService: InscripcionService) {}

  ngOnInit(): void {
    this.estudianteForm = this.formBuilder.group({
      dpiEstudiante: [null, Validators.required],
      grupoId: [null, Validators.required]
    });
  }

  setGrupo(id: number): void {
    this.estudianteForm.patchValue({
      grupoId: id
    });
  }

  submit(): void {
    this.mensaje.set('');
    this.mensajeError.set('');
    const inscripcion: ReinscripcionRequest = this.estudianteForm.value;
    this.inscripcionService.inscripcionReingreso(inscripcion).subscribe({
      next: () => {
        this.mensaje.set('Se ha completado la inscripcion con exito');
        this.estudianteForm.reset();
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
