import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';

type EntityResponseType = HttpResponse<ISeguimientoCondicion>;
type EntityArrayResponseType = HttpResponse<ISeguimientoCondicion[]>;

@Injectable({ providedIn: 'root' })
export class SeguimientoCondicionService {
  public resourceUrl = SERVER_API_URL + 'api/seguimiento-condicions';

  constructor(protected http: HttpClient) {}

  create(seguimientoCondicion: ISeguimientoCondicion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seguimientoCondicion);
    return this.http
      .post<ISeguimientoCondicion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(seguimientoCondicion: ISeguimientoCondicion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(seguimientoCondicion);
    return this.http
      .put<ISeguimientoCondicion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISeguimientoCondicion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISeguimientoCondicion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(seguimientoCondicion: ISeguimientoCondicion): ISeguimientoCondicion {
    const copy: ISeguimientoCondicion = Object.assign({}, seguimientoCondicion, {
      fecha:
        seguimientoCondicion.fecha && seguimientoCondicion.fecha.isValid() ? seguimientoCondicion.fecha.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha ? moment(res.body.fecha) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((seguimientoCondicion: ISeguimientoCondicion) => {
        seguimientoCondicion.fecha = seguimientoCondicion.fecha ? moment(seguimientoCondicion.fecha) : undefined;
      });
    }
    return res;
  }
}
