import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Carrera } from "../components/modelos/admin/carrera";
import { Grado } from "../components/modelos/admin/grado";

@Injectable({
    providedIn: 'root'
})

export class CarreraService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getTodasLasCarreras(pagina: number): Observable<Carrera[]> {
        return this.httpClient.get<Carrera[]>(`${this.restConstants.getApiUrl()}/carrera/todas/${pagina}`);
    }

    public getCarrerasActivas(pagina: number): Observable<Carrera[]> {
        return this.httpClient.get<Carrera[]>(`${this.restConstants.getApiUrl()}/carrera/activas/${pagina}`);
    }

    public getCarreraPorId(codigo: string): Observable<Carrera> {
        return this.httpClient.get<Carrera>(`${this.restConstants.getApiUrl()}/carrera/unico/${codigo}`);
    }

    public getCarreraPorGrado(idGrado: number): Observable<Carrera[]> {
        return this.httpClient.get<Carrera[]>(`${this.restConstants.getApiUrl()}/carrera/por-grado/${idGrado}`);
    }

    public agregarCarrera(carrera: Carrera): Observable<Carrera[]> {
        return this.httpClient.post<Carrera[]>(`${this.restConstants.getApiUrl()}/carrera`, carrera);
    }

    public modificarCarrera(carrera: Carrera, codigo: string): Observable<Carrera> {
        return this.httpClient.put<Carrera>(`${this.restConstants.getApiUrl()}/carrera/modificar/${codigo}`, carrera);
    }

    public modificarEstadoCarrera(codigo: string): Observable<Carrera> {
        return this.httpClient.put<Carrera>(`${this.restConstants.getApiUrl()}/carrera/estado/${codigo}`, null);
    }

    public getGradosDisponibles(): Observable<Grado[]> {
        return this.httpClient.get<Grado[]>(`${this.restConstants.getApiUrl()}/carrera/grados-disponibles`);
    }
}