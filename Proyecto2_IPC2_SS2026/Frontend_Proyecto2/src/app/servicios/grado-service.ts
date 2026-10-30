import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Grado } from "../components/modelos/admin/grado";

@Injectable({
    providedIn: 'root'
})

export class GradoService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getGrados(): Observable<Grado[]> {
        return this.httpClient.get<Grado[]>(`${this.restConstants.getApiUrl()}/grado/todos`);
    }

    public modificarEstado(id: number): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/grado/${id}`, null);
    }
}