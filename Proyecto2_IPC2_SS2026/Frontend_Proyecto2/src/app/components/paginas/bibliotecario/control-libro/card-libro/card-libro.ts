import { Component, Input } from '@angular/core';
import { Libro } from '../../../../modelos/biblioteca/libro';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../../../servicios/auth-service';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';

@Component({
  imports: [RouterLink],
  selector: 'app-card-libro',
  styles: ``,
  templateUrl: './card-libro.html',
})
export class CardLibro {
  @Input({required: true})
  libro!: Libro;
  rolSuperAdmin = RolUsuario.SUPER_ADMINISTRADOR;
  rolBibliotecario = RolUsuario.BIBLIOTECARIO;

  constructor(private readonly authService: AuthService) {}
}
