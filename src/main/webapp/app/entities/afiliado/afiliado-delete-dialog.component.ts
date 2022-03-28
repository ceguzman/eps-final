import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAfiliado } from 'app/shared/model/afiliado.model';
import { AfiliadoService } from './afiliado.service';

@Component({
  templateUrl: './afiliado-delete-dialog.component.html',
})
export class AfiliadoDeleteDialogComponent {
  afiliado?: IAfiliado;

  constructor(protected afiliadoService: AfiliadoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.afiliadoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('afiliadoListModification');
      this.activeModal.close();
    });
  }
}
