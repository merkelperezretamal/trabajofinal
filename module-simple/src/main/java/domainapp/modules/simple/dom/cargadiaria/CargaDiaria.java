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

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple" )
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="CargaDiaria_equipo_tag_UNQ", members = {"equipo","codigo"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class CargaDiaria implements Comparable<CargaDiaria> {

    @javax.jdo.annotations.Column(allowsNull = "false", name = "equipoId")
    @Property(editing = Editing.DISABLED)
    @Getter
    @Setter
    private Equipo equipo;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Getter @Setter @Title
    private String codigo;

    //De Equipo

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Property(hidden = Where.EVERYWHERE) //Oculta la propiedad (para que no se vea cuando se actualiza por ejemplo)
    @Getter @Setter
    private double horometro;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double disponibilidad;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double rpm;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionAceite;

    //De Motor

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double temperaturaAceite;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double temperaturaAgua;

    //De Compresor

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double temperaturaSuccion1;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double temperaturaSuccion2;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double temperaturaSuccion3;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionSuccion1;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionSuccion2;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionSuccion3;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionDescarga;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double caudalDiario;

    @Override
    public int compareTo(final CargaDiaria other) {
        return ComparisonChain.start()
                .compare(this.getEquipo(), other.getEquipo())
                .compare(this.getCodigo(), other.getCodigo())
                .result();
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    public CargaDiaria(Equipo equipo,
                       String codigo,
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
                       double presionDescarga,
                       double caudalDiario) {
        this.equipo = equipo;
        this.codigo = codigo;
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
        this.caudalDiario = caudalDiario;
    }

    @Override
    public String toString() {
        return getCodigo();
    }

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
