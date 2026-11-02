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
  pagina = signal<number>(1);
  fechaAsistenciaForm!: FormGroup;
  idMaes!: number;
  idGrupo!: number;
  idCurso!: number;
  
  constructor(private route: ActivatedRoute, private maestroService: MaestroService, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.idMaes = this.route.snapshot.params['idMaes'];
    this.idGrupo = this.route.snapshot.params['idGrupo'];
    this.idCurso = this.route.snapshot.params['idCurso'];
    this.cargarDias(this.pagina());

    this.fechaAsistenciaForm = this.formBuilder.group({
      idGrupoEstudiantes: [null], 
      idCurso: [null],
      fecha: [null, Validators.required]
    });
  }

  submit(): void {
    this.mensajeError.set('');
    this.fechaAsistenciaForm.patchValue({
      idGrupoEstudiantes: this.idGrupo,
      idCurso: this.idCurso
    });
    const asistencia: AsistenciaRequest = this.fechaAsistenciaForm.value;
    this.maestroService.generarAsistencias(asistencia).subscribe({
      next: () => {
        this.cargarDias(this.pagina());
        this.fechaAsistenciaForm.reset();
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  moverPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.cargarDias(pagina);
  }

  private cargarDias(pagina: number): void {
    this.maestroService.getFechasAsistencia(pagina, this.idGrupo, this.idCurso).subscribe({
      next: (fechas: Date[]) => {
        if (fechas.length === 0) {
          return;
        }
        this.pagina.set(pagina);
        this.diasDeAsistencia.set(fechas);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
