import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { RolUsuario } from "../components/modelos/rol-usuario-enum";
import { Empleado } from "../components/modelos/empleado";

@Injectable({
    providedIn: 'root'
})

export class EmpleadoService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public getEmpleadoPorDpi(dpi: string): Observable<Empleado> {
        return this.httpClient.get<Empleado>(`${this.restConstants.getApiUrl()}/empleado/${dpi}`);
    }

    public getEmpleadosPorRol(rol: RolUsuario, pagina: number): Observable<Empleado[]> {
        return this.httpClient.get<Empleado[]>(`${this.restConstants.getApiUrl()}/empleado/empleados-rol/${rol}/${pagina}`);
    }

    public crearYContratar(empleado: Empleado): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/empleado/crear`, empleado);
    }

    public contratar(empleado: Empleado): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/empleado/contratar`, empleado);
    }
}