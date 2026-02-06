import { HttpClient, HttpRequest } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../shared/interfaces/user';
import { LoginRequest } from './interfaces/login-request';
import { LoginSuccess } from './interfaces/login-success';
import { LogoutSuccess } from './interfaces/logout-success';
import { RefreshSuccess } from './interfaces/refresh-success';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private httpClient = inject(HttpClient);
  private servicePath = 'http://localhost:9000/api/auth';

  login(loginRequest: LoginRequest): Observable<LoginSuccess> {
    return this.httpClient.post<LoginSuccess>(`${this.servicePath}/login`, loginRequest);
  }

  me(): Observable<User> {
    return this.httpClient.get<User>(`${this.servicePath}/me`);
  }

  // Méthode pour rafraîchir l'access token. Utilisée par l'intercepteur HTTP
  refreshToken(refreshToken: string): Observable<RefreshSuccess> {
    return this.httpClient.post<RefreshSuccess>(`${this.servicePath}/refresh`, { refreshToken: refreshToken });
  }

  logout(refreshToken: string): Observable<LogoutSuccess> {
    return this.httpClient.post<LogoutSuccess>(`${this.servicePath}/logout`, { refreshToken: refreshToken });
  }

  cloneRequestSettingAuthorizationHeader(request: HttpRequest<unknown>, accessToken: string): HttpRequest<unknown> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${accessToken}`
      }
    });
  }
}