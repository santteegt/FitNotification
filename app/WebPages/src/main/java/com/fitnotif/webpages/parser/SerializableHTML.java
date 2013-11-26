package com.fitnotif.webpages.parser;
import java.io.Serializable;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * Interfaz para la generacion de contenido html
 * @author santiago
 * @version 1.0
 */
public interface SerializableHTML<T> extends Serializable {
    
    public void generateHTML(HTMLConstructor html)throws Exception;
    
    public Node getNode(Document document)throws Exception;
    
    public Collection<SerializableHTML<?>> getChildren();
    
}
