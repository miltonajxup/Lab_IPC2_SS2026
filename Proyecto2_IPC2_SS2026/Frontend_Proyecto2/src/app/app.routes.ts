import { Routes } from '@angular/router';
import { LayoutSuperAdmin } from './components/paginas/super-admin/layout-super-admin/layout-super-admin';
import { ControlCicloEscolar } from './components/paginas/super-admin/control-ciclo-escolar/control-ciclo-escolar';
import { Login } from './components/paginas/login/login';
import { Form } from './components/paginas/super-admin/control-ciclo-escolar/form/form';
import { Modificar } from './components/paginas/super-admin/control-ciclo-escolar/modificar/modificar';
import { LayoutAdmin } from './components/paginas/admin/layout-admin/layout-admin';
import { AsignarCursoMaestro } from './components/paginas/admin/asignar-curso-maestro/asignar-curso-maestro';

export const routes: Routes = [
    { path: '', component: Login },


    { path: 'super-admin', component: LayoutSuperAdmin }, 
    { path: 'control-ciclo-escolar', component: ControlCicloEscolar }, 
    { path: 'modificar-ciclo-escolar', component: Modificar },
    { path: 'form-ciclo-escolar', component: Form }, 


    { path: 'admin', component: LayoutAdmin }, 
    { path: 'asingar-curso-maestro', component: AsignarCursoMaestro },
];
