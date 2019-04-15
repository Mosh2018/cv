import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpService} from '../http.service';
import {CustomError, SignUpData, User} from '../../models/user';
import {ApiResponse} from '../../models/api.response';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<string>;
  public currentUser: Observable<string>;
  constructor(httpService: HttpService, private http: HttpService, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<string>(localStorage.getItem('currentUser'));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): string {
    return this.currentUserSubject.value;
  }

  callBackendForLogin(credential): Promise<Observable<User> | User | any> {
    return this.http.save('login', credential).toPromise()
      .then((user: User) => {
        if (user && user.status === ApiResponse.IT_IS_OK) {
          localStorage.setItem('currentUser', user.jwt);
          this.currentUserSubject.next(user.jwt);
          return user;
        }
      }).catch(error => {
        this.logout();
        this.currentUserSubject.next(null);
        return error;
      });
  }

  callBackendForSignUp(signUpData): Promise<Observable<any> | any> {
    return this.http.save('sign-up', signUpData).toPromise()
      .then((signUp: SignUpData) => {
        if (signUp && signUp.status === ApiResponse.IT_IS_OK) {
            // do something
          return signUp;
        }
      }).catch( error => {
         return error;
      });
  }

  callBackendForInfo() {
    return this.http.fetch('test').toPromise()
      .then( x => console.log(x.toString())).catch( x => console.log('Error', x));
  }

  logout() {
    console.log('logged out');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    // this.router.navigate(['/']);
  }
}


