import { Component } from '@angular/core';
import { AuthService } from '../../../../servicios/auth-service';
import { RouterLink } from '@angular/router';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [RouterLink],
  selector: 'app-super-menu',
  styles: ``,
  templateUrl: './super-menu.html',
})
export class SuperMenu {
  constructor(private readonly authService: AuthService) {}
  rolSuperAdmin: RolUsuario = RolUsuario.SUPER_ADMINISTRADOR;
}
