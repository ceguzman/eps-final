import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDiagnostico, Diagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from './diagnostico.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-diagnostico-update',
  templateUrl: './diagnostico-update.component.html',
})
export class DiagnosticoUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    resultadoLaboratorio: [null, [Validators.required, Validators.maxLength(100)]],
    urlImagen: [null, [Validators.required, Validators.maxLength(500)]],
    tipoCondicionSalud: [null, [Validators.required, Validators.maxLength(240)]],
    cliente: [null, Validators.required],
  });

  constructor(
    protected diagnosticoService: DiagnosticoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ diagnostico }) => {
      this.updateForm(diagnostico);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(diagnostico: IDiagnostico): void {
    this.editForm.patchValue({
      id: diagnostico.id,
      resultadoLaboratorio: diagnostico.resultadoLaboratorio,
      urlImagen: diagnostico.urlImagen,
      tipoCondicionSalud: diagnostico.tipoCondicionSalud,
      cliente: diagnostico.cliente,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const diagnostico = this.createFromForm();
    if (diagnostico.id !== undefined) {
      this.subscribeToSaveResponse(this.diagnosticoService.update(diagnostico));
    } else {
      this.subscribeToSaveResponse(this.diagnosticoService.create(diagnostico));
    }
  }

  private createFromForm(): IDiagnostico {
    return {
      ...new Diagnostico(),
      id: this.editForm.get(['id'])!.value,
      resultadoLaboratorio: this.editForm.get(['resultadoLaboratorio'])!.value,
      urlImagen: this.editForm.get(['urlImagen'])!.value,
      tipoCondicionSalud: this.editForm.get(['tipoCondicionSalud'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiagnostico>>): void {
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

  trackById(index: number, item: ICliente): any {
    return item.id;
  }
}
