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

export const routes: Routes = [
    { path: '', component: Login },
    { path: 'login/primer-ingreso', component: PrimerIngreso },
    { path: 'login/recuperar-contrasenia', component: RecuperarContrasenia }, 
    { path: 'login/cambiar-contrasenia/:idUsuario', component: CambiarContrasenia },


    { path: 'super-admin', component: LayoutSuperAdmin }, 
    { path: 'control-ciclo-escolar', component: ControlCicloEscolar }, 
    { path: 'modificar-ciclo-escolar', component: Modificar },
    { path: 'form-ciclo-escolar', component: Form }, 
    { path: 'super-admin/control-grado', component: ControlGrado }, 
    { path: 'reporte/sup-adm-promedios', component: ReportesSupAdmPromedio }, 
    { path: 'reporte/sup-adm-solvencia', component: ReportesSupAdmSolvencias }, 
    { path: 'reporte/sup-adm-historial-pago', component: ReportSuperAdminHistorialPago }, 
    { path: 'reporte/sup-adm-ingresos', component: ReporteSupAdmIngresos }, 


    { path: 'admin', component: LayoutAdmin }, 
    { path: 'asingar-curso-maestro', component: AsignarCursoMaestro }, 
    { path: 'carrera', component: ControlCarrera }, 
    { path: 'carrera/crear', component: CrearCarrera },
    { path: 'carrera/modificar/:codigo', component: ModificarCarrera },
    { path: 'admin/nuevo-contrato', component: NuevoContrato }, 
    { path: 'admin/ver-empleados', component: VerEmpleados }, 
    { path: 'admin/generar-boletas', component: GenerarBoletas },
    { path: 'admin/reportes/promedios', component: ReportesAdmin }, 
    { path: 'admin/reportes/solvencia', component: ReportesAdminSolvencia },
    { path: 'admin/reportes/historial-pago', component: ReportesAdminHistorialPago }, 
    { path: 'admin/reportes/ingresos', component: ReporteAdminIngresos }, 
    

    { path: 'curso', component: ControlCurso },
    { path: 'curso/crear', component: CrearCurso }, 
    { path: 'curso/modificar/:id', component: ModificarCurso },

    { path: 'grupo', component: ControlGrupo },
    { path: 'grupo/crear/grado', component: CrearParaGrado }, 
    { path: 'grupo/crear/carrera', component: CrearParaCarrera }, 

    { path: 'curriculo', component: ControlCurricculo }, 
    { path: 'curriculo/crear/grado', component: CrearCurriculoGrado }, 
    { path: 'curriculo/crear/carrera', component: CrearCurriculoCarrera }, 
    { path: 'curriculo/modificar/grado', component: ModificarCurriculoGrado}, 
    { path: 'curriculo/modificar/carrera', component: ModificarCurriculoCarrera }, 

    { path: 'secretaria', component: LayoutSecretaria },
    { path: 'inscripcion/primer-ingreso', component: InscripcionPrimerIngreso }, 
    { path: 'recepcion-pago', component: RecepcionPago }, 
    { path: 'consulta-expediente', component: ConsularExpediente }, 
    { path: 'reporte/secre/promedios', component: ReportesSecretariaPromedio }, 
    { path: 'reporte/secre/solvencias', component: ReporteSecreSolvencia }, 

    { path: 'maestro', component:LayoutMaestro }, 
    { path: 'maestro/curso', component: ConsultaCurso }, 
    { path: 'maestro/opciones-curso/:idMaes', component: OpcionesCurso }, 
    { path: 'maestro/estudianes/:idMaes/:idGrupo/:idCurso', component: Estudiantes }, 
    { path: 'maestro/menu-asistencia/:idMaes/:idGrupo/:idCurso', component: ControlAsistencia }, 
    { path: 'maestro/tomar-asistencia/:idMaes/:idGrupo/:idCurso/:fecha', component:  TomarAsistencia}, 
    { path: 'maestro/menu-nota/:idMaes/:idGrupo/:idCurso', component: MenuNota }, 
    { path: 'maestro/menu-nota/modificar/:idGrupo/:idCurso/:idNota', component: ModificarNota }, 

    { path: 'bibliotecario', component: LayoutBibliotecario }, 
    { path: 'control-libro', component: ControlLibro }, 
    { path: 'libro/crear', component: CrearLibro }, 
    { path: 'libro/modificar/:codigo', component: ModificarLibro }, 
];
