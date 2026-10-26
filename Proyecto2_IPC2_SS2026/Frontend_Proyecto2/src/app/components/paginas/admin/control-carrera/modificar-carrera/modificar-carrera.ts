import { Component, OnInit, signal } from '@angular/core';
import { Menu } from '../../componentes/menu/menu';
import { Header } from '../../../../header/header';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormCarrera } from '../form-carrera/form-carrera';
import { Carrera } from '../../../../modelos/admin/carrera';
import { CarreraService } from '../../../../../servicios/carrera-service';

@Component({
  imports: [Menu, Header, RouterLink, FormCarrera],
  selector: 'app-modificar-carrera',
  styles: ``,
  templateUrl: './modificar-carrera.html',
})
export class ModificarCarrera implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  codigoCarrera!: string;
  carreraParaActualizar = signal<Carrera | undefined>(undefined);
  mensajeError = signal<string>('');

  constructor(private route: ActivatedRoute, private carreraService: CarreraService) {}

  ngOnInit(): void {
    this.codigoCarrera = this.route.snapshot.params['codigo'];
    this.carreraService.getCarreraPorId(this.codigoCarrera).subscribe({
      next: (carrera: Carrera) => {
        this.carreraParaActualizar.set(carrera);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
