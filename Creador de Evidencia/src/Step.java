
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author javier
 */
public class Step {
    private List<String> pasos;
    private List<String> resultados;

    public Step() {
        pasos = new ArrayList();
        resultados = new ArrayList();
    }

    public Step(List<String> pasos) {
        this.pasos = pasos;
    }

    public List<String> getPasos() {
        return pasos;
    }

    public void addPaso(String paso) {
        this.pasos.add(paso);
    }
    
    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public List<String> getResultados() {
        return resultados;
    }

    public void addResultado(String resultado) {
        this.resultados.add(resultado);
    }
        
    public void setResultados(List<String> resultados) {
        this.resultados = resultados;
    }
}
