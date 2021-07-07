package domainapp.modules.simple.dom.motor;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.planta.Planta;
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
import javax.jdo.annotations.VersionStrategy;

import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple" )
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="Motor_equipo_tag_UNQ", members = {"equipo","tag"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Motor<string> implements Comparable<Motor>{

    public Motor(Equipo equipo, String tag, String marca, String modelo, String serial){
        this.equipo = equipo;
        this.tag = tag;
        this.marca = marca;
        this.modelo = modelo;
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
    private String modelo;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private String serial;

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
            final @ParameterLayout(named="Modelo") String modelo,
            final @ParameterLayout(named="Serial") String serial) {
        setTag(tag);
        setMarca(marca);
        setModelo(modelo);
        setSerial(serial);
        return this;
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
