import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SeguimientoCondicionService } from 'app/entities/seguimiento-condicion/seguimiento-condicion.service';
import { ISeguimientoCondicion, SeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';

describe('Service Tests', () => {
  describe('SeguimientoCondicion Service', () => {
    let injector: TestBed;
    let service: SeguimientoCondicionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISeguimientoCondicion;
    let expectedResult: ISeguimientoCondicion | ISeguimientoCondicion[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SeguimientoCondicionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SeguimientoCondicion(0, 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fecha: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SeguimientoCondicion', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fecha: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.create(new SeguimientoCondicion()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SeguimientoCondicion', () => {
        const returnedFromService = Object.assign(
          {
            estadoCondicion: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            diagnostico: 'BBBBBB',
            evolucionTratamiento: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SeguimientoCondicion', () => {
        const returnedFromService = Object.assign(
          {
            estadoCondicion: 'BBBBBB',
            fecha: currentDate.format(DATE_FORMAT),
            diagnostico: 'BBBBBB',
            evolucionTratamiento: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fecha: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SeguimientoCondicion', () => {
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
