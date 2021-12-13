import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-plantas',
  templateUrl: './plantas.page.html',
  styleUrls: ['./plantas.page.scss'],
})
export class PlantasPage implements OnInit {

  public resultadosP : any = null;
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
    this.listarPlantas();
  }


  listarPlantas(){
    const httpOptions = {
      headers: new HttpHeaders({
        'Accept':  'application/json;profile="urn:org.apache.isis/v1"',
        'Authorization': 'Basic dXN1YXJpbzp1c3Vhcmlv',
      })
    }
    const URL = this.URLservidor+'/restful/services/simple.PlantaMenu/actions/listarTodas/invoke';
    this.http.get(URL, httpOptions)
    
    .subscribe((resultados : Array<any>) => {
      var array = resultados;
      array.pop();
      this.resultadosP = array;
    });

  }

    goDetPla(id_pla) { 
      this.router.navigate(['/planta-detalle', { idPla: id_pla }])
  }

filterItemsOfType(){
  return this.resultadosP.filter(resultado => resultado.titulo != null);
}


}