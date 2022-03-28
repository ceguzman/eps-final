import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tipo-documento',
        loadChildren: () => import('./tipo-documento/tipo-documento.module').then(m => m.PruuebaTipoDocumentoModule),
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.PruuebaClienteModule),
      },
      {
        path: 'afiliado',
        loadChildren: () => import('./afiliado/afiliado.module').then(m => m.PruuebaAfiliadoModule),
      },
      {
        path: 'diagnostico',
        loadChildren: () => import('./diagnostico/diagnostico.module').then(m => m.PruuebaDiagnosticoModule),
      },
      {
        path: 'seguimiento-condicion',
        loadChildren: () => import('./seguimiento-condicion/seguimiento-condicion.module').then(m => m.PruuebaSeguimientoCondicionModule),
      },
      {
        path: 'indicadores-salud',
        loadChildren: () => import('./indicadores-salud/indicadores-salud.module').then(m => m.PruuebaIndicadoresSaludModule),
      },
      {
        path: 'control-profesional',
        loadChildren: () => import('./control-profesional/control-profesional.module').then(m => m.PruuebaControlProfesionalModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PruuebaEntityModule {}
