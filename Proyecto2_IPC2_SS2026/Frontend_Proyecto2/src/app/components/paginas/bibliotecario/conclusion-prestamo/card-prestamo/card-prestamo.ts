import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PrestamoAtrasado } from '../../../../modelos/biblioteca/prestamo-atrasado';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  imports: [CurrencyPipe, DatePipe],
  selector: 'app-card-prestamo',
  styles: ``,
  templateUrl: './card-prestamo.html',
})
export class CardPrestamo {
  @Input({required: true})
  prestamo!: PrestamoAtrasado;

  @Output()
  prestamoParaAceptar = new EventEmitter<PrestamoAtrasado>();

  elegirPrestamo(): void {
    this.prestamoParaAceptar.emit(this.prestamo);
  }
}
