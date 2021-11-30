import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { PlantaDetallePageRoutingModule } from './planta-detalle-routing.module';

import { PlantaDetallePage } from './planta-detalle.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    PlantaDetallePageRoutingModule
  ],
  declarations: [PlantaDetallePage]
})
export class PlantaDetallePageModule {}
