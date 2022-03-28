import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DiagnosticoService } from 'app/entities/diagnostico/diagnostico.service';
import { IDiagnostico, Diagnostico } from 'app/shared/model/diagnostico.model';

describe('Service Tests', () => {
  describe('Diagnostico Service', () => {
    let injector: TestBed;
    let service: DiagnosticoService;
    let httpMock: HttpTestingController;
    let elemDefault: IDiagnostico;
    let expectedResult: IDiagnostico | IDiagnostico[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DiagnosticoService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Diagnostico(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Diagnostico', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Diagnostico()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Diagnostico', () => {
        const returnedFromService = Object.assign(
          {
            resultadoLaboratorio: 'BBBBBB',
            urlImagen: 'BBBBBB',
            tipoCondicionSalud: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Diagnostico', () => {
        const returnedFromService = Object.assign(
          {
            resultadoLaboratorio: 'BBBBBB',
            urlImagen: 'BBBBBB',
            tipoCondicionSalud: 'BBBBBB',
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

      it('should delete a Diagnostico', () => {
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
