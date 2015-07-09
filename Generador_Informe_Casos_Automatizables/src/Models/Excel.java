
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.JaxbXmlPart;
import org.docx4j.openpackaging.parts.SpreadsheetML.SharedStrings;
import org.docx4j.openpackaging.parts.SpreadsheetML.WorksheetPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.samples.PartsList;
import org.xlsx4j.sml.Cell;
import org.xlsx4j.sml.Row;
import org.xlsx4j.sml.STCellType;
import org.xlsx4j.sml.SheetData;
import org.xlsx4j.sml.Worksheet;

public class Excel {

    private Logger log = LoggerFactory.getLogger(PartsList.class);

    private List<WorksheetPart> worksheets = new ArrayList<WorksheetPart>();

    private SharedStrings sharedStrings = null;

    private int Num_Col_Nombre;
    private int Num_Col_Step;
    private int Num_Col_Descripcion;

    public Excel(File file, int Num_Col_Nombre, int Num_Col_Step, int Num_Col_Descripcion) {
        this.Num_Col_Nombre = Num_Col_Nombre;
        this.Num_Col_Step = Num_Col_Step;
        this.Num_Col_Descripcion = Num_Col_Descripcion;
        try {
            org.docx4j.openpackaging.packages.OpcPackage xlsxPkg = org.docx4j.openpackaging.packages.OpcPackage.load(file);

            // List the parts by walking the rels tree
            RelationshipsPart rp = xlsxPkg.getRelationshipsPart();
            StringBuilder sb = new StringBuilder();
            printInfo(rp, sb, "");
            traverseRelationships(xlsxPkg, rp, sb, "    ");
        } catch (Docx4JException ex) {

        }
    }

    public List<Caso_de_Prueba> getCasos_de_Prueba() {
        List<Caso_de_Prueba> resultado = new ArrayList();
        Worksheet ws = worksheets.get(worksheets.size()-1).getJaxbElement();
        SheetData data = ws.getSheetData();
        Caso_de_Prueba cp = null;
        List<Row> row = data.getRow();
        for (Row r : row) {
            try {
                String data_cell = getData(r.getC().get(this.Num_Col_Step));
                if (data_cell.equalsIgnoreCase("Step 1")) {
                    if (cp != null) {
                        resultado.add(cp);
                    }
                    cp = new Caso_de_Prueba();
                    cp.setNombre(getData(r.getC().get(Num_Col_Nombre)));
                    cp.addStep(getStep(r.getC().get(Num_Col_Descripcion)));

                } else {
                    cp.addStep(getStep(r.getC().get(Num_Col_Descripcion)));
                }
            } catch (java.lang.NullPointerException e) {
                
            }
            
        }
        resultado.add(cp);

        return resultado;
    }

    public void MostrarDatos() {
        // Now lets print the cell content
        for (WorksheetPart sheet : worksheets) {
            Worksheet ws = sheet.getJaxbElement();
            SheetData data = ws.getSheetData();
            for (Row r : data.getRow().subList(1, data.getRow().size())) {
                System.out.println("row " + r.getR());
                for (Cell c : r.getC()) {
                    if (c.getT().equals(STCellType.S)) {
                        try {
                            System.out.println("  " + c.getR() + " contains "
                                    + sharedStrings.getJaxbElement().getSi().get(Integer.parseInt(c.getV())).getT().getValue()
                            );
                        } catch (java.lang.NullPointerException e) {

                        }
                    } else {
                        // TODO: handle other cell types
                        System.out.println("  " + c.getR() + " contains " + c.getV());
                    }
                }
            }
        }
    }

    private String getData(Cell c) {
        String resultado = "";
        if (c.getT().equals(STCellType.S)) {
            try {
                resultado = sharedStrings.getJaxbElement().getSi().get(Integer.parseInt(c.getV())).getT().getValue();
            } catch (java.lang.NullPointerException e) {

            }
        } else {
            resultado = c.getV();
        }
        return resultado;
    }

    private Step getStep(Cell c) {
        Step resultado = new Step();
        if (c.getT().equals(STCellType.S)) {
            try {
                String[] split = sharedStrings.getJaxbElement().getSi().get(Integer.parseInt(c.getV())).getT().getValue().split("\n");
                for (int i = 0; i < split.length; i++) {
                    resultado.addPaso(split[i]);
                }
            } catch (java.lang.NullPointerException e) {

            }
        } else {
            resultado.addPaso(c.getV());
        }
        return resultado;
    }

    private void printInfo(Part p, StringBuilder sb, String indent) {
        sb.append("\n").append(indent).append("Part ").append(p.getPartName()).append(" [").append(p.getClass().getName()).append("] ");
        if (p instanceof JaxbXmlPart) {
            Object o = ((JaxbXmlPart) p).getJaxbElement();
            if (o instanceof javax.xml.bind.JAXBElement) {
                sb.append(" containing JaxbElement:").append(XmlUtils.JAXBElementDebug((JAXBElement) o));
            } else {
                sb.append(" containing JaxbElement:").append(o.getClass().getName());
            }
        }
        if (p instanceof WorksheetPart) {
            worksheets.add((WorksheetPart) p);
        } else if (p instanceof SharedStrings) {
            sharedStrings = (SharedStrings) p;
        }

    }

    /**
     * This HashMap is intended to prevent loops.
     */
    private static final HashMap<Part, Part> handled = new HashMap<>();

    private void traverseRelationships(org.docx4j.openpackaging.packages.OpcPackage wordMLPackage,
            RelationshipsPart rp,
            StringBuilder sb, String indent) {

        // TODO: order by rel id
        for (Relationship r : rp.getRelationships().getRelationship()) {

            log.info("For Relationship Id=" + r.getId()
                    + " Source is " + rp.getSourceP().getPartName()
                    + ", Target is " + r.getTarget());

            if (r.getTargetMode() != null
                    && r.getTargetMode().equals("External")) {

                sb.append("\n").append(indent).append("external resource ").append(r.getTarget()).append(" of type ").append(r.getType());
                continue;
            }

            Part part = rp.getPart(r);

            printInfo(part, sb, indent);
            if (handled.get(part) != null) {
                sb.append(" [additional reference] ");
                continue;
            }
            handled.put(part, part);
            if (part.getRelationshipsPart() == null) {
                // sb.append(".. no rels" );						
            } else {
                traverseRelationships(wordMLPackage, part.getRelationshipsPart(), sb, indent + "    ");
            }

        }

    }
    public static List<String> getAlfabeto(){
        List<String> resultado = new ArrayList();
        String toCharArray = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
        for(int i = 0; i< toCharArray.length(); i++){
            resultado.add(toCharArray.substring(i, i+1));
        }
        return resultado;
    }
    
    public static int getPosition(char letra){
        return (int)letra - 'A' + 0;
    }
}
