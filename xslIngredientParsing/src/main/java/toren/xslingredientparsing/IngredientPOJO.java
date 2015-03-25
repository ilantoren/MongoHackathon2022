/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toren.xslingredientparsing;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author freda
 */
 @XmlRootElement(name = "Ingredient")
public class IngredientPOJO {
     @Override
     public String toString() {
         return id + "  " + desc;
     }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public void setAdj_protein(String adj_protein) {
        this.adj_protein = adj_protein;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public void setCaffeine(String caffeine) {
        this.caffeine = caffeine;
    }

    public void setCalcium(String calcium) {
        this.calcium = calcium;
    }

    public void setCarbohydrate(String carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public void setChoFact(String choFact) {
        this.choFact = choFact;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFatFact(String fatFact) {
        this.fatFact = fatFact;
    }

    public void setFgId(String fgId) {
        this.fgId = fgId;
    }

    public void setFiber(String fiber) {
        this.fiber = fiber;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public void setGlucose(String glucose) {
        this.glucose = glucose;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIron(String iron) {
        this.iron = iron;
    }

    public void setLactose(String lactose) {
        this.lactose = lactose;
    }

    public void setMagnesium(String magnesium) {
        this.magnesium = magnesium;
    }

    public void setMonoFat(String monoFat) {
        this.monoFat = monoFat;
    }

    public void setNfactor(String nfactor) {
        this.nfactor = nfactor;
    }

    public void setPolyFat(String polyFat) {
        this.polyFat = polyFat;
    }

    public void setProFact(String proFact) {
        this.proFact = proFact;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public void setSatFat(String satFat) {
        this.satFat = satFat;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public void setSucrose(String sucrose) {
        this.sucrose = sucrose;
    }

    public void setSugars(String sugars) {
        this.sugars = sugars;
    }

    public void setTheobromine(String theobromine) {
        this.theobromine = theobromine;
    }

    public void setTransFat(String transFat) {
        this.transFat = transFat;
    }

    public void setVitA(String vitA) {
        this.vitA = vitA;
    }

    public void setVitC(String vitC) {
        this.vitC = vitC;
    }

    public void setVitD(String vitD) {
        this.vitD = vitD;
    }

    public String getEnergy() {
        return energy;
    }

    public String getAdj_protein() {
        return adj_protein;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public String getCaffeine() {
        return caffeine;
    }

    public String getCalcium() {
        return calcium;
    }

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public String getChoFact() {
        return choFact;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public String getDesc() {
        return desc;
    }

    public String getFatFact() {
        return fatFact;
    }

    public String getFgId() {
        return fgId;
    }

    public String getFiber() {
        return fiber;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public String getGlucose() {
        return glucose;
    }

    public String getId() {
        return id;
    }

    public String getIron() {
        return iron;
    }

    public String getLactose() {
        return lactose;
    }

    public String getMagnesium() {
        return magnesium;
    }

    public String getMonoFat() {
        return monoFat;
    }

    public String getNfactor() {
        return nfactor;
    }

    public String getPolyFat() {
        return polyFat;
    }

    public String getProFact() {
        return proFact;
    }

    public String getProtein() {
        return protein;
    }

    public String getSatFat() {
        return satFat;
    }

    public String getSodium() {
        return sodium;
    }

    public String getSucrose() {
        return sucrose;
    }

    public String getSugars() {
        return sugars;
    }

    public String getTheobromine() {
        return theobromine;
    }

    public String getTransFat() {
        return transFat;
    }

    public String getVitA() {
        return vitA;
    }

    public String getVitC() {
        return vitC;
    }

    public String getVitD() {
        return vitD;
    }

    @XmlAttribute
    String energy;
    @XmlAttribute
    String adj_protein;
    @XmlAttribute
    String alcohol;
    @XmlAttribute
    String caffeine;
    @XmlAttribute
    String calcium;
    @XmlAttribute
    String carbohydrate;
    @XmlAttribute
    String choFact;
    @XmlAttribute
    String cholesterol;
    @XmlAttribute
    String desc;
    @XmlAttribute
    String fatFact;
    @XmlAttribute
    String fgId;
    @XmlAttribute
    String fiber;
    @XmlAttribute
    String foodGroup;
    @XmlAttribute
    String glucose;
    @XmlAttribute
    String id;
    @XmlAttribute
    String iron;
    @XmlAttribute
    String lactose;
    @XmlAttribute
    String magnesium;
    @XmlAttribute
    String monoFat;
    @XmlAttribute
    String nfactor;
    @XmlAttribute
    String polyFat;
    @XmlAttribute
    String proFact;
    @XmlAttribute
    String protein;
    @XmlAttribute
    String satFat;
    @XmlAttribute
    String sodium;
    @XmlAttribute
    String sucrose;
    @XmlAttribute
    String sugars;
    @XmlAttribute
    String theobromine;
    @XmlAttribute
    String transFat;
    @XmlAttribute
    String vitA;
    @XmlAttribute
    String vitC;
    @XmlAttribute
    String vitD;

}
