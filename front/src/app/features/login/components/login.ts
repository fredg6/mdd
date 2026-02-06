import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/auth/auth-service';
import { LoginRequest } from '../../../core/auth/interfaces/login-request';
import { LoginSuccess } from '../../../core/auth/interfaces/login-success';
import { Header } from "../../../core/layout/header/header";
import { SessionService } from '../../../core/session/session-service';
import { ActionButton } from "../../../shared/components/action-button/action-button";
import { User } from '../../../shared/interfaces/user';

@Component({
  selector: 'app-login',
  imports: [Header, ActionButton, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);
  private sessionService = inject(SessionService);
  protected onError = signal(false);
  protected form = this.formBuilder.group({
    emailOrUsername: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });
  
  protected goBack(): void {
    window.history.back();
  }

  submit(): void {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: LoginSuccess) => {
        localStorage.setItem('access-token', response.jwt);
        localStorage.setItem('refresh-token', response.refreshToken);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.login(user);
          this.router.navigate(['feed']);
        });
      },
      error: (err) => console.log(err)
    });
  }
}