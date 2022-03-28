import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISeguimientoCondicion, SeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';
import { SeguimientoCondicionService } from './seguimiento-condicion.service';
import { IDiagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from 'app/entities/diagnostico/diagnostico.service';

@Component({
  selector: 'jhi-seguimiento-condicion-update',
  templateUrl: './seguimiento-condicion-update.component.html',
})
export class SeguimientoCondicionUpdateComponent implements OnInit {
  isSaving = false;
  diagnosticos: IDiagnostico[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    estadoCondicion: [null, [Validators.required, Validators.maxLength(100)]],
    fecha: [null, [Validators.required]],
    diagnostico: [null, [Validators.required, Validators.maxLength(255)]],
    evolucionTratamiento: [null, [Validators.required, Validators.maxLength(255)]],
    diagnosticos: [null, Validators.required],
  });

  constructor(
    protected seguimientoCondicionService: SeguimientoCondicionService,
    protected diagnosticoService: DiagnosticoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seguimientoCondicion }) => {
      this.updateForm(seguimientoCondicion);

      this.diagnosticoService.query().subscribe((res: HttpResponse<IDiagnostico[]>) => (this.diagnosticos = res.body || []));
    });
  }

  updateForm(seguimientoCondicion: ISeguimientoCondicion): void {
    this.editForm.patchValue({
      id: seguimientoCondicion.id,
      estadoCondicion: seguimientoCondicion.estadoCondicion,
      fecha: seguimientoCondicion.fecha,
      diagnostico: seguimientoCondicion.diagnostico,
      evolucionTratamiento: seguimientoCondicion.evolucionTratamiento,
      diagnosticos: seguimientoCondicion.diagnosticos,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const seguimientoCondicion = this.createFromForm();
    if (seguimientoCondicion.id !== undefined) {
      this.subscribeToSaveResponse(this.seguimientoCondicionService.update(seguimientoCondicion));
    } else {
      this.subscribeToSaveResponse(this.seguimientoCondicionService.create(seguimientoCondicion));
    }
  }

  private createFromForm(): ISeguimientoCondicion {
    return {
      ...new SeguimientoCondicion(),
      id: this.editForm.get(['id'])!.value,
      estadoCondicion: this.editForm.get(['estadoCondicion'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      diagnostico: this.editForm.get(['diagnostico'])!.value,
      evolucionTratamiento: this.editForm.get(['evolucionTratamiento'])!.value,
      diagnosticos: this.editForm.get(['diagnosticos'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeguimientoCondicion>>): void {
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

  trackById(index: number, item: IDiagnostico): any {
    return item.id;
  }
}
