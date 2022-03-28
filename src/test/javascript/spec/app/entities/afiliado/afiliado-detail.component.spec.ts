import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruuebaTestModule } from '../../../test.module';
import { AfiliadoDetailComponent } from 'app/entities/afiliado/afiliado-detail.component';
import { Afiliado } from 'app/shared/model/afiliado.model';

describe('Component Tests', () => {
  describe('Afiliado Management Detail Component', () => {
    let comp: AfiliadoDetailComponent;
    let fixture: ComponentFixture<AfiliadoDetailComponent>;
    const route = ({ data: of({ afiliado: new Afiliado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [AfiliadoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AfiliadoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AfiliadoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load afiliado on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.afiliado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
