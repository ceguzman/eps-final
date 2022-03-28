import { ICliente } from 'app/shared/model/cliente.model';

export interface IAfiliado {
  id?: number;
  parentesco?: string;
  correoElectronico?: string;
  nombreAfiliado?: string;
  cliente?: ICliente;
}

export class Afiliado implements IAfiliado {
  constructor(
    public id?: number,
    public parentesco?: string,
    public correoElectronico?: string,
    public nombreAfiliado?: string,
    public cliente?: ICliente
  ) {}
}
