import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PruuebaTestModule } from '../../../test.module';
import { TipoDocumentoComponent } from 'app/entities/tipo-documento/tipo-documento.component';
import { TipoDocumentoService } from 'app/entities/tipo-documento/tipo-documento.service';
import { TipoDocumento } from 'app/shared/model/tipo-documento.model';

describe('Component Tests', () => {
  describe('TipoDocumento Management Component', () => {
    let comp: TipoDocumentoComponent;
    let fixture: ComponentFixture<TipoDocumentoComponent>;
    let service: TipoDocumentoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruuebaTestModule],
        declarations: [TipoDocumentoComponent],
      })
        .overrideTemplate(TipoDocumentoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoDocumentoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoDocumentoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoDocumento(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoDocumentos && comp.tipoDocumentos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
