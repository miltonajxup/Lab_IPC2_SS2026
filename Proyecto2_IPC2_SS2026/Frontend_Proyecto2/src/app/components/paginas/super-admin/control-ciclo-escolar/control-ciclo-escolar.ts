import { Component, OnInit, signal } from '@angular/core';
import { SuperAdminService } from '../../../../servicios/super-admin-service';
import { AñoLectivo } from '../../../modelos/admin/año-lectivo';
import { Menu } from '../componentes/menu/menu';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Menu, Header, DatePipe, RouterLink],
  selector: 'app-control-ciclo-escolar',
  styles: ``,
  templateUrl: './control-ciclo-escolar.html',
})
export class ControlCicloEscolar implements OnInit {

  rol: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
  anios = signal<AñoLectivo[]>([]);
  mensaje = signal<string>('');

  constructor(private superAdminService: SuperAdminService) {}
  
  ngOnInit(): void {
    this.superAdminService.getAñosLectivos().subscribe({
      next: (años: AñoLectivo[]) => {
        this.anios.set(años);
      }, 
      error: (error: any) => {
        console.log(error.error);
      }
    });
  }

  activarAnio(anio: number): void {
    this.superAdminService.activarAño(anio).subscribe({
      next: (anios: AñoLectivo[]) => {
        this.anios.set(anios);
      }, 
      error: (error: any) => {
        this.mensaje.set(error.error)
      }
    })
  }

}
