import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { Grado } from '../../../modelos/admin/grado';
import { GrupoService } from '../../../../servicios/grupo-service';

@Component({
  imports: [],
  selector: 'app-lista-grados-normales',
  styles: ``,
  templateUrl: './lista-grados-normales.html',
})
export class ListaGradosNormales implements OnInit {
  @Input() 
  idGrado!: number | undefined;

  @Output()
  gradoSelecionado = new EventEmitter<number>();


  grados = signal<Grado[]>([]);
  mensajeError = signal<string>('');

  constructor(private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.grupoService.getGradosNormales().subscribe({
      next: (grados: Grado[]) => {
        this.grados.set(grados);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  elegirGrado(id: number): void {
    this.gradoSelecionado.emit(id);
  }
}
