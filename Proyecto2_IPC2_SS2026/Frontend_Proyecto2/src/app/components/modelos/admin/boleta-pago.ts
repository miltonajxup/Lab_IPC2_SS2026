export interface BoletaPago {
    id: number,
    monto: number,
    estado: boolean,
    tipoPago: string, 
    mesPago: string,
    cicloEscolar: number,
    idEstudiante: string, 
    nombreEstudiante: string
}