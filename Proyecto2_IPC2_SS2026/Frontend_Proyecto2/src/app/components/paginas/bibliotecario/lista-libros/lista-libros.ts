import { Component, OnInit, signal } from '@angular/core';
import { Libro } from '../../../modelos/biblioteca/libro';
import { LibroService } from '../../../../servicios/libro-service';
import { CardLibro } from '../control-libro/card-libro/card-libro';

@Component({
  imports: [CardLibro],
  selector: 'app-lista-libros',
  styles: ``,
  templateUrl: './lista-libros.html',
})
export class ListaLibros implements OnInit {
  mensajeError = signal<string>('');
  libros = signal<Libro[]>([]);
  pagina = signal<number>(1);

  constructor(private libroService: LibroService) {}

  ngOnInit(): void {
    this.libroService.getLibros(this.pagina()).subscribe({
      next: (libros: Libro[]) => {
        this.libros.set(libros);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  moverPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.libroService.getLibros(pagina).subscribe({
      next: (libros: Libro[]) => {
        if (libros.length === 0) {
          return;
        }
        this.libros.set(libros);
        this.pagina.set(pagina);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
