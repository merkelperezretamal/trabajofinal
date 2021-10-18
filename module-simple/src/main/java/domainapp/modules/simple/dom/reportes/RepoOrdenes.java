package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoOrdenes {

    @Getter @Setter
    private String numeroOrden;
    @Getter @Setter
    private String mantenimiento;

    public RepoOrdenes(String numeroOrden, String mantenimiento) {
        this.numeroOrden = numeroOrden;
        this.mantenimiento = mantenimiento;
    }

    public RepoOrdenes() {}

}
