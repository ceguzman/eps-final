import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { IndicadoresSaludUpdateComponent } from 'app/entities/indicadores-salud/indicadores-salud-update.component';
import { IndicadoresSaludService } from 'app/entities/indicadores-salud/indicadores-salud.service';
import { IndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

describe('Component Tests', () => {
  describe('IndicadoresSalud Management Update Component', () => {
    let comp: IndicadoresSaludUpdateComponent;
    let fixture: ComponentFixture<IndicadoresSaludUpdateComponent>;
    let service: IndicadoresSaludService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [IndicadoresSaludUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(IndicadoresSaludUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IndicadoresSaludUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicadoresSaludService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndicadoresSalud(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new IndicadoresSalud();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
