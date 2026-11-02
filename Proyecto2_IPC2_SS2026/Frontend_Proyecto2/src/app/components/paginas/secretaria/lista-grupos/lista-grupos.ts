import { Component, EventEmitter, Input, OnInit, Output, signal } from '@angular/core';
import { GrupoSeccionDB } from '../../../modelos/admin/grupo-seccion-db';
import { GrupoService } from '../../../../servicios/grupo-service';

@Component({
  imports: [],
  selector: 'app-lista-grupos',
  styles: ``,
  templateUrl: './lista-grupos.html',
})
export class ListaGrupos implements OnInit {
  @Input()
  idGrupo!: number;
  
  @Output()
  grupoElegido = new EventEmitter<number>();

  gruposAnuales = signal<GrupoSeccionDB[]>([]);
  pagina = signal<number>(1);
  mensajeError = signal<string>('');

  constructor(private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.grupoService.getGruposDelAño(this.pagina()).subscribe({
      next: (grupos: GrupoSeccionDB[]) =>{
        this.gruposAnuales.set(grupos);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  cambiarPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.grupoService.getGruposDelAño(pagina).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        if (grupos.length === 0) {
          return;
        }
        this.gruposAnuales.set(grupos);
        this.pagina.set(pagina);
      }
    });
  }

  elegirGrupo(id: number): void {
    this.grupoElegido.emit(id);
  }
  
}
