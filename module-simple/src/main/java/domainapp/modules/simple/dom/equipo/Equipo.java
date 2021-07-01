/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.dom.equipo;

import com.google.common.collect.ComparisonChain;
import domainapp.modules.simple.dom.cargadiaria.CargaDiaria;
import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.mantenimiento.ETipoMantenimiento;
import domainapp.modules.simple.dom.tarea.Tarea;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import java.util.SortedSet;
import java.util.TreeSet;

import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

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
    private SortedSet<CargaDiaria> cargasDiarias = new TreeSet<CargaDiaria>();

    @Persistent(mappedBy = "equipo", dependentElement = "true")
    @Collection()
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private SortedSet<Mantenimiento> mantenimientos = new TreeSet<Mantenimiento>();

    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="false")
    private boolean activo;
/*
    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }
*/
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
                            final @ParameterLayout (named="Modelo") String modelo,
                            final @ParameterLayout (named="Serial") String serial) {
        return repositoryService.persist(new Motor(this, tag, marca, modelo, serial));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Compresor nuevoCompresor(final @ParameterLayout (named="TAG") String tag,
                                    final @ParameterLayout (named="Marca") String marca,
                                    final @ParameterLayout (named="Modelo") String modelo,
                                    final @ParameterLayout (named="Frame") String frame,
                                    final @ParameterLayout (named="Cylinder 1") String cylinder1,
                                    final @ParameterLayout (named="Cylinder 2") String cylinder2,
                                    final @ParameterLayout (named="Cylinder 3") String cylinder3,
                                    final @ParameterLayout (named="Cylinder 4") String cylinder4) {
        return repositoryService.persist(new Compresor(this,tag,marca,modelo,frame,cylinder1,cylinder2,cylinder3,cylinder4));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public CargaDiaria nuevaCargaDiaria(final @ParameterLayout (named="Codigo") String codigo,
                                        final @ParameterLayout (named="Horometro") double horometro,
                                        @ParameterLayout (named="RPM") double rpm,
                                        @ParameterLayout (named="Presion Aceite") double presionAceite,
                                        @ParameterLayout (named="Temperatura Aceite") double temperaturaAceite,
                                        @ParameterLayout (named="Temperatura Agua") double temperaturaAgua,
                                        @ParameterLayout (named="Temperatura Succion 1") double temperaturaSuccion1,
                                        @ParameterLayout (named="Presion Succion 1") double presionSuccion1,
                                        @ParameterLayout (named="Temperatura Succion 2") double temperaturaSuccion2,
                                        @ParameterLayout (named="Presion Succion 2") double presionSuccion2,
                                        @ParameterLayout (named="Temperatura Succion 3") double temperaturaSuccion3,
                                        @ParameterLayout (named="Presion Succion 3") double presionSuccion3,
                                        @ParameterLayout (named="Presion Descarga") double presionDescarga,
                                        @ParameterLayout (named="Caudal Diario") double caudalDiario) {
        return repositoryService.persist(new CargaDiaria(this,
                                                        codigo,
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
                                                        presionDescarga,
                                                        caudalDiario));
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Mantenimiento nuevoMantenimiento(final @ParameterLayout (named="Tipo de Mtto") ETipoMantenimiento tipoMantenimiento,
                            final @ParameterLayout (named="Horas") int horas) {
        return repositoryService.persist(new Mantenimiento(tipoMantenimiento, horas, this));
    }

    @Action(semantics = SemanticsOf.IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED)
    public void cambioEstado() {
        setActivo(!this.activo);
    }

    @Programmatic
    public boolean default0CambioEstado() {
        return this.activo;
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


/*

    //En primer proyecto lo borramos, en multimodulo hacia que tire error
    public double default0UpdateHorometro() {
        return getHorometro();
    }

    public TranslatableString validate0UpdateHorometro(final double horometro) {
        return horometro != null && horometro.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    } */

}