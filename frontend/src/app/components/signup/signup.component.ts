import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {SignUpData} from '../../models/user';
import {ApiResponse} from '../../models/api.response';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signUpForm = this.fb.group({
          firstName:  ['', Validators.required],
          lastName:  ['', Validators.required],
          username:  ['', Validators.required],
          email:  ['', Validators.required],
          password:  ['', Validators.required],
          passwordCon:  ['', Validators.required],
  }
  );

  constructor( private fb: FormBuilder,
               private auth: AuthenticationService,
               private router: Router) {}

  ngOnInit() {
  }

  submit() {
    this.auth.callBackendForSignUp(this.signUpForm.value).then(
      (response: SignUpData) => {
        console.log(response);
        if (response.status === ApiResponse.IT_IS_OK) {
          this.router.navigate(['/login']);
        }
        if (response.status === ApiResponse.IT_IS_NOT_UNIQUE) {
          // do something
          this.router.navigate(['/']);
        }
      }
   ).catch();
  }
}
