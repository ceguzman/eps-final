import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IControlProfesional, ControlProfesional } from 'app/shared/model/control-profesional.model';
import { ControlProfesionalService } from './control-profesional.service';
import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';
import { IndicadoresSaludService } from 'app/entities/indicadores-salud/indicadores-salud.service';

@Component({
  selector: 'jhi-control-profesional-update',
  templateUrl: './control-profesional-update.component.html',
})
export class ControlProfesionalUpdateComponent implements OnInit {
  isSaving = false;
  indicadoressaluds: IIndicadoresSalud[] = [];
  fechaDp: any;

  editForm = this.fb.group({
    id: [],
    tipoMedico: [null, [Validators.required, Validators.maxLength(200)]],
    fecha: [null, [Validators.required]],
    nombreMedico: [null, [Validators.required, Validators.maxLength(250)]],
    observaciones: [null, [Validators.required, Validators.maxLength(200)]],
    indicadorSalud: [null, Validators.required],
  });

  constructor(
    protected controlProfesionalService: ControlProfesionalService,
    protected indicadoresSaludService: IndicadoresSaludService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controlProfesional }) => {
      this.updateForm(controlProfesional);

      this.indicadoresSaludService.query().subscribe((res: HttpResponse<IIndicadoresSalud[]>) => (this.indicadoressaluds = res.body || []));
    });
  }

  updateForm(controlProfesional: IControlProfesional): void {
    this.editForm.patchValue({
      id: controlProfesional.id,
      tipoMedico: controlProfesional.tipoMedico,
      fecha: controlProfesional.fecha,
      nombreMedico: controlProfesional.nombreMedico,
      observaciones: controlProfesional.observaciones,
      indicadorSalud: controlProfesional.indicadorSalud,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const controlProfesional = this.createFromForm();
    if (controlProfesional.id !== undefined) {
      this.subscribeToSaveResponse(this.controlProfesionalService.update(controlProfesional));
    } else {
      this.subscribeToSaveResponse(this.controlProfesionalService.create(controlProfesional));
    }
  }

  private createFromForm(): IControlProfesional {
    return {
      ...new ControlProfesional(),
      id: this.editForm.get(['id'])!.value,
      tipoMedico: this.editForm.get(['tipoMedico'])!.value,
      fecha: this.editForm.get(['fecha'])!.value,
      nombreMedico: this.editForm.get(['nombreMedico'])!.value,
      observaciones: this.editForm.get(['observaciones'])!.value,
      indicadorSalud: this.editForm.get(['indicadorSalud'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IControlProfesional>>): void {
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

  trackById(index: number, item: IIndicadoresSalud): any {
    return item.id;
  }
}
