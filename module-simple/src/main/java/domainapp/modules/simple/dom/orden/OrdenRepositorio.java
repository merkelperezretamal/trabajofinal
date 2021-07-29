package domainapp.modules.simple.dom.orden;

import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.OrdenMenu",
        repositoryFor = Orden.class
)
@DomainServiceLayout(
        named = "Ordenes", //TITULO DE LEYENDA
        menuOrder = "10"
)
public class OrdenRepositorio {

//    @Action(domainEvent = OrdenRepositorio.CreateDomainEvent.class)
//    @MemberOrder(sequence = "1")
//    public Orden create(
//            @ParameterLayout(named="Numero")
//            final int numero){
//        return repositoryService.persist(new Orden(numero));
//    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Orden> listAll() {
        return repositoryService.allInstances(Orden.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<OrdenRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
