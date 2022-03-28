import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IControlProfesional } from 'app/shared/model/control-profesional.model';
import { ControlProfesionalService } from './control-profesional.service';
import { ControlProfesionalDeleteDialogComponent } from './control-profesional-delete-dialog.component';

@Component({
  selector: 'jhi-control-profesional',
  templateUrl: './control-profesional.component.html',
})
export class ControlProfesionalComponent implements OnInit, OnDestroy {
  controlProfesionals?: IControlProfesional[];
  eventSubscriber?: Subscription;

  constructor(
    protected controlProfesionalService: ControlProfesionalService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.controlProfesionalService
      .query()
      .subscribe((res: HttpResponse<IControlProfesional[]>) => (this.controlProfesionals = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInControlProfesionals();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IControlProfesional): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInControlProfesionals(): void {
    this.eventSubscriber = this.eventManager.subscribe('controlProfesionalListModification', () => this.loadAll());
  }

  delete(controlProfesional: IControlProfesional): void {
    const modalRef = this.modalService.open(ControlProfesionalDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.controlProfesional = controlProfesional;
  }
}
