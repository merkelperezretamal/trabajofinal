import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-carga-diaria',
  templateUrl: './carga-diaria.page.html',
  styleUrls: ['./carga-diaria.page.scss'],
})
export class CargaDiariaPage implements OnInit {

  public resultadosC : any = null;
  private autenticacion = '';
  public URLservidor: String;
  public URLSecundaria: String =  'https://secco-app.herokuapp.com';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  ionViewWillEnter(){
    if(window.localStorage.autenticacion){
      this.autenticacion = window.localStorage.autenticacion;
    }
    if(window.localStorage.URLservidor){
      this.URLservidor = window.localStorage.URLservidor;
    }else{ 
      this.URLservidor = this.URLSecundaria;
    }
  this.listarCargas();
  }

  listarCargas(){
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept':  'application/json;profile="urn:org.apache.isis/v1"',
        'Authorization': 'Basic bmFjaG86cGFzcw==',
      })
    }
    const URL = this.URLSecundaria+'/restful/services/simple.CargaDiariaMenu/actions/listarTodas/invoke';
    this.http.get(URL, httpOptions)
    .subscribe((resultados : Array<any>) => {
      var array = resultados;
      array.pop();
      this.resultadosC = array;
    });

  }

    goDetCar(id_car) { 
      this.router.navigate(['/ver-carga', { idCar: id_car }])
  }

filterItemsOfType(){
  return this.resultadosC .filter(resultado => resultado.titulo != null);
}


}