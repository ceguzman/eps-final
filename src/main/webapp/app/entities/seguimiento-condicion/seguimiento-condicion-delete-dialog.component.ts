import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';
import { SeguimientoCondicionService } from './seguimiento-condicion.service';

@Component({
  templateUrl: './seguimiento-condicion-delete-dialog.component.html',
})
export class SeguimientoCondicionDeleteDialogComponent {
  seguimientoCondicion?: ISeguimientoCondicion;

  constructor(
    protected seguimientoCondicionService: SeguimientoCondicionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.seguimientoCondicionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('seguimientoCondicionListModification');
      this.activeModal.close();
    });
  }
}
