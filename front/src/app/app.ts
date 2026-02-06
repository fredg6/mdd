import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './core/auth/auth-service';
import { SessionService } from './core/session/session-service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  private router = inject(Router);
  private authService = inject(AuthService);
  private sessionService = inject(SessionService);
  
  ngOnInit(): void {
    this.autoLogin();
  }

  autoLogin(): void {
    const accessToken = localStorage.getItem('access-token');
    if (accessToken) {
      this.authService.me().subscribe((user) => {
        this.sessionService.login(user);
        this.router.navigateByUrl(this.router.url);
      });
    }
  }
}