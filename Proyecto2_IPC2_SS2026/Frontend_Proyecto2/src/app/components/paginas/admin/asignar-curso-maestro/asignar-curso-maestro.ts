import { Component, OnInit, signal } from '@angular/core';
import { Header } from '../../../header/header';
import { EmpleadoService } from '../../../../servicios/empleado-service';
import { CursoService } from '../../../../servicios/curso-service';
import { Empleado } from '../../../modelos/empleado';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';
import { Menu } from '../componentes/menu/menu';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AdminService } from '../../../../servicios/admin-service';
import { GrupoService } from '../../../../servicios/grupo-service';
import { GrupoSeccionDB } from '../../../modelos/admin/grupo-seccion-db';
import { CurriculoDB } from '../../../modelos/admin/curriculo-db';
import { Respuesta } from '../../../modelos/respuesta';

@Component({
  imports: [Menu, Header, ReactiveFormsModule],
  selector: 'app-asignar-curso-maestro',
  styles: ``,
  templateUrl: './asignar-curso-maestro.html',
})
export class AsignarCursoMaestro implements OnInit {
  rol: string = "Administrador";
  rolMaestro: RolUsuario = RolUsuario.MAESTRO;
  paginaMaestros = signal<number>(1);
  paginaCursos = signal<number>(1);
  paginaGrupos = signal<number>(1);

  maestros = signal<Empleado[]>([]);
  cursos = signal<CurriculoDB[]>([]);
  gruposAnio = signal<GrupoSeccionDB[]>([]);
  formAsignar!: FormGroup;
  mensaje = signal<string>('');
  mensajeError = signal<string>('');

  constructor(
    private empleadoService: EmpleadoService, 
    private cursoService: CursoService, 
    private formBuilder: FormBuilder,
    private adminService: AdminService, 
    private grupoService: GrupoService) {}

  ngOnInit(): void {
    this.formAsignar = this.formBuilder.group({
      maestro: [null, [Validators.required]], 
      cursoId: [null, [Validators.required]],
      grupoId: [null, [Validators.required]]
    })

    this.empleadoService.getEmpleadosPorRol(this.rolMaestro, this.paginaMaestros()).subscribe({
      next: (empleados: Empleado[]) => {
        this.maestros.set(empleados);
      }
    });
    this.grupoService.getGruposDelAño(this.paginaGrupos()).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        this.gruposAnio.set(grupos);
      }
    })
  }

  avanzarMaestros(avanzar: number): void {
    const pagina = this.paginaMaestros() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.empleadoService.getEmpleadosPorRol(this.rolMaestro, pagina).subscribe({
      next: (maestros: Empleado[]) => {
        if (maestros.length === 0) {
          return;
        }
        this.maestros.set(maestros);
        this.paginaMaestros.set(pagina)
      }
    });
  }

  avanzarCursos(avanzar: number): void {
    const pagina = this.paginaCursos() + avanzar;
    if (pagina < 1) {
      return;
    }
  }
  
  avanzarGrupos(avanzar: number): void {
    const pagina = this.paginaGrupos() + avanzar;
    if (pagina < 1) {
      return;
    }
    this.grupoService.getGruposDelAño(pagina).subscribe({
      next: (grupos: GrupoSeccionDB[]) => {
        if (grupos.length === 0) {
          return;
        }
        this.gruposAnio.set(grupos);
        this.paginaGrupos.set(pagina);
      }
    })
  }

  setMaestro(idMaestro: string): void {
    this.formAsignar.patchValue({
      maestro: idMaestro
    });
  }

  setGrupoSeccion(grupoId: number, gradoId: number, carreraId: string): void {
    this.formAsignar.patchValue({
      grupoId: grupoId,
      cursoId: null
    });
    if (carreraId) {
      this.grupoService.getCursosCurriculoCarrera(carreraId, gradoId).subscribe({
        next: (cursos: CurriculoDB[]) => {
          this.cursos.set(cursos);
        }
      });
    } else {
      this.grupoService.getCursosCurriculoGrado(gradoId).subscribe({
        next: (cursos: CurriculoDB[]) => {
          this.cursos.set(cursos);
        }
      })
    }
  }

  setCurso(cursoId: number): void {
    this.formAsignar.patchValue({
      cursoId: cursoId
    });
  }


  submit(): void {
    const asignacion = this.formAsignar.value;
    this.adminService.asignarMaestroCurso(asignacion).subscribe({
      next: (respuesta: Respuesta) => {
        this.mensaje.set(respuesta.mensaje);
        this.formAsignar.reset();
        this.mensajeError.set('');
      }, 
      error: (error: any) => {
        this.mensajeError.set(error.error);
        this.mensaje.set('');
      }
    });
  }
}
