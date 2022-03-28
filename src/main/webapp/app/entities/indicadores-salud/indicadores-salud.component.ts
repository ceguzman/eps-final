import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';
import { IndicadoresSaludService } from './indicadores-salud.service';
import { IndicadoresSaludDeleteDialogComponent } from './indicadores-salud-delete-dialog.component';

@Component({
  selector: 'jhi-indicadores-salud',
  templateUrl: './indicadores-salud.component.html',
})
export class IndicadoresSaludComponent implements OnInit, OnDestroy {
  indicadoresSaluds?: IIndicadoresSalud[];
  eventSubscriber?: Subscription;

  constructor(
    protected indicadoresSaludService: IndicadoresSaludService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.indicadoresSaludService.query().subscribe((res: HttpResponse<IIndicadoresSalud[]>) => (this.indicadoresSaluds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIndicadoresSaluds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIndicadoresSalud): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIndicadoresSaluds(): void {
    this.eventSubscriber = this.eventManager.subscribe('indicadoresSaludListModification', () => this.loadAll());
  }

  delete(indicadoresSalud: IIndicadoresSalud): void {
    const modalRef = this.modalService.open(IndicadoresSaludDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.indicadoresSalud = indicadoresSalud;
  }
}
