import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CargaDiariaPageRoutingModule } from './carga-diaria-routing.module';

import { CargaDiariaPage } from './carga-diaria.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CargaDiariaPageRoutingModule
  ],
  declarations: [CargaDiariaPage]
})
export class CargaDiariaPageModule {}
