import { Injectable } from "@angular/core";
import { RestConstats } from "../shared/rest-api/rest-constants";
import { HttpClient } from "@angular/common/http";
import { LoginRequest } from "../components/modelos/login/login-request";
import { Observable } from "rxjs";
import { LoginResponse } from "../components/modelos/login/login-response";

@Injectable({
    providedIn: 'root'
})

export class AuthService {

    private tokenKey: string = 'token';
    
    restConstants = new RestConstats();

    constructor(private httpClient: HttpClient) {}

    public login(usuario: LoginRequest): Observable<LoginResponse> {
        return this.httpClient.post<LoginResponse>(`${this.restConstants.getApiUrl()}/auth/ingresar`, usuario);
    }

    saveToken(token: string): void {
        localStorage.setItem(this.tokenKey, token);
    }

    getToken(): string {
        return localStorage.getItem(this.tokenKey) as string;
    }

    logout(): void {
        localStorage.removeItem(this.tokenKey);
    }

    isLoggedIn(): boolean {
        const token = this.getToken();
        if (!token) return false;
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.exp * 1000 > Date.now();
    }

    hasRole(role: string): boolean {
        const token = this.getToken();
        if (!token) return false;
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.roles?.includes(role);
    }

    getRoles(): string[] {
        if (!this.isLoggedIn()) {
            return [];
        }
        const token = this.getToken();
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.roles;
    }

    getUserId(): string | undefined {
        if (!this.isLoggedIn()) {
            return undefined;
        }
        const token = this.getToken();
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.sub;
    }
}