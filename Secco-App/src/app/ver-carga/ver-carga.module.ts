import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { VerCargaPageRoutingModule } from './ver-carga-routing.module';

import { VerCargaPage } from './ver-carga.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    VerCargaPageRoutingModule
  ],
  declarations: [VerCargaPage]
})
export class VerCargaPageModule {}
