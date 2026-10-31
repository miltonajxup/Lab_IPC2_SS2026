import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuBibliotecario } from '../menu-bibliotecario/menu-bibliotecario';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Multa } from '../../../modelos/biblioteca/multa';
import { MultaService } from '../../../../servicios/multa-service';
import { CardMulta } from './card-multa/card-multa';
import { ModalMulta } from './modal-multa/modal-multa';

@Component({
  imports: [Header, MenuBibliotecario, CardMulta, ModalMulta],
  selector: 'app-recepcion-multa',
  styles: ``,
  templateUrl: './recepcion-multa.html',
})
export class RecepcionMulta implements OnInit {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
  multas = signal<Multa[]>([]);
  multaElegida = signal<Multa | undefined>(undefined);
  pagina = signal<number>(1);
  mensajeError = signal<string>('');

  constructor(private multaService: MultaService) {}

  ngOnInit(): void {
    this.llamarMultas();
  }

  elegirMulta(multa: Multa): void {
    this.multaElegida.set(multa);
  }

  moverPagina(paso: number): void {
      const pagina: number = this.pagina() + paso;
      if (pagina < 1) {
        return;
      }
      this.multaService.getMultasPendientes(pagina).subscribe({
        next: (multas: Multa[]) => {
          if (multas.length === 0) {
            return;
          }
          this.pagina.set(pagina);
          this.multas.set(multas);
        }
      });
    }

  pagarMulta(): void {
    this.multaService.pagarMulta(this.multaElegida()!.id).subscribe({
      next: () => {
        this.llamarMultas();
        this.multaElegida.set(undefined);
      }, 
      error: (error: any) =>{
        this.mensajeError.set(error.error);
      }
    });
  }

  private llamarMultas(): void {
    this.multaService.getMultasPendientes(this.pagina()).subscribe({
      next: (multas: Multa[]) => {
        this.multas.set(multas);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
