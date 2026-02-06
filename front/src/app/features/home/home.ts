import { Component, computed, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LayoutService } from '../../core/layout/layout-service';
import { ActionButton } from "../../shared/components/action-button/action-button";

@Component({
  selector: 'app-home',
  imports: [ActionButton, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {
  private layoutService = inject(LayoutService);
  private isHandsetPortrait = this.layoutService.isHandsetPortrait;
  protected buttonGroupClasses = computed(() => {
    return {
      'button-group--handset-portrait': this.isHandsetPortrait()
    };
  });
}