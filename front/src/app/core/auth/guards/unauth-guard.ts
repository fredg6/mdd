import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { SessionService } from '../../session/session-service';

export const unauthGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const sessionService = inject(SessionService);
  
  if (sessionService.isConnected()) {
    return router.createUrlTree(['feed']);
  }
  
  return true;
};
