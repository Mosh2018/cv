import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import { JwtHelperService } from '@auth0/angular-jwt';
import {UserBasicInfo} from '../../models/user-basic-info';
import {Role} from '../../models/api.response';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private auth: AuthenticationService, private jwt: JwtHelperService) { }

  ngOnInit() {}

  isNotLogged() {
    return this.jwt.isTokenExpired();
  }
  isLogged() {
    return !this.isNotLogged();
  }

  getFirstName() {
    return this.getUserBasicInfo().firstName;
  }

  getLastName() {
    return this.getUserBasicInfo().lastName;
  }

  isAdmin() {
    return this.getUserBasicInfo().roles.some( role => role === Role.ROLE_ADMIN);
  }

  private getUserBasicInfo(): UserBasicInfo {
    return this.jwt.decodeToken(this.auth.currentUserValue);
  }
}
