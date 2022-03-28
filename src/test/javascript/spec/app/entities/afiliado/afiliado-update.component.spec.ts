import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { AfiliadoUpdateComponent } from 'app/entities/afiliado/afiliado-update.component';
import { AfiliadoService } from 'app/entities/afiliado/afiliado.service';
import { Afiliado } from 'app/shared/model/afiliado.model';

describe('Component Tests', () => {
  describe('Afiliado Management Update Component', () => {
    let comp: AfiliadoUpdateComponent;
    let fixture: ComponentFixture<AfiliadoUpdateComponent>;
    let service: AfiliadoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [AfiliadoUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AfiliadoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AfiliadoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AfiliadoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Afiliado(123);
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
        const entity = new Afiliado();
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
