import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { ToastController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  private loginForm: FormGroup;
  URLServidorInicial : string = 'http://localhost:8080';
  usuario = '';
  password = '';

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute,
    private router: Router, private formBuilder: FormBuilder, private loginService: LoginService,
    public toastController: ToastController) { 

      this.loginForm = this.formBuilder.group({
        usuario: ['', Validators.required],
        password: ['', Validators.required],
      });
    }

  ngOnInit() {

    window.localStorage.URLservidor = this.URLServidorInicial;
  }

  submit() {
    this.usuario = this.loginForm.controls.usuario.value,
    this.password = this.loginForm.controls.password.value
    console.log('entro ??');
    this.loginService.realizaLogin(this.usuario, this.password)
      .subscribe(
        (response) => {

          if (response && response.length) {
            //Guarda el nombre de usuario en cookie
            window.localStorage.usuario = this.usuario;

            //Guarda la autenticacion en cookie
            window.localStorage.autenticacion = btoa(this.usuario + ":" + this.password);

            this.router.navigate(['/home'])
            console.log('entro');
          }
        },
        (error) => {
          console.log(error);
          console.log('Respuesta de la API recibida con error: ' + error.statusText);
          this.loginErroneoToast();
        })
  }

  async loginErroneoToast() {
    const toast = await this.toastController.create({
      message: 'Usuario o Contraseña incorrecto, vuelva a ingresarlos',
      duration: 2500
    });
    toast.present();
  }


}