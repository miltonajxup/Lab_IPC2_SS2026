import { Injectable, signal } from "@angular/core";
import { Usuario } from "../components/modelos/usuario";

@Injectable({
    providedIn: 'root'
})

export class SesionService {
    private usuario = signal<Usuario | null>(null);

    public getUsuario(): Usuario {
        return this.usuario()!;
    }

    public iniciarSesion(usuario: Usuario): void {
        this.usuario.set(usuario);
    }

    public cerrarSesion(): void {
        this.usuario.set(null);
    }
}