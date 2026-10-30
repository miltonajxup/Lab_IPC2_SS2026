import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { Carrera } from '../../../../modelos/admin/carrera';
import { Grado } from '../../../../modelos/admin/grado';
import { CurriculoDB } from '../../../../modelos/admin/curriculo-db';
import { CarreraService } from '../../../../../servicios/carrera-service';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, Menu, RouterLink],
  selector: 'app-modificar-para-carrera',
  styles: ``,
  templateUrl: './modificar-para-carrera.html',
})
export class ModificarCurriculoCarrera implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  pagina = signal<number>(1);
  codigoCarrera!: string;
  idGrado!: number;
  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  carreras = signal<Carrera[]>([]);
  grados = signal<Grado[]>([]);
  curriculos = signal<CurriculoDB[]>([]);
  idCurriculo!: number;

  constructor(private carreraService: CarreraService, private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.carreraService.getCarrerasActivas(this.pagina()).subscribe({
      next: (carreras: Carrera[]) => {
        this.carreras.set(carreras);
      }
    });
  }

  setCarrera(carrera: Carrera): void {
    this.operacionCompletada.set(false);
    this.codigoCarrera = carrera.codigo;
    this.grados.set(carrera.grados!);
    this.idGrado = 0;
    this.curriculos.set([]);
  }

  setGrado(id: number): void {
    this.idGrado = id;
    this.grupoService.getCursosCurriculoCarrera(this.codigoCarrera, id).subscribe({
      next: (curriculos: CurriculoDB[]) => {
        this.curriculos.set(curriculos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setOpcionEliminar(id: number): void {
    this.idCurriculo = id;
  }

  avanzarPaginaCarreras(avanzar: number): void {
    const pagina = this.pagina() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.carreraService.getCarrerasActivas(pagina).subscribe({
      next: (carreras: Carrera[]) => {
        if (carreras.length === 0) {
          return;
        }
        this.carreras.set(carreras);
        this.pagina.set(pagina);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  eliminarCurriculo(id: number): void {
    this.grupoService.eliminarCurriculo(id).subscribe({
      next: () => {
        this.operacionCompletada.set(true);
        this.grados.set([]);
        this.curriculos.set([]);
        this.codigoCarrera = '';
        this.idGrado = 0;
        this.idCurriculo = 0;
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
