import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { TipoDocumentoService } from './tipo-documento.service';
import { TipoDocumentoDeleteDialogComponent } from './tipo-documento-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-documento',
  templateUrl: './tipo-documento.component.html',
})
export class TipoDocumentoComponent implements OnInit, OnDestroy {
  tipoDocumentos?: ITipoDocumento[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoDocumentoService: TipoDocumentoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoDocumentoService.query().subscribe((res: HttpResponse<ITipoDocumento[]>) => (this.tipoDocumentos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoDocumentos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoDocumento): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoDocumentos(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoDocumentoListModification', () => this.loadAll());
  }

  delete(tipoDocumento: ITipoDocumento): void {
    const modalRef = this.modalService.open(TipoDocumentoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoDocumento = tipoDocumento;
  }
}
