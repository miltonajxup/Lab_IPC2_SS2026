import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SesionService } from '../../servicios/sesion-service';

@Component({
  imports: [RouterLink],
  selector: 'app-header',
  styles: ``,
  templateUrl: './header.html',
})
export class Header {
  @Input({required: true})
  rol!: string;

  constructor(private sesionService: SesionService) {}

  cerrarSesion(): void {
    this.sesionService.cerrarSesion();
  }
}
