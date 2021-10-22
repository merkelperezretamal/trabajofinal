package domainapp.modules.simple.dom.cargadiaria;


import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.equipo.Equipo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@SuppressWarnings("ALL")
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple" )
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="CargaDiaria_fecha_UNQ", members = {"fecha"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class CargaDiaria implements Comparable<CargaDiaria> {

    @javax.jdo.annotations.Column(allowsNull = "false", name = "equipoId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Equipo equipo;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter @Title
    private Date fecha;

    //De Equipo

    @javax.jdo.annotations.Column(allowsNull = "false")
    //@Property(hidden = Where.EVERYWHERE) //Oculta la propiedad (para que no se vea cuando se actualiza por ejemplo)
    @Getter @Setter
    @PropertyLayout(named="Horometro") private double horometro;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="Rpm") private double rpm;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="Oil Press") private double presionAceite;

    //De Motor

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="Oil Temp") private double temperaturaAceite;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="Wat Temp") private double temperaturaAgua;

    //De Compresor

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="TS 1°") private double temperaturaSuccion1;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="TS 2°") private double temperaturaSuccion2;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="TS 3°") private double temperaturaSuccion3;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="PS 1°") private double presionSuccion1;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="PS 2°") private double presionSuccion2;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="PS 3°") private double presionSuccion3;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    @PropertyLayout(named="PD") private double presionDescarga;


    @Override
    public int compareTo(final CargaDiaria other) {
        return ComparisonChain.start()
                .compare(this.getFecha(), other.getFecha())
                .result();
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    public CargaDiaria(Equipo equipo,
                       double horometro,
                       double rpm,
                       double presionAceite,
                       double temperaturaAceite,
                       double temperaturaAgua,
                       double temperaturaSuccion1,
                       double presionSuccion1,
                       double temperaturaSuccion2,
                       double presionSuccion2,
                       double temperaturaSuccion3,
                       double presionSuccion3,
                       double presionDescarga){
        this.equipo = equipo;
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

        this.fecha = new Date();
    }

    //Para los reportes
    public String RepoFecha() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaComoCadena = dateFormat.format(this.fecha);
        return fechaComoCadena;
    }
    public String RepoHorometro() { return String.valueOf(this.horometro); }
    public String RepoRpm() { return String.valueOf(this.rpm); }
    public String RepoPresionAceite() { return String.valueOf(this.presionAceite); }
    public String RepoTemperaturaAceite() { return String.valueOf(this.temperaturaAceite); }
    public String RepoTemperaturaAgua() { return String.valueOf(this.temperaturaAgua); }
    public String RepoTemperaturaSuccion1() { return String.valueOf(this.temperaturaSuccion1); }
    public String RepoTemperaturaSuccion2() { return String.valueOf(this.temperaturaSuccion2); }
    public String RepoTemperaturaSuccion3() { return String.valueOf(this.temperaturaSuccion3); }
    public String RepoPresionSuccion1() { return String.valueOf(this.presionSuccion1); }
    public String RepoPresionSuccion2() { return String.valueOf(this.presionSuccion2); }
    public String RepoPresionSuccion3() { return String.valueOf(this.presionSuccion3); }
    public String RepoPresionDescarga() { return String.valueOf(this.presionDescarga); }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;
}
