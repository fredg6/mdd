import { computed, Injectable, signal } from '@angular/core';
import { User } from '../../shared/interfaces/user';
import { LogoutSuccess } from '../auth/interfaces/logout-success';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  private _currentUser = signal<User | null>(null);
  currentUser = this._currentUser.asReadonly();
  isConnected = computed(() => this.currentUser() !== null);

  login(user: User): void {
    this._currentUser.set(user);
  }

  logout(logoutSuccess: LogoutSuccess): void {
    this._currentUser.set(null);
    localStorage.removeItem('access-token');
    localStorage.removeItem('refresh-token');
    console.info(logoutSuccess.message);
  }
}