package domainapp.modules.simple.dom.orden;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.mantenimiento.ETipoMantenimiento;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.mantenimiento.MantenimientoRepositorio;
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
import java.util.List;
import java.util.SortedSet;

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

    public Orden(int numeroOrden, String tipoMantenimiento, Compresor compresor) {
        this.numeroOrden = numeroOrden;
        this.fecha = new Date();
        this.tipoMantenimiento = tipoMantenimiento;
        this.compresor = compresor;

    }

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter
    @Setter
    @Title
    private int numeroOrden;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private Date fecha;

    @Persistent(mappedBy = "ordenes", dependentElement = "true")
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter
    @Setter
    private Mantenimiento mantenimiento;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private String tipoMantenimiento;

    @javax.jdo.annotations.Column(allowsNull = "true", name = "motorId")
    @Property(editing = Editing.DISABLED, hidden = Where.EVERYWHERE)
    @Getter @Setter
    private Motor motor;

    @javax.jdo.annotations.Column(allowsNull = "true", name = "compresorId")
    @Property(editing = Editing.DISABLED, hidden = Where.EVERYWHERE)
    @Getter @Setter
    private Compresor compresor;

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


    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    @PropertyLayout (named = "Volver")
    public Object regresar() {
        if(this.motor != null){
            return this.motor;
        }else{
            return this.compresor;
        }
    }
    @Programmatic
    public Object default0Regresar () {
        if(this.motor != null){
            return this.motor;
        }else{
            return this.compresor;
        }
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    @ActionLayout(named = "Asignar Mantenimiento")
    public Orden agregarMantenimiento(
            @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named = "Mantenimiento")
            final Mantenimiento mantenimiento) {
        SortedSet<Orden> ordenesMantenimiento = mantenimiento.getOrdenes();
        setMantenimiento(mantenimiento);
        ordenesMantenimiento.add(this);
        mantenimiento.setOrdenes(ordenesMantenimiento);
        return this;
    }

    public List<Mantenimiento> choices0AgregarMantenimiento() {
        if(this.tipoMantenimiento.equals("MOTOR_3500")){
            return mantenimientoRepositorio.buscarPorTipo(ETipoMantenimiento.MOTOR_3500);
        }else {
            if (this.tipoMantenimiento.equals("MOTOR_3600")) {
                return mantenimientoRepositorio.buscarPorTipo(ETipoMantenimiento.MOTOR_3600);
            } else {
                return mantenimientoRepositorio.buscarPorTipo(ETipoMantenimiento.COMPRESOR);
            }
        }
    }

    @javax.inject.Inject
    @javax.jdo.annotations.NotPersistent
    @lombok.Getter(AccessLevel.NONE) @lombok.Setter(AccessLevel.NONE)
    MantenimientoRepositorio mantenimientoRepositorio;

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
