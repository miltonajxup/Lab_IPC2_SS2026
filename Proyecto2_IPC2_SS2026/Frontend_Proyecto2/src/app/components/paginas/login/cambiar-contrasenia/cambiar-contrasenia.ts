import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../../../servicios/login-service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Usuario } from '../../../modelos/usuario';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-cambiar-contrasenia',
  styles: ``,
  templateUrl: './cambiar-contrasenia.html',
})
export class CambiarContrasenia {
  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  contraseniaForm!: FormGroup;
  idUsuario!: string;

  constructor(private loginService: LoginService, private formBuider: FormBuilder, private router: ActivatedRoute) {}

  ngOnInit(): void {
    this.idUsuario = this.router.snapshot.params['idUsuario'];
    this.contraseniaForm = this.formBuider.group({
      contrasenia: [null, [Validators.required, Validators.minLength(4)]], 
      confirmacion: [null, Validators.required]
    });
  }

  submit(): void {
    this.mensajeError.set('');
    const {confirmacion, ...contraseña} = this.contraseniaForm.value;
    const nuevaCont = this.contraseniaForm.value.contrasenia;
    if (nuevaCont !== confirmacion) {
      this.mensajeError.set('Las contraseñas no coinciden');
      return;
    }
    const usuario: Usuario = contraseña;
    this.loginService.modificarContraseña(usuario, this.idUsuario).subscribe({
      next: () => {
        this.operacionCompletada.set(true);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }

}
