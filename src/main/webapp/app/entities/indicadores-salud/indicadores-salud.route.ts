import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIndicadoresSalud, IndicadoresSalud } from 'app/shared/model/indicadores-salud.model';
import { IndicadoresSaludService } from './indicadores-salud.service';
import { IndicadoresSaludComponent } from './indicadores-salud.component';
import { IndicadoresSaludDetailComponent } from './indicadores-salud-detail.component';
import { IndicadoresSaludUpdateComponent } from './indicadores-salud-update.component';

@Injectable({ providedIn: 'root' })
export class IndicadoresSaludResolve implements Resolve<IIndicadoresSalud> {
  constructor(private service: IndicadoresSaludService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIndicadoresSalud> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((indicadoresSalud: HttpResponse<IndicadoresSalud>) => {
          if (indicadoresSalud.body) {
            return of(indicadoresSalud.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new IndicadoresSalud());
  }
}

export const indicadoresSaludRoute: Routes = [
  {
    path: '',
    component: IndicadoresSaludComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicadoresSaluds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IndicadoresSaludDetailComponent,
    resolve: {
      indicadoresSalud: IndicadoresSaludResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicadoresSaluds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IndicadoresSaludUpdateComponent,
    resolve: {
      indicadoresSalud: IndicadoresSaludResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicadoresSaluds',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IndicadoresSaludUpdateComponent,
    resolve: {
      indicadoresSalud: IndicadoresSaludResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'IndicadoresSaluds',
    },
    canActivate: [UserRouteAccessService],
  },
];
