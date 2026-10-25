import { Component } from '@angular/core'

@Component({
    imports: [],
    selector: 'app-contador',
    templateUrl: './contador.component.html'
})

export class Contador {
    protected count : number = 0;

    protected incrementar(): void {
        this.count ++;
    }

    protected probar() {
        
    }

}