/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toren.xslingredientparsing;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamSource;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import javax.xml.transform.stream.StreamResult;
/**
 *
 * @author freda
 */
public class MapXmlData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            File f = new File("./resources/nutr_data.xml");
            File xsl = new File("./resources/remap.xsl");
            final FileInputStream map = new FileInputStream("resources/map.xml");
            fis = new FileInputStream( f );
            //Class.forName(net.sf.saxon.TransformerFactoryImpl);
            TransformerFactory factory = SAXTransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
            
            XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(new FileInputStream(xsl));
             FileInputStream reader2 = new FileInputStream( f );
             JsonXMLConfig config = new JsonXMLConfigBuilder()
            .autoArray(true)
            .autoPrimitive(true)
            .prettyPrint(true)
            .build();
            //XMLStreamWriter w = new JsonXMLOutputFactory(config).createXMLStreamWriter(new StreamResult( new FileWriter( "result.xml")));
            //Result result = new StAXResult(writer);
            StreamResult result = new StreamResult(new FileWriter( "result.xml"));
            Transformer transformer;
            transformer = factory.newTransformer(new StreamSource( new FileInputStream(xsl)));
            transformer.setURIResolver( new URIResolver() {

                @Override
                public Source resolve(String href, String base) throws TransformerException {
                    if ( href.equals("map.xml") ) {
                        try {
                            return new StreamSource( map );
                        }
                        finally {
                            //
                        }
                    }
                    else 
                        throw new TransformerException( "no file by that name");
                }
            
        });
            transformer.transform(new StreamSource(reader2), result);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MapXmlData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(MapXmlData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(MapXmlData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(MapXmlData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MapXmlData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fis != null)
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(MapXmlData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
