import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Usuario } from "../components/modelos/usuario";

@Injectable({
    providedIn: 'root'
})

export class ConsultaEstudianteEstudiante {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getEstudiantesPagoSeccion(idGrado: number, idSeccion: number): Observable<Usuario[]> {
        return this.httpClient.get<Usuario[]>(`${this.restConstants.getApiUrl()}/consulta-estudiante/grado-seccion/${idGrado}/${idSeccion}`);
    }

    public getEstudiantesCarreraSeccion(idCarrera: number, idSeccion: number): Observable<Usuario[]> {
        return this.httpClient.get<Usuario[]>(`${this.restConstants.getApiUrl()}/carrera-seccion/${idCarrera}/${idSeccion}`);
    }

    public getEstudiantePorNombre(nombre: string): Observable<Usuario> {
        return this.httpClient.get<Usuario>(`${this.restConstants.getApiUrl()}/por-nombre/${nombre}`);
    }
}