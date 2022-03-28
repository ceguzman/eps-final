import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAfiliado } from 'app/shared/model/afiliado.model';

type EntityResponseType = HttpResponse<IAfiliado>;
type EntityArrayResponseType = HttpResponse<IAfiliado[]>;

@Injectable({ providedIn: 'root' })
export class AfiliadoService {
  public resourceUrl = SERVER_API_URL + 'api/afiliados';

  constructor(protected http: HttpClient) {}

  create(afiliado: IAfiliado): Observable<EntityResponseType> {
    return this.http.post<IAfiliado>(this.resourceUrl, afiliado, { observe: 'response' });
  }

  update(afiliado: IAfiliado): Observable<EntityResponseType> {
    return this.http.put<IAfiliado>(this.resourceUrl, afiliado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAfiliado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAfiliado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
