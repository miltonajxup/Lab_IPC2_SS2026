import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Prestamo } from "../components/modelos/biblioteca/prestamo";
import { PrestamoRequest } from "../components/modelos/biblioteca/prestamo-request";
import { PrestamoAtrasado } from "../components/modelos/biblioteca/prestamo-atrasado";

@Injectable({
    providedIn: 'root'
})

export class PrestamoService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public buscarPorNombre(nombre: string): Observable<Prestamo[]> {
        return this.httpClient.get<Prestamo[]>(`${this.restConstants.getApiUrl()}/prestamo/buscar-por-nombre/${nombre}`);
    }

    public prestamosSinTerminar(pagina: number): Observable<PrestamoAtrasado[]> {
        return this.httpClient.get<PrestamoAtrasado[]>(`${this.restConstants.getApiUrl()}/prestamo/sin-terminar/${pagina}`);
    }

    public getTodosLosPrestamos(pagina: number): Observable<Prestamo[]> {
        return this.httpClient.get<Prestamo[]>(`${this.restConstants.getApiUrl()}/prestamo/todos-los-registros/${pagina}`);
    }

    public getPrestamoUnico(id: number): Observable<Prestamo> {
        return this.httpClient.get<Prestamo>(`${this.restConstants.getApiUrl()}/prestamo/unico/${id}`);
    }

    public realizarPrestamo(prestamo: PrestamoRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/prestamo`, prestamo);
    }

    public terminarPrestamo(id: number): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/prestamo/${id}`, null);
    }
}