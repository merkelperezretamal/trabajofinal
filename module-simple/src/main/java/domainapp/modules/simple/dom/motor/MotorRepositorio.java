package domainapp.modules.simple.dom.motor;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.equipo.QEquipo;
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
    public List<Motor> listarTodos() {
        return repositoryService.allInstances(Motor.class);
    }

//    @Action(semantics = SemanticsOf.SAFE)
//    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
//    @MemberOrder(sequence = "2")
//    public List<Motor> buscarPorTipo(
//            @ParameterLayout(named="Tipo de Modelo")
//            final ETipoModelo tipoModelo) {
//        TypesafeQuery<Motor> q = isisJdoSupport.newTypesafeQuery(Motor.class);
//        final QMotor cand = QMotor.candidate();
//        q = q.filter(
//                cand.tipoModelo.eq(q.stringParameter("tipoModelo"))
//        );
//        return q.setParameter("tipoModelo", tipoModelo)
//                .executeList();
//    }

    public static class CreateDomainEvent extends ActionDomainEvent<MotorRepositorio> {}

//    @Action()
//    public Blob exportarDetalle(Motor motor) throws JRException, IOException {
//        EjecutarReportes ejecutarReportes = new EjecutarReportes();
//        return ejecutarReportes.ListadoMotorPDF(motor);
//    }

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

}
