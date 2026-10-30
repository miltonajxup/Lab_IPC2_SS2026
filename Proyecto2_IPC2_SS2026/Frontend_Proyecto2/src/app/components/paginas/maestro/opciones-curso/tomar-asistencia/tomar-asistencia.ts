import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../../header/header';
import { MenuMaestro } from '../../componentes/menu-maestro/menu-maestro';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Asistencia } from '../../../../modelos/maestro/asistencia';
import { MaestroService } from '../../../../../servicios/maestro-service';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  imports: [Header, MenuMaestro, RouterLink],
  selector: 'app-tomar-asistencia',
  styles: ``,
  templateUrl: './tomar-asistencia.html',
})
export class TomarAsistencia implements OnInit {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');
  idMaes!: number;
  idGrupo!: number;
  idCurso!: number;
  fecha!: Date;
  asistencias = signal<Asistencia[]>([]);

  constructor(private maestroService: MaestroService, private router: ActivatedRoute) {}

  ngOnInit(): void {
    this.idMaes = this.router.snapshot.params['idMaes'];
    this.idGrupo = this.router.snapshot.params['idGrupo'];
    this.idCurso = this.router.snapshot.params['idCurso'];
    this.fecha = this.router.snapshot.params['fecha'];
    this.llamarAsistencias();
  }

  modificarEstado(asistencia: Asistencia): void {
    const estado: boolean = !asistencia.estado;
    this.maestroService.tomarAsistencia(asistencia.id).subscribe({
      next: () => {
        this.llamarAsistencias();
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  private llamarAsistencias(): void {
    this.maestroService.getAsistenciasFecha(this.idGrupo, this.idCurso, this.fecha).subscribe({
      next: (asistencias: Asistencia[]) => {
        this.asistencias.set(asistencias);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
