import { BreakpointObserver, Breakpoints, BreakpointState } from '@angular/cdk/layout';
import { Component, computed, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ActionButton } from "../../shared/components/action-button/action-button";

@Component({
  selector: 'app-home',
  imports: [ActionButton],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit {
  private breakpointObserver = inject(BreakpointObserver);
  private destroyRef = inject(DestroyRef);
  private isHandsetPortrait = signal(false);
  protected buttonGroupClasses = computed(() => {
    return {
      'button-group--handset-portrait': this.isHandsetPortrait()
    };
  });

  ngOnInit(): void {
    this.observeLayoutChanges();
  }

  private observeLayoutChanges() {
    const layoutChanges$ = this.breakpointObserver.observe([
      Breakpoints.Handset,
      Breakpoints.Tablet,
      Breakpoints.Web
    ]);
    
    layoutChanges$.pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe(result => this.activateResponsiveLayout(result));
  }

  private activateResponsiveLayout(layoutChange: BreakpointState) {
    const layoutChangeBreakpoints = layoutChange.breakpoints;
    this.isHandsetPortrait.set(layoutChangeBreakpoints[Breakpoints.HandsetPortrait]);
  }
}