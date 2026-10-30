import { Usuario } from "../usuario";

export interface EventoInscripcionPrimerIngreso {
    estudiante: Usuario,
    grupoId: number
}