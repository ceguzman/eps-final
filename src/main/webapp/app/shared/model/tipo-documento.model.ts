import { ICliente } from 'app/shared/model/cliente.model';
import { State } from 'app/shared/model/enumerations/state.model';

export interface ITipoDocumento {
  id?: number;
  iniciales?: string;
  nombreDocumento?: string;
  estadoTipoDocuemnto?: State;
  clientes?: ICliente[];
}

export class TipoDocumento implements ITipoDocumento {
  constructor(
    public id?: number,
    public iniciales?: string,
    public nombreDocumento?: string,
    public estadoTipoDocuemnto?: State,
    public clientes?: ICliente[]
  ) {}
}
