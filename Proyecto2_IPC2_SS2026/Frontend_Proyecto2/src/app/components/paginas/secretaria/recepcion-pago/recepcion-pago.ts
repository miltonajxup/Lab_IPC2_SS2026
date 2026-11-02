import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { RecepcionPagoService } from '../../../../servicios/recepcion-pago-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BoletaPago } from '../../../modelos/admin/boleta-pago';
import { Usuario } from '../../../modelos/usuario';
import { AuthService } from '../../../../servicios/auth-service';

@Component({
  imports: [Header, MenuSecretaria, ReactiveFormsModule],
  selector: 'app-recepcion-pago',
  styles: ``,
  templateUrl: './recepcion-pago.html',
})
export class RecepcionPago implements OnInit {
  rol: RolUsuario = RolUsuario.SECRETARIA;
  mensajeError = signal<string>('');
  busquedaForm!: FormGroup;
  boletas = signal<BoletaPago[]>([]);
  idEstudiante!: string;
  idBoleta = signal<number>(0);

  constructor(
    private pagoService: RecepcionPagoService, 
    private formBuilder: FormBuilder, 
    private authService: AuthService) {}

  ngOnInit(): void {
    this.busquedaForm = this.formBuilder.group({
      idEstudiante: [null, Validators.required]
    });
  }

  buscar(): void {
    this.idEstudiante = this.busquedaForm.value.idEstudiante;
    this.llamarBoletas();
  }

  pagarBoleta(idBoleta: number): void {
    this.pagoService.pagarBoleta(idBoleta, this.authService.getUserId()!).subscribe({
      next: () => {
        this.llamarBoletas();
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  private llamarBoletas(): void {
    this.mensajeError.set('');
    this.pagoService.getBoletaEstudiante(this.idEstudiante).subscribe({
      next: (boletas: BoletaPago[]) => {
        if (boletas.length === 0) {
          this.mensajeError.set('No se encontraron boletas del estudiante');
        }
        this.setIdBoleta(0);
        this.boletas.set(boletas);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setIdBoleta(idBoleta: number): void {
    this.idBoleta.set(idBoleta);
  }

}
