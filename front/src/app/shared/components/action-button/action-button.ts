import { Component, input } from '@angular/core';

@Component({
  selector: 'app-action-button',
  imports: [],
  templateUrl: './action-button.html',
  styleUrl: './action-button.scss',
})
export class ActionButton {
  label = input.required<string>();
}
