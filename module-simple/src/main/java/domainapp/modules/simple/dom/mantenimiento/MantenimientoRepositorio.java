package domainapp.modules.simple.dom.mantenimiento;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.MantenimientoMenu",
        repositoryFor = Mantenimiento.class
)
@DomainServiceLayout(
        named = "Mantenimientos", //TITULO DE LEYENDA
        menuOrder = "7"
)
public class MantenimientoRepositorio {

    @Action(domainEvent = MantenimientoRepositorio.CreateDomainEvent.class)
    @MemberOrder(sequence = "1")
    public Mantenimiento create(
            @ParameterLayout(named = "TipoMantenimiento") final ETipoMantenimiento tipoMantenimiento,
            @ParameterLayout(named = "Horas") final int horas) {
        return repositoryService.persist(new Mantenimiento(tipoMantenimiento, horas));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Mantenimiento> listAll() {
        return repositoryService.allInstances(Mantenimiento.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<MantenimientoRepositorio> {
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;



}