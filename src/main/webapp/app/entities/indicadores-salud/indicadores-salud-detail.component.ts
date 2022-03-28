import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

@Component({
  selector: 'jhi-indicadores-salud-detail',
  templateUrl: './indicadores-salud-detail.component.html',
})
export class IndicadoresSaludDetailComponent implements OnInit {
  indicadoresSalud: IIndicadoresSalud | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ indicadoresSalud }) => (this.indicadoresSalud = indicadoresSalud));
  }

  previousState(): void {
    window.history.back();
  }
}
