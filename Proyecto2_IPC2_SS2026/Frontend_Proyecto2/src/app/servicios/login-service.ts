import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Usuario } from "../components/modelos/usuario";

@Injectable({
    providedIn: 'root'
})

export class LoginService {
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public login(usuario: Usuario): Observable<Usuario> {
        return this.httpClient.post<Usuario>(`${this.restConstants.getApiUrl()}/login/ingresar`, usuario);
    }

    public colocarContraseña(usuario: Usuario): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/login/colocar-contrasenia`, usuario);
    }

    public recuperarContraseña(usuario: Usuario): Observable<void> {
        return this.httpClient.post<void>(`${this.restConstants.getApiUrl()}/login/recuperar-contrasenia`, usuario);
    }

    public modificarContraseña(usuario: Usuario, idUsuario: string): Observable<string> {
        return this.httpClient.put<string>(`${this.restConstants.getApiUrl()}/login/modificar-contrasenia/${idUsuario}`, usuario);
    }

}