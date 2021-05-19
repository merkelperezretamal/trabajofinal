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
import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.motor.Motor;
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

import static org.apache.isis.applib.annotation.CommandReification.ENABLED;
import static org.apache.isis.applib.annotation.SemanticsOf.IDEMPOTENT;
import static org.apache.isis.applib.annotation.SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE, schema = "simple")
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column="id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column="version")
@javax.jdo.annotations.Unique(name="Equipo_denominacion_UNQ", members = {"denominacion"})
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

 /*   @javax.jdo.annotations.Column(allowsNull = "false")
//    @Property(hidden = Where.EVERYWHERE) //Oculta la propiedad (para que no se vea cuando se actualiza por ejemplo)
    @Getter @Setter
    private double horometro;

    @javax.jdo.annotations.Column(allowsNull = "true")
    @Getter @Setter
    private double porcentajeDisponibilidad;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double rpm;

    @javax.jdo.annotations.Column(allowsNull = "false")
    @Getter @Setter
    private double presionAceite;

    @javax.jdo.annotations.Column(allowsNull = "true", length = 4000)
    @Property(editing = Editing.ENABLED)
    private String notes;*/

    @Persistent(
            mappedBy = "equipo",
            dependentElement = "true"
    )
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private Motor motor;

    @Persistent(
            mappedBy = "equipo",
            dependentElement = "true"
    )
    @Getter @Setter
    @javax.jdo.annotations.Column(allowsNull="true")
    private Compresor compresor;

  /*  public Equipo(final String denominacion) {
        this.denominacion = denominacion;

    }

    @Action(semantics = IDEMPOTENT, command = CommandReification.ENABLED, publishing = Publishing.ENABLED, associateWith = "horometro")
    public Equipo actualizarHorometro(
            @Parameter(maxLength = 40)
            @ParameterLayout(named = "Horometro") //teniamos "Name"
            final double horometro) {
        setHorometro(horometro);
        return this;
    }
*/
    /* En primer proyecto lo borramos, en multimodulo hacia que tire error
    public double default0UpdateHorometro() {
        return getHorometro();
    } */

   /* public TranslatableString validate0UpdateHorometro(final double horometro) {
        return horometro != null && horometro.contains("!") ? TranslatableString.tr("Exclamation mark is not allowed") : null;
    } */

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
    public Compresor nuevoCompresor(final String tag) {
        return repositoryService.persist(new Compresor(this, tag));
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