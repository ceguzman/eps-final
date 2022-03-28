import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { ControlProfesionalDetailComponent } from 'app/entities/control-profesional/control-profesional-detail.component';
import { ControlProfesional } from 'app/shared/model/control-profesional.model';

describe('Component Tests', () => {
  describe('ControlProfesional Management Detail Component', () => {
    let comp: ControlProfesionalDetailComponent;
    let fixture: ComponentFixture<ControlProfesionalDetailComponent>;
    const route = ({ data: of({ controlProfesional: new ControlProfesional(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [ControlProfesionalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ControlProfesionalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ControlProfesionalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load controlProfesional on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.controlProfesional).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
