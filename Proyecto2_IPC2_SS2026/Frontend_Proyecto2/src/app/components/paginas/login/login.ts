import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginRequest } from '../../modelos/login/login-request';
import { AuthService } from '../../../servicios/auth-service';
import { ErrorResponse } from '../../modelos/error-response';
import { LoginResponse } from '../../modelos/login/login-response';
import { RolUsuario } from '../../modelos/rol-usuario-enum';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-login',
  styles: ``,
  templateUrl: './login.html',
})
export class Login implements OnInit {

  formLogin!: FormGroup;
  mensaje = signal<string>('');

  constructor(
    private formBuider: FormBuilder, 
    private authService: AuthService, 
    private router: Router) {}

  ngOnInit(): void {
    this.formLogin = this.formBuider.group({
      nombreUsuario: [null, [Validators.required, Validators.maxLength(60)]], 
      contrasenia: [null, [Validators.required, Validators.maxLength(100)]]
    });
  }

  sumbit(): void {
    this.mensaje.set('');
    const usuarioLog: LoginRequest = this.formLogin.value;
    this.authService.login(usuarioLog).subscribe({
      next: (response: LoginResponse) => {
        this.authService.saveToken(response.token);
        if (this.comprobarRol([RolUsuario.SUPER_ADMINISTRADOR])) {
           this.router.navigate(['/super-admin']);
         } else if (this.comprobarRol([RolUsuario.ADMINISTRADOR])) {
          this.router.navigate(['/admin']);
        } else if (this.comprobarRol([RolUsuario.SECRETARIA])) {
          this.router.navigate(['/secretaria']);
        } else if (this.comprobarRol([RolUsuario.MAESTRO])) {
          this.router.navigate(['/maestro']);
        } else if (this.comprobarRol([RolUsuario.BIBLIOTECARIO])) {
          this.router.navigate(['/bibliotecario']);
        } else if (this.comprobarRol([RolUsuario.ESTUDIANTE])) {
          this.router.navigate(['/estudiante']);
        } else {
          console.log('El rol no tiene una vista');
        }
      }, 
      error: (error: ErrorResponse) => {
        this.mensaje.set(error.mensaje);
      }
    })
  }

  private comprobarRol(token: string[]):boolean {
    const rolesEnToken = this.authService.getRoles();
    const hasAnyCommon = rolesEnToken.some(element => token.includes(element));
    return hasAnyCommon;
  }
  
}
