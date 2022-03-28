import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IControlProfesional } from 'app/shared/model/control-profesional.model';
import { ControlProfesionalService } from './control-profesional.service';

@Component({
  templateUrl: './control-profesional-delete-dialog.component.html',
})
export class ControlProfesionalDeleteDialogComponent {
  controlProfesional?: IControlProfesional;

  constructor(
    protected controlProfesionalService: ControlProfesionalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.controlProfesionalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('controlProfesionalListModification');
      this.activeModal.close();
    });
  }
}
