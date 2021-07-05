package domainapp.modules.simple.dom.orden;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import lombok.Getter;
import lombok.Setter;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;

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

    public Orden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
        this.fecha = new Date();
    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter
    @Setter
    private int numeroOrden;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private Date fecha;

    @Persistent(mappedBy = "orden", dependentElement = "true")
    @javax.jdo.annotations.Column(allowsNull = "true", length = 40)
    @Getter
    @Setter
    private Mantenimiento mantenimiento;

    @Override
    public int compareTo(final Orden other) {
        return ComparisonChain.start()
                .compare(this.getNumeroOrden(), other.getNumeroOrden())
                .compare(this.getMantenimiento().getEquipo().getPlanta().getNombre(),
                        other.getMantenimiento().getEquipo().getPlanta().getNombre())
                .result();
    }


}
