import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { CursoMaestro } from '../../../modelos/maestro/curso-maestro';
import { MaestroService } from '../../../../servicios/maestro-service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Header } from '../../../header/header';
import { MenuMaestro } from '../componentes/menu-maestro/menu-maestro';
import { SesionService } from '../../../../servicios/sesion-service';
import { Usuario } from '../../../modelos/usuario';

@Component({
  imports: [Header, MenuMaestro, RouterLink],
  selector: 'app-opciones-curso',
  styles: ``,
  templateUrl: './opciones-curso.html',
})
export class OpcionesCurso implements OnInit {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');
  idMaes!: number;
  curso = signal<CursoMaestro | undefined>(undefined);

  constructor(
    private router: ActivatedRoute, 
    private maestroService: MaestroService, 
    private sesionService: SesionService) {}

  ngOnInit(): void {
    const usuario: Usuario = this.sesionService.getUsuario();
    let idUsuario: string;
    if (usuario == null) {
      idUsuario = '';
    } else {
      idUsuario = usuario.dpi!;
    }
    this.idMaes = this.router.snapshot.params['idMaes'];
    this.maestroService.getCursoPorId(this.idMaes, idUsuario).subscribe({
      next: (curso: CursoMaestro) => {
        this.curso.set(curso);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
