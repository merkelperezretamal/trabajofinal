import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ToastController } from '@ionic/angular';

@Component({
  selector: 'app-ver-carga',
  templateUrl: './ver-carga.page.html',
  styleUrls: ['./ver-carga.page.scss'],
})
export class VerCargaPage implements OnInit {

  idCar;
  carData;
  param : any;
  private autenticacion = '';
  public URLservidor: String;
  public URLSecundaria: String =  'https://secco-app.herokuapp.com';

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, public toastController: ToastController) {}

  ngOnInit() {
    if(window.localStorage.autenticacion){
      this.autenticacion = window.localStorage.autenticacion;
    }
    if(window.localStorage.URLservidor){
      this.URLservidor = window.localStorage.URLservidor;
    }else{ 
      this.URLservidor = this.URLSecundaria;
    }
    this.param = this.activatedRoute.snapshot.params;
    if (Object.keys(this.param).length) {
			this.listarCar(this.param.idCar);
		}
  }

  listarCar(idCar) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept': 'application/json;profile="urn:org.apache.isis/v1"',
        'Authorization': 'Basic bmFjaG86cGFzcw==',
      })
    }
    const URL = this.URLSecundaria+'/restful/objects/simple.CargaDiaria/' + idCar;
    this.http.get(URL, httpOptions)
      .subscribe((resultados) => {
        this.carData = resultados;
      });
  }

}