import { PagoBoleta } from "./pago-boleta";

export interface HistorialPagoEstudiante {
    historial: PagoBoleta[], 
    pagosPendientes: PagoBoleta[], 
    dineroIngresado: number, 
    saldoPendiente: number
}