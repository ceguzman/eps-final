import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';
import { IndicadoresSaludService } from './indicadores-salud.service';

@Component({
  templateUrl: './indicadores-salud-delete-dialog.component.html',
})
export class IndicadoresSaludDeleteDialogComponent {
  indicadoresSalud?: IIndicadoresSalud;

  constructor(
    protected indicadoresSaludService: IndicadoresSaludService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.indicadoresSaludService.delete(id).subscribe(() => {
      this.eventManager.broadcast('indicadoresSaludListModification');
      this.activeModal.close();
    });
  }
}
