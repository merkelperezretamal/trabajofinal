import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CargaDiariaPage } from './carga-diaria.page';

const routes: Routes = [
  {
    path: '',
    component: CargaDiariaPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CargaDiariaPageRoutingModule {}
