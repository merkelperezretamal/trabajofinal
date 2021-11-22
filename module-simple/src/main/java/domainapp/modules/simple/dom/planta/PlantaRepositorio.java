package domainapp.modules.simple.dom.planta;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.impl.QSimpleObject;
import domainapp.modules.simple.dom.impl.SimpleObject;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import javax.swing.*;
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
    public Planta crear(
            @ParameterLayout(named="Nombre")
            final String nombre,
            @ParameterLayout(named="Provincia")
            final String provincia,
            @ParameterLayout(named="Cliente")
            final String cliente){

        List<Planta> listaPlantas = buscarPorNombre(nombre);

        if(listaPlantas.isEmpty()){
            return repositoryService.persist(new Planta(nombre, provincia, cliente));
        }else{
            JOptionPane.showMessageDialog(null, "Ya existe una Planta con ese nombre");
            JOptionPane.showMessageDialog(null, "Redirigiendote a la Planta existente");
            return listaPlantas.get(0);
        }
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "2")
    public List<Planta> listarTodas() {
        return repositoryService.allInstances(Planta.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "3")
    public List<Planta> buscarPorNombre(
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
