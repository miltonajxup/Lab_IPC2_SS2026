import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../../../servicios/login-service';
import { Usuario } from '../../../modelos/usuario';
import { Router, RouterLink } from '@angular/router';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-recuperar-contrasenia',
  styles: ``,
  templateUrl: './recuperar-contrasenia.html',
})
export class RecuperarContrasenia implements OnInit {
  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  contraseniaForm!: FormGroup;

  constructor(private loginService: LoginService, private formBuider: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.contraseniaForm = this.formBuider.group({
      nombre: [null, Validators.required],
      dpi: [null, Validators.required],
      contrasenia: [null, Validators.required], 
    });
  }

  submit(): void {
    this.mensajeError.set('');
    const usuario: Usuario = this.contraseniaForm.value;
    const dpi: string = this.contraseniaForm.value.dpi;
    this.loginService.recuperarContraseña(usuario).subscribe({
      next: () => {
        this.router.navigate([
          '/login/cambiar-contrasenia/',
          dpi
        ]);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
