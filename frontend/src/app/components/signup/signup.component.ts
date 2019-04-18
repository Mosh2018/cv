import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {AuthenticationService} from '../../services/authentication/authentication.service';
import {SignUpData, Validation} from '../../models/user';
import {ApiResponse} from '../../models/api.response';
import {Router} from '@angular/router';
import {forbiddenNameValidator} from '../../validators/nameValidator';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signUpForm = this.fb.group({
          firstName:  ['', [Validators.required, forbiddenNameValidator]],
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
    console.log(this.signUpForm.get('firstName').errors);
  }

  validator(x: string): boolean {
    return this.control(x).touched && this.control(x).invalid;
  }

  errors(x) {
    return this.control(x).errors;
  }
  control(x: string) {
    return this.signUpForm.controls[x];
  }
  submit() {
    this.auth.callBackendForSignUp(this.signUpForm.value).then(
      (response: SignUpData) => {
        console.log(response);
        if (response && response.status === ApiResponse.OK && response.validationResult.length === 0) {
          this.router.navigate(['/login']);
        } else if (response && response.status === ApiResponse.OK && response.validationResult.length > 0) {
          response.validationResult.forEach( (x: Validation) => console.log(x.message));
        }
        if (response === undefined || !response || response.status !== ApiResponse.OK) {
          // do something
          this.router.navigate(['/']);
        }
      }
   );
  }

  reset() {
    this.signUpForm.reset();
  }
}
