package domainapp.modules.simple.dom.reportes;

import lombok.Getter;
import lombok.Setter;

public class RepoOrdenes {

    @Getter @Setter
    private String numeroOrden;
    @Getter @Setter
    private String fecha;
    @Getter @Setter
    private String mantenimiento;
    @Getter @Setter
    private String motorCompresor;

    public RepoOrdenes(String numeroOrden, String fecha, String mantenimiento, String motorCompresor) {
        this.numeroOrden = numeroOrden;
        this.fecha = fecha;
        this.mantenimiento = mantenimiento;
        this.motorCompresor = motorCompresor;
    }

    public RepoOrdenes() {}

}
