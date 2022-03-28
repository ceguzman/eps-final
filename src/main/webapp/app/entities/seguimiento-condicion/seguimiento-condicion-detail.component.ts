import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';

@Component({
  selector: 'jhi-seguimiento-condicion-detail',
  templateUrl: './seguimiento-condicion-detail.component.html',
})
export class SeguimientoCondicionDetailComponent implements OnInit {
  seguimientoCondicion: ISeguimientoCondicion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seguimientoCondicion }) => (this.seguimientoCondicion = seguimientoCondicion));
  }

  previousState(): void {
    window.history.back();
  }
}
