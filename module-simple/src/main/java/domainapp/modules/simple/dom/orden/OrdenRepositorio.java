package domainapp.modules.simple.dom.orden;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.motor.ETipoModelo;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.motor.QMotor;
import domainapp.modules.simple.dom.reportes.EjecutarReportes;
import net.sf.jasperreports.engine.JRException;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.io.IOException;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.OrdenMenu",
        repositoryFor = Orden.class
)
@DomainServiceLayout(
        named = "Ordenes", //TITULO DE LEYENDA
        menuOrder = "6"
)
public class OrdenRepositorio {

    @Action(domainEvent = OrdenRepositorio.CreateDomainEvent.class)
    @MemberOrder(sequence = "1")
    public Orden crear(
            @ParameterLayout(named="Numero")
            final int numeroOrden,
            @ParameterLayout(named="Tipo de Mantenimiento")
            final String tipoMantenimiento,
            @ParameterLayout(named="Motor")
            final Motor motor){
        return repositoryService.persist(new Orden(numeroOrden, tipoMantenimiento, motor));
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Orden> listarTodas() {
        return repositoryService.allInstances(Orden.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<OrdenRepositorio> {}

    @Action()
    @ActionLayout(named = "Listado Exportado")
    public Blob exportarListado() throws JRException, IOException {
        EjecutarReportes ejecutarReportes = new EjecutarReportes();
        return ejecutarReportes.ListadoOrdenesPDF(repositoryService.allInstances(Orden.class));
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
