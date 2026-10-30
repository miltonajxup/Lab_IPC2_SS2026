import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { BoletaPago } from "../components/modelos/admin/boleta-pago";

@Injectable({
    providedIn: 'root'
})

export class RecepcionPagoService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getBoletaEstudiante(idEstudiante: string): Observable<BoletaPago[]> {
        return this.httpClient.get<BoletaPago[]>(`${this.restConstants.getApiUrl()}/recepcion-pago/${idEstudiante}`);
    }

    public pagarBoleta(idPago: number, idSecretaria: string): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/recepcion-pago/${idPago}/${idSecretaria}`, null);
    }
}