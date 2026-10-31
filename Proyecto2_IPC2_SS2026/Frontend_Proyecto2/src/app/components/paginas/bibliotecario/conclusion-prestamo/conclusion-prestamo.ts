import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuBibliotecario } from '../menu-bibliotecario/menu-bibliotecario';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { PrestamoService } from '../../../../servicios/prestamo-service';
import { Modal } from './modal-prestamo/modal-prestamo';
import { PrestamoAtrasado } from '../../../modelos/biblioteca/prestamo-atrasado';
import { MultaService } from '../../../../servicios/multa-service';
import { MultaRequest } from '../../../modelos/biblioteca/multa-request';
import { CardPrestamo } from './card-prestamo/card-prestamo';

@Component({
  imports: [Header, MenuBibliotecario, Modal, CardPrestamo],
  selector: 'app-conclusion-prestamo',
  styles: ``,
  templateUrl: './conclusion-prestamo.html',
})
export class ConclusionPrestamo implements OnInit {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
  prestamos = signal<PrestamoAtrasado[]>([]);
  pagina = signal<number>(1);
  prestamoElegido = signal<PrestamoAtrasado | undefined>(undefined);
  mensajeError = signal<string>('');

  constructor(private prestamoService: PrestamoService, private multaService: MultaService) {}

  ngOnInit(): void {
    this.llamarPrestamos();
  }

  moverPagina(paso: number): void {
    const pagina: number = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.prestamoService.prestamosSinTerminar(pagina).subscribe({
      next: (prestamos: PrestamoAtrasado[]) => {
        if (prestamos.length === 0) {
          return;
        }
        this.pagina.set(pagina);
        this.prestamos.set(prestamos);
      }
    });
  }

  elegirPrestamo(prestamo: PrestamoAtrasado): void {
    this.prestamoElegido.set(prestamo);
  }

  terminarPrestamo(): void {
    let multa: MultaRequest = {
      idPrestamo: this.prestamoElegido()!.id,
      monto: this.prestamoElegido()!.montoMulta
    }
    this.prestamoService.terminarPrestamo(this.prestamoElegido()!.id).subscribe({
      next: () =>{
        this.multaService.agregarMulta(multa).subscribe({
          next: () => {},
          error: (error: any) => {
            this.mensajeError.set(error.error);
          }
        })
        this.prestamoElegido.set(undefined);
        this.llamarPrestamos();
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }

  private llamarPrestamos(): void  {
    this.prestamoService.prestamosSinTerminar(this.pagina()).subscribe({
      next: (prestamos: PrestamoAtrasado[]) => {
        this.prestamos.set(prestamos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
