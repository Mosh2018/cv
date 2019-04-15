import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../../services/authentication/authentication.service';

@Component({
  selector: 'app-my-info',
  templateUrl: './my-info.component.html',
  styleUrls: ['./my-info.component.css']
})
export class MyInfoComponent implements OnInit {

  constructor(private auth: AuthenticationService) { }

  ngOnInit() {
    this.auth.callBackendForInfo();
  }

}
