package domainapp.modules.simple.dom.reportes;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.planta.Planta;
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
            RepoEquipos repoEquipo = new RepoEquipos(equipo.RepoDenominacion(), equipo.RepoPlanta(), equipo.RepoMotor(), equipo.RepoCompresor());
            repoEquipos.add(repoEquipo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(repoEquipos);
        return GenerarArchivoPDF("DetalleEquipos.jrxml", "Listado de Equipos.pdf", ds);
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
