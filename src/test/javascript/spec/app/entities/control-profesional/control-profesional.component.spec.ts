import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PruuebaTestModule } from '../../../test.module';
import { ControlProfesionalComponent } from 'app/entities/control-profesional/control-profesional.component';
import { ControlProfesionalService } from 'app/entities/control-profesional/control-profesional.service';
import { ControlProfesional } from 'app/shared/model/control-profesional.model';

describe('Component Tests', () => {
  describe('ControlProfesional Management Component', () => {
    let comp: ControlProfesionalComponent;
    let fixture: ComponentFixture<ControlProfesionalComponent>;
    let service: ControlProfesionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [ControlProfesionalComponent],
      })
        .overrideTemplate(ControlProfesionalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ControlProfesionalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ControlProfesionalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ControlProfesional(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.controlProfesionals && comp.controlProfesionals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
