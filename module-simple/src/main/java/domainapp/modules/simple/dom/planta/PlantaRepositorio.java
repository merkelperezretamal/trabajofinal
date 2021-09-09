package domainapp.modules.simple.dom.planta;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.impl.QSimpleObject;
import domainapp.modules.simple.dom.impl.SimpleObject;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.PlantaMenu",
        repositoryFor = Planta.class
)
@DomainServiceLayout(
        named = "Plantas", //TITULO DE LEYENDA
        menuOrder = "1"
)
public class PlantaRepositorio {

    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "1")
    public Planta create(
            @ParameterLayout(named="Nombre")
            final String nombre,
            @ParameterLayout(named="Provincia")
            final String provincia,
            @ParameterLayout(named="Cliente")
            final String cliente){
            return repositoryService.persist(new Planta(nombre, provincia, cliente));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Planta> listAll() {
        return repositoryService.allInstances(Planta.class);
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<Planta> findByName(
            @ParameterLayout(named="Nombre")
            final String nombre
    ) {
        TypesafeQuery<Planta> q = isisJdoSupport.newTypesafeQuery(Planta.class);
        final QPlanta cand = QPlanta.candidate();
        q = q.filter(
                cand.nombre.indexOf(q.stringParameter("nombre")).ne(-1)
        );
        return q.setParameter("nombre", nombre)
                .executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<PlantaRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
