import { Routes } from '@angular/router';
import { authGuard } from './core/auth/guards/auth-guard';
import { unauthGuard } from './core/auth/guards/unauth-guard';

export const routes: Routes = [
    {
        path: '',
        canActivate: [unauthGuard],
        loadComponent: () => import('./features/home/home').then(m => m.Home)
    },
    {
        path: 'login',
        canActivate: [unauthGuard],
        loadComponent: () => import('./features/login/components/login').then(m => m.Login)
    },
    {
        path: 'feed',
        canActivate: [authGuard],
        loadComponent: () => import('./features/feed/feed').then(m => m.Feed)
    }
];
