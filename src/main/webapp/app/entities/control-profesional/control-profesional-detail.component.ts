import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IControlProfesional } from 'app/shared/model/control-profesional.model';

@Component({
  selector: 'jhi-control-profesional-detail',
  templateUrl: './control-profesional-detail.component.html',
})
export class ControlProfesionalDetailComponent implements OnInit {
  controlProfesional: IControlProfesional | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ controlProfesional }) => (this.controlProfesional = controlProfesional));
  }

  previousState(): void {
    window.history.back();
  }
}
