package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class RepoCargasDiarias {

    @Getter @Setter
    private String fecha;
    @Getter @Setter
    private String horometro;
    @Getter @Setter
    private String rpm;
    @Getter @Setter
    private String presionAceite;
    @Getter @Setter
    private String temperaturaAceite;
    @Getter @Setter
    private String temperaturaAgua;
    @Getter @Setter
    private String temperaturaSuccion1;
    @Getter @Setter
    private String temperaturaSuccion2;
    @Getter @Setter
    private String temperaturaSuccion3;
    @Getter @Setter
    private String presionSuccion1;
    @Getter @Setter
    private String presionSuccion2;
    @Getter @Setter
    private String presionSuccion3;
    @Getter @Setter
    private String presionDescarga;

    public RepoCargasDiarias(String fecha,
                             String horometro,
                             String rpm,
                             String presionAceite,
                             String temperaturaAceite,
                             String temperaturaAgua,
                             String temperaturaSuccion1,
                             String temperaturaSuccion2,
                             String temperaturaSuccion3,
                             String presionSuccion1,
                             String presionSuccion2,
                             String presionSuccion3,
                             String presionDescarga) {
        this.fecha = fecha;
        this.horometro = horometro;
        this.rpm = rpm;
        this.presionAceite = presionAceite;
        this.temperaturaAceite = temperaturaAceite;
        this.temperaturaAgua = temperaturaAgua;
        this.temperaturaSuccion1 = temperaturaSuccion1;
        this.temperaturaSuccion2 = temperaturaSuccion2;
        this.temperaturaSuccion3 = temperaturaSuccion3;
        this.presionSuccion1 = presionSuccion1;
        this.presionSuccion2 = presionSuccion2;
        this.presionSuccion3 = presionSuccion3;
        this.presionDescarga = presionDescarga;
    }

    public RepoCargasDiarias() {}
}
