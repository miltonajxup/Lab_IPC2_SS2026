import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../../header/header';
import { MenuBibliotecario } from '../../menu-bibliotecario/menu-bibliotecario';
import { FormLibro } from '../form/form';
import { RolUsuario } from '../../../../modelos/rol-usuario-enum';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Libro } from '../../../../modelos/biblioteca/libro';
import { LibroService } from '../../../../../servicios/libro-service';

@Component({
  imports: [Header, MenuBibliotecario, FormLibro, RouterLink],
  selector: 'app-modificar-libro',
  styles: ``,
  templateUrl: './modificar-libro.html',
})
export class ModificarLibro implements OnInit {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
  codigoLibro!: string;
  libroParaActualizar = signal<Libro | undefined>(undefined);
  mensajeError = signal<string>('');

  constructor(private router: ActivatedRoute, private libroService: LibroService) {}

  ngOnInit(): void {
    this.codigoLibro = this.router.snapshot.params['codigo'];
    this.libroService.getLibroPorId(this.codigoLibro).subscribe({
      next: (libro: Libro) => {
        this.libroParaActualizar.set(libro);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
