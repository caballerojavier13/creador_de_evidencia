
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
public class Caso_de_Prueba {
    
    private String Nombre;
    
    private List<Step> steps;

    public Caso_de_Prueba() {
        steps = new ArrayList();
    }

    public Caso_de_Prueba(String Nombre) {
        this.Nombre = Nombre;
        steps = new ArrayList();
    }

    public Caso_de_Prueba(String Nombre, List<Step> steps) {
        this.Nombre = Nombre;
        this.steps = steps;
    }
    

    /**
     * Get the value of steps
     *
     * @return the value of steps
     */
    public List<Step> getSteps() {
        return steps;
    }
    
    
    public void addStep(Step texto){
        steps.add(texto);
    }
    

    /**
     * Set the value of steps
     *
     * @param steps new value of steps
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }


    /**
     * Get the value of Nombre
     *
     * @return the value of Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Set the value of Nombre
     *
     * @param Nombre new value of Nombre
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return Nombre;
    }

}
