import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIndicadoresSalud, IndicadoresSalud } from 'app/shared/model/indicadores-salud.model';
import { IndicadoresSaludService } from './indicadores-salud.service';
import { IDiagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from 'app/entities/diagnostico/diagnostico.service';

@Component({
  selector: 'jhi-indicadores-salud-update',
  templateUrl: './indicadores-salud-update.component.html',
})
export class IndicadoresSaludUpdateComponent implements OnInit {
  isSaving = false;
  diagnosticos: IDiagnostico[] = [];

  editForm = this.fb.group({
    id: [],
    frecuenciaCardiaca: [null, [Validators.required, Validators.maxLength(200)]],
    tencionArterial: [null, [Validators.required, Validators.maxLength(200)]],
    saturacionOxigeno: [null, [Validators.required, Validators.maxLength(255)]],
    vacunas: [null, [Validators.required, Validators.maxLength(100)]],
    distanciaRecorrida: [null, [Validators.required, Validators.maxLength(30)]],
    diagnostico: [null, Validators.required],
  });

  constructor(
    protected indicadoresSaludService: IndicadoresSaludService,
    protected diagnosticoService: DiagnosticoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicadoresSalud }) => {
      this.updateForm(indicadoresSalud);

      this.diagnosticoService.query().subscribe((res: HttpResponse<IDiagnostico[]>) => (this.diagnosticos = res.body || []));
    });
  }

  updateForm(indicadoresSalud: IIndicadoresSalud): void {
    this.editForm.patchValue({
      id: indicadoresSalud.id,
      frecuenciaCardiaca: indicadoresSalud.frecuenciaCardiaca,
      tencionArterial: indicadoresSalud.tencionArterial,
      saturacionOxigeno: indicadoresSalud.saturacionOxigeno,
      vacunas: indicadoresSalud.vacunas,
      distanciaRecorrida: indicadoresSalud.distanciaRecorrida,
      diagnostico: indicadoresSalud.diagnostico,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const indicadoresSalud = this.createFromForm();
    if (indicadoresSalud.id !== undefined) {
      this.subscribeToSaveResponse(this.indicadoresSaludService.update(indicadoresSalud));
    } else {
      this.subscribeToSaveResponse(this.indicadoresSaludService.create(indicadoresSalud));
    }
  }

  private createFromForm(): IIndicadoresSalud {
    return {
      ...new IndicadoresSalud(),
      id: this.editForm.get(['id'])!.value,
      frecuenciaCardiaca: this.editForm.get(['frecuenciaCardiaca'])!.value,
      tencionArterial: this.editForm.get(['tencionArterial'])!.value,
      saturacionOxigeno: this.editForm.get(['saturacionOxigeno'])!.value,
      vacunas: this.editForm.get(['vacunas'])!.value,
      distanciaRecorrida: this.editForm.get(['distanciaRecorrida'])!.value,
      diagnostico: this.editForm.get(['diagnostico'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIndicadoresSalud>>): void {
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
