package domainapp.modules.simple.dom.motor;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.MotorMenu",
        repositoryFor = Motor.class
)
@DomainServiceLayout(
        named = "Motores", //TITULO DE LEYENDA
        menuOrder = "4"
)
public class MotorRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Motor> listAll() {
        return repositoryService.allInstances(Motor.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<MotorRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
