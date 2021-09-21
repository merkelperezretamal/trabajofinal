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

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.impl.SimpleObjects;
import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.planta.QPlanta;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.EquipoMenu",
        repositoryFor = Equipo.class
)
@DomainServiceLayout(
        named = "Equipos", //TITULO DE LEYENDA
        menuOrder = "2"
)
public class EquipoRepositorio {

    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1")
    public Equipo crear(
            @ParameterLayout(named="Denominacion")
            final String denominacion){
        return repositoryService.persist(new Equipo(denominacion));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Equipo> listarTodos() {
        return repositoryService.allInstances(Equipo.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "5")
    public List<Equipo> buscarPorPlanta(
            @ParameterLayout(named="Planta")
            final String nombrePlanta) {
        TypesafeQuery<Equipo> q = isisJdoSupport.newTypesafeQuery(Equipo.class);
        final QEquipo cand = QEquipo.candidate();
        q = q.filter(
                cand.planta.nombre.indexOf(q.stringParameter("nombrePlanta")).ne(-1)
        );
        return q.setParameter("nombrePlanta", nombrePlanta)
                .executeList();
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "4")
    public List<Equipo> buscarPorDenominacion(
            @ParameterLayout(named="Denominacion")
            final String denominacion) {
        TypesafeQuery<Equipo> q = isisJdoSupport.newTypesafeQuery(Equipo.class);
        final QEquipo cand = QEquipo.candidate();
        q = q.filter(
                cand.denominacion.indexOf(q.stringParameter("denominacion")).ne(-1)
        );
        return q.setParameter("denominacion", denominacion)
                .executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<EquipoRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
