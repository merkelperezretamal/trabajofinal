package domainapp.modules.simple.dom.planta;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.cargadiaria.CargaDiaria;
import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.motor.Motor;
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

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Planta_nombre_UNQ", members = {"nombre"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Planta implements Comparable<Planta> {

    public Planta(@NonNull String nombre, String provincia, String cliente) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.cliente = cliente;
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Getter
    @Setter
    private String nombre;

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private String provincia;

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private String cliente;

    @Persistent(mappedBy = "planta", dependentElement = "true")
    @Collection()
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private SortedSet<Equipo> equipos = new TreeSet<Equipo>();

    @Override
    public int compareTo(final Planta other) {
        return ComparisonChain.start()
                .compare(this.getNombre(), other.getNombre())
                .result();
    }

    @Override
    public String toString() {
        return getNombre()+" - "+getProvincia();
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Equipo nuevoEquipo(
                            @ParameterLayout(named="Denominacion")
                            final String denominacion) {
        return repositoryService.persist(new Equipo(denominacion, this));
    }

    public String title() {
        return String.format(
                "%s",
                getNombre(), getProvincia());
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
