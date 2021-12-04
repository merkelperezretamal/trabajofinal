import { getCurrencySymbol } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CargadiariaService } from 'src/app/services/cargadiaria.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-crear-carga',
  templateUrl: './crear-carga.page.html',
  styleUrls: ['./crear-carga.page.scss'],
})
export class CrearCargaPage implements OnInit {

  idEquipo: any;
  datosCarga = '';
  cargaForm: FormGroup;
  private autenticacion = '';

  public urlServidor = 'https://secco-app.herokuapp.com';
  public equipoArray: any = null;

  constructor(private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private cargadiariaService: CargadiariaService,
    private paramRoute: ActivatedRoute,
    private router: Router) {


    this.cargaForm = this.fb.group({
      horometro: [''],
      rpm: [''],
      presionAceite: [''],
      temperaturaAceite: [''],
      temperaturaAgua: [''],
      temperaturaSuccion1: [''],
      presionSuccion1: [''],
      temperaturaSuccion2: [''],
      presionSuccion2: [''],
      temperaturaSuccion3: [''],
      presionSuccion3: [''],
      presionDescarga: [''],
    });
  }

  

  ngOnInit() {
    this.listarEquipos();
  }
  

  onSelectEquipo(equipo) {
    console.log(equipo);
    this.cargaForm.patchValue({ equipo: equipo.$$instanceId });
    this.idEquipo = equipo.$$instanceId;
  }

  listarEquipos() {
    const httpOptions = {
      headers: new HttpHeaders({
        Accept: 'application/json;profile="urn:org.apache.isis/v1"',
        Authorization: 'Basic bmFjaG86cGFzcw==',
      }),
    };
    const URL =
      this.urlServidor +
      '/restful/services/simple.EquipoMenu/actions/listarTodos/invoke';
    this.http.get(URL, httpOptions).subscribe((resultE: Array<any>) => {
      var array = resultE;
      array.pop();
      this.equipoArray = array;
    });
  }

  

  submit() {
    Swal.fire({
      title: "Confirma la Carga",
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: "Sí,confirmo",
      confirmButtonColor: "#68DE65",
      cancelButtonText: "Cancelar",
      cancelButtonColor: "#DF4343",
  })
  .then(resultado => {
      if (resultado.isConfirmed) {

        this.cargadiariaService
          .crearCarga(this.idEquipo, this.cargaForm.value)
          .subscribe((carga) => {
      
          });
          this.router.navigate(['/menu/home']);
           Swal.fire({
            title: "Carga existosa",
                  icon: 'success', 
                  confirmButtonColor: "#68DE65",               
                })
                this.cargaForm.reset(); 
      } else {
        Swal.fire({
          title: "Carga Cancelada ", 
          confirmButtonColor: "#68DE65",
          icon: 'error',
                 })
      }
  });



  }
}