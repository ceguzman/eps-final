import { IControlProfesional } from 'app/shared/model/control-profesional.model';
import { IDiagnostico } from 'app/shared/model/diagnostico.model';

export interface IIndicadoresSalud {
  id?: number;
  frecuenciaCardiaca?: string;
  tencionArterial?: string;
  saturacionOxigeno?: string;
  vacunas?: string;
  distanciaRecorrida?: string;
  controlesProfesionales?: IControlProfesional[];
  diagnostico?: IDiagnostico;
}

export class IndicadoresSalud implements IIndicadoresSalud {
  constructor(
    public id?: number,
    public frecuenciaCardiaca?: string,
    public tencionArterial?: string,
    public saturacionOxigeno?: string,
    public vacunas?: string,
    public distanciaRecorrida?: string,
    public controlesProfesionales?: IControlProfesional[],
    public diagnostico?: IDiagnostico
  ) {}
}
