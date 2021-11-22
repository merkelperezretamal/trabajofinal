import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ToastController } from '@ionic/angular';


@Component({
  selector: 'app-planta-detalle',
  templateUrl: './planta-detalle.page.html',
  styleUrls: ['./planta-detalle.page.scss'],
})
export class PlantaDetallePage implements OnInit {

  idPla;
  plaData;
  param : any;
  private autenticacion = '';
  public URLservidor: String;
  public URLSecundaria: String =  'http://localhost:8080';

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
			this.listarPla(this.param.idPla);
		}
  }

  listarPla(idPla) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept': 'application/json;profile="urn:org.apache.isis/v1"',
        // 'Authorization': 'Basic bmFjaG86cGFzcw==',
        'Authorization': 'Basic ' + this.autenticacion,

      })
    }
    const URL = this.URLservidor+'/restful/objects/simple.Planta/' + idPla;
    this.http.get(URL, httpOptions)
      .subscribe((resultados) => {
        this.plaData = resultados;
      });

  }

}