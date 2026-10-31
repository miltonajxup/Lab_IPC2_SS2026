import { LibroPrestamoRequest } from "./libro-prestamo-request"

export interface PrestamoRequest {
    fechaPrestamo: Date, 
    fechaDevolucionEsperada: Date,
    fechaDevolucionCompletada: Date, 
    idPrestamista: string,
    librosPrestamo: LibroPrestamoRequest[]
}