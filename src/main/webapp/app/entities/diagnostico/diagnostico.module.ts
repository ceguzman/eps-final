import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruuebaSharedModule } from 'app/shared/shared.module';
import { DiagnosticoComponent } from './diagnostico.component';
import { DiagnosticoDetailComponent } from './diagnostico-detail.component';
import { DiagnosticoUpdateComponent } from './diagnostico-update.component';
import { DiagnosticoDeleteDialogComponent } from './diagnostico-delete-dialog.component';
import { diagnosticoRoute } from './diagnostico.route';

@NgModule({
  imports: [PruuebaSharedModule, RouterModule.forChild(diagnosticoRoute)],
  declarations: [DiagnosticoComponent, DiagnosticoDetailComponent, DiagnosticoUpdateComponent, DiagnosticoDeleteDialogComponent],
  entryComponents: [DiagnosticoDeleteDialogComponent],
})
export class PruuebaDiagnosticoModule {}
