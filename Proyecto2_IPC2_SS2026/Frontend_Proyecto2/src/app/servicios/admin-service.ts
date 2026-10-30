import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Mes } from "../components/modelos/mes-enum";
import { Observable } from "rxjs";
import { BoletaPago } from "../components/modelos/admin/boleta-pago";
import { PagoEmpleado } from "../components/modelos/admin/pago-empleado";
import { CursoMaestro } from "../components/modelos/admin/curso-maestro";
import { Respuesta } from "../components/modelos/respuesta";
import { MesBoleta } from "../components/modelos/admin/mes-boleta";

@Injectable({
    providedIn: 'root'
})

export class AdminService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getBoletasGeneradas(mes: Mes, pagina: number): Observable<BoletaPago[]> {
        return this.httpClient.get<BoletaPago[]>(`${this.restConstants.getApiUrl()}/administrador/boletas-generadas/${mes}/${pagina}`);
    }

    public getPagosGenerados(mes: Mes, pagina: number): Observable<PagoEmpleado[]> {
        return this.httpClient.get<PagoEmpleado[]>(`${this.restConstants.getApiUrl()}/administrador/pagos-generados/${mes}/${pagina}`);
    }

    public generarBoletasMes(mes: MesBoleta): Observable<Respuesta> {
        return this.httpClient.post<Respuesta>(`${this.restConstants.getApiUrl()}/administrador/generar-boletas`, mes);
    }

    public generarPagosMes(mes: Mes): Observable<Respuesta> {
        return this.httpClient.post<Respuesta>(`${this.restConstants.getApiUrl()}/administrador/generar-pagos/${mes}`, null);
    }

    public asignarMaestroCurso(cursoMaestro: CursoMaestro): Observable<Respuesta> {
        return this.httpClient.post<Respuesta>(`${this.restConstants.getApiUrl()}/administrador/asignar-curso-maestro`, cursoMaestro);
    }
}