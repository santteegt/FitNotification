package com.fitnotif.parser;

import com.fitnotif.common.NotificationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Clase que permite parsear archivos XML de tipo Notification
 * @author malgia
 * @version 1.0
 */
public class NotificationParser extends XMLParser{
    
    
    /**
     * Constructor de la clase
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public NotificationParser() throws FileNotFoundException, IOException{
            File test=new File("/home/malgia/XML");
            InputStream pIn=new FileInputStream(test);
            String xml=readStream(pIn);
        try {
            this.parser.parse(new InputSource(new StringReader(xml)));
            this.document = this.parser.getDocument();
        } catch (SAXException ex) {
            throw new NotificationException("ERRPRS", "NO SE PUDO PARSEAR EL DOCUMENTO", ex);
        } 
    }
    
    public NotificationParser(String xml) throws IOException{
        super(xml);
    }
}
