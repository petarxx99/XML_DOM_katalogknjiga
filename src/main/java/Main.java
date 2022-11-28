import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class Main {
    public static void main(String[] args){
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse("katalogknjiga.xml");

            NodeList books = document.getElementsByTagName("book");
            for(int i=0; i< books.getLength(); i++){
                Element book = (Element) books.item(i);
                obradiKnjigu(book);
            }
        } catch(IOException | ParserConfigurationException | SAXException e){
            e.printStackTrace();
        }
    }

    public static void obradiKnjigu(Element book){
        Element priceElement = (Element) book.getElementsByTagName("price").item(0);
        Double price = Double.parseDouble(priceElement.getFirstChild().getTextContent());
        if(price<=10) return;

        Element publishDateElement = (Element) book.getElementsByTagName("publish_date").item(0);
        int datumIzdavanja = Integer.parseInt(publishDateElement.getFirstChild().getTextContent()
                .split("-")[0].trim());
        if(datumIzdavanja <= 2005) return;

        String id = book.getAttribute("id");
        System.out.println("id = " + id);
        ispisiPodatke(new String[]{"author", "title", "genre", "price", "publish_date", "description"}, book);
    }

    public static void ispisiPodatke(String[] naziviElemenata, Element roditeljskiElement){
        for(String nazivJednogElementa : naziviElemenata){
            ispisiPodatke(nazivJednogElementa, roditeljskiElement);
        }
    }

    public static void ispisiPodatke(String nazivElementa, Element roditeljskiElement){
        Element element = (Element) roditeljskiElement.getElementsByTagName(nazivElementa).item(0);
        System.out.println(nazivElementa + " = " + element.getFirstChild().getTextContent());
    }
}
