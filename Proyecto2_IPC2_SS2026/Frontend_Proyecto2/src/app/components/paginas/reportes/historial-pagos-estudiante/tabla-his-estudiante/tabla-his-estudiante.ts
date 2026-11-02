import { Component, Input, signal } from '@angular/core';
import { PagoBoleta } from '../../../../modelos/admin/reporte/pago-boleta';

@Component({
  imports: [],
  selector: 'app-tabla-his-estudiante',
  styles: ``,
  templateUrl: './tabla-his-estudiante.html',
})
export class TablaHisEstudiante {
  @Input({required: true})
  pagos: PagoBoleta[] | undefined;
}
