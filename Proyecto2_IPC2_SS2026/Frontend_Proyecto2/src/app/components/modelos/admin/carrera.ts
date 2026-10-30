import { Grado } from "./grado";

export interface Carrera {
    codigo: string,
    nombre: string,
    estado?: boolean,
    grados?: Grado[],
    gradosId?: number[]
}