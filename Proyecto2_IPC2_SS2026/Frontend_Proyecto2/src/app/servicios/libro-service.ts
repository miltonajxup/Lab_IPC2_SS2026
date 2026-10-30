import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Libro } from "../components/modelos/biblioteca/libro";

@Injectable({
    providedIn: 'root'
})

export class LibroService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getLibros(pagina: number): Observable<Libro[]> {
        return this.httpClient.get<Libro[]>(`${this.restConstants.getApiUrl()}/libro/todos/${pagina}`);
    }

    public getLibroPorId(codigo: string): Observable<Libro> {
        return this.httpClient.get<Libro>(`${this.restConstants.getApiUrl()}/libro/unico/${codigo}`);
    }

    public agregarLlibro(libro: Libro): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/libro/`, libro);
    }

    public modificar(libro: Libro, codigo: string): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/libro/modificar/${codigo}`, libro);
    }

    public modificarCantidad(libro: Libro, codigo: string): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/libro/modificar-cantidad/${codigo}`, libro);
    }
}