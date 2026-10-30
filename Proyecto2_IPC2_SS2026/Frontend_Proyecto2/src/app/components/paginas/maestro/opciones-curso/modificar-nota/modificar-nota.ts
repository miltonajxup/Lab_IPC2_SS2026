import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../../header/header';
import { MenuMaestro } from '../../componentes/menu-maestro/menu-maestro';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Nota } from '../../../../modelos/nota';
import { MaestroService } from '../../../../../servicios/maestro-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  imports: [Header, MenuMaestro, RouterLink, ReactiveFormsModule],
  selector: 'app-modificar-nota',
  styles: ``,
  templateUrl: './modificar-nota.html',
})
export class ModificarNota implements OnInit {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');
  operacionCorrecta = signal<boolean>(false);
  idCurso!: number;
  idGrupo!: number;
  nota = signal<Nota | undefined>(undefined);
  notaForm!: FormGroup;

  constructor(private maestroService: MaestroService, private router: ActivatedRoute, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.idGrupo = this.router.snapshot.params['idGrupo'];
    this.idCurso = this.router.snapshot.params['idCurso'];
    const idNota = this.router.snapshot.params['idNota'];
    this.maestroService.getNotaCurso(idNota).subscribe({
      next: (nota: Nota) => {
        this.nota.set(nota);
        this.notaForm.reset(nota);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });

    this.notaForm = this.formBuilder.group({
      zona: [0, [Validators.required, Validators.min(0), Validators.max(70)]],
      examenFinal: [0, [Validators.required, Validators.min(0), Validators.max(30)]]
    });
  }

  submit(): void {
    this.operacionCorrecta.set(false);
    this.maestroService.modificarZona(this.nota()!.idNota, this.notaForm.value).subscribe({
      next: () => {
        this.operacionCorrecta.set(true);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
    this.maestroService.moidificarExamenFinal(this.nota()!.idNota, this.notaForm.value).subscribe({
      next: () => {
        this.operacionCorrecta.set(true);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
