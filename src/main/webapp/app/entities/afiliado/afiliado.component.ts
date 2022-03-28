import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAfiliado } from 'app/shared/model/afiliado.model';
import { AfiliadoService } from './afiliado.service';
import { AfiliadoDeleteDialogComponent } from './afiliado-delete-dialog.component';

@Component({
  selector: 'jhi-afiliado',
  templateUrl: './afiliado.component.html',
})
export class AfiliadoComponent implements OnInit, OnDestroy {
  afiliados?: IAfiliado[];
  eventSubscriber?: Subscription;

  constructor(protected afiliadoService: AfiliadoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.afiliadoService.query().subscribe((res: HttpResponse<IAfiliado[]>) => (this.afiliados = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAfiliados();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAfiliado): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAfiliados(): void {
    this.eventSubscriber = this.eventManager.subscribe('afiliadoListModification', () => this.loadAll());
  }

  delete(afiliado: IAfiliado): void {
    const modalRef = this.modalService.open(AfiliadoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.afiliado = afiliado;
  }
}
