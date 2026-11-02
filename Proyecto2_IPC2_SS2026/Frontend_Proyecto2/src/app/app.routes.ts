import { Routes } from '@angular/router';
import { LayoutSuperAdmin } from './components/paginas/super-admin/layout-super-admin/layout-super-admin';
import { ControlCicloEscolar } from './components/paginas/super-admin/control-ciclo-escolar/control-ciclo-escolar';
import { Login } from './components/paginas/login/login';
import { Form } from './components/paginas/super-admin/control-ciclo-escolar/form/form';
import { Modificar } from './components/paginas/super-admin/control-ciclo-escolar/modificar/modificar';
import { LayoutAdmin } from './components/paginas/admin/layout-admin/layout-admin';
import { AsignarCursoMaestro } from './components/paginas/admin/asignar-curso-maestro/asignar-curso-maestro';
import { ControlCarrera } from './components/paginas/admin/control-carrera/control-carrera';
import { CrearCarrera } from './components/paginas/admin/control-carrera/crear-carrera/crear-carrera';
import { ModificarCarrera } from './components/paginas/admin/control-carrera/modificar-carrera/modificar-carrera';
import { ControlCurso } from './components/paginas/admin/control-curso/control-curso';
import { CrearCurso } from './components/paginas/admin/control-curso/crear-curso/crear-curso';
import { ModificarCurso } from './components/paginas/admin/control-curso/modificar-curso/modificar-curso';
import { ControlGrupo } from './components/paginas/admin/control-grupo/control-grupo';
import { CrearParaGrado } from './components/paginas/admin/control-grupo/crear-para-grado/crear-para-grado';
import { CrearParaCarrera } from './components/paginas/admin/control-grupo/crear-para-carrera/crear-para-carrera';
import { ControlCurricculo } from './components/paginas/admin/control-curricculo/control-curricculo';
import { CrearCurriculoGrado } from './components/paginas/admin/control-curricculo/crear-para-grado/crear-para-grado';
import { CrearCurriculoCarrera } from './components/paginas/admin/control-curricculo/crear-para-carrera/crear-para-carrera';
import { ModificarCurriculoGrado } from './components/paginas/admin/control-curricculo/modificar-para-grado/modificar-para-grado';
import { ModificarCurriculoCarrera } from './components/paginas/admin/control-curricculo/modificar-para-carrera/modificar-para-carrera';
import { LayoutSecretaria } from './components/paginas/secretaria/layout-secretaria/layout-secretaria';
import { InscripcionPrimerIngreso } from './components/paginas/secretaria/inscripcion-primer-ingreso/inscripcion-primer-ingreso';
import { LayoutMaestro } from './components/paginas/maestro/layout-maestro/layout-maestro';
import { ConsultaCurso } from './components/paginas/maestro/consulta-curso/consulta-curso';
import { OpcionesCurso } from './components/paginas/maestro/opciones-curso/opciones-curso';
import { ControlAsistencia } from './components/paginas/maestro/opciones-curso/asistencia/asistencia';
import { Estudiantes } from './components/paginas/maestro/opciones-curso/estudiantes/estudiantes';
import { TomarAsistencia } from './components/paginas/maestro/opciones-curso/tomar-asistencia/tomar-asistencia';
import { MenuNota } from './components/paginas/maestro/opciones-curso/menu-nota/menu-nota';
import { ModificarNota } from './components/paginas/maestro/opciones-curso/modificar-nota/modificar-nota';
import { PrimerIngreso } from './components/paginas/login/primer-ingreso/primer-ingreso';
import { RecuperarContrasenia } from './components/paginas/login/recuperar-contrasenia/recuperar-contrasenia';
import { CambiarContrasenia } from './components/paginas/login/cambiar-contrasenia/cambiar-contrasenia';
import { NuevoContrato } from './components/paginas/admin/nuevo-contrato/nuevo-contrato';
import { VerEmpleados } from './components/paginas/admin/ver-empleados/ver-empleados';
import { ControlGrado } from './components/paginas/super-admin/control-grado/control-grado';
import { ControlLibro } from './components/paginas/bibliotecario/control-libro/control-libro';
import { LayoutBibliotecario } from './components/paginas/bibliotecario/layout-bibliotecario/layout-bibliotecario';
import { CrearLibro } from './components/paginas/bibliotecario/control-libro/crear-libro/crear-libro';
import { ModificarLibro } from './components/paginas/bibliotecario/control-libro/modificar-libro/modificar-libro';
import { GenerarBoletas } from './components/paginas/admin/generar-boletas/generar-boletas';
import { RecepcionPago } from './components/paginas/secretaria/recepcion-pago/recepcion-pago';
import { ConsularExpediente } from './components/paginas/secretaria/consular-expediente/consular-expediente';
import { ReportesAdmin } from './components/paginas/admin/reportes-admin/reportes-admin';
import { ReportesSupAdmPromedio } from './components/paginas/super-admin/reportes-sup-adm-promedio/reportes-sup-adm-promedio';
import { ReportesSecretariaPromedio } from './components/paginas/secretaria/reportes-secretaria-promedio/reportes-secretaria-promedio';
import { ReportesSupAdmSolvencias } from './components/paginas/super-admin/reportes-sup-adm-solvencias/reportes-sup-adm-solvencias';
import { ReportesAdminSolvencia } from './components/paginas/admin/reportes-admin-solvencia/reportes-admin-solvencia';
import { ReporteSecreSolvencia } from './components/paginas/secretaria/reporte-secre-solvencia/reporte-secre-solvencia';
import { ReportSuperAdminHistorialPago } from './components/paginas/super-admin/reportes-historial-pago/reportes-historial-pago';
import { ReportesAdminHistorialPago } from './components/paginas/admin/reportes-admin-historial-pago/reportes-admin-historial-pago';
import { ReporteSupAdmIngresos } from './components/paginas/super-admin/reporte-sup-adm-ingresos/reporte-sup-adm-ingresos';
import { ReporteAdminIngresos } from './components/paginas/admin/reporte-admin-ingresos/reporte-admin-ingresos';
import { PrestamoLibro } from './components/paginas/bibliotecario/prestamo-libro/prestamo-libro';
import { ConclusionPrestamo } from './components/paginas/bibliotecario/conclusion-prestamo/conclusion-prestamo';
import { RecepcionMulta } from './components/paginas/bibliotecario/recepcion-multa/recepcion-multa';
import { LayoutEstudiante } from './components/paginas/estudiante/layout-estudiante/layout-estudiante';
import { ConsultarNotas } from './components/paginas/estudiante/consultar-notas/consultar-notas';
import { RolUsuario } from './components/modelos/rol-usuario-enum';
import { RoleGuardService } from './servicios/role-guard-service';
import { EstadoCuenta } from './components/paginas/estudiante/estado-cuenta/estado-cuenta';
import { ConsultarLibros } from './components/paginas/estudiante/consultar-libros/consultar-libros';
import { Reinscripcion } from './components/paginas/secretaria/reinscripcion/reinscripcion';

