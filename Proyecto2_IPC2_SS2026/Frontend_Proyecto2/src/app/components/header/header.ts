import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../servicios/auth-service';

@Component({
  imports: [RouterLink],
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
