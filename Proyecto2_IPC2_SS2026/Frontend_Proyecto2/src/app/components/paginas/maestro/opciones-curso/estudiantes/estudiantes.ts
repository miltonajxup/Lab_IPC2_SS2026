import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../../header/header';
import { MenuMaestro } from '../../componentes/menu-maestro/menu-maestro';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { EstudianteCurso } from '../../../../modelos/maestro/estudiante-curso';
import { MaestroService } from '../../../../../servicios/maestro-service';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  imports: [Header, MenuMaestro, RouterLink],
  selector: 'app-estudiantes',
  styles: ``,
  templateUrl: './estudiantes.html',
})
export class Estudiantes implements OnInit {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');
  estudiantes = signal<EstudianteCurso[]>([]);
  idMaes!: number;
  idCurso!: number;

  constructor(private router: ActivatedRoute, private maestroService: MaestroService) {}

  ngOnInit(): void {
    this.idMaes = this.router.snapshot.params['idMaes'];
    const idGrupo: number = this.router.snapshot.params['idGrupo'];
    this.idCurso = this.router.snapshot.params['idCurso'];
    this.maestroService.getEstudiantesCurso(idGrupo, this.idCurso).subscribe({
      next: (estudiantes: EstudianteCurso[]) => {
        this.estudiantes.set(estudiantes);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
