import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CurriculoDB } from '../../../../modelos/admin/curriculo-db';

@Component({
  imports: [],
  selector: 'app-modal-curriculo',
  styles: ``,
  templateUrl: './modal-curriculo.html',
})
export class ModalCurriculo {
  @Input()
  curriculoSeleccionado!: CurriculoDB;

  @Output()
  confirmacionDeAccion = new EventEmitter<void>();

  confirmar(): void  {
      this.confirmacionDeAccion.emit();
  }
}
