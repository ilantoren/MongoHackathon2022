/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toren.xslingredientparsing;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author freda
 */
@XmlRootElement (name = "Data")
public class IngredientFile {
    @XmlElement
    IngredientPOJO[] ingredient;
    
}
