import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { Promedio } from "../components/modelos/admin/reporte/promedio";
import { Solvencia } from "../components/modelos/admin/reporte/solvencia";
import { HistorialPagoEstudiante } from "../components/modelos/admin/reporte/historial-pago-estudiante";
import { Ingreso } from "../components/modelos/admin/reporte/ingreso";

@Injectable({
    providedIn: 'root'
})

export class ReporteSercvice {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getReportePromedio(idGrupo: number): Observable<Promedio[]> {
        return this.httpClient.get<Promedio[]>(`${this.restConstants.getApiUrl()}/reporte/promedio/${idGrupo}`);
    }

    public getReporteSolventes(idGrupo: number, pagina: number): Observable<Solvencia[]> {
        return this.httpClient.get<Solvencia[]>(`${this.restConstants.getApiUrl()}/reporte/solvencia/${idGrupo}/${pagina}`);
    }

    public getReportePagosSinFecha(idEstudiane: string, pagina: number): Observable<HistorialPagoEstudiante> {
        return this.httpClient.get<HistorialPagoEstudiante>(`${this.restConstants.getApiUrl()}/reporte/pagos-estudiante/${idEstudiane}/${pagina}`);
    }

    public getReportePagosFechas(idEstudiane: string, fechaInicio: Date, fechaFin: Date, pagina: number): Observable<HistorialPagoEstudiante> {
        return this.httpClient.get<HistorialPagoEstudiante>(`${this.restConstants.getApiUrl()}/reporte/pagos-estudiante-fechas/${idEstudiane}/${fechaInicio}/${fechaFin}/${pagina}`);
    }

    public getIngresosHistoricos(): Observable<Ingreso[]> {
        return this.httpClient.get<Ingreso[]>(`${this.restConstants.getApiUrl()}/reporte/ingresos`);
    }

    public getIngresosPorFecha(fechaInicio: Date, fechaFin: Date): Observable<Ingreso[]> {
        return this.httpClient.get<Ingreso[]>(`${this.restConstants.getApiUrl()}/reporte/ingresos-fechas/${fechaInicio}/${fechaFin}`);
    }



    public exportarPromedios(idGrupo: number): string {
        return this.restConstants.getApiUrl() + `/exportar-reporte/promedios/${idGrupo}`;
    }

    public exportarSolvencias(idGrupo: number, pagina: number): string {
        return this.restConstants.getApiUrl() + `/exportar-reporte/solvencias/${idGrupo}/${pagina}`;
    }

    public exportarPagosSinFecha(idEstudiante: string, pagina: number): string {
        return this.restConstants.getApiUrl() + `/exportar-reporte/pagos-estudiante/${idEstudiante}/${pagina}`;
    }

    public exportarPagosEntreFechas(idEstudiante: string, fechaInicio: Date, fechaFin: Date, pagina: number): string {
        return this.restConstants.getApiUrl() + `/exportar-reporte/pagos-estudiante-fechas/${idEstudiante}/${fechaInicio}/${fechaFin}/${pagina}`;
    }

    public exportarIngresos(): string {
        return this.restConstants.getApiUrl() + `/exportar-reporte/ingresos`;
    }

    public exportarIngresosFechas(fechaInicio: Date, fechaFin: Date): string {
        return this.restConstants.getApiUrl() + `/exportar-reporte/ingresos-fechas/${fechaInicio}/${fechaFin}`;
    }
}