import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoDocumento, TipoDocumento } from 'app/shared/model/tipo-documento.model';
import { TipoDocumentoService } from './tipo-documento.service';

@Component({
  selector: 'jhi-tipo-documento-update',
  templateUrl: './tipo-documento-update.component.html',
})
export class TipoDocumentoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    iniciales: [null, [Validators.required, Validators.maxLength(20)]],
    nombreDocumento: [null, [Validators.required, Validators.maxLength(20)]],
    estadoTipoDocuemnto: [null, [Validators.required]],
  });

  constructor(protected tipoDocumentoService: TipoDocumentoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoDocumento }) => {
      this.updateForm(tipoDocumento);
    });
  }

  updateForm(tipoDocumento: ITipoDocumento): void {
    this.editForm.patchValue({
      id: tipoDocumento.id,
      iniciales: tipoDocumento.iniciales,
      nombreDocumento: tipoDocumento.nombreDocumento,
      estadoTipoDocuemnto: tipoDocumento.estadoTipoDocuemnto,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoDocumento = this.createFromForm();
    if (tipoDocumento.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoDocumentoService.update(tipoDocumento));
    } else {
      this.subscribeToSaveResponse(this.tipoDocumentoService.create(tipoDocumento));
    }
  }

  private createFromForm(): ITipoDocumento {
    return {
      ...new TipoDocumento(),
      id: this.editForm.get(['id'])!.value,
      iniciales: this.editForm.get(['iniciales'])!.value,
      nombreDocumento: this.editForm.get(['nombreDocumento'])!.value,
      estadoTipoDocuemnto: this.editForm.get(['estadoTipoDocuemnto'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDocumento>>): void {
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
}
