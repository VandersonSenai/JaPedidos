package japedidos.bd;

import japedidos.ConfigurationLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author thiago
 */
public class ConfigurationFile extends File {
    private Document xmlDoc;
    private File xmlFile;
    private Element rootElement;
    private NodeList mainNodeList;
    private boolean loaded = false;
    
    protected ConfigurationFile(File directory, String file) {
        super(directory, file);
        this.xmlFile = super.getAbsoluteFile();
    }
    
    protected ConfigurationFile(String file) {
        super(file);
        this.xmlFile = super.getAbsoluteFile();
    }
    
    public void create(String content) throws IOException {
        FileWriter fileWriter = new FileWriter(getXml());
        PrintWriter filePrinter = new PrintWriter(fileWriter);
        
        filePrinter.println("<?xml version=\"1.0\"?>");
        filePrinter.println("<japedidos version=\"0.1\">");
        filePrinter.println(content);
        filePrinter.println("</japedidos>");
        filePrinter.flush();
        
        filePrinter.close();
        fileWriter.close();

        setLoaded(false);
    }
    
    public void load(ConfigurationFile file, ConfigurationLoader loadTask) {
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.xmlDoc = docBuilder.parse(this.xmlFile);
            
            this.rootElement = xmlDoc.getDocumentElement();
            this.mainNodeList = this.rootElement.getChildNodes();
            loadTask.load(file);
            this.loaded = true;
        } catch (IOException ex3) {
            System.out.println(ex3.getMessage());
        } catch (ParserConfigurationException ex) {
            System.out.println(ex.getMessage());
        } catch (SAXException ex2) {
            System.out.println(ex2.getMessage());
        }
    }
    
    public void save() throws IOException {}
    
    public File getXml() {
        return this.xmlFile;
    }
    
    public Document getDocument() {
        return this.xmlDoc;
    }
    
    public Element getRootElement() {
        return this.rootElement;
    }
    
    public NodeList getMainNodeList() {
        return this.mainNodeList;
    }
    
    public boolean isLoaded() {
        return this.loaded;
    }
    
    protected void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
