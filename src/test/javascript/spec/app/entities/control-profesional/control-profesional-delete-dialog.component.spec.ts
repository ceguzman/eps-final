import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruuebaTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ControlProfesionalDeleteDialogComponent } from 'app/entities/control-profesional/control-profesional-delete-dialog.component';
import { ControlProfesionalService } from 'app/entities/control-profesional/control-profesional.service';

describe('Component Tests', () => {
  describe('ControlProfesional Management Delete Component', () => {
    let comp: ControlProfesionalDeleteDialogComponent;
    let fixture: ComponentFixture<ControlProfesionalDeleteDialogComponent>;
    let service: ControlProfesionalService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [ControlProfesionalDeleteDialogComponent],
      })
        .overrideTemplate(ControlProfesionalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ControlProfesionalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ControlProfesionalService);
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
