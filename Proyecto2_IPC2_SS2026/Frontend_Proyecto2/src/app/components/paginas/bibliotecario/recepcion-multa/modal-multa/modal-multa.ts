import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Multa } from '../../../../modelos/biblioteca/multa';

@Component({
  imports: [],
  selector: 'app-modal-multa',
  styles: ``,
  templateUrl: './modal-multa.html',
})
export class ModalMulta {
  @Input()
  multaSeleccionada!: Multa;

  @Output()
  confirmacion = new EventEmitter<void>();

  confirmarAccion(): void {
    this.confirmacion.emit();
  }
}
