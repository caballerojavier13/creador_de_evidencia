
import java.math.BigInteger;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.STBorder;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblBorders;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author javier
 */
public class Word {

    private String url;

    private String Nombre;

    private static WordprocessingMLPackage wordMLPackage;

    private static ObjectFactory factory;
    
    ;private int index_inicio_nombre_corto;

    public Word(String url, String Nombre, int index_inicio_nombre_corto) {
        this.index_inicio_nombre_corto = index_inicio_nombre_corto;
        try {
            this.url = url;
            this.Nombre = Nombre;
            wordMLPackage = WordprocessingMLPackage.createPackage();
            factory = Context.getWmlObjectFactory();
        } catch (InvalidFormatException ex) {
            
        }
    }

    public void AgregarEncabezado() {
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", Configuracion.getInstance().getTitulo());
        Tbl table = factory.createTbl();
        int anchoCelda1 = 2000;
        int anchoCelda2 = 8000;
        addBorders(table);

        Tr tr = factory.createTr();

        addTableCellWithWidth(tr, "Caso de Prueba", anchoCelda1);
        addTableCellWithWidth(tr, Nombre, anchoCelda2);

        table.getContent().add(tr);

        tr = factory.createTr();

        addTableCellWithWidth(tr, "Fecha", anchoCelda1);
        addTableCellWithWidth(tr, getFecha(), anchoCelda2);

        table.getContent().add(tr);

        tr = factory.createTr();

        addTableCellWithWidth(tr, "Resultado", anchoCelda1);
        addTableCellWithWidth(tr, Configuracion.getInstance().getTestResult() , anchoCelda2);

        table.getContent().add(tr);

        wordMLPackage.getMainDocumentPart().addObject(table);
    }

    public void AgregarStep(int numStep, Step s) {
        wordMLPackage.getMainDocumentPart().addParagraphOfText("");
        wordMLPackage.getMainDocumentPart().addParagraphOfText("Step" + (numStep + 1) + ":");
        wordMLPackage.getMainDocumentPart().addParagraphOfText("");
        for(int i=0; i< s.getPasos().size(); i++){
            wordMLPackage.getMainDocumentPart().addParagraphOfText(s.getPasos().get(i));
        }
        wordMLPackage.getMainDocumentPart().addParagraphOfText("");
    }

    public void Guardar() {
        try {
            wordMLPackage.save(new java.io.File(url + "/" + getNombreCorto() + ".docx"));
        } catch (Docx4JException ex) {

        }
    }

    /**
     * Get the value of Nombre
     *
     * @return the value of Nombre
     */
    public String getNombre() {
        return Nombre;
    }
    
    public String getNombreCorto(){
        return Nombre.substring(0, index_inicio_nombre_corto);
    }

    /**
     * Set the value of Nombre
     *
     * @param Nombre new value of Nombre
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * Get the value of url
     *
     * @return the value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the value of url
     *
     * @param url new value of url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * In this method we create a cell and add the given content to it. If the
     * given width is greater than 0, we set the width on the cell. Finally, we
     * add the cell to the row.
     */
    private static void addTableCellWithWidth(Tr row, String content, int width) {
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
                wordMLPackage.getMainDocumentPart().createParagraphOfText(
                        content));

        if (width > 0) {
            setCellWidth(tableCell, width);
        }
        row.getContent().add(tableCell);
    }

    /**
     * In this method we create a table cell properties object and a table width
     * object. We set the given width on the width object and then add it to the
     * properties object. Finally we set the properties on the table cell.
     */
    private static void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }

    /**
     * In this method we'll add the borders to the table.
     */
    private static void addBorders(Tbl table) {

        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
        border.setSz(new BigInteger("8"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);
        TblBorders borders = new TblBorders();

        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }
    
    private String getFecha(){
        return Configuracion.getInstance().getDate();
    }
}
