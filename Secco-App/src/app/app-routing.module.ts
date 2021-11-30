import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './guards/login.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'menu',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadChildren: () => import('./login/login.module').then( m => m.LoginPageModule)
  },
  {
    path: 'menu',
    loadChildren: () => import('./menu/menu.module').then( m => m.MenuPageModule), canActivate: [LoginGuard],
  },
  {
    path: 'plantas',
    loadChildren: () => import('./plantas/plantas.module').then( m => m.PlantasPageModule)
  },
  {
    path: 'equipos',
    loadChildren: () => import('./equipos/equipos.module').then( m => m.EquiposPageModule)
  },
  {
    path: 'carga-diaria',
    loadChildren: () => import('./carga-diaria/carga-diaria.module').then( m => m.CargaDiariaPageModule)
  },
  {
    path: 'planta-detalle',
    loadChildren: () => import('./planta-detalle/planta-detalle.module').then( m => m.PlantaDetallePageModule)
  },
  {
    path: 'equipo-detalle',
    loadChildren: () => import('./equipo-detalle/equipo-detalle.module').then( m => m.EquipoDetallePageModule)
  },
  {
    path: 'ver-carga',
    loadChildren: () => import('./ver-carga/ver-carga.module').then( m => m.VerCargaPageModule)
  },
  {
    path: 'crear-carga',
    loadChildren: () => import('./crear-carga/crear-carga.module').then( m => m.CrearCargaPageModule)
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
