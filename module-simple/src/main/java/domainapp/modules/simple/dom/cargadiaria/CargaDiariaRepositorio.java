package domainapp.modules.simple.dom.cargadiaria;

import domainapp.modules.simple.dom.reportes.EjecutarReportes;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.value.Blob;
import org.datanucleus.query.typesafe.TypesafeQuery;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
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

        @Action(semantics = SemanticsOf.SAFE)
        @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
        @MemberOrder(sequence = "2")
        public List<CargaDiaria> buscarPorEquipo(
                @ParameterLayout(named="Denominacion")
                final String denominacion) {
                TypesafeQuery<CargaDiaria> q = isisJdoSupport.newTypesafeQuery(CargaDiaria.class);
                final QCargaDiaria cand = QCargaDiaria.candidate();
                q = q.filter(
                        cand.equipo.denominacion.indexOf(q.stringParameter("denominacion")).ne(-1)
                );
                return q.setParameter("denominacion", denominacion)
                        .executeList();
        }

        @Action()
        @ActionLayout(named = "Listado Exportado")
        public Blob exportarListado(String denominacion) throws JRException, IOException {
                EjecutarReportes ejecutarReportes = new EjecutarReportes();
                List<CargaDiaria> cargasDiarias = buscarPorEquipo(denominacion);
                return ejecutarReportes.ListadoCargasDiariasPDF(cargasDiarias);
        }

        @Action()
        @ActionLayout(named = "Listado Exportado")
        public Blob exportarListado() throws JRException, IOException {
                EjecutarReportes ejecutarReportes = new EjecutarReportes();
                return ejecutarReportes.ListadoCargasDiariasPDF(repositoryService.allInstances(CargaDiaria.class));
        }

        @javax.inject.Inject
        RepositoryService repositoryService;

        @javax.inject.Inject
        IsisJdoSupport isisJdoSupport;


}
