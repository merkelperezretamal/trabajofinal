package domainapp.modules.simple.dom.motor;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.motor.MotorRepositorio;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.orden.Orden;
import domainapp.modules.simple.dom.orden.OrdenRepositorio;
import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.tarea.Tarea;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;
import org.apache.isis.applib.value.Blob;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple" )
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Motor_equipo_tag_UNQ", members = {"equipo","tag"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Motor implements Comparable<Motor>{

    public Motor(Equipo equipo, String tag, String marca, ETipoModelo modelo, String serial){
        this.equipo = equipo;
        this.tag = tag;
        this.marca = marca;
        this.tipoModelo = modelo;
        this.serial = serial;
    }

    public String title() {
        return String.format(
                "%s",
                getTag(), getEquipo().getDenominacion());
    }

    @javax.jdo.annotations.Column(allowsNull = "false", name = "equipoId")
    @Property(editing = Editing.DISABLED)
    @Getter
    @Setter
    private Equipo equipo;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @Getter
    @Setter
    @Title
    private String tag;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private String marca;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private ETipoModelo tipoModelo;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private String serial;

    @Persistent(mappedBy = "motor", dependentElement = "true")
    @Collection()
    @Getter @Setter
    private SortedSet<Orden> ordenes = new TreeSet<Orden>();

    @Override
    public String toString() {
        return getTag();
    }

    @Override
    public int compareTo(final Motor other) {
        return ComparisonChain.start()
                .compare(this.getEquipo(), other.getEquipo())
                .compare(this.getTag(), other.getTag())
                .result();
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public Motor editarMotor(
            final @ParameterLayout(named="TAG") String tag,
            final @ParameterLayout(named="Marca") String marca,
            final @ParameterLayout(named="Modelo") ETipoModelo tipoModelo,
            final @ParameterLayout(named="Serial") String serial) {

        List<Motor> listaMotores = motorRepositorio.buscarPorTag(tag);

        if(listaMotores.isEmpty()){
            setTag(tag);
            setMarca(marca);
            setTipoModelo(tipoModelo);
            setSerial(serial);
            return this;
        }else{
            messageService.raiseError("Ya existe un motor con el tag '"+tag+"'. Presione 'Continue' para volver");
            return listaMotores.get(0);
        }
    }

    public String default0EditarMotor(){ return this.tag; }
    public String default1EditarMotor(){ return this.marca; }
    public ETipoModelo default2EditarMotor(){ return this.tipoModelo; }
    public String default3EditarMotor(){ return this.serial; }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Orden nuevaOrden(final @ParameterLayout (named="Numero de Orden") int numeroOrden) {
        if(this.tipoModelo.equals(ETipoModelo.MOTOR_3500)){
            return repositoryService.persist(new Orden(numeroOrden, "MOTOR_3500", this));
        }else{
            return repositoryService.persist(new Orden(numeroOrden, "MOTOR_3600", this));
        }
    }

    @Action()
    public Blob exportarListadoOrdenes() throws JRException, IOException {
        return ordenRepositorio.exportarListado(this.tag, 0);
    }

    @Action()
    public Blob exportarDetalleMotor() throws JRException, IOException {
        return motorRepositorio.exportarDetalle(this);
    }

    //Para los reportes
    public String RepoTag() { return this.tag; }
    public String RepoEquipo() { return this.equipo.getDenominacion(); }
    public String RepoMarca() { return this.getMarca(); }
    public String RepoTipoModelo() { return this.getTipoModelo().name(); }
    public String RepoSerial() { return this.getSerial(); }

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

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    OrdenRepositorio ordenRepositorio;

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MotorRepositorio motorRepositorio;

}
