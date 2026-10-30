import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { AñoLectivo } from "../components/modelos/admin/año-lectivo";

@Injectable({
    providedIn: 'root'
})

export class SuperAdminService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getAñoActivo(): Observable<AñoLectivo> {
        return this.httpClient.get<AñoLectivo>(`${this.restConstants.getApiUrl()}/super-admin/anio-activo`);
    }

    public getAñosUtiles(): Observable<AñoLectivo[]> {
        return this.httpClient.get<AñoLectivo[]>(`${this.restConstants.getApiUrl()}/super-admin/anios-utiles`);
    }

    public getAñosLectivos(): Observable<AñoLectivo[]> {
        return this.httpClient.get<AñoLectivo[]>(`${this.restConstants.getApiUrl()}/super-admin/anios-lectivos`);
    }

    public agregarAñoLectivo(nuevoAño: AñoLectivo): Observable<AñoLectivo[]> {
        return this.httpClient.post<AñoLectivo[]>(`${this.restConstants.getApiUrl()}/super-admin`, nuevoAño);
    }

    public modificarAño(modificado: AñoLectivo, año: number): Observable<AñoLectivo> {
        return this.httpClient.put<AñoLectivo>(`${this.restConstants}/super-admin/modificar/${año}`, modificado);
    }

    public activarAño(año: number): Observable<AñoLectivo[]> {
        return this.httpClient.put<AñoLectivo[]>(`${this.restConstants.getApiUrl()}/super-admin/activar/${año}`, null);
    }
}