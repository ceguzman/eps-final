import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { IndicadoresSaludDetailComponent } from 'app/entities/indicadores-salud/indicadores-salud-detail.component';
import { IndicadoresSalud } from 'app/shared/model/indicadores-salud.model';

describe('Component Tests', () => {
  describe('IndicadoresSalud Management Detail Component', () => {
    let comp: IndicadoresSaludDetailComponent;
    let fixture: ComponentFixture<IndicadoresSaludDetailComponent>;
    const route = ({ data: of({ indicadoresSalud: new IndicadoresSalud(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [IndicadoresSaludDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IndicadoresSaludDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IndicadoresSaludDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load indicadoresSalud on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.indicadoresSalud).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
