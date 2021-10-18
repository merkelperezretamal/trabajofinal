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

    public RepoCargasDiarias(String horometro, String rpm, String presionAceite) {
//        this.fecha = fecha;
        this.horometro = horometro;
        this.rpm = rpm;
        this.presionAceite = presionAceite;
    }

    public RepoCargasDiarias() {}
}
