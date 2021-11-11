package domainapp.modules.simple.dom.mantenimiento;
import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.orden.Orden;
import domainapp.modules.simple.dom.tarea.Tarea;
import domainapp.modules.simple.dom.equipo.Equipo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Mantenimiento_horas_tipoMantenimiento_UNQ", members = {"horas", "tipoMantenimiento"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Mantenimiento implements Comparable<Mantenimiento>{

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Getter
    @Setter
    private ETipoMantenimiento tipoMantenimiento;

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private int horas;

    @javax.jdo.annotations.Column(allowsNull = "true", name = "ordenesId")
    @Property(editing = Editing.DISABLED)
    @Collection(hidden = Where.EVERYWHERE)
    @Getter @Setter
    private SortedSet<Orden> ordenes = new TreeSet<Orden>();

    @Persistent(mappedBy = "mantenimiento", dependentElement = "true")
    @Collection()
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private SortedSet<Tarea> tareas = new TreeSet<Tarea>();


    public Mantenimiento(ETipoMantenimiento tipoMantenimiento, int horas) {
        this.tipoMantenimiento = tipoMantenimiento;
        this.horas = horas;
    }

    @Override
    public int compareTo(final Mantenimiento other) {
        return ComparisonChain.start()
                .compare(this.getTipoMantenimiento(), other.getTipoMantenimiento())
                .compare(this.getHoras(), other.getHoras())
                .result();
    }

    @Override
    public String toString() {
        return getTipoMantenimiento()+" - "+getHoras();
    }

    public String title() {
        return getTipoMantenimiento()+" - "+getHoras();
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Tarea nuevaTarea(final @ParameterLayout (named="Nombre") String nombre,
                            final @ParameterLayout (named="Descripcion") String descripcion) {
        return repositoryService.persist(new Tarea(nombre, descripcion, this));
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Mantenimiento modificarHoras(
            @ParameterLayout(named="Horas")
            final int horas) {
        setHoras(horas);
        return this;
    }
    public int default0ModificarHoras() {
        return getHoras();
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
