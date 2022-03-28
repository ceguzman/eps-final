import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PruuebaTestModule } from '../../../test.module';
import { AfiliadoComponent } from 'app/entities/afiliado/afiliado.component';
import { AfiliadoService } from 'app/entities/afiliado/afiliado.service';
import { Afiliado } from 'app/shared/model/afiliado.model';

describe('Component Tests', () => {
  describe('Afiliado Management Component', () => {
    let comp: AfiliadoComponent;
    let fixture: ComponentFixture<AfiliadoComponent>;
    let service: AfiliadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [AfiliadoComponent],
      })
        .overrideTemplate(AfiliadoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AfiliadoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AfiliadoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Afiliado(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.afiliados && comp.afiliados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
