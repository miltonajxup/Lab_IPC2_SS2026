import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { InscripcionService } from '../../../../servicios/inscripcion-service';
import { GrupoSeccionDB } from '../../../modelos/admin/grupo-seccion-db';
import { GrupoService } from '../../../../servicios/grupo-service';
import { EventoInscripcionPrimerIngreso } from '../../../modelos/secretaria/inscripcion-primer-ingreso';

@Component({
  imports: [Header, MenuSecretaria, ReactiveFormsModule],
  selector: 'app-inscripcion-primer-ingreso',
  styles: ``,
  templateUrl: './inscripcion-primer-ingreso.html',
})
export class InscripcionPrimerIngreso implements OnInit {
  rol: RolUsuario = RolUsuario.SECRETARIA;

  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  pagina = signal<number>(1);
  estudianteForm!: FormGroup;
  gruposAnuales = signal<GrupoSeccionDB[]>([]);

  constructor(private formBuilder: FormBuilder, private inscripcionService: InscripcionService, private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.estudianteForm = this.formBuilder.group({
      dpi: [null, [Validators.required, Validators.maxLength(13)]],
      nombre: [null, [Validators.required, Validators.max(40)]], 
      numeroEncargado: [null, [Validators.required, Validators.maxLength(10)]], 
      numeroEstudiante: [null, [Validators.required, Validators.maxLength(10)]], 
      grupoId: [null, Validators.required]
    });

    this.grupoService.getGruposDelAño(this.pagina()).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        this.gruposAnuales.set(grupos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  cambiarPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.grupoService.getGruposDelAño(pagina).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        if (grupos.length === 0) {
          return;
        }
        this.gruposAnuales.set(grupos);
        this.pagina.set(pagina);
      }
    });
  }

  setGrupo(grupoId: number): void {
    this.estudianteForm.patchValue({
      grupoId: grupoId
    });
    console.log(this.estudianteForm.value)
  }

  submit(): void {
    this.operacionCompletada.set(false);
    this.mensajeError.set('');
    if (this.estudianteForm.valid) {
      const {grupoId, ...estudiante} = this.estudianteForm.value;
      const inscripcion: EventoInscripcionPrimerIngreso = {
        estudiante, 
        grupoId
      };
      this.inscripcionService.inscripcionPrimerIngreso(inscripcion).subscribe({
        next: () => {
          this.estudianteForm.reset();
          this.operacionCompletada.set(true);
        }, 
        error: (error: any) => {
          this.mensajeError.set(error.error);
        }
      })
    }
  }
}
