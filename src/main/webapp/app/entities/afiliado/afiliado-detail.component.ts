import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAfiliado } from 'app/shared/model/afiliado.model';

@Component({
  selector: 'jhi-afiliado-detail',
  templateUrl: './afiliado-detail.component.html',
})
export class AfiliadoDetailComponent implements OnInit {
  afiliado: IAfiliado | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ afiliado }) => (this.afiliado = afiliado));
  }

  previousState(): void {
    window.history.back();
  }
}
