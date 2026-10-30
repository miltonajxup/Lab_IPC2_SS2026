import { RolUsuario } from "./rol-usuario-enum";

export interface Empleado {
    dpi: string,
    nombre: string,
    rol: RolUsuario,
    salario?: number,
    fechaContratacion?: Date,
}