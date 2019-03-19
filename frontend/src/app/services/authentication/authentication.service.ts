import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpService} from '../http.service';
import {User} from '../../models/user';
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

  callBackendForLogin(credential): Promise<Observable<User> | User> {
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
        return new User(error.status, error.jwt);
      });
  }

  logout() {
    console.log('logged out');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/']);
  }
}


