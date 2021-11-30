import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenuPage } from './menu.page';

const routes: Routes = [
  {
    path: '',
    component: MenuPage,
    children: [
      {
        path: 'home',
        loadChildren: () =>
          import('../home/home.module').then((m) => m.HomePageModule),
      },
      {
        path: 'plantas',
        loadChildren: () => import('../plantas/plantas.module').then(m => m.PlantasPageModule)
      },
      {
        path: 'equipos',
        loadChildren: () => import('../equipos/equipos.module').then(m => m.EquiposPageModule)
      },
      {
        path: 'carga-diaria',
        loadChildren: () => import('../carga-diaria/carga-diaria.module').then(m => m.CargaDiariaPageModule)
      },
      {
        path: 'planta-detalle',
        loadChildren: () =>
          import('../planta-detalle/planta-detalle.module').then(
            (m) => m.PlantaDetallePageModule
          ),
      },
      {
        path: 'equipo-detalle',
        loadChildren: () =>
          import('../equipo-detalle/equipo-detalle.module').then(
            (m) => m.EquipoDetallePageModule
          ),
      },
      {
        path: 'ver',
        loadChildren: () =>
          import('../ver-carga/ver-carga.module').then(
            (m) => m.VerCargaPageModule
          ),
      },
      {
        path: 'crear',
        loadChildren: () =>
          import('../crear-carga/crear-carga.module').then(
            (m) => m.CrearCargaPageModule
          ),
      },
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MenuPageRoutingModule { }
