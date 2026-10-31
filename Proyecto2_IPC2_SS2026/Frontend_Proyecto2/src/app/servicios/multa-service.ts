import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Multa } from "../components/modelos/biblioteca/multa";
import { MultaRequest } from "../components/modelos/biblioteca/multa-request";

@Injectable({
    providedIn: 'root'
})

export class MultaService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getMultasPendientes(pagina: number): Observable<Multa[]> {
        return this.httpClient.get<Multa[]>(`${this.restConstants.getApiUrl()}/multa/${pagina}`);
    }

    public getMultaPorPrestamo(idPrestamo: number): Observable<Multa> {
        return this.httpClient.get<Multa>(`${this.restConstants.getApiUrl()}/multa/por-prestamo/${idPrestamo}`);
    }

    public agregarMulta(multa: MultaRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/multa`, multa);
    }

    public pagarMulta(id: number): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/multa`, null);
    }
}