import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SecretariaService } from '../../../../servicios/secretaria-service';
import { Expediente } from '../../../modelos/secretaria/expediente';
import { Nota } from '../../../modelos/nota';

@Component({
  imports: [Header, MenuSecretaria, ReactiveFormsModule],
  selector: 'app-consular-expediente',
  styles: ``,
  templateUrl: './consular-expediente.html',
})
export class ConsularExpediente implements OnInit {
  rol: RolUsuario = RolUsuario.SECRETARIA;
  busquedaForm!: FormGroup;
  expediente = signal<Expediente | undefined>(undefined);
  notas = signal<Nota[]>([]);
  mensajeError = signal<string>('');
  
  constructor(private secretariaService: SecretariaService, private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.busquedaForm = this.formBuilder.group({
      idEstudiante: [null, Validators.required]
    });
  }
  
  buscar(): void {
    const id: string = this.busquedaForm.value.idEstudiante;
    this.secretariaService.getExpediente(id).subscribe({
      next: (expediente: Expediente) => {
        this.expediente.set(expediente);
        this.notas.set(expediente.notas);
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
      }
    });
  }
}
