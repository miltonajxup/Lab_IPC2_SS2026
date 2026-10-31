import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Usuario } from "../components/modelos/usuario";

@Injectable({
    providedIn: 'root'
})

export class UsuarioService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public buscarUsuarioPorReferencia(referencia: string): Observable<Usuario> {
        return this.httpClient.get<Usuario>(`${this.restConstants.getApiUrl()}/usuario/${referencia}`);
    }
}