import { LibroPrestamo } from "./libro-prestamo"

export interface Prestamo {
    id: number,
    fechaPrestamo: Date,
    fechaDevolucionEsperada: Date,
    fechaDevolucionCompletada: Date, 
    dpiPrestamista: string,
    nombrePrestamista: string, 
    devuelto: boolean,
    libros: LibroPrestamo[]
}