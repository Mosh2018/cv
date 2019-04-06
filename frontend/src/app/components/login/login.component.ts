import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {ApiResponse} from '../../models/api.response';
import {User} from '../../models/user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginFrom = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  isNotValid = false;

  constructor(private fb: FormBuilder,
              private auth: AuthenticationService,
              private router: Router) { }
  ngOnInit() {
  }

  loginSubmit() {
    this.auth.callBackendForLogin(this.loginFrom.value).then(
      (user: User) => {
        if (user.status === ApiResponse.IT_IS_OK) {
          this.isNotValid = false;
          this.router.navigate(['/my-info']);
        } else {
          this.isNotValid = true;
        }
      }
    ).catch(err => {
      this.auth.logout();
      this.isNotValid = true; });
  }
}
