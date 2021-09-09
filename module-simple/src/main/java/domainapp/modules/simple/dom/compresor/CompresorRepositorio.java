package domainapp.modules.simple.dom.compresor;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.motor.Motor;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.CompresorMenu",
        repositoryFor = Compresor.class
)
@DomainServiceLayout(
        named = "Compresores", //TITULO DE LEYENDA
        menuOrder = "5"
)
public class CompresorRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Compresor> listAll() {
        return repositoryService.allInstances(Compresor.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<CompresorRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
