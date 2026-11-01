import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "./auth-service";

@Injectable({
    providedIn: 'root'
})

export class RoleGuardService implements CanActivate {
    constructor(private router: Router, private authService: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
        if (!this.userRoleInAllowedRoles(route.data['allowedRoles'])) {
            this.router.navigate(['/']);
            return false;
        }
        
        return true;
    }

    userRoleInAllowedRoles(allowedRoles: string[]): boolean {
        const rolesInToken = this.authService.getRoles();
        const hasAnyCommon = rolesInToken.some(element => allowedRoles.includes(element));

        return hasAnyCommon;
    }
}