package domainapp.modules.simple.dom.cargadiaria;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.List;
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.CargaDiariaMenu",
        repositoryFor = CargaDiaria.class
)
@DomainServiceLayout(
        named = "Cargas Diarias", //TITULO DE LEYENDA
        menuOrder = "3"
)
public class CargaDiariaRepositorio {

        @Action(semantics = SemanticsOf.SAFE)
        @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
        @MemberOrder(sequence = "1")
        public List<CargaDiaria> listarTodas() {
            return repositoryService.allInstances(CargaDiaria.class);
        }

        public static class CreateDomainEvent extends ActionDomainEvent<CargaDiariaRepositorio> {}

        @javax.inject.Inject
        RepositoryService repositoryService;

        @javax.inject.Inject
        IsisJdoSupport isisJdoSupport;


}
