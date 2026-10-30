import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { CurriculoRequest } from "../components/modelos/admin/curriculo-request";
import { GrupoSeccionDB } from "../components/modelos/admin/grupo-seccion-db";
import { GrupoSeccionRequest } from "../components/modelos/admin/grupo-seccion-request";
import { CurriculoDB } from "../components/modelos/admin/curriculo-db";
import { Grado } from "../components/modelos/admin/grado";

@Injectable({
    providedIn: 'root'
})

export class GrupoService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getGradosNormales(): Observable<Grado[]> {
        return this.httpClient.get<Grado[]>(`${this.restConstants.getApiUrl()}/grupo-academico/grados`);
    }

    public getGruposDelAño(pagina: number): Observable<GrupoSeccionDB[]> {
        return this.httpClient.get<GrupoSeccionDB[]>(`${this.restConstants.getApiUrl()}/grupo-academico/grupos-anio-actual/${pagina}`)
    }

    public getGruposPorAño(anio: number, pagina: number): Observable<GrupoSeccionDB[]> {
        return this.httpClient.get<GrupoSeccionDB[]>(`${this.restConstants.getApiUrl()}/grupo-academico/grupos-anio/${anio}/${pagina}`);
    }

    public getTodosLosGrupos(pagina: number): Observable<GrupoSeccionDB[]> {
        return this.httpClient.get<GrupoSeccionDB[]>(`${this.restConstants.getApiUrl()}/grupo-academico/todos-los-grupos/${pagina}`)
    }

    public crearGrupoGrado(grupo: GrupoSeccionRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/grupo-academico/crear-grupo-grado`, grupo);
    }

    public crearGrupoCarrera(grupo: GrupoSeccionRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/grupo-academico/crear-grupo-carrera`, grupo);
    }




    public getCursosCurriculoGrado(idGrado: number): Observable<CurriculoDB[]> {
        return this.httpClient.get<CurriculoDB[]>(`${this.restConstants.getApiUrl()}/grupo-academico/cursos-curriculo-grado/${idGrado}`);
    }

    public getCursosCurriculoCarrera(idCarrera: string, idGrado: number): Observable<CurriculoDB[]> {
        return this.httpClient.get<CurriculoDB[]>(`${this.restConstants.getApiUrl()}/grupo-academico/cursos-curriculo-carrera/${idCarrera}/${idGrado}`);
    }

    public agregarCurriculoGrado(curriculo: CurriculoRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/grupo-academico/agregar-curriculo-grado`, curriculo);
    }

    public agregarCurriculoCarrera(curriculo: CurriculoRequest): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/grupo-academico/agregar-curriculo-carrera`, curriculo);
    }

    public eliminarCurriculo(id: number): Observable<void> {
        return this.httpClient.delete<void>(`${this.restConstants.getApiUrl()}/grupo-academico/${id}`);
    }
}