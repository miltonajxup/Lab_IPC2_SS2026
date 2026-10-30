import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Carrera } from '../../../../modelos/admin/carrera';
import { CarreraService } from '../../../../../servicios/carrera-service';
import { Grado } from '../../../../modelos/admin/grado';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-form-carrera',
  styles: ``,
  templateUrl: './form-carrera.html',
})
export class FormCarrera implements OnInit {

  @Input()
  isEditMode!: boolean;
  @Input()
  carreraParaEditar!: Carrera;

  carreraForm!: FormGroup;
  grados = signal<Grado[]>([]);
  operacionCompletada = signal<boolean>(false);
  mensajeError = signal<string>('');
  estadoCarrera = signal<boolean>(false);

  constructor(private formBuilder: FormBuilder, private carreraService: CarreraService) {}

  ngOnInit(): void {
    this.carreraForm = this.formBuilder.group({
      codigo: [null, [Validators.required, Validators.maxLength(20)]],
      nombre: [null, [Validators.required, Validators.maxLength(60)]],
      gradosId: [[], [Validators.required]]
    });
    this.carreraService.getGradosDisponibles().subscribe({
      next: (grados: Grado[]) => {
        this.grados.set(grados);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
    if (this.isEditMode && this.carreraParaEditar) {
      this.estadoCarrera.set(this.carreraParaEditar.estado!);
    }
    this.reset();
  }

  marcarGrado(idGrado: number): void {
    let grados: number[] = this.carreraForm.value.gradosId ?? [];
    
    for (let i = 0; i < grados.length; i++) {
      if (grados[i] === idGrado) {
        grados.splice(i, 1);
        this.carreraForm.patchValue({
          gradosId: grados
        });
        return;
      }
    }
    grados.push(idGrado);
    this.carreraForm.patchValue({
      gradosId: grados
    });
    console.log(grados);
  }

  submit(): void {
    if (this.carreraForm.valid) {
      if (this.isEditMode) {
        this.modificarCarrera();
      } else {
        this.crearCarrera();
      }
    }
  }

  private crearCarrera(): void {
    const nuevo = this.carreraForm.value;
    this.carreraService.agregarCarrera(nuevo).subscribe({
      next: () => {
        this.reset();
        this.operacionCompletada.set(true);
      },
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }

  private modificarCarrera(): void {
    this.carreraParaEditar = this.carreraForm.value;
    const codigo = this.carreraParaEditar.codigo;
    this.carreraService.modificarCarrera(this.carreraParaEditar, codigo).subscribe({
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
      this.carreraForm.reset(this.carreraParaEditar);
      this.cargarGrados();
    } else {
      this.carreraForm.reset();
    }
    this.operacionCompletada.set(false);
    this.mensajeError.set('');
  }

  seleccionado(idGrado: number): boolean {
    const grados: number[] = this.carreraForm.value.gradosId ?? [];
    for (let i = 0; i < grados.length; i++ ) {
      if (grados[i] == idGrado) {
        return true;
      }
    }
    return false;
  }

  marcarGradoo(idGrado: number): void {
    const grados: number[] = this.carreraForm.value.gradosId ?? [];

    const index = grados.indexOf(idGrado);

    if (index !== -1) {
      grados.splice(index, 1);
    } else {
      grados.push(idGrado);
    }

    this.carreraForm.patchValue({
      gradosId: grados
    });
  }

  private cargarGrados(): void {
    let grados: number[] = [];
    let gradosCarrera: Grado[] = this.carreraParaEditar.grados ?? [];
    if (this.carreraParaEditar) {
      for (let i = 0; i < gradosCarrera.length; i++) {
        grados.push(gradosCarrera[i].id);
      }
    }
    this.carreraForm.patchValue({gradosId: grados});
  }

  cambiarEstado(): void {
    const codigo: string = this.carreraParaEditar.codigo;
    this.carreraService.modificarEstadoCarrera(codigo).subscribe({
      next: () => {
        this.estadoCarrera.update(estado => !estado);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    })
  }
}
