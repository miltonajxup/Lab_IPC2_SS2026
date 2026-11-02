import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { Grado } from '../../../../modelos/admin/grado';
import { GrupoService } from '../../../../../servicios/grupo-service';
import { CurriculoDB } from '../../../../modelos/admin/curriculo-db';
import { Header } from '../../../../header/header';
import { Menu } from '../../componentes/menu/menu';
import { RouterLink } from '@angular/router';
import { ModalCurriculo } from '../modal-curriculo/modal-curriculo';
import { ListaGradosNormales } from '../../lista-grados-normales/lista-grados-normales';

@Component({
  imports: [Header, Menu, RouterLink, ModalCurriculo, ListaGradosNormales],
  selector: 'app-modificar-para-grado',
  styles: ``,
  templateUrl: './modificar-para-grado.html',
})
export class ModificarCurriculoGrado  {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;

  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  curriculos = signal<CurriculoDB[]>([]);
  idGrado = signal<number | undefined>(undefined);
  curriculoSeleccionado = signal<CurriculoDB | undefined>(undefined);

  constructor(private grupoService: GrupoService) {}
  
  setGrado(id: number): void {
    this.operacionCompletada.set(false);
    this.idGrado.set(id);
    this.grupoService.getCursosCurriculoGrado(id).subscribe({
      next: (curriculos: CurriculoDB[]) => {
        this.curriculos.set(curriculos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setOpcionEliminar(curriculo: CurriculoDB): void {
    this.curriculoSeleccionado.set(curriculo);
  }

  eliminarCurriculo(): void {
    this.grupoService.eliminarCurriculo(this.curriculoSeleccionado()!.id).subscribe({
      next: () => {
        this.operacionCompletada.set(true);
        this.curriculos.set([]);
        this.idGrado.set(undefined);
        this.curriculoSeleccionado.set(undefined);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
