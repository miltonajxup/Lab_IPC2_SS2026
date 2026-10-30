export interface PagoBoleta {
    idPago: number, 
    idBoleta: number, 
    monto: number, 
    pagado: boolean, 
    tipoPago: string, 
    mesPago: string, 
    anio: number, 
    dpiEstudiante: string, 
    nombreEstudiante: string, 
    fechaPago: Date, 
    nombreSecretaria: string
}