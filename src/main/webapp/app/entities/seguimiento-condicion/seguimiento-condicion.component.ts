import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';
import { SeguimientoCondicionService } from './seguimiento-condicion.service';
import { SeguimientoCondicionDeleteDialogComponent } from './seguimiento-condicion-delete-dialog.component';

@Component({
  selector: 'jhi-seguimiento-condicion',
  templateUrl: './seguimiento-condicion.component.html',
})
export class SeguimientoCondicionComponent implements OnInit, OnDestroy {
  seguimientoCondicions?: ISeguimientoCondicion[];
  eventSubscriber?: Subscription;

  constructor(
    protected seguimientoCondicionService: SeguimientoCondicionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.seguimientoCondicionService
      .query()
      .subscribe((res: HttpResponse<ISeguimientoCondicion[]>) => (this.seguimientoCondicions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSeguimientoCondicions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISeguimientoCondicion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSeguimientoCondicions(): void {
    this.eventSubscriber = this.eventManager.subscribe('seguimientoCondicionListModification', () => this.loadAll());
  }

  delete(seguimientoCondicion: ISeguimientoCondicion): void {
    const modalRef = this.modalService.open(SeguimientoCondicionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.seguimientoCondicion = seguimientoCondicion;
  }
}
