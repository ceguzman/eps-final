import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PruuebaTestModule } from '../../../test.module';
import { IndicadoresSaludComponent } from 'app/entities/indicadores-salud/indicadores-salud.component';
import { IndicadoresSaludService } from 'app/entities/indicadores-salud/indicadores-salud.service';
import { IndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

describe('Component Tests', () => {
  describe('IndicadoresSalud Management Component', () => {
    let comp: IndicadoresSaludComponent;
    let fixture: ComponentFixture<IndicadoresSaludComponent>;
    let service: IndicadoresSaludService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [IndicadoresSaludComponent],
      })
        .overrideTemplate(IndicadoresSaludComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicadoresSaludComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicadoresSaludService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IndicadoresSalud(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.indicadoresSaluds && comp.indicadoresSaluds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
