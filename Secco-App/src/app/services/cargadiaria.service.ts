import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class CargadiariaService {

  private autenticacion = '';

  constructor(private httpClient: HttpClient) { }

  private urlServidor = 'https://secco-app.herokuapp.com';

  httpOptions = {
    headers: new HttpHeaders({
      Accept: 'application/json;profile="urn:org.apache.isis/v1"',
      'Authorization': 'Basic dXN1YXJpbzp1c3Vhcmlv',
    }),
  };

  private Url = this.urlServidor + '/restful/objects/simple.Equipo/';

  getCarga(id: number) {
    console.log('id de getcarga de cargadiariaService ' + id);
    return this.httpClient.get(this.Url + id, this.httpOptions);
  }
  crearCarga(id, carga) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Accept': 'application/json;profile="urn:org.apache.isis/v1"',
        'Authorization': 'Basic dXN1YXJpbzp1c3Vhcmlv',
      }),
    };
    const crearCargaUrl =
      this.urlServidor + '/restful/objects/simple.Equipo/' + id;

    console.log(carga);
    let datos = {
      horometro: {
        value: carga.horometro,
      },
      rPM: {
        value: carga.rpm,
      },
      oilPress: {
        value: carga.presionAceite,
      },
      oilTemp: {
        value: carga.temperaturaAceite,
      },
      watTemp: {
        value: carga.temperaturaAgua,
      },
      'tS1°': {
        value: carga.temperaturaSuccion1,
      },
      'pS1°': {
        value: carga.presionSuccion1,
      },
      'tS2°': {
        value: carga.temperaturaSuccion2,
      },
      'pS2°': {
        value: carga.presionSuccion2,
      },
      'tS3°': {
        value: carga.temperaturaSuccion3,
      },
      'pS3°': {
        value: carga.presionSuccion3,
      },
      'pD': {
        value: carga.presionDescarga,
      },
    };
    console.log(datos);
    return this.httpClient.post(
      crearCargaUrl + '/actions/nuevaCargaDiaria/invoke',
      JSON.stringify(datos),
      httpOptions
    );
  }
}