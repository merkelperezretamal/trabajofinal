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
    private String planta;
    @Getter @Setter
    private String motor;
    @Getter @Setter
    private String compresor;
    @Getter @Setter
    private String activo;

    public RepoEquipos(String denominacion, String planta, String motor, String compresor, String activo) {
        this.denominacion = denominacion;
        this.planta = planta;
        this.motor = motor;
        this.compresor = compresor;
        this.activo = activo;
    }

    public RepoEquipos() {}

}
