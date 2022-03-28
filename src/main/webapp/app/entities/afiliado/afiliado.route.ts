import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAfiliado, Afiliado } from 'app/shared/model/afiliado.model';
import { AfiliadoService } from './afiliado.service';
import { AfiliadoComponent } from './afiliado.component';
import { AfiliadoDetailComponent } from './afiliado-detail.component';
import { AfiliadoUpdateComponent } from './afiliado-update.component';

@Injectable({ providedIn: 'root' })
export class AfiliadoResolve implements Resolve<IAfiliado> {
  constructor(private service: AfiliadoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAfiliado> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((afiliado: HttpResponse<Afiliado>) => {
          if (afiliado.body) {
            return of(afiliado.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Afiliado());
  }
}

export const afiliadoRoute: Routes = [
  {
    path: '',
    component: AfiliadoComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Afiliados',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AfiliadoDetailComponent,
    resolve: {
      afiliado: AfiliadoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Afiliados',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AfiliadoUpdateComponent,
    resolve: {
      afiliado: AfiliadoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Afiliados',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AfiliadoUpdateComponent,
    resolve: {
      afiliado: AfiliadoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Afiliados',
    },
    canActivate: [UserRouteAccessService],
  },
];
