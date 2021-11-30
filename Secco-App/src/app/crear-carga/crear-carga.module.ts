import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CrearCargaPageRoutingModule } from './crear-carga-routing.module';

import { CrearCargaPage } from './crear-carga.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    CrearCargaPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [CrearCargaPage]
})
export class CrearCargaPageModule {}
