import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PruuebaSharedModule } from 'app/shared/shared.module';
import { AfiliadoComponent } from './afiliado.component';
import { AfiliadoDetailComponent } from './afiliado-detail.component';
import { AfiliadoUpdateComponent } from './afiliado-update.component';
import { AfiliadoDeleteDialogComponent } from './afiliado-delete-dialog.component';
import { afiliadoRoute } from './afiliado.route';

@NgModule({
  imports: [PruuebaSharedModule, RouterModule.forChild(afiliadoRoute)],
  declarations: [AfiliadoComponent, AfiliadoDetailComponent, AfiliadoUpdateComponent, AfiliadoDeleteDialogComponent],
  entryComponents: [AfiliadoDeleteDialogComponent],
})
export class PruuebaAfiliadoModule {}
