import { Nota } from "../nota";

export interface Expediente {
    dpi: string, 
    nombre: string,
    rol: string, 
    estado: string, 
    grado: string,
    notas: Nota[]
}