package domainapp.modules.simple.dom.compresor;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.compresor.Compresor;
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
@javax.jdo.annotations.Unique(name="Compresor_equipo_tag_UNQ", members = {"equipo","tag"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class Compresor implements Comparable<Compresor>{

    public Compresor(Equipo equipo, String tag){
        this.equipo = equipo;
        this.tag = tag;
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

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @Getter
    @Setter
    @Title
    private String tag;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double temperaturaSuccion1;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double temperaturaSuccion2;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double temperaturaSuccion3;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double presionSuccion1;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double presionSuccion2;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double presionSuccion3;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double presionDescarga;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double caudalDiario;

    @Override
    public String toString() {
        return getTag();
    }

    @Override
    public int compareTo(final Compresor other) {
        return ComparisonChain.start()
                .compare(this.getEquipo(), other.getEquipo())
                .compare(this.getTag(), other.getTag())
                .result();
    }

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
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
