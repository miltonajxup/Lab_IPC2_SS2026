import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Menu } from '../componentes/menu/menu';
import { Grado } from '../../../modelos/admin/grado';
import { GradoService } from '../../../../servicios/grado-service';
import { Header } from '../../../header/header';

@Component({
  imports: [Header, Menu],
  selector: 'app-control-grado',
  styles: ``,
  templateUrl: './control-grado.html',
})
export class ControlGrado implements OnInit {
  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
  mensajeError = signal<string>('');
  grados = signal<Grado[]>([]);

  constructor(private gradoService: GradoService) {}

  ngOnInit(): void {
    this.llamarGrados();
  }

  modificarEstado(id: number): void  {
    this.gradoService.modificarEstado(id).subscribe({
      next: () => {
        this.llamarGrados();
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }

  private llamarGrados(): void {
    this.gradoService.getGrados().subscribe({
      next: (grados: Grado[]) => {
        this.grados.set(grados);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
