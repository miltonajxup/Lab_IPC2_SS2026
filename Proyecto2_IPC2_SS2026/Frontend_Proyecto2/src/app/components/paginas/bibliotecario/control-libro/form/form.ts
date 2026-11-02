import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LibroService } from '../../../../../servicios/libro-service';
import { Libro } from '../../../../modelos/biblioteca/libro';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-form-libro',
  styles: ``,
  templateUrl: './form.html',
})
export class FormLibro implements OnInit {
  @Input()
  libroParaEditar!: Libro;
  @Input()
  isEditMode!: boolean;

  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  libroForm!: FormGroup;

  constructor(private libroService: LibroService, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.libroForm = this.formBuilder.group({
      codigo: [null, [Validators.required, Validators.maxLength(20)]], 
      titulo: [null, [Validators.required, Validators.maxLength(60)]], 
      autor: [null, [Validators.required, Validators.maxLength(60)]], 
      editorial: [null, [Validators.required, Validators.maxLength(60)]], 
      cantidadDisponible: [null, [Validators.required, Validators.min(0)]], 
    });
    this.reset();
  }

  submit(): void {
    if (this.libroForm.valid) {
      if (this.isEditMode) {
        this.modificarLibro();
      } else {
        this.crearLibro();
      }
    }
  }

  private crearLibro(): void {
    const libro = this.libroForm.value;
    this.libroService.agregarLlibro(libro).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  private modificarLibro(): void {
    this.libroParaEditar = this.libroForm.value;
    const libro = this.libroForm.value;
    this.libroService.modificar(libro, libro.codigo).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }

  private reset(): void {
    if (this.isEditMode) {
      this.libroForm.reset(this.libroParaEditar);
    } else {
      this.libroForm.reset();
    }
    this.mensajeError.set('');
    this.operacionCompletada.set(false);
  }

}
