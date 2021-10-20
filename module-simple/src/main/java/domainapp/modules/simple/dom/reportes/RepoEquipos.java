package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.planta.Planta;
import lombok.Getter;
import lombok.Setter;

public class RepoEquipos {

    @Getter @Setter
    private String denominacion;
    @Getter @Setter
    private String motor;
    @Getter @Setter
    private String compresor;


    public RepoEquipos(String denominacion, String motor, String compresor) {
        this.denominacion = denominacion;
        this.motor = motor;
        this.compresor = compresor;
    }

    public RepoEquipos() {}

}
