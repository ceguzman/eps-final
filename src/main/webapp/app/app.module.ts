import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PruuebaSharedModule } from 'app/shared/shared.module';
import { PruuebaCoreModule } from 'app/core/core.module';
import { PruuebaAppRoutingModule } from './app-routing.module';
import { PruuebaHomeModule } from './home/home.module';
import { PruuebaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PruuebaSharedModule,
    PruuebaCoreModule,
    PruuebaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PruuebaEntityModule,
    PruuebaAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class PruuebaAppModule {}
