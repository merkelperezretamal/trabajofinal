package domainapp.modules.simple.dom.mantenimiento;
import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.tarea.Tarea;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.SortedSet;
import java.util.TreeSet;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Mantenimiento_tag_UNQ", members = {"tag"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor

public class Mantenimiento implements Comparable<Mantenimiento> {
    @lombok.NonNull
    @Getter
    @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private String tag;

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Getter
    @Setter
    private ETipoMantenimiento tipoMantenimiento;

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private int horas;

    @Persistent(mappedBy = "mantenimiento", dependentElement = "true")
    @Collection()
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private SortedSet<Tarea> tareas = new TreeSet<Tarea>();

    public Mantenimiento(@NonNull ETipoMantenimiento tipoMantenimiento, int horas) {
        String horasTexto;
        this.tipoMantenimiento = tipoMantenimiento;
        this.horas = horas;
        horasTexto = String.valueOf(horas);
        this.tag = this.tipoMantenimiento + " " + horasTexto;
    }

    @Override
    public int compareTo(final Mantenimiento other) {
        return ComparisonChain.start()
                .compare(this.getTag(), other.getTag())
                .result();
    }


    @Override
    public String toString() {
        return getTipoMantenimiento()+" - "+getHoras();
    }

    public String title() {
        return String.format(
                "%s",
                getTipoMantenimiento(), getHoras());
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Tarea nuevaTarea(final @ParameterLayout (named="Nombre") String nombre,
                            final @ParameterLayout (named="Descripcion") String descripcion) {
        return repositoryService.persist(new Tarea(nombre, descripcion, this));
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
