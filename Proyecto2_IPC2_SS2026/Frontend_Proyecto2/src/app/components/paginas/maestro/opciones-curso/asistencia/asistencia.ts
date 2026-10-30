import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { MaestroService } from '../../../../../servicios/maestro-service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Header } from '../../../../header/header';
import { MenuMaestro } from '../../componentes/menu-maestro/menu-maestro';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AsistenciaRequest } from '../../../../modelos/maestro/asistencia-request';

@Component({
  imports: [Header, MenuMaestro, ReactiveFormsModule, RouterLink],
  selector: 'app-asistencia',
  styles: ``,
  templateUrl: './asistencia.html',
})
export class ControlAsistencia implements OnInit {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');  
  diasDeAsistencia = signal<Date[]>([]);
  fechaAsistenciaForm!: FormGroup;
  idMaes!: number;
  idGrupo!: number;
  idCurso!: number;
  
  constructor(private route: ActivatedRoute, private maestroService: MaestroService, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.idMaes = this.route.snapshot.params['idMaes'];
    this.idGrupo = this.route.snapshot.params['idGrupo'];
    this.idCurso = this.route.snapshot.params['idCurso'];
    this.cargarDias();

    this.fechaAsistenciaForm = this.formBuilder.group({
      idGrupoEstudiantes: [null], 
      idCurso: [null],
      fecha: [null, Validators.required]
    });
  }

  submit(): void {
    this.fechaAsistenciaForm.patchValue({
      idGrupoEstudiantes: this.idGrupo,
      idCurso: this.idCurso
    });
    const asistencia: AsistenciaRequest = this.fechaAsistenciaForm.value;
    this.maestroService.generarAsistencias(asistencia).subscribe({
      next: () => {
        this.cargarDias();
        this.fechaAsistenciaForm.reset();
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
    this.cargarDias();
  }

  private cargarDias(): void {
    this.maestroService.getFechasAsistencia(this.idGrupo, this.idCurso).subscribe({
      next: (fechas: Date[]) => {
        this.diasDeAsistencia.set(fechas);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
