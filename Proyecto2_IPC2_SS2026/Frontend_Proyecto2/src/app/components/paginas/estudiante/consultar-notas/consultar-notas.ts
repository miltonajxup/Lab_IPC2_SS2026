import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuEstudiante } from '../menu-estudiante/menu-estudiante';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Nota } from '../../../modelos/nota';
import { EstudianteService } from '../../../../servicios/estudiante-service';
import { AñoInscrito } from '../../../modelos/estudiante/anio-inscrito';
import { AuthService } from '../../../../servicios/auth-service';

@Component({
  imports: [Header, MenuEstudiante],
  selector: 'app-consultar-notas',
  styles: ``,
  templateUrl: './consultar-notas.html',
})
export class ConsultarNotas implements OnInit {
  rol: RolUsuario = RolUsuario.ESTUDIANTE;
  ciclosEscolares = signal<AñoInscrito[]>([]);
  notas = signal<Nota[]>([]);

  constructor(private estudianteService: EstudianteService, private authService: AuthService) {}

  ngOnInit(): void {
    this.estudianteService.getAñosEstudiados(this.authService.getUserId()!).subscribe({
      next: (años: AñoInscrito[]) => {
        this.ciclosEscolares.set(años);
      }, 
      error: (error: any) => {
        console.log(error.error);
      }
    });
  }

  buscarNotas(año: number): void {
    this.estudianteService.buscarNotas(año, this.authService.getUserId()!).subscribe({
      next: (notas: Nota[]) => {
        this.notas.set(notas);
      }, 
      error: (error: any) => {
        console.log(error.error);
      }
    });
  }
}
