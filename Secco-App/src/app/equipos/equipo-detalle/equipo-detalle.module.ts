import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EquipoDetallePageRoutingModule } from './equipo-detalle-routing.module';

import { EquipoDetallePage } from './equipo-detalle.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EquipoDetallePageRoutingModule
  ],
  declarations: [EquipoDetallePage]
})
export class EquipoDetallePageModule {}
