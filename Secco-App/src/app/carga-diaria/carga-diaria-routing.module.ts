import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CargaDiariaPage } from './carga-diaria.page';

const routes: Routes = [
  {
    path: '',
    component: CargaDiariaPage
  },
  {
    path: 'crear-carga',
    loadChildren: () => import('./crear-carga/crear-carga.module').then( m => m.CrearCargaPageModule)
  },
  {
    path: 'ver-carga',
    loadChildren: () => import('./ver-carga/ver-carga.module').then( m => m.VerCargaPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CargaDiariaPageRoutingModule {}
