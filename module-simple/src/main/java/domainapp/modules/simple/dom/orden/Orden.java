package domainapp.modules.simple.dom.orden;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.tarea.Tarea;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.Date;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Orden_numeroOrden_UNQ", members = {"numeroOrden"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Orden implements Comparable<Orden>{

    public Orden(int numeroOrden, String tipoMantenimiento, Motor motor) {
        this.numeroOrden = numeroOrden;
        this.fecha = new Date();
        this.tipoMantenimiento = tipoMantenimiento;
        this.motor = motor;
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter
    @Setter
    @Title
    private int numeroOrden;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private Date fecha;

    @Persistent(mappedBy = "orden", dependentElement = "true")
    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @Getter
    @Setter
    private Mantenimiento mantenimiento;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private String tipoMantenimiento;

    @javax.jdo.annotations.Column(allowsNull = "true", name = "motorId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Motor motor;

    @Override
    public int compareTo(final Orden other) {
        return ComparisonChain.start()
                .compare(this.getNumeroOrden(), other.getNumeroOrden())
                .result();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Orden modificar(
            @ParameterLayout(named = "Numero")
            final int numeroOrden) {
        setNumeroOrden(numeroOrden);
        return this;
    }
    public int default0Modificar() {
        return getNumeroOrden();
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE)
    @lombok.Setter(AccessLevel.NONE)
    RepositoryService repositoryService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE)
    @lombok.Setter(AccessLevel.NONE)
    TitleService titleService;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE)
    @lombok.Setter(AccessLevel.NONE)
    MessageService messageService;

}
