import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Prestamo } from '../../../../modelos/biblioteca/prestamo';

@Component({
  imports: [],
  selector: 'app-modal',
  styles: ``,
  templateUrl: './modal-prestamo.html',
})
export class Modal {
  @Input()
  eventoSeleccionado!: Prestamo;

  @Output()
  confirmationExecuter = new EventEmitter<void>();

  executeConfirmation(): void {
    this.confirmationExecuter.emit();
  }
}
