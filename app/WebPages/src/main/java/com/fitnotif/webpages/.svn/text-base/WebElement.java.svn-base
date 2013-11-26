
package com.fitnotif.webpages;

import com.fitnotif.webpages.parser.HTMLConstructor;
import com.fitnotif.webpages.parser.SerializableHTML;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Objeto que representa un elemento web, con propiedades e hijos.
 * Esta es una implementacion del <b>Composite Pattern</b>
 * @author santiago
 * @version 1.0
 */
public class WebElement<T extends WebElement> extends ArrayList<T> implements SerializableHTML{
    
    private static final long serialVersionUID = 2L;
    
    protected final Map<String, Object> properties = new LinkedHashMap<String, Object>();
    
    private String tag;
    
    private WebElement parent;
    
    private String id;

    
    
    public WebElement(){
        
    }
    
    /**
     * Define una propiedad estatica del elemento
     * @param name
     * @param value
     * @throws Exception 
     */
    protected final void define(String name, Object value){
        properties.put(name, value);
        
    }
    
    /**
     * Define un valor que establece el xml que define el elemento
     * @param name
     * @param value
     * @throws Exception 
     */
    public void setAttrValue(String name, Object value)throws Exception{
        properties.put(name, value);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters y setters">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WebElement getParent() {
        return parent;
    }

    public void setParent(WebElement parent) {
        this.parent = parent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    //</editor-fold>
    
    @Override
    public synchronized boolean add(T elemento) {
        boolean res = super.add(elemento);

        if (res) {
            onAdd(elemento);
            //updateChildren();
        }

        return res;
    }
    
    /**
     * Aniade una coleccion de elementos hijos
     * @param c
     * @return 
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean res = super.addAll(c);

        if (res) {
            for (T elemento : c) {
                onAdd(elemento);
            }
            updateChildren();
        }

        return res;
    }
    
    /**
     * Actualiza los parámetros de los hijos.
     */
    public void updateChildren() {
    }
    
    /**
     * Establece el elemento padre para el elemento en cuestion
     * @param elemento 
     */
    private void onAdd(T elemento) {
        if (elemento == null) {
            return;
        }

        elemento.setParent(this);
    }

    /**
     * Paso para elimiar un elemento
     * @param elemento 
     */
    private void onRemove(T elemento) {
        if (elemento == null) {
            return;
        }

        elemento.setParent(null);
    }
    
    
    
    public Node getNode(Document document)throws Exception {
        Element element = null;//document.createElement(getTag());

        /*if (getAtributosXml() != null) {
            for (String attr : getAtributosXml()) {
                if (!properties.get(attr).esValorPorDefecto()) {
                    elemento.setAttribute(attr, properties.get(attr)
                            .getValorString());
                }
            }
        }

        if (getHijosXml() != null) {
            for (String hijo : getHijosXml()) {
                if (!properties.get(hijo).esValorPorDefecto()) {
                    Element interno = document.createElement(hijo);
                    interno.appendChild(document.createTextNode(properties.get(
                            hijo).getValorString()));
                }

            }
        }*/

        return element;
    }

    public void generateHTML(HTMLConstructor html) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<SerializableHTML<?>> getChildren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
