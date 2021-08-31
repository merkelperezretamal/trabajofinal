package domainapp.modules.simple.dom.equipo;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.cargadiaria.CargaDiaria;
import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.motor.ETipoModelo;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.planta.Planta;
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


@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Equipo_denominacion_planta_UNQ", members = {"denominacion", "planta"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
@lombok.Getter @lombok.Setter
@lombok.RequiredArgsConstructor
public class Equipo implements Comparable<Equipo> {

    public Equipo(@NonNull String denominacion, Planta planta) {
        this.denominacion = denominacion;
        this.planta = planta;
        this.activo = true;
    }

    @javax.jdo.annotations.Column(allowsNull = "false", length = 40)
    @lombok.NonNull
    @Getter
    @Setter
    @Title
    private String denominacion;

    @Persistent(mappedBy = "equipo", dependentElement = "true")
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private Motor motor;

    @Persistent(mappedBy = "equipo", dependentElement = "true")
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private Compresor compresor;

    @javax.jdo.annotations.Column(allowsNull = "true", name = "plantaId")
    @Property(editing = Editing.DISABLED)
    @Getter @Setter
    private Planta planta;

    @Persistent(mappedBy = "equipo", dependentElement = "true")
    @Collection()
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private SortedSet<CargaDiaria> cargasDiarias = new TreeSet<CargaDiaria>();

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private boolean activo;

    @Override
    public String toString() {
        return getDenominacion();
    }

    @Override
    public int compareTo(final Equipo other) {
        return ComparisonChain.start()
                .compare(this.getDenominacion(), other.getDenominacion())
                .result();
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Motor nuevoMotor(final @ParameterLayout (named="TAG") String tag,
                            final @ParameterLayout (named="Marca") String marca,
                            final @ParameterLayout (named="Modelo") ETipoModelo tipoModelo,
                            final @ParameterLayout (named="Serial") String serial) {
        return repositoryService.persist(new Motor(this, tag, marca, tipoModelo, serial));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Compresor nuevoCompresor(final @ParameterLayout (named="TAG") String tag,
                                    final @ParameterLayout (named="Marca") String marca,
                                    final @ParameterLayout (named="Modelo") String modelo,
                                    final @ParameterLayout (named="Frame") String frame,
                                    final @ParameterLayout (named="Cyl 1") String cylinder1,
                                    final @ParameterLayout (named="Cyl 2") String cylinder2,
                                    final @ParameterLayout (named="Cyl 3") String cylinder3,
                                    final @ParameterLayout (named="Cyl 4") String cylinder4) {
        return repositoryService.persist(new Compresor(this,tag,marca,modelo,frame,cylinder1,cylinder2,cylinder3,cylinder4));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public CargaDiaria nuevaCargaDiaria(final @ParameterLayout (named="Horometro") double horometro,
                                        @ParameterLayout (named="RPM") double rpm,
                                        @ParameterLayout (named="Oil Press") double presionAceite,
                                        @ParameterLayout (named="Oil Temp") double temperaturaAceite,
                                        @ParameterLayout (named="Wat Temp") double temperaturaAgua,
                                        @ParameterLayout (named="TS 1°") double temperaturaSuccion1,
                                        @ParameterLayout (named="PS 1°") double presionSuccion1,
                                        @ParameterLayout (named="TS 2°") double temperaturaSuccion2,
                                        @ParameterLayout (named="PS 2°") double presionSuccion2,
                                        @ParameterLayout (named="TS 3°") double temperaturaSuccion3,
                                        @ParameterLayout (named="PS 3°") double presionSuccion3,
                                        @ParameterLayout (named="PD 3") double presionDescarga){
        return repositoryService.persist(new CargaDiaria(this,
                                                        horometro,
                                                        rpm,
                                                        presionAceite,
                                                        temperaturaAceite,
                                                        temperaturaAgua,
                                                        temperaturaSuccion1,
                                                        presionSuccion1,
                                                        temperaturaSuccion2,
                                                        presionSuccion2,
                                                        temperaturaSuccion3,
                                                        presionSuccion3,
                                                        presionDescarga));
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
    public Equipo editarEquipo(
            final @ParameterLayout(named="TAG") String denominacion) {
        setDenominacion(denominacion);
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