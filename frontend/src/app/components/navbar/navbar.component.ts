import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private auth: AuthenticationService, private jwt: JwtHelperService) { }

  ngOnInit() {}

  isNotLogged() {
    console.log('isTokenExpert', this.jwt.isTokenExpired());
    return this.jwt.isTokenExpired();
  }
  isLogged() {
    return !this.isNotLogged();
  }

}
