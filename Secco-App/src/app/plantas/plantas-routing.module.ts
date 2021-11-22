import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PlantasPage } from './plantas.page';

const routes: Routes = [
  {
    path: '',
    component: PlantasPage
  },
  {
    path: 'planta-detalle',
    loadChildren: () => import('./planta-detalle/planta-detalle.module').then( m => m.PlantaDetallePageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PlantasPageRoutingModule {}
