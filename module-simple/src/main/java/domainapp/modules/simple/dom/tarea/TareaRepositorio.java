package domainapp.modules.simple.dom.tarea;

import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.motor.Motor;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.TareaMenu",
        repositoryFor = Tarea.class
)
@DomainServiceLayout(
        named = "Tareas", //TITULO DE LEYENDA
        menuOrder = "8"
)
public class TareaRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Tarea> listAll() {
        return repositoryService.allInstances(Tarea.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<TareaRepositorio> {}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;
}
