import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PruuebaTestModule } from '../../../test.module';
import { SeguimientoCondicionComponent } from 'app/entities/seguimiento-condicion/seguimiento-condicion.component';
import { SeguimientoCondicionService } from 'app/entities/seguimiento-condicion/seguimiento-condicion.service';
import { SeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';

describe('Component Tests', () => {
  describe('SeguimientoCondicion Management Component', () => {
    let comp: SeguimientoCondicionComponent;
    let fixture: ComponentFixture<SeguimientoCondicionComponent>;
    let service: SeguimientoCondicionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [SeguimientoCondicionComponent],
      })
        .overrideTemplate(SeguimientoCondicionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeguimientoCondicionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeguimientoCondicionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SeguimientoCondicion(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.seguimientoCondicions && comp.seguimientoCondicions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
