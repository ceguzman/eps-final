import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IControlProfesional, ControlProfesional } from 'app/shared/model/control-profesional.model';
import { ControlProfesionalService } from './control-profesional.service';
import { ControlProfesionalComponent } from './control-profesional.component';
import { ControlProfesionalDetailComponent } from './control-profesional-detail.component';
import { ControlProfesionalUpdateComponent } from './control-profesional-update.component';

@Injectable({ providedIn: 'root' })
export class ControlProfesionalResolve implements Resolve<IControlProfesional> {
  constructor(private service: ControlProfesionalService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IControlProfesional> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((controlProfesional: HttpResponse<ControlProfesional>) => {
          if (controlProfesional.body) {
            return of(controlProfesional.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ControlProfesional());
  }
}

export const controlProfesionalRoute: Routes = [
  {
    path: '',
    component: ControlProfesionalComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ControlProfesionals',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ControlProfesionalDetailComponent,
    resolve: {
      controlProfesional: ControlProfesionalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ControlProfesionals',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ControlProfesionalUpdateComponent,
    resolve: {
      controlProfesional: ControlProfesionalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ControlProfesionals',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ControlProfesionalUpdateComponent,
    resolve: {
      controlProfesional: ControlProfesionalResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ControlProfesionals',
    },
    canActivate: [UserRouteAccessService],
  },
];
