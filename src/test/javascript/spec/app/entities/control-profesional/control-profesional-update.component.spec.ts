import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { ControlProfesionalUpdateComponent } from 'app/entities/control-profesional/control-profesional-update.component';
import { ControlProfesionalService } from 'app/entities/control-profesional/control-profesional.service';
import { ControlProfesional } from 'app/shared/model/control-profesional.model';

describe('Component Tests', () => {
  describe('ControlProfesional Management Update Component', () => {
    let comp: ControlProfesionalUpdateComponent;
    let fixture: ComponentFixture<ControlProfesionalUpdateComponent>;
    let service: ControlProfesionalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [ControlProfesionalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ControlProfesionalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ControlProfesionalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ControlProfesionalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ControlProfesional(123);
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
        const entity = new ControlProfesional();
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
