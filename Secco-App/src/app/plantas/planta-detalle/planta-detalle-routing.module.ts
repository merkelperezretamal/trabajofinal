import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PlantaDetallePage } from './planta-detalle.page';

const routes: Routes = [
  {
    path: '',
    component: PlantaDetallePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PlantaDetallePageRoutingModule {}
