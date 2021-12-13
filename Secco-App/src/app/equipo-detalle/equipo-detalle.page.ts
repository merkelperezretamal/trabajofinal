import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastController } from '@ionic/angular';


@Component({
  selector: 'app-equipo-detalle',
  templateUrl: './equipo-detalle.page.html',
  styleUrls: ['./equipo-detalle.page.scss'],
})
export class EquipoDetallePage implements OnInit {

  idEqu;
  equData;
  param : any;
  private autenticacion = '';
  public URLservidor: String;
  public URLSecundaria: String =  'https://secco-app.herokuapp.com';

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, public toastController: ToastController) { }

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
			this.listarEqu(this.param.idEqu);
		}
  }

  listarEqu(idEqu) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept': 'application/json;profile="urn:org.apache.isis/v1"',
        'Authorization': 'Basic dXN1YXJpbzp1c3Vhcmlv',
      })
    }
    const URL = this.URLSecundaria+'/restful/objects/simple.Equipo/' + idEqu;
    console.log(URL);
    

    this.http.get(URL, httpOptions)
      .subscribe((resultados) => {
        this.equData = resultados;
      });
  }

}