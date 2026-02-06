import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, EMPTY, switchMap, throwError } from 'rxjs';
import { SessionService } from '../../session/session-service';
import { AuthService } from '../auth-service';


export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const sessionService = inject(SessionService);
  
  const accessToken = localStorage.getItem('access-token');
  if (accessToken) {
    req = authService.cloneRequestSettingAuthorizationHeader(req, accessToken);
  }
  
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        const refreshToken = localStorage.getItem('refresh-token');
        
        if (refreshToken) {
          return authService.refreshToken(refreshToken).pipe(
            switchMap((refreshSuccess) => {
              localStorage.setItem('access-token', refreshSuccess.jwt);
              req = authService.cloneRequestSettingAuthorizationHeader(req, refreshSuccess.jwt);
              return next(req);
            }),
            catchError((refreshError) => {
              console.info(refreshError.message);
              // Si le rafraîchissement échoue, déconnecter l'utilisateur
              return authService.logout(refreshToken).pipe(
                switchMap(logoutSuccess => {
                  sessionService.logout(logoutSuccess);
                  return EMPTY;
                }),
                catchError(logoutError => {
                  return throwError(() => logoutError);
                })
              );
            })
          );
        }
      }
      return throwError(() => error);
    })
  );
};