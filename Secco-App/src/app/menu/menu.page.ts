import { Component, OnInit } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';
import { Storage } from '@ionic/storage';
import { AlertController, ToastController } from '@ionic/angular';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage {
  constructor(
    private menu: MenuController,
    public alertController: AlertController,
    public navCtrl: NavController,
    public toastController: ToastController,
    private storage: Storage
  ) { }



  ngOnInit() { }

  closeMenu() {
    this.menu.close();
  }

  logout() {
    this.storage.set('isUserLoggedIn', false);
    this.navCtrl.navigateRoot('/login');
  }

  async alertaLogOut() {
    const alert = await this.alertController.create({
      header: 'Cerrar Sesión',
      message: '¿Esta seguro que desea cerrar sesión?',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
            console.log('Apreto boton cancelar');
          }
        }, {
          text: 'Si, salir',
          handler: () => {
            console.log('Apreto boton Salir');
            this.navCtrl.navigateRoot('/login');
          }
        }
      ]
    });

    await alert.present();
  }

  goToPlantas() {
    this.navCtrl.navigateRoot('menu/plantas');
    this.menu.close();
  }

  goToEquipos() {
    this.navCtrl.navigateRoot('menu/equipos');
    this.menu.close();
  }

  goToCargasDiarias() {
    this.navCtrl.navigateRoot('menu/carga-diaria');
    this.menu.close();
  }

  goToNuevaCarga() {
    this.navCtrl.navigateRoot('menu/crear');
    this.menu.close();
  }

  goToHome() {
    this.navCtrl.navigateRoot('menu/home');
    this.menu.close();
  }

}