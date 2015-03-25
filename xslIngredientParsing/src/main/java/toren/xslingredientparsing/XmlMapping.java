/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toren.xslingredientparsing;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author freda
 */
public class XmlMapping {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            ObjectMapper xmlMapper = new XmlMapper();
            ObjectReader reader = xmlMapper.reader(IngredientFile.class);
            
            MappingIterator<IngredientPOJO> it = reader.readValues(new FileInputStream( "result.xml"));
            while( it.hasNext()) {
                System.out.println(  it.nextValue() );
            }
        } catch (IOException ex) {
            Logger.getLogger(XmlMapping.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
