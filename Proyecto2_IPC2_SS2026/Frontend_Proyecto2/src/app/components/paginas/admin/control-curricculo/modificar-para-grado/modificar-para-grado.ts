import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Grado } from '../../../../modelos/admin/grado';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { CurriculoDB } from '../../../../modelos/admin/curriculo-db';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, Menu, RouterLink],
  selector: 'app-modificar-para-grado',
  styles: ``,
  templateUrl: './modificar-para-grado.html',
})
export class ModificarCurriculoGrado implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  grados = signal<Grado[]>([]);
  curriculos = signal<CurriculoDB[]>([]);
  idGrado!: number;
  idCurriculo!: number;

  constructor(private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.grupoService.getGradosNormales().subscribe({
      next: (grados: Grado[]) => {
        this.grados.set(grados);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setGrado(id: number): void {
    this.operacionCompletada.set(false);
    this.idGrado = id;
    this.grupoService.getCursosCurriculoGrado(id).subscribe({
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

  eliminarCurriculo(id: number): void {
    this.grupoService.eliminarCurriculo(id).subscribe({
      next: () => {
        this.operacionCompletada.set(true);
        this.curriculos.set([]);
        this.idGrado = 0;
        this.idCurriculo = 0;
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
