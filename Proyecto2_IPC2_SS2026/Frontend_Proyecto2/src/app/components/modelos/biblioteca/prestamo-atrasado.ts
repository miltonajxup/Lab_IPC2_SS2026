import { LibroPrestamo } from "./libro-prestamo";

export interface PrestamoAtrasado {
    id: number,
    fechaPrestamo: Date,
    fechaDevolucionEsperada: Date,
    fechaDevolucionCompletada: Date, 
    dpiPrestamista: string,
    nombrePrestamista: string, 
    devuelto: boolean,
    libros: LibroPrestamo[], 
    diasAtraso: number, 
    montoMulta: number
}