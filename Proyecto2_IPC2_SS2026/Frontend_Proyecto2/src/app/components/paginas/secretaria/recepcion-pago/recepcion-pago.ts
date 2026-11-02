import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { RecepcionPagoService } from '../../../../servicios/recepcion-pago-service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BoletaPago } from '../../../modelos/admin/boleta-pago';
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
  pagina = signal<number>(1);

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
    this.pagina.set(1);
    this.idEstudiante = this.busquedaForm.value.idEstudiante;
    this.llamarBoletas(this.pagina());
  }

  pagarBoleta(idBoleta: number): void {
    this.pagoService.pagarBoleta(idBoleta, this.authService.getUserId()!).subscribe({
      next: () => {
        this.llamarBoletas(this.pagina());
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  private llamarBoletas(pagina: number): void {
    this.mensajeError.set('');
    this.pagoService.getBoletaEstudiante(pagina, this.idEstudiante).subscribe({
      next: (boletas: BoletaPago[]) => {
        if (boletas.length === 0) {
          return;
        }
        this.setIdBoleta(0);
        this.boletas.set(boletas);
        this.pagina.set(pagina);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  setIdBoleta(idBoleta: number): void {
    this.idBoleta.set(idBoleta);
  }

  moverPagina(paso: number): void {
    const pagina: number = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.llamarBoletas(pagina);
  }

}
