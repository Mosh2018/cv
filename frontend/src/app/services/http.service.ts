import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ApiController} from '../models/api.controller';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  constructor(private http: HttpClient) { }

  save(controller: string, data: any) {
    return this.http.post(this.getAPI(controller), data);
  }

  fetch(controller: string) {
    return this.http.get(this.getAPI(controller));
  }

  private getAPI(controller: string) {
    const API = 'http://localhost:8080/api/'; // this url can get from environment
    switch (controller) {
      case 'login':
        return API + ApiController.LOGIN;
      case 'sign-up':
        return API + ApiController.SIGN_UP;
      case 'get_profile':
        return API + ApiController.PROFILE;
      case  'test':
        return API + ApiController.TEST;
      default:
        return '';
    }
  }

}
