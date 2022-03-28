import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { SeguimientoCondicionDetailComponent } from 'app/entities/seguimiento-condicion/seguimiento-condicion-detail.component';
import { SeguimientoCondicion } from 'app/shared/model/seguimiento-condicion.model';

describe('Component Tests', () => {
  describe('SeguimientoCondicion Management Detail Component', () => {
    let comp: SeguimientoCondicionDetailComponent;
    let fixture: ComponentFixture<SeguimientoCondicionDetailComponent>;
    const route = ({ data: of({ seguimientoCondicion: new SeguimientoCondicion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [SeguimientoCondicionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SeguimientoCondicionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SeguimientoCondicionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load seguimientoCondicion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.seguimientoCondicion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
