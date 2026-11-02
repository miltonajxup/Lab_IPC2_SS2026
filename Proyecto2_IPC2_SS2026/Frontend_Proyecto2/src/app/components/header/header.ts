import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../servicios/auth-service';
import { SuperMenu } from '../paginas/super-admin/super-menu/super-menu';

@Component({
  imports: [RouterLink, SuperMenu],
  selector: 'app-header',
  styles: ``,
  templateUrl: './header.html',
})
export class Header {
  @Input({required: true})
  rol!: string;

  constructor(
    private authService: AuthService) {}

  cerrarSesion(): void {
    this.authService.logout();
  }
}
