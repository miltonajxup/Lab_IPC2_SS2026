import { Component, OnInit, signal } from '@angular/core';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';
import { Mes } from '../../../modelos/mes-enum';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminService } from '../../../../servicios/admin-service';
import { Respuesta } from '../../../modelos/respuesta';
import { KeyValuePipe } from '@angular/common';
import { MesBoleta } from '../../../modelos/admin/mes-boleta';

@Component({
  imports: [Header, Menu, ReactiveFormsModule, KeyValuePipe],
  selector: 'app-generar-boletas',
  styles: ``,
  templateUrl: './generar-boletas.html',
})
export class GenerarBoletas implements OnInit {
  rol: RolUsuario = RolUsuario.ADMINISTRADOR;
  meses = Mes;
  boletaForm!: FormGroup;
  mensaje = signal<string>('');
  
  constructor(private formBuilder: FormBuilder, private adminService: AdminService) {}

  ngOnInit(): void {
    this.boletaForm = this.formBuilder.group({
      mes: [null, Validators.required]
    });
  }

  submit(): void {
    if (this.boletaForm.valid) {
      const mes: MesBoleta = this.boletaForm.value;
      this.adminService.generarBoletasMes(mes).subscribe({
        next: (respuesta: Respuesta) => {
          this.mensaje.set(respuesta.mensaje);
          this.boletaForm.reset();
        }, 
        error: (error: any) => {
          console.log(error.error);
        }
      });
    }
  }
}
