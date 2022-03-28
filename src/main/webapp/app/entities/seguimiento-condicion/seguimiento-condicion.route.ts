import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISeguimientoCondicion, SeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';
import { SeguimientoCondicionService } from './seguimiento-condicion.service';
import { SeguimientoCondicionComponent } from './seguimiento-condicion.component';
import { SeguimientoCondicionDetailComponent } from './seguimiento-condicion-detail.component';
import { SeguimientoCondicionUpdateComponent } from './seguimiento-condicion-update.component';

@Injectable({ providedIn: 'root' })
export class SeguimientoCondicionResolve implements Resolve<ISeguimientoCondicion> {
  constructor(private service: SeguimientoCondicionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISeguimientoCondicion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((seguimientoCondicion: HttpResponse<SeguimientoCondicion>) => {
          if (seguimientoCondicion.body) {
            return of(seguimientoCondicion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SeguimientoCondicion());
  }
}

export const seguimientoCondicionRoute: Routes = [
  {
    path: '',
    component: SeguimientoCondicionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SeguimientoCondicions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SeguimientoCondicionDetailComponent,
    resolve: {
      seguimientoCondicion: SeguimientoCondicionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SeguimientoCondicions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SeguimientoCondicionUpdateComponent,
    resolve: {
      seguimientoCondicion: SeguimientoCondicionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SeguimientoCondicions',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SeguimientoCondicionUpdateComponent,
    resolve: {
      seguimientoCondicion: SeguimientoCondicionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SeguimientoCondicions',
    },
    canActivate: [UserRouteAccessService],
  },
];
