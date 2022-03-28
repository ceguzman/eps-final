import { IAfiliado } from 'app/shared/model/afiliado.model';
import { IDiagnostico } from 'app/shared/model/diagnostico.model';
import { ITipoDocumento } from 'app/shared/model/tipo-documento.model';

export interface ICliente {
  id?: number;
  numeroDocumento?: string;
  primerNombre?: string;
  primerApellido?: string;
  segundoNombre?: string;
  segundoApellido?: string;
  imgUrlContentType?: string;
  imgUrl?: any;
  afiliados?: IAfiliado[];
  diagnosticos?: IDiagnostico[];
  typoDocumento?: ITipoDocumento;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public numeroDocumento?: string,
    public primerNombre?: string,
    public primerApellido?: string,
    public segundoNombre?: string,
    public segundoApellido?: string,
    public imgUrlContentType?: string,
    public imgUrl?: any,
    public afiliados?: IAfiliado[],
    public diagnosticos?: IDiagnostico[],
    public typoDocumento?: ITipoDocumento
  ) {}
}
