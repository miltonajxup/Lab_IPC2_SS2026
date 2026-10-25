import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { LoginService } from '../../../servicios/login-service';
import { Usuario } from '../../modelos/usuario';

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
    private loginService: LoginService) {}

  ngOnInit(): void {
    this.formLogin = this.formBuider.group({
      nombre: [null, [Validators.required, Validators.maxLength(60)]], 
      contrasenia: [null, [Validators.required, Validators.maxLength(100)]]
    });
  }

  sumbit(): void {
    this.loginService.login(this.formLogin.value).subscribe({
      next: (usuario: Usuario) => {
        console.log(usuario);
      }, 
      error: (error: any) => {
        this.mensaje.set(error.error);
      }
    })
  }
  
}
