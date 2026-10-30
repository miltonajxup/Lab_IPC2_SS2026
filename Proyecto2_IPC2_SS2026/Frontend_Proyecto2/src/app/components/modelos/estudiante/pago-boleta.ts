export interface PagoBoleta {
    idPago: number,
    idBoleta: number,
    monto: number,
    pagado: boolean,
    tipoPago: string,
    mesPago: string,
    año: number,
    dpiEstudiante: string,
    nombreEstudiante: string,
    fechaPago: Date,
    nombreSecretaria: string
}