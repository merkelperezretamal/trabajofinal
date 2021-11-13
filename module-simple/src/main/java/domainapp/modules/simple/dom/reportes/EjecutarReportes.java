package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.cargadiaria.CargaDiaria;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.orden.Orden;
import domainapp.modules.simple.dom.planta.Planta;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSourceProvider;
import net.sf.jasperreports.engine.data.JRAbstractTextDataSource;
import org.apache.isis.applib.value.Blob;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EjecutarReportes {

    public Blob ListadoEquiposPDF(List<Equipo> equipos) throws JRException, IOException {

        List<RepoEquipos> repoEquipos = new ArrayList<RepoEquipos>();
        repoEquipos.add(new RepoEquipos());

        for (Equipo equipo : equipos) {
            RepoEquipos repoEquipo = new RepoEquipos(equipo.RepoDenominacion(), equipo.RepoPlanta(), equipo.RepoMotor(), equipo.RepoCompresor(), equipo.RepoActivo());
            repoEquipos.add(repoEquipo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoEquipos);
        return GenerarArchivoPDF("DetalleEquipos.jrxml", "Listado de Equipos.pdf", ds);
    }

    public Blob ListadoCargasDiariasPDF(List<CargaDiaria> cargasDiarias) throws JRException, IOException {
        List<RepoCargasDiarias> repoCargasDiarias = new ArrayList<RepoCargasDiarias>();
        repoCargasDiarias.add(new RepoCargasDiarias());

        for (CargaDiaria cargaDiaria : cargasDiarias) {
            RepoCargasDiarias repoCargaDiaria = new RepoCargasDiarias(cargaDiaria.RepoFecha(), cargaDiaria.RepoHorometro(), cargaDiaria.RepoEquipo(), cargaDiaria.RepoRpm(), cargaDiaria.RepoPresionAceite(), cargaDiaria.RepoTemperaturaAceite(), cargaDiaria.RepoTemperaturaAgua(), cargaDiaria.RepoTemperaturaSuccion1(), cargaDiaria.RepoTemperaturaSuccion2(), cargaDiaria.RepoTemperaturaSuccion3(), cargaDiaria.RepoPresionSuccion1(), cargaDiaria.RepoPresionSuccion2(), cargaDiaria.RepoPresionSuccion3(), cargaDiaria.RepoPresionDescarga());
            repoCargasDiarias.add(repoCargaDiaria);
        }


        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoCargasDiarias);
        return GenerarArchivoPDF("DetalleCargasDiarias.jrxml", "Listado de Cargas Diarias.pdf", ds);
    }

    public Blob ListadoOrdenesPDF(List<Orden> ordenes) throws JRException, IOException {

        List<RepoOrdenes> repoOrdenes = new ArrayList<RepoOrdenes>();
        repoOrdenes.add(new RepoOrdenes());

        for (Orden orden : ordenes) {
            RepoOrdenes repoOrden = new RepoOrdenes(orden.RepoNumero(), orden.RepoFecha(), orden.RepoMantenimiento(), orden.RepoMotorCompresor());
            repoOrdenes.add(repoOrden);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoOrdenes);
        return GenerarArchivoPDF("DetalleOrdenes.jrxml", "Listado de Ordenes.pdf", ds);
    }

    private Blob GenerarArchivoPDF(String archivoDesing, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException{

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesing);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return new Blob(nombreSalida, "application/pdf", contentBytes);
    }

}
