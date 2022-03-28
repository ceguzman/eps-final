import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruuebaSharedModule } from 'app/shared/shared.module';
import { IndicadoresSaludComponent } from './indicadores-salud.component';
import { IndicadoresSaludDetailComponent } from './indicadores-salud-detail.component';
import { IndicadoresSaludUpdateComponent } from './indicadores-salud-update.component';
import { IndicadoresSaludDeleteDialogComponent } from './indicadores-salud-delete-dialog.component';
import { indicadoresSaludRoute } from './indicadores-salud.route';

@NgModule({
  imports: [PruuebaSharedModule, RouterModule.forChild(indicadoresSaludRoute)],
  declarations: [
    IndicadoresSaludComponent,
    IndicadoresSaludDetailComponent,
    IndicadoresSaludUpdateComponent,
    IndicadoresSaludDeleteDialogComponent,
  ],
  entryComponents: [IndicadoresSaludDeleteDialogComponent],
})
export class PruuebaIndicadoresSaludModule {}
