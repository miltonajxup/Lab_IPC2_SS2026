import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { CursoMaestro } from "../components/modelos/maestro/curso-maestro";
import { Asistencia } from "../components/modelos/maestro/asistencia";
import { AsistenciaRequest } from "../components/modelos/maestro/asistencia-request";
import { Nota } from "../components/modelos/nota";
import { NotaRequest } from "../components/modelos/maestro/nota-request";
import { EstudianteCurso } from "../components/modelos/maestro/estudiante-curso";

@Injectable({
    providedIn: 'root'
})

export class MaestroService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getCursos(idMaestro: string): Observable<CursoMaestro[]> {
        return this.httpClient.get<CursoMaestro[]>(`${this.restConstants.getApiUrl()}/maestro/cursos/${idMaestro}`);
    }

    public getCursoPorId(idCursoMaestro: number, idMaestro: string): Observable<CursoMaestro> {
        return this.httpClient.get<CursoMaestro>(`${this.restConstants.getApiUrl()}/maestro/curso-asignado/${idCursoMaestro}/${idMaestro}`);
    }
    
    public getEstudiantesCurso(idGrupo: number, idCurso: number): Observable<EstudianteCurso[]> {
        return this.httpClient.get<EstudianteCurso[]>(`${this.restConstants.getApiUrl()}/maestro/estudiantes-curso/${idGrupo}/${idCurso}`);
    }

    public getFechasAsistencia(idGrupo: number, idCurso: number): Observable<Date[]> {
        return this.httpClient.get<Date[]>(`${this.restConstants.getApiUrl()}/maestro/fechas-aisitencia/${idGrupo}/${idCurso}`);
    }

    public getAsistenciasFecha(idGrupo: number, idCurso: number, fecha: Date): Observable<Asistencia[]> {
        return this.httpClient.get<Asistencia[]>(`${this.restConstants.getApiUrl()}/maestro/aisitencias/${idGrupo}/${idCurso}/${fecha}`);
    }

    public generarAsistencias(asistencia: AsistenciaRequest): Observable<Asistencia[]> {
        return this.httpClient.post<Asistencia[]>(`${this.restConstants.getApiUrl()}/maestro/`, asistencia);
    }

    public tomarAsistencia(idAsistencia: number): Observable<void> {
        return this.httpClient.put<void>(`${this.restConstants.getApiUrl()}/maestro/asistencia/${idAsistencia}`, null);
    }

    public getNotasCurso(idGrupo: number, idCurso: number): Observable<Nota[]> {
        return this.httpClient.get<Nota[]>(`${this.restConstants.getApiUrl()}/maestro/notas-curso/${idGrupo}/${idCurso}`);
    }

    public getNotaCurso(id: number): Observable<Nota> {
        return this.httpClient.get<Nota>(`${this.restConstants.getApiUrl()}/maestro/nota-unica/${id}`);
    }

    public modificarZona(idNota: number, nota: NotaRequest): Observable<Nota> {
        return this.httpClient.put<Nota>(`${this.restConstants.getApiUrl()}/maestro/modificar-zona/${idNota}`, nota);
    }

    public moidificarExamenFinal(idNota: number, nota: NotaRequest): Observable<Nota> {
        return this.httpClient.put<Nota>(`${this.restConstants.getApiUrl()}/maestro/modificar-examen-final/${idNota}`, nota);
    }
}