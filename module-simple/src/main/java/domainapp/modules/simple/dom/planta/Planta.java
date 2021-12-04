package domainapp.modules.simple.dom.planta;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.mantenimiento.MantenimientoRepositorio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.value.Blob;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import javax.swing.*;
import java.io.IOException;
import java.util.List;
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
        this.activo = true;
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

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private boolean activo;

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

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Equipo nuevoEquipo(@ParameterLayout(named="Denominacion") final String denominacion) {

        List<Equipo> listaEquipos = equipoRepositorio.buscarPorDenominacion(denominacion);

        if(listaEquipos.isEmpty()){
            return repositoryService.persist(new Equipo(denominacion, this));
        }else{
//            JOptionPane.showMessageDialog(null, "Ya existe un equipo con esa denominacion en "+this.nombre);
//            JOptionPane.showMessageDialog(null, "Redirigiendote al equipo existente");
            messageService.raiseError("Ya existe un equipo con la denominacion '"+denominacion+"'. Presione 'Continue' para volver a la planta "+this.nombre);

            return listaEquipos.get(0);
        }
    }

    public String title() {
        return String.format(
                "%s",
                getNombre(), getProvincia());
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public void cambioEstado() {
        setActivo(!this.activo);
    }

    @Programmatic
    public boolean default0CambioEstado() {
        return this.activo;
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Planta editarPlanta(
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Provincia") String provincia,
            final @ParameterLayout(named="Cliente") String cliente) {
        setNombre(nombre);
        setProvincia(provincia);
        setCliente(cliente);
        return this;
    }

    public String default0EditarPlanta() {
        return this.nombre;
    }
    public String default1EditarPlanta() {
        return this.provincia;
    }
    public String default2EditarPlanta() {
        return this.cliente;
    }

    @Action()
    public Blob exportarListadoEquipos() throws JRException, IOException {
        return equipoRepositorio.exportarListado(this.nombre);
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    EquipoRepositorio equipoRepositorio;

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
