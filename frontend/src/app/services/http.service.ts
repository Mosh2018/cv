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

  private getAPI(controller: string) {
    const API = 'http://localhost:8080/api/';
    switch (controller) {
      case 'login':
        return API + ApiController.LOGIN;
      default:
        return '';
    }
  }

}
