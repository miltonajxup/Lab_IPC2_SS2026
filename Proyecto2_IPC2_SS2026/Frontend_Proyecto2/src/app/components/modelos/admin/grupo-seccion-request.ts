import { Seccion } from "../seccion-enum";

export interface GrupoSeccionRequest {
    anioLectivo: number,
    grado: number,
    carrera?: string,
    seccion: Seccion,
}