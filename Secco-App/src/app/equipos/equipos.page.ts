import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-equipos',
  templateUrl: './equipos.page.html',
  styleUrls: ['./equipos.page.scss'],
})
export class EquiposPage implements OnInit {

  public resultadosE : any = null;
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
    this.listarEquipo();
  }

  listarEquipo(){
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept':  'application/json;profile="urn:org.apache.isis/v1"',
        'Authorization': 'Basic dXN1YXJpbzp1c3Vhcmlv',
      })
    }
    const URL = this.URLSecundaria+'/restful/services/simple.EquipoMenu/actions/listarTodos/invoke';
    this.http.get(URL, httpOptions)
    .subscribe((resultados : Array<any>) => {
      var array = resultados;
      array.pop();
      this.resultadosE = array;
    });

  }

  goDetEqu(id_equ) { 
    this.router.navigate(['/equipo-detalle', { idEqu: id_equ }])
  }


filterItemsOfType(){
 return this.resultadosE.filter(resultado => resultado.titulo != null);
 

  }

}