export const routes: Routes = [
    { path: '', component: Login },
    { path: 'login/primer-ingreso', component: PrimerIngreso },
    { path: 'login/recuperar-contrasenia', component: RecuperarContrasenia }, 
    { path: 'login/cambiar-contrasenia/:idUsuario', component: CambiarContrasenia },



    {  
        path: 'super-admin', component: LayoutSuperAdmin, 
        canActivate: [RoleGuardService], data: {allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR]}
    }, 
    { 
        path: 'control-ciclo-escolar', component: ControlCicloEscolar, 
        canActivate: [RoleGuardService], data: {allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR]}
    }, 
    { 
        path: 'modificar-ciclo-escolar', component: Modificar, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    },
    { 
        path: 'form-ciclo-escolar', component: Form, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    }, 
    { 
        path: 'super-admin/control-grado', component: ControlGrado, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    }, 
    { 
        path: 'reporte/sup-adm-promedios', component: ReportesSupAdmPromedio, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    }, 
    { 
        path: 'reporte/sup-adm-solvencia', component: ReportesSupAdmSolvencias, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    }, 
    { 
        path: 'reporte/sup-adm-historial-pago', component: ReportSuperAdminHistorialPago, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    }, 
    { 
        path: 'reporte/sup-adm-ingresos', component: ReporteSupAdmIngresos, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR] }
    }, 




    { 
        path: 'admin', component: LayoutAdmin, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    }, 
    { 
        path: 'asingar-curso-maestro', component: AsignarCursoMaestro, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    }, 
    { 
        path: 'carrera', component: ControlCarrera, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    }, 
    { 
        path: 'carrera/crear', component: CrearCarrera, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    },
    { 
        path: 'carrera/modificar/:codigo', component: ModificarCarrera, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    },


    { 
        path: 'curso', component: ControlCurso, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    },
    { 
        path: 'curso/crear', component: CrearCurso, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'curso/modificar/:id', component: ModificarCurso, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    },

    { 
        path: 'grupo', component: ControlGrupo, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    },
    { 
        path: 'grupo/crear/grado', component: CrearParaGrado, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'grupo/crear/carrera', component: CrearParaCarrera, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 


    { 
        path: 'curriculo', component: ControlCurricculo, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'curriculo/crear/grado', component: CrearCurriculoGrado, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'curriculo/crear/carrera', component: CrearCurriculoCarrera, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'curriculo/modificar/grado', component: ModificarCurriculoGrado, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'curriculo/modificar/carrera', component: ModificarCurriculoCarrera, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 


    { 
        path: 'admin/nuevo-contrato', component: NuevoContrato, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    }, 
    { 
        path: 'admin/ver-empleados', component: VerEmpleados, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    }, 
    { 
        path: 'admin/generar-boletas', component: GenerarBoletas, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    },
    { 
        path: 'admin/reportes/promedios', component: ReportesAdmin, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] }
    }, 
    { 
        path: 'admin/reportes/solvencia', component: ReportesAdminSolvencia, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    },
    { 
        path: 'admin/reportes/historial-pago', component: ReportesAdminHistorialPago, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    { 
        path: 'admin/reportes/ingresos', component: ReporteAdminIngresos, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR] } 
    }, 
    



    { 
        path: 'secretaria', component: LayoutSecretaria, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    },
    { 
        path: 'inscripcion/primer-ingreso', component: InscripcionPrimerIngreso, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    }, 
    {
        path: 'inscripcion/reingreso', component: Reinscripcion, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    }, 
    { 
        path: 'recepcion-pago', component: RecepcionPago, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    }, 
    { 
        path: 'consulta-expediente', component: ConsularExpediente, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    }, 
    { 
        path: 'reporte/secre/promedios', component: ReportesSecretariaPromedio, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    }, 
    { 
        path: 'reporte/secre/solvencias', component: ReporteSecreSolvencia, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.SECRETARIA] } 
    }, 




    { 
        path: 'maestro', component:LayoutMaestro, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] }
    }, 
    { 
        path: 'maestro/curso', component: ConsultaCurso, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 
    { 
        path: 'maestro/opciones-curso/:idMaes', component: OpcionesCurso, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 
    { 
        path: 'maestro/estudianes/:idMaes/:idGrupo/:idCurso', component: Estudiantes, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 
    { 
        path: 'maestro/menu-asistencia/:idMaes/:idGrupo/:idCurso', component: ControlAsistencia, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 
    { 
        path: 'maestro/tomar-asistencia/:idMaes/:idGrupo/:idCurso/:fecha', component:  TomarAsistencia, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 
    { 
        path: 'maestro/menu-nota/:idMaes/:idGrupo/:idCurso', component: MenuNota, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 
    { 
        path: 'maestro/menu-nota/modificar/:idMaes/:idGrupo/:idCurso/:idNota', component: ModificarNota, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.MAESTRO] } 
    }, 




    { 
        path: 'bibliotecario', component: LayoutBibliotecario, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    }, 
    { 
        path: 'control-libro', component: ControlLibro, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    }, 
    { 
        path: 'libro/crear', component: CrearLibro, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    }, 
    { 
        path: 'libro/modificar/:codigo', component: ModificarLibro, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    }, 
    { 
        path: 'prestamo', component: PrestamoLibro, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    }, 
    { 
        path: 'recepcion-prestamo', component: ConclusionPrestamo, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    } ,
    { 
        path: 'recepcion-multa', component: RecepcionMulta, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.BIBLIOTECARIO] } 
    }, 




    { 
        path: 'estudiante', component: LayoutEstudiante, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ESTUDIANTE] } 
    }, 
    { 
        path: 'estudiante/consultar-notas', component: ConsultarNotas, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ESTUDIANTE] } 
    },
    {
        path: 'estudiante/estado-cuenta', component: EstadoCuenta, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ESTUDIANTE] } 
    }, 
    {
        path: 'estudiante/consultar-libros', component: ConsultarLibros, 
        canActivate: [RoleGuardService], data: { allowedRoles:[RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ESTUDIANTE] } 
    }
];
