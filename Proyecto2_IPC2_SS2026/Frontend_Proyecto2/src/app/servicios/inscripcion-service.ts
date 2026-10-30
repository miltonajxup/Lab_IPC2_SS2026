import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { EventoInscripcionPrimerIngreso } from "../components/modelos/secretaria/inscripcion-primer-ingreso";
import { EventoInscripcionReingreso } from "../components/modelos/secretaria/inscripcion-reingreso";

@Injectable({
    providedIn: 'root'
})

export class InscripcionService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public inscripcionPrimerIngreso(inscripcion: EventoInscripcionPrimerIngreso): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/inscripcion/primer-ingreso`, inscripcion);
    }

    public inscripcionReingreso(inscripcion: EventoInscripcionReingreso): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/inscripcion/reingreso`, inscripcion);
    }
    
}