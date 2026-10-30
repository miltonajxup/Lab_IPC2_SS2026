import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Nota } from "../components/modelos/nota";
import { InformacionMedica } from "../components/modelos/estudiante/informacion-medica";
import { Prestamo } from "../components/modelos/biblioteca/prestamo";
import { Multa } from "../components/modelos/biblioteca/multa";
import { BoletaPago } from "../components/modelos/admin/boleta-pago";
import { PagoBoleta } from "../components/modelos/estudiante/pago-boleta";

@Injectable({
    providedIn: 'root'
})

export class EstudianteService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public buscarNotas(idEstudiante: string): Observable<Nota[]> {
        return this.httpClient.get<Nota[]>(`${this.restConstants.getApiUrl()}/estudiante/notas/${idEstudiante}`);
    }

    public buscarInformacionMedica(idEstudiante: string): Observable<InformacionMedica> {
        return this.httpClient.get<InformacionMedica>(`${this.restConstants.getApiUrl()}/estudiante/info-medica/${idEstudiante}`);
    }

    public modificarInforMedica(idEstudiante: string, info: InformacionMedica): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/estudiante/modificar-info-medica/${idEstudiante}`, info);
    }

    public getPrestamosPendientes(idEstudiante: string): Observable<Prestamo[]> {
        return this.httpClient.get<Prestamo[]>(`${this.restConstants.getApiUrl()}/estudiante/prestamos-pendientes/${idEstudiante}`);
    }

    public getMultasSinPagar(idEstudiante: string): Observable<Multa[]> {
        return this.httpClient.get<Multa[]>(`${this.restConstants.getApiUrl()}/estudiante/multas/${idEstudiante}`);
    }

    public getBoletasDelAño(idEstudiante: string): Observable<BoletaPago[]> {
        return this.httpClient.get<BoletaPago[]>(`${this.restConstants.getApiUrl()}/estudiante/boletas-año/${idEstudiante}`);
    }

    public getPagosRealizados(idEstudiante: string): Observable<PagoBoleta[]> {
        return this.httpClient.get<PagoBoleta[]>(`${this.restConstants.getApiUrl()}/estudiante/pagos-realizados/${idEstudiante}`);
    }
}