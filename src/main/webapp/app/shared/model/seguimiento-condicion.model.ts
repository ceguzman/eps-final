import { Moment } from 'moment';
import { IDiagnostico } from 'app/shared/model/diagnostico.model';

export interface ISeguimientoCondicion {
  id?: number;
  estadoCondicion?: string;
  fecha?: Moment;
  diagnostico?: string;
  evolucionTratamiento?: string;
  diagnosticos?: IDiagnostico;
}

export class SeguimientoCondicion implements ISeguimientoCondicion {
  constructor(
    public id?: number,
    public estadoCondicion?: string,
    public fecha?: Moment,
    public diagnostico?: string,
    public evolucionTratamiento?: string,
    public diagnosticos?: IDiagnostico
  ) {}
}
