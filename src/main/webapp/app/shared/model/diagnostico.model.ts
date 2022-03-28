import { ISeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';
import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IDiagnostico {
  id?: number;
  resultadoLaboratorio?: string;
  urlImagen?: string;
  tipoCondicionSalud?: string;
  seguimientoCondiciones?: ISeguimientoCondicion[];
  indicadoresSaluds?: IIndicadoresSalud[];
  cliente?: ICliente;
}

export class Diagnostico implements IDiagnostico {
  constructor(
    public id?: number,
    public resultadoLaboratorio?: string,
    public urlImagen?: string,
    public tipoCondicionSalud?: string,
    public seguimientoCondiciones?: ISeguimientoCondicion[],
    public indicadoresSaluds?: IIndicadoresSalud[],
    public cliente?: ICliente
  ) {}
}
