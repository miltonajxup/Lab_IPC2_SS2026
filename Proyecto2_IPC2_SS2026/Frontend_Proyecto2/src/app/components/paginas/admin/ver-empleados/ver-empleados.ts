import { Component, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { EmpleadoService } from '../../../../servicios/empleado-service';
import { Empleado } from '../../../modelos/empleado';
import { KeyValuePipe } from '@angular/common';

@Component({
  imports: [Header, Menu, KeyValuePipe],
  selector: 'app-ver-empleados',
  styles: ``,
  templateUrl: './ver-empleados.html',
})
export class VerEmpleados {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  mensajeError = signal<string>('');
  roles = RolUsuario;
  pagina = signal<number>(1);
  empleados = signal<Empleado[]>([]);
  rolElegido!: RolUsuario;
  rolEstudiante: RolUsuario = RolUsuario.ESTUDIANTE;

  constructor(private empleadoService: EmpleadoService) {}

  buscarEmpleados(rol: RolUsuario): void {
    this.pagina.set(1);
    this.rolElegido = rol;
    this.llamarEmpleados(this.pagina());
  }

  moverPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    if (!this.rolElegido) {
      return;
    }
    this.llamarEmpleados(pagina);
  }

  llamarEmpleados(pagina: number): void {
    this.empleadoService.getEmpleadosPorRol(this.rolElegido, pagina).subscribe({
      next:(empleados: Empleado[]) => {
        
        this.empleados.set(empleados);
        this.pagina.set(pagina);
      }
    });
  }
}
