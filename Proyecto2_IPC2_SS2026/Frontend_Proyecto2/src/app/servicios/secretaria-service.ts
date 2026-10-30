import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Expediente } from "../components/modelos/secretaria/expediente";

@Injectable({
    providedIn: 'root'
})

export class SecretariaService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getExpediente(idEstudiante: string): Observable<Expediente> {
        return this.httpClient.get<Expediente>(`${this.restConstants.getApiUrl()}/secretaria/expediente-estudiante/${idEstudiante}`);
    }
}