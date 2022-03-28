import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { ClienteDeleteDialogComponent } from './cliente-delete-dialog.component';

@Component({
  selector: 'jhi-cliente',
  templateUrl: './cliente.component.html',
})
export class ClienteComponent implements OnInit, OnDestroy {
  clientes?: ICliente[];
  eventSubscriber?: Subscription;

  constructor(
    protected clienteService: ClienteService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInClientes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICliente): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInClientes(): void {
    this.eventSubscriber = this.eventManager.subscribe('clienteListModification', () => this.loadAll());
  }

  delete(cliente: ICliente): void {
    const modalRef = this.modalService.open(ClienteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cliente = cliente;
  }
}
