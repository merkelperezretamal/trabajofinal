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
import lombok.AccessLevel;
import lombok.Getter;
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

    @Action(semantics = NON_IDEMPOTENT_ARE_YOU_SURE)
    public void borrar() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.remove(this);
    }

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
    public Motor nuevoMotor(final String tag) {
        return repositoryService.persist(new Motor(this, tag));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public Compresor nuevoCompresor(final String tag,
                                    final String marca,
                                    final String modelo,
                                    final String frame,
                                    final String cylinder1,
                                    final String cylinder2,
                                    final String cylinder3,
                                    final String cylinder4) {
        return repositoryService.persist(new Compresor(this,tag,marca,modelo,frame,cylinder1,cylinder2,cylinder3,cylinder4));
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    public CargaDiaria nuevaCargaDiaria(final String codigo,
                                        final double horometro,
                                        double porcentajeDisponibilidad,
                                        double rpm,
                                        double presionAceite,
                                        double temperaturaAceite,
                                        double temperaturaAgua,
                                        double temperaturaSuccion1,
                                        double presionSuccion1,
                                        double temperaturaSuccion2,
                                        double presionSuccion2,
                                        double temperaturaSuccion3,
                                        double presionSuccion3,
                                        double presionDescarga,
                                        double caudalDiario) {
        return repositoryService.persist(new CargaDiaria(this,
                                                        codigo,
                                                        horometro,
                                                        porcentajeDisponibilidad,
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