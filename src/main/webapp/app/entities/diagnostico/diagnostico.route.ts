import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDiagnostico, Diagnostico } from 'app/shared/model/diagnostico.model';
import { DiagnosticoService } from './diagnostico.service';
import { DiagnosticoComponent } from './diagnostico.component';
import { DiagnosticoDetailComponent } from './diagnostico-detail.component';
import { DiagnosticoUpdateComponent } from './diagnostico-update.component';

@Injectable({ providedIn: 'root' })
export class DiagnosticoResolve implements Resolve<IDiagnostico> {
  constructor(private service: DiagnosticoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDiagnostico> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((diagnostico: HttpResponse<Diagnostico>) => {
          if (diagnostico.body) {
            return of(diagnostico.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Diagnostico());
  }
}

export const diagnosticoRoute: Routes = [
  {
    path: '',
    component: DiagnosticoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diagnosticos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DiagnosticoDetailComponent,
    resolve: {
      diagnostico: DiagnosticoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diagnosticos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DiagnosticoUpdateComponent,
    resolve: {
      diagnostico: DiagnosticoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diagnosticos',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DiagnosticoUpdateComponent,
    resolve: {
      diagnostico: DiagnosticoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Diagnosticos',
    },
    canActivate: [UserRouteAccessService],
  },
];
