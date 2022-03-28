import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { SeguimientoCondicionUpdateComponent } from 'app/entities/seguimiento-condicion/seguimiento-condicion-update.component';
import { SeguimientoCondicionService } from 'app/entities/seguimiento-condicion/seguimiento-condicion.service';
import { SeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';

describe('Component Tests', () => {
  describe('SeguimientoCondicion Management Update Component', () => {
    let comp: SeguimientoCondicionUpdateComponent;
    let fixture: ComponentFixture<SeguimientoCondicionUpdateComponent>;
    let service: SeguimientoCondicionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [SeguimientoCondicionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SeguimientoCondicionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SeguimientoCondicionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SeguimientoCondicionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SeguimientoCondicion(123);
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
        const entity = new SeguimientoCondicion();
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
