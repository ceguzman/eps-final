import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDiagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from './diagnostico.service';
import { DiagnosticoDeleteDialogComponent } from './diagnostico-delete-dialog.component';

@Component({
  selector: 'jhi-diagnostico',
  templateUrl: './diagnostico.component.html',
})
export class DiagnosticoComponent implements OnInit, OnDestroy {
  diagnosticos?: IDiagnostico[];
  eventSubscriber?: Subscription;

  constructor(
    protected diagnosticoService: DiagnosticoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.diagnosticoService.query().subscribe((res: HttpResponse<IDiagnostico[]>) => (this.diagnosticos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDiagnosticos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDiagnostico): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDiagnosticos(): void {
    this.eventSubscriber = this.eventManager.subscribe('diagnosticoListModification', () => this.loadAll());
  }

  delete(diagnostico: IDiagnostico): void {
    const modalRef = this.modalService.open(DiagnosticoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.diagnostico = diagnostico;
  }
}
