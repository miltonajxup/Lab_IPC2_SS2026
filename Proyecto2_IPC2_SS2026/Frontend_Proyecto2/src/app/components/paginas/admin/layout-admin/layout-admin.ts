import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { Menu } from '../componentes/menu/menu';

@Component({
  imports: [Menu, Header],
  selector: 'app-layout-super-admin',
  styles: ``,
  templateUrl: './layout-admin.html',
})
export class LayoutAdmin {
  rol: string = 'Administrador';
}
