import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../../../servicios/login-service';
import { Usuario } from '../../../modelos/usuario';
import { RouterLink } from '@angular/router';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-primer-ingreso',
  styles: ``,
  templateUrl: './primer-ingreso.html',
})
export class PrimerIngreso implements OnInit {
  operacionCompletada = signal<boolean>(false);
  mensajeError = signal<string>('');
  formLogin!: FormGroup;

  constructor(private loginService: LoginService, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.formLogin = this.formBuilder.group({
      dpi: [null, Validators.required], 
      nombre: [null, Validators.required],
      contrasenia: [null, [Validators.required, Validators.minLength(4)]],
      confirmacionContrasenia: [null, Validators.required]
    });
  }

  submit(): void {
    this.operacionCompletada.set(false);
    this.mensajeError.set('');
    if (this.formLogin.valid) {
      const contraseña = this.formLogin.value.contrasenia;
      const confirmacion = this.formLogin.value.confirmacionContrasenia;
      if (contraseña !== confirmacion) {
        this.mensajeError.set('No coinciden las contraseñas');
        return;
      }
      const {confirmacionContrasenia, ...usuario} = this.formLogin.value;
      this.loginService.colocarContraseña(usuario).subscribe({
        next: () => {
          this.operacionCompletada.set(true);
        }, 
        error: (error: any) =>{
          this.mensajeError.set(error.error);
        }
      });
    }
  }
}
