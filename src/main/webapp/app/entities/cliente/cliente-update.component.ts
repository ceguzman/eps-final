import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';
import { TipoDocumentoService } from 'app/entities/tipo-documento/tipo-documento.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  tipodocumentos: ITipoDocumento[] = [];

  editForm = this.fb.group({
    id: [],
    numeroDocumento: [null, [Validators.required, Validators.maxLength(255)]],
    primerNombre: [null, [Validators.required, Validators.maxLength(50)]],
    primerApellido: [null, [Validators.required, Validators.maxLength(50)]],
    segundoNombre: [null, [Validators.maxLength(50)]],
    segundoApellido: [null, [Validators.maxLength(50)]],
    imgUrl: [],
    imgUrlContentType: [],
    typoDocumento: [null, Validators.required],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected clienteService: ClienteService,
    protected tipoDocumentoService: TipoDocumentoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);

      this.tipoDocumentoService.query().subscribe((res: HttpResponse<ITipoDocumento[]>) => (this.tipodocumentos = res.body || []));
    });
  }

  updateForm(cliente: ICliente): void {
    this.editForm.patchValue({
      id: cliente.id,
      numeroDocumento: cliente.numeroDocumento,
      primerNombre: cliente.primerNombre,
      primerApellido: cliente.primerApellido,
      segundoNombre: cliente.segundoNombre,
      segundoApellido: cliente.segundoApellido,
      imgUrl: cliente.imgUrl,
      imgUrlContentType: cliente.imgUrlContentType,
      typoDocumento: cliente.typoDocumento,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('pruuebaApp.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id'])!.value,
      numeroDocumento: this.editForm.get(['numeroDocumento'])!.value,
      primerNombre: this.editForm.get(['primerNombre'])!.value,
      primerApellido: this.editForm.get(['primerApellido'])!.value,
      segundoNombre: this.editForm.get(['segundoNombre'])!.value,
      segundoApellido: this.editForm.get(['segundoApellido'])!.value,
      imgUrlContentType: this.editForm.get(['imgUrlContentType'])!.value,
      imgUrl: this.editForm.get(['imgUrl'])!.value,
      typoDocumento: this.editForm.get(['typoDocumento'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITipoDocumento): any {
    return item.id;
  }
}
