import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

type EntityResponseType = HttpResponse<IIndicadoresSalud>;
type EntityArrayResponseType = HttpResponse<IIndicadoresSalud[]>;

@Injectable({ providedIn: 'root' })
export class IndicadoresSaludService {
  public resourceUrl = SERVER_API_URL + 'api/indicadores-saluds';

  constructor(protected http: HttpClient) {}

  create(indicadoresSalud: IIndicadoresSalud): Observable<EntityResponseType> {
    return this.http.post<IIndicadoresSalud>(this.resourceUrl, indicadoresSalud, { observe: 'response' });
  }

  update(indicadoresSalud: IIndicadoresSalud): Observable<EntityResponseType> {
    return this.http.put<IIndicadoresSalud>(this.resourceUrl, indicadoresSalud, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIndicadoresSalud>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIndicadoresSalud[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
