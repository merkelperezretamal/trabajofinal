package domainapp.modules.simple.dom.tarea;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.planta.Planta;
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
@javax.jdo.annotations.Unique(name="Tarea_nombre_UNQ", members = {"nombre"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Tarea implements Comparable<Tarea> {

    @Getter
    @Setter
    @Title
    @javax.jdo.annotations.Column(allowsNull = "false")
    private String nombre;

    @Getter
    @Setter
    @javax.jdo.annotations.Column(allowsNull = "false")
    private String descripcion;


    @javax.jdo.annotations.Column(allowsNull = "false", name = "mantenimientoId")
    @Property(editing = Editing.DISABLED)
    @Getter
    @Setter
    private Mantenimiento mantenimiento;


    public Tarea(String nombre, String descripcion, Mantenimiento mantenimiento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.mantenimiento = mantenimiento;
    }

    @Override
    public int compareTo(final Tarea other) {
        return ComparisonChain.start()
                .compare(this.getNombre(), other.getNombre())
                .result();
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Tarea modificarDescripcion(
            @ParameterLayout(named="Descripcion")
            final String descripcion) {
        setDescripcion(descripcion);
        return this;
    }
    public String default0ModificarDescripcion() {
        return getDescripcion();
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