import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EquipoDetallePage } from './equipo-detalle.page';

const routes: Routes = [
  {
    path: '',
    component: EquipoDetallePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class EquipoDetallePageRoutingModule {}
