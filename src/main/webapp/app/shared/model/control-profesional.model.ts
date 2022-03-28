import { Moment } from 'moment';
import { IIndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

export interface IControlProfesional {
  id?: number;
  tipoMedico?: string;
  fecha?: Moment;
  nombreMedico?: string;
  observaciones?: string;
  indicadorSalud?: IIndicadoresSalud;
}

export class ControlProfesional implements IControlProfesional {
  constructor(
    public id?: number,
    public tipoMedico?: string,
    public fecha?: Moment,
    public nombreMedico?: string,
    public observaciones?: string,
    public indicadorSalud?: IIndicadoresSalud
  ) {}
}
