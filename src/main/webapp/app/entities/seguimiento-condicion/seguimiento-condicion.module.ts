import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruuebaSharedModule } from 'app/shared/shared.module';
import { SeguimientoCondicionComponent } from './seguimiento-condicion.component';
import { SeguimientoCondicionDetailComponent } from './seguimiento-condicion-detail.component';
import { SeguimientoCondicionUpdateComponent } from './seguimiento-condicion-update.component';
import { SeguimientoCondicionDeleteDialogComponent } from './seguimiento-condicion-delete-dialog.component';
import { seguimientoCondicionRoute } from './seguimiento-condicion.route';

@NgModule({
  imports: [PruuebaSharedModule, RouterModule.forChild(seguimientoCondicionRoute)],
  declarations: [
    SeguimientoCondicionComponent,
    SeguimientoCondicionDetailComponent,
    SeguimientoCondicionUpdateComponent,
    SeguimientoCondicionDeleteDialogComponent,
  ],
  entryComponents: [SeguimientoCondicionDeleteDialogComponent],
})
export class PruuebaSeguimientoCondicionModule {}
