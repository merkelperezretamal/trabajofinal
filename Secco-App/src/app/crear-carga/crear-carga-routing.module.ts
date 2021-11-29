import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CrearCargaPage } from './crear-carga.page';

const routes: Routes = [
  {
    path: '',
    component: CrearCargaPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CrearCargaPageRoutingModule {}
