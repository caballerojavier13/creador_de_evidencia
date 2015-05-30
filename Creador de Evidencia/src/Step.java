
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

    public Step() {
        pasos = new ArrayList();
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

    
    
   
    
}
