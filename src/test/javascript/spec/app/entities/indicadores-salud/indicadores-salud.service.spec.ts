import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { IndicadoresSaludService } from 'app/entities/indicadores-salud/indicadores-salud.service';
import { IIndicadoresSalud, IndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

describe('Service Tests', () => {
  describe('IndicadoresSalud Service', () => {
    let injector: TestBed;
    let service: IndicadoresSaludService;
    let httpMock: HttpTestingController;
    let elemDefault: IIndicadoresSalud;
    let expectedResult: IIndicadoresSalud | IIndicadoresSalud[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(IndicadoresSaludService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new IndicadoresSalud(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a IndicadoresSalud', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new IndicadoresSalud()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a IndicadoresSalud', () => {
        const returnedFromService = Object.assign(
          {
            frecuenciaCardiaca: 'BBBBBB',
            tencionArterial: 'BBBBBB',
            saturacionOxigeno: 'BBBBBB',
            vacunas: 'BBBBBB',
            distanciaRecorrida: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of IndicadoresSalud', () => {
        const returnedFromService = Object.assign(
          {
            frecuenciaCardiaca: 'BBBBBB',
            tencionArterial: 'BBBBBB',
            saturacionOxigeno: 'BBBBBB',
            vacunas: 'BBBBBB',
            distanciaRecorrida: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IndicadoresSalud', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
