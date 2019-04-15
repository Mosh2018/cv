import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {StartPageComponent} from './components/cv/start-page/start-page.component';
import {MyInfoComponent} from './components/cv/my-info/my-info.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './services/guard/auth-guard';
import {AdminPageComponent} from './components/admin-page/admin-page.component';
import {AdminGuard} from './services/guard/admin-guard';
import {SignupComponent} from './components/signup/signup.component';
import {JwtModule} from '@auth0/angular-jwt';

const routes: Routes = [
  {path: '', component: StartPageComponent},
  {path: 'sign-up', component: SignupComponent},
  {path: 'my-info', component: MyInfoComponent, canActivate: [AuthGuard]},
  {path: 'admin-page', component: AdminPageComponent, canActivate: [AuthGuard,  AdminGuard]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: ''},
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    JwtModule.forRoot({
      config: {

        tokenGetter: function tokenGetter() {
          return localStorage.getItem('currentUser');
        },
        whitelistedDomains: ['localhost:8080'],
        blacklistedRoutes: ['http://localhost:8080/api/']
      }
    })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
