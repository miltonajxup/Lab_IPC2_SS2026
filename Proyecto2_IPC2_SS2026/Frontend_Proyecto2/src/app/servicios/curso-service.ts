import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Curso } from "../components/modelos/admin/curso";
import { CursoRequest } from "../components/modelos/admin/curso-request";

@Injectable({
    providedIn: 'root'
})

export class CursoService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getCursos(pagina: number): Observable<Curso[]> {
        return this.httpClient.get<Curso[]>(`${this.restConstants.getApiUrl()}/curso/${pagina}`);
    }

    public getCursoPorId(id: number): Observable<Curso> {
        return this.httpClient.get<Curso>(`${this.restConstants.getApiUrl()}/curso/unico/${id}`);
    }

    public agregarCurso(curso: Curso): Observable<Curso[]> {
        return this.httpClient.post<Curso[]>(`${this.restConstants.getApiUrl()}/curso`, curso);
    }

    public modificarCurso(curso: CursoRequest, id: number): Observable<Curso> {
        return this.httpClient.put<Curso>(`${this.restConstants.getApiUrl()}/curso/${id}`, curso);
    }
}