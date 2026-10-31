import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Multa } from '../../../../modelos/biblioteca/multa';

@Component({
  imports: [],
  selector: 'app-card-multa',
  styles: ``,
  templateUrl: './card-multa.html',
})
export class CardMulta {
  @Input({required: true})
  multa!: Multa;

  @Output()
  multaParaAceptar = new EventEmitter<Multa>();

  elegirMulta(): void {
    this.multaParaAceptar.emit(this.multa);
  }
}
