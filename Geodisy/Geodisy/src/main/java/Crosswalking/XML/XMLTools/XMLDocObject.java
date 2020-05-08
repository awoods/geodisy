package Crosswalking.XML.XMLTools;


import BaseFiles.GeoLogger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Wrapper for the Document class so I can control it more easily
 * This Class generates the XMLDocument Object (not an XML file)
 */
public class XMLDocObject {
    Document doc;
    String doi;
    String pURL;
    GeoLogger logger = new GeoLogger(this.getClass());


    public XMLDocObject() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public Document getDoc(){
        return doc;
    }

    public Element createGMDElement(String s){
        try {
            return doc.createElement(addGMD(s));
        }catch (DOMException e){
            logger.error("There was an invalid/illegal character in string " + s);
        }
        return doc.createElement(addGMD("Junk_Dont_Use_This_XML"));
    }

    //May never get used
    public Element createGCOElement(String s){
        return doc.createElement(addGCO(s));
    }

    public Element create_Element(String s){
        return doc.createElement(s);
    }

    // GMD is either a description or parent element w/o a value
    private String addGMD(String s){
        return "gmd:" + s;

    }

    // GCO indicates generally a value rather than a description
    private String addGCO(String s) {
        return "gco:" + s;
    }

    //Adds the element at the lowest level of the hierarchy that holds the value
    public Element addGCOVal(String value, String label) {
        Element val = doc.createElement(addGCO(label));
        val.setTextContent(value);
        return val;
    }

    public Element addGMDVal(String value, String label) {
        Element val = doc.createElement(addGMD(label));
        val.setTextContent(value);
        return val;
    }

    public Element addRoleCode(String altTitleVal) {
        return addDescritiveTag("CI_RoleCode",altTitleVal);
    }

    public Element addDescription(String type){
        return addDescritiveTag("description",type);
    }

    public Element addDescritiveTag(String label, String value){
        Element val = doc.createElement((addGMD(label)));
        val.setTextContent(value);
        return val;
    }

    public void addRoot(Element root){
        doc.appendChild(root);
    }

    public String getDoi(){return doi;}

    public void setDoi(String doi){this.doi = doi;}

    public void setPURL(String pURL){this.pURL = pURL;}

}
