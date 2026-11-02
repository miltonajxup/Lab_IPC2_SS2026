import { Component } from '@angular/core';
import { Header } from '../../../header/header';
import { MenuSecretaria } from '../componentes/menu-secretaria/menu-secretaria';
import { RolUsuario } from '../../../modelos/rol-usuario-enum';

@Component({
  imports: [Header, MenuSecretaria],
  selector: 'app-layout-secretaria',
  styles: ``,
  templateUrl: './layout-secretaria.html',
})
export class LayoutSecretaria {
  rol: RolUsuario = RolUsuario.SECRETARIA;
}
