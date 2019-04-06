import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../authentication/authentication.service';
import {Role} from '../../models/api.response';
import {UserBasicInfo} from '../../models/user-basic-info';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private router: Router, private loginAuthService: AuthenticationService, private jwt: JwtHelperService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.getUserBasicInfo() != null && this.isAdmin()) {
      return true;
    }
    this.loginAuthService.logout();
    this.router.navigate(['/login']);
    return false;
  }

  isAdmin() {
    return this.getUserBasicInfo().roles.some( role => role === Role.ROLE_ADMIN);
  }
  private getUserBasicInfo(): UserBasicInfo {
    if (this.loginAuthService.currentUserValue != null) {
      return this.jwt.decodeToken(this.loginAuthService.currentUserValue);
    }
    return null;
  }
}
