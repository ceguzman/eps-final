import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAfiliado, Afiliado } from 'app/shared/model/afiliado.model';
import { AfiliadoService } from './afiliado.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

@Component({
  selector: 'jhi-afiliado-update',
  templateUrl: './afiliado-update.component.html',
})
export class AfiliadoUpdateComponent implements OnInit {
  isSaving = false;
  clientes: ICliente[] = [];

  editForm = this.fb.group({
    id: [],
    parentesco: [null, [Validators.required, Validators.maxLength(100)]],
    correoElectronico: [null, [Validators.required, Validators.maxLength(180)]],
    nombreAfiliado: [null, [Validators.required, Validators.maxLength(250)]],
    cliente: [null, Validators.required],
  });

  constructor(
    protected afiliadoService: AfiliadoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afiliado }) => {
      this.updateForm(afiliado);

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(afiliado: IAfiliado): void {
    this.editForm.patchValue({
      id: afiliado.id,
      parentesco: afiliado.parentesco,
      correoElectronico: afiliado.correoElectronico,
      nombreAfiliado: afiliado.nombreAfiliado,
      cliente: afiliado.cliente,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const afiliado = this.createFromForm();
    if (afiliado.id !== undefined) {
      this.subscribeToSaveResponse(this.afiliadoService.update(afiliado));
    } else {
      this.subscribeToSaveResponse(this.afiliadoService.create(afiliado));
    }
  }

  private createFromForm(): IAfiliado {
    return {
      ...new Afiliado(),
      id: this.editForm.get(['id'])!.value,
      parentesco: this.editForm.get(['parentesco'])!.value,
      correoElectronico: this.editForm.get(['correoElectronico'])!.value,
      nombreAfiliado: this.editForm.get(['nombreAfiliado'])!.value,
      cliente: this.editForm.get(['cliente'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAfiliado>>): void {
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
