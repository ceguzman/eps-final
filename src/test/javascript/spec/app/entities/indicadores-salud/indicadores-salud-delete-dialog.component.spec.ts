import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruuebaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { IndicadoresSaludDeleteDialogComponent } from 'app/entities/indicadores-salud/indicadores-salud-delete-dialog.component';
import { IndicadoresSaludService } from 'app/entities/indicadores-salud/indicadores-salud.service';

describe('Component Tests', () => {
  describe('IndicadoresSalud Management Delete Component', () => {
    let comp: IndicadoresSaludDeleteDialogComponent;
    let fixture: ComponentFixture<IndicadoresSaludDeleteDialogComponent>;
    let service: IndicadoresSaludService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [IndicadoresSaludDeleteDialogComponent],
      })
        .overrideTemplate(IndicadoresSaludDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicadoresSaludDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IndicadoresSaludService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
