import { Component, Input } from '@angular/core';
import { required } from '@angular/forms/signals';

@Component({
  imports: [],
  selector: 'app-header',
  styles: ``,
  templateUrl: './header.html',
})
export class Header {
  @Input({required: true})
  rol!: string;
}
