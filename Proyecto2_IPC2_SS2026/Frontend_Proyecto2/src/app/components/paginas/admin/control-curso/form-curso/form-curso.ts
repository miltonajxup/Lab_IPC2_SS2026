import { Component, Input, OnInit, signal } from '@angular/core';
import { Curso } from '../../../../modelos/admin/curso';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CursoService } from '../../../../../servicios/curso-service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-form-curso',
  styles: ``,
  templateUrl: './form-curso.html',
})
export class FormCurso implements OnInit {
  @Input()
  cursoParaEditar!: Curso;
  @Input()
  isEditMode!: boolean;

  mensajeError = signal<string>('');
  operacionCompletada = signal<boolean>(false);
  cursoForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private cursoService: CursoService) {}

  ngOnInit(): void {
    this.cursoForm = this.formBuilder.group({
      idCurso: [null],
      nombre: [null, [Validators.required, Validators.maxLength(40)]],
      descripcion: [null, [Validators.maxLength(300)]]
    });
    this.reset();
  }
  submit(): void {
    if (this.cursoForm.valid) {
      if (this.isEditMode) {
        this.modificarCurso();
      } else {
        this.crearCurso();
      }
    }
  }

  private crearCurso(): void {
    const nuevo = this.cursoForm.value;
    this.cursoService.agregarCurso(nuevo).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }

  private modificarCurso(): void {
    const id: number = this.cursoParaEditar.idCurso;
    this.cursoParaEditar = this.cursoForm.value;
    const {idCurso, ...cursoRequest} = this.cursoParaEditar;
    this.cursoService.modificarCurso(cursoRequest, id).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }

  private reset(): void {
    if (this.isEditMode) {
      this.cursoForm.reset(this.cursoParaEditar);
    } else {
      this.cursoForm.reset();
    }
    this.operacionCompletada.set(false);
    this.mensajeError.set('');
  }

}
