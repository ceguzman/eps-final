import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { DiagnosticoDetailComponent } from 'app/entities/diagnostico/diagnostico-detail.component';
import { Diagnostico } from 'app/shared/model/diagnostico.model';

describe('Component Tests', () => {
  describe('Diagnostico Management Detail Component', () => {
    let comp: DiagnosticoDetailComponent;
    let fixture: ComponentFixture<DiagnosticoDetailComponent>;
    const route = ({ data: of({ diagnostico: new Diagnostico(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [DiagnosticoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DiagnosticoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiagnosticoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load diagnostico on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.diagnostico).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
