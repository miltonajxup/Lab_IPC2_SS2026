import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Libro } from '../../../modelos/biblioteca/libro';
import { LibroService } from '../../../../servicios/libro-service';
import { MenuBibliotecario } from '../menu-bibliotecario/menu-bibliotecario';
import { RouterLink } from '@angular/router';

@Component({
  imports: [Header, MenuBibliotecario, RouterLink],
  selector: 'app-control-libro',
  styles: ``,
  templateUrl: './control-libro.html',
})
export class ControlLibro implements OnInit {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
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
