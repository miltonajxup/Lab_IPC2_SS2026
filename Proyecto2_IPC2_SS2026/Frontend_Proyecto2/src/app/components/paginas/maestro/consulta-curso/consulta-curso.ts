import { Component, OnInit, signal } from '@angular/core';
import { MenuMaestro } from '../componentes/menu-maestro/menu-maestro';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { CursoMaestro } from '../../../modelos/maestro/curso-maestro';
import { MaestroService } from '../../../../servicios/maestro-service';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../../servicios/auth-service';

@Component({
  imports: [MenuMaestro, Header, RouterLink],
  selector: 'app-consulta-curso',
  styles: ``,
  templateUrl: './consulta-curso.html',
})
export class ConsultaCurso implements OnInit {
  rol: RolUsuario = RolUsuario.MAESTRO;

  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  cursos = signal<CursoMaestro[]>([]);

  constructor(private maestroService: MaestroService, private authService: AuthService) {}

  ngOnInit(): void {
    this.maestroService.getCursos(this.authService.getUserId()!).subscribe({
      next: (cursos: CursoMaestro[]) => {
        this.cursos.set(cursos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
