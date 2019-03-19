import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainComponent} from './components/main/main.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {StartPageComponent} from './components/cv/start-page/start-page.component';
import {MyInfoComponent} from './components/cv/my-info/my-info.component';
import {HttpService} from './services/http.service';
import {LoginComponent} from './components/login/login.component';
import {JwtModule} from '@auth0/angular-jwt';
import { AdminPageComponent } from './components/admin-page/admin-page.component';

export function tokenGetterFun() {
  return localStorage.getItem('currentUser');
}

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    NavbarComponent,
    StartPageComponent,
    MyInfoComponent,
    LoginComponent,
    AdminPageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    JwtModule.forRoot({ config: {
        tokenGetter: tokenGetterFun } })
  ],
  providers: [
    HttpService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
