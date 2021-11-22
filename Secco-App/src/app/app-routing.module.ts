import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'equipos',
    loadChildren: () => import('./equipos/equipos.module').then( m => m.EquiposPageModule)
  },
  {
    path: 'equipo',
    loadChildren: () => import('./equipos/equipo-detalle/equipo-detalle.module').then( m => m.EquipoDetallePageModule)
  },
  {
    path: 'plantas',
    loadChildren: () => import('./plantas/plantas.module').then( m => m.PlantasPageModule)
  },
  {
    path: 'planta',
    loadChildren: () => import('./plantas/planta-detalle/planta-detalle.module').then( m => m.PlantaDetallePageModule)
  },
  {
    path: 'carga-diaria',
    loadChildren: () => import('./carga-diaria/carga-diaria.module').then( m => m.CargaDiariaPageModule)
  },
  {
    path: 'ver',
    loadChildren: () => import('./carga-diaria/ver-carga/ver-carga.module').then( m => m.VerCargaPageModule)
  },
  {
    path: 'crear',
    loadChildren: () => import('./carga-diaria/crear-carga/crear-carga.module').then( m => m.CrearCargaPageModule)
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
