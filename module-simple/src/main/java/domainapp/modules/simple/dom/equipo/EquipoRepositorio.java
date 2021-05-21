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
        menuOrder = "10"
)
public class EquipoRepositorio {

    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1")
    public Equipo create(
            @ParameterLayout(named="Denominacion")
            final String denominacion){
        return repositoryService.persist(new Equipo(denominacion));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Equipo> listAll() {
        return repositoryService.allInstances(Equipo.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<EquipoRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
