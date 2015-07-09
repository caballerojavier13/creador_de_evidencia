
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
    
    private int nomb_cu;
    private int nomb_cp;
    private int desc_cp;
    private int nomb_step;
    private int desc_step;
    private int resul_step;
    
    private int tam_nombre_corto;
    
    
    private Configuracion() {
        Titulo = "Evidencia de Ejecución";
        Date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        TestResult = "Passed";
        
        nomb_cu = 1;
        nomb_cp = 3;
        desc_cp = 4;
        nomb_step = 7;
        desc_step = 8;
        resul_step = 9;
        
        this.tam_nombre_corto = 17;
        
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

    public int getNomb_cu() {
        return nomb_cu;
    }

    public void setNomb_cu(int nomb_cu) {
        this.nomb_cu = nomb_cu;
    }

    public int getNomb_cp() {
        return nomb_cp;
    }

    public void setNomb_cp(int nomb_cp) {
        this.nomb_cp = nomb_cp;
    }

    public int getDesc_cp() {
        return desc_cp;
    }

    public void setDesc_cp(int desc_cp) {
        this.desc_cp = desc_cp;
    }

    public int getNomb_step() {
        return nomb_step;
    }

    public void setNomb_step(int nomb_step) {
        this.nomb_step = nomb_step;
    }

    public int getDesc_step() {
        return desc_step;
    }

    public void setDesc_step(int desc_step) {
        this.desc_step = desc_step;
    }

    public int getResul_step() {
        return resul_step;
    }

    public void setResul_step(int resul_step) {
        this.resul_step = resul_step;
    }

    public int getTam_nombre_corto() {
        return tam_nombre_corto;
    }

    public void setTam_nombre_corto(int tam_nombre_corto) {
        this.tam_nombre_corto = tam_nombre_corto;
    }
    
}
