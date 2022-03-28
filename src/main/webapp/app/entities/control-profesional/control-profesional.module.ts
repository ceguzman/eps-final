import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruuebaSharedModule } from 'app/shared/shared.module';
import { ControlProfesionalComponent } from './control-profesional.component';
import { ControlProfesionalDetailComponent } from './control-profesional-detail.component';
import { ControlProfesionalUpdateComponent } from './control-profesional-update.component';
import { ControlProfesionalDeleteDialogComponent } from './control-profesional-delete-dialog.component';
import { controlProfesionalRoute } from './control-profesional.route';

@NgModule({
  imports: [PruuebaSharedModule, RouterModule.forChild(controlProfesionalRoute)],
  declarations: [
    ControlProfesionalComponent,
    ControlProfesionalDetailComponent,
    ControlProfesionalUpdateComponent,
    ControlProfesionalDeleteDialogComponent,
  ],
  entryComponents: [ControlProfesionalDeleteDialogComponent],
})
export class PruuebaControlProfesionalModule {}
