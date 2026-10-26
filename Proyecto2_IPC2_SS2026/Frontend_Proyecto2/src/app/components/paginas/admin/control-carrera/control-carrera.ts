import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { CarreraService } from '../../../../servicios/carrera-service';
import { Carrera } from '../../../modelos/admin/carrera';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, Menu, RouterLink],
  selector: 'app-carrera',
  styles: ``,
  templateUrl: './control-carrera.html',
})
export class ControlCarrera implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  carreras = signal<Carrera[]>([]);
  pagina = signal<number>(1);
  mensajeError = signal<string>('');

  constructor(private carreraService: CarreraService) {}

  ngOnInit(): void {
    this.carreraService.getTodasLasCarreras(this.pagina()).subscribe({
      next: (carreras: Carrera[]) => {
        this.carreras.set(carreras);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  avanzarPagina(avanzar: number): void  {
    const pagina = this.pagina() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.carreraService.getTodasLasCarreras(pagina).subscribe({
      next: (carreras: Carrera[]) => {
        if (carreras.length === 0) {
          return;
        }
        this.carreras.set(carreras);
        this.pagina.set(pagina);
      }
    });
  }


}
