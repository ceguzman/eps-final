import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from './diagnostico.service';

@Component({
  templateUrl: './diagnostico-delete-dialog.component.html',
})
export class DiagnosticoDeleteDialogComponent {
  diagnostico?: IDiagnostico;

  constructor(
    protected diagnosticoService: DiagnosticoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.diagnosticoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('diagnosticoListModification');
      this.activeModal.close();
    });
  }
}
