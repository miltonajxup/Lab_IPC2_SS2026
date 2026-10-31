import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuBibliotecario } from '../menu-bibliotecario/menu-bibliotecario';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Libro } from '../../../modelos/biblioteca/libro';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrestamoService } from '../../../../servicios/prestamo-service';
import { LibroService } from '../../../../servicios/libro-service';
import { UsuarioService } from '../../../../servicios/usuario-service';
import { Usuario } from '../../../modelos/usuario';
import { PrestamoRequest } from '../../../modelos/biblioteca/prestamo-request';
import { LibroPrestamo } from '../../../modelos/biblioteca/libro-prestamo';
import { LibroPrestamoRequest } from '../../../modelos/biblioteca/libro-prestamo-request';

@Component({
  imports: [Header, MenuBibliotecario, ReactiveFormsModule],
  selector: 'app-prestamo-libro',
  styles: ``,
  templateUrl: './prestamo-libro.html',
})
export class PrestamoLibro implements OnInit {
  rol: RolUsuario = RolUsuario.BIBLIOTECARIO;
  libros = signal<Libro[]>([]);
  busquedaForm!: FormGroup;
  prestamoForm!: FormGroup;
  pagina = signal<number>(1);
  librosElegidos = signal<LibroPrestamo[]>([]);
  usuario = signal<Usuario | undefined>(undefined);
  mensajeError = signal<string>('');
  operacionExitosa = signal<boolean>(false);

  constructor(
    private prestamoService: PrestamoService, 
    private librosService: LibroService, 
    private usuarioService: UsuarioService, 
    private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.llamarLibros();

    this.busquedaForm = this.formBuilder.group({
      idUsuario: [null, Validators.required]
    });

    this.prestamoForm = this.formBuilder.group({
      fechaPrestamo: [null, Validators.required], 
      fechaDevolucionEsperada: [null, Validators.required], 
      idPrestamista: [null, Validators.required], 
      librosPrestamo: [[], Validators.required]
    });
  }

  moverPagina(paso: number): void {
    const pagina = this.pagina() + paso;
    if (pagina < 1) {
      return;
    }
    this.librosService.getLibros(pagina).subscribe({
      next: (libros: Libro[]) => {
        if (libros.length === 0) {
          return;
        }
        this.libros.set(libros);
        this.pagina.set(pagina);
      }
    })
  }

  buscarUsuario(): void {
    this.mensajeError.set('');
    this.usuario.set(undefined);
    const referencia: string = this.busquedaForm.value.idUsuario;
    this.usuarioService.buscarUsuarioPorReferencia(referencia).subscribe({
      next: (usuario: Usuario) => {
        this.usuario.set(usuario);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  elegirUsuario(): void {
    if (!this.usuario()) {
      return;
    }
    this.prestamoForm.patchValue({
      idPrestamista: this.usuario()?.dpi
    });
  }

  elegirLibro(libro: Libro): void {
    let libros: LibroPrestamo[] = this.librosElegidos();
    for (let i = 0; i < libros.length; i++) {
      if (libro.codigo === libros[i].codigo) {
        libros.splice(i, 1);
        this.librosElegidos.set(libros);
        return;
      }
    }
    libros.push({
      codigo: libro.codigo, 
      cantidad: libro.cantidadDisponible, 
      titulo: libro.titulo
    });
    this.librosElegidos.set(libros);
    this.prestamoForm.patchValue({librosPrestamo: libros});
  }

  crearPrestamo(): void {
    this.operacionExitosa.set(false);
    this.mensajeError.set('');
    if (this.prestamoForm.invalid) {
      return;
    }
    this.mensajeError.set('');
    if (this.libros().length === 0) {
      this.mensajeError.set('No se han elegido los libros del prestamo');
      return;
    }
    
    const prestamo: PrestamoRequest = this.prestamoForm.value;
    this.prestamoService.realizarPrestamo(prestamo).subscribe({
      next: () => {
        this.operacionExitosa.set(true);
        this.prestamoForm.reset();
        this.llamarLibros();
        this.librosElegidos.set([]);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  private llamarLibros(): void {
    this.librosService.getLibros(this.pagina()).subscribe({
      next: (libros: Libro[]) => {
        this.libros.set(libros);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

}
