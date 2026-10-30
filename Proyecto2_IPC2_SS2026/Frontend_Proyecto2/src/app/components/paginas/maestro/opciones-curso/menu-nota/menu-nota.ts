import { Component, signal } from '@angular/core';
import { Header } from '../../../../header/header';
import { MenuMaestro } from '../../componentes/menu-maestro/menu-maestro';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Nota } from '../../../../modelos/nota';
import { MaestroService } from '../../../../../servicios/maestro-service';

@Component({
  imports: [Header, MenuMaestro, RouterLink],
  selector: 'app-menu-nota',
  styles: ``,
  templateUrl: './menu-nota.html',
})
export class MenuNota {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');
  idMaes!: number;
  idCurso!: number;
  idGrupo!: number;
  notas = signal<Nota[]>([]);

  constructor(private maestroService: MaestroService, private router: ActivatedRoute) {}

  ngOnInit(): void {
    this.idMaes = this.router.snapshot.params['idMaes'];
    this.idGrupo = this.router.snapshot.params['idGrupo'];
    this.idCurso = this.router.snapshot.params['idCurso'];
    this.maestroService.getNotasCurso(this.idGrupo, this.idCurso).subscribe({
      next: (notas: Nota[]) => {
        this.notas.set(notas);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
