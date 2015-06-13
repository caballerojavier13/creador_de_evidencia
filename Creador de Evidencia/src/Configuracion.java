
import java.text.SimpleDateFormat;
import java.util.Calendar;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier
 */
public class Configuracion {
    
    private String Titulo;
    private String Date;
    private String TestResult;
    
    private Configuracion() {
        Titulo = "Evidencia de Ejecución";
        Date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        TestResult = "";
    }
    
    public static Configuracion getInstance() {
        return ConfiguracionHolder.INSTANCE;
    }
    
    private static class ConfiguracionHolder {

        private static final Configuracion INSTANCE = new Configuracion();
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getTestResult() {
        return TestResult;
    }

    public void setTestResult(String TestResult) {
        this.TestResult = TestResult;
    }
    
        
    
    
}
