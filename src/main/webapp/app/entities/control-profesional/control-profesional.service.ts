import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IControlProfesional } from 'app/shared/model/control-profesional.model';

type EntityResponseType = HttpResponse<IControlProfesional>;
type EntityArrayResponseType = HttpResponse<IControlProfesional[]>;

@Injectable({ providedIn: 'root' })
export class ControlProfesionalService {
  public resourceUrl = SERVER_API_URL + 'api/control-profesionals';

  constructor(protected http: HttpClient) {}

  create(controlProfesional: IControlProfesional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controlProfesional);
    return this.http
      .post<IControlProfesional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(controlProfesional: IControlProfesional): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(controlProfesional);
    return this.http
      .put<IControlProfesional>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IControlProfesional>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IControlProfesional[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(controlProfesional: IControlProfesional): IControlProfesional {
    const copy: IControlProfesional = Object.assign({}, controlProfesional, {
      fecha: controlProfesional.fecha && controlProfesional.fecha.isValid() ? controlProfesional.fecha.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((controlProfesional: IControlProfesional) => {
        controlProfesional.fecha = controlProfesional.fecha ? moment(controlProfesional.fecha) : undefined;
      });
    }
    return res;
  }
}
