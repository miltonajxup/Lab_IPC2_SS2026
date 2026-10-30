import { RolUsuario } from "./rol-usuario-enum";

export interface Usuario {
    dpi?: string, 
    nombre: string, 
    contrasenia?: string,
    //tipo: string,
    rol?: RolUsuario,
    salario?: number,
    fechaContratacion?: Date,
    numeroEncargado?: string,
    numeroEstudiante?: string,
    estado?: string
}