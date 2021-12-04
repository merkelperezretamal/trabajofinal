package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.motor.ETipoModelo;
import domainapp.modules.simple.dom.orden.Orden;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.Title;

import javax.jdo.annotations.Persistent;
import java.util.SortedSet;
import java.util.TreeSet;

public class RepoMotor {

    @Getter @Setter
    private String equipo;

    @Getter @Setter
    private String tag;

    @Getter @Setter
    private String marca;

    @Getter @Setter
    private String tipoModelo;

    @Getter @Setter
    private String serial;

    public RepoMotor(String equipo, String tag, String marca, String tipoModelo, String serial) {
        this.equipo = equipo;
        this.tag = tag;
        this.marca = marca;
        this.tipoModelo = tipoModelo;
        this.serial = serial;
    }

    public RepoMotor() { }
}
