export interface Prestamo {
    id: number,
    fechaPrestamo: Date,
    fechaDevolucionEsperada: Date,
    fechaDevolucionCompletada: Date, 
    dpiPrestamista: string,
    devuelto: boolean
}