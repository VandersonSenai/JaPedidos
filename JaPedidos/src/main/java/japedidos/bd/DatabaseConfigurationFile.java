package japedidos.bd;

import japedidos.ConfigurationLoader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author thiago
 */
public class DatabaseConfigurationFile extends ConfigurationFile {
    public static final File CONFIG_DIRECTORY = new File(japedidos.JaPedidos.ROOT_DIRECTORY);
    
    public final String DEFAULT_ADDRESS = "localhost";
    public final String DEFAULT_PORT = "3306";
    public final String DEFAULT_SCHEMA = "japedidos";
    public final String DEFAULT_USER = "root";
    public final String DEFAULT_USER_PASSWORD = "";
    
    private String address;
    private String port;
    private String schema;
    private String user;
    private String userPassword;
    
    public DatabaseConfigurationFile(String filePath) {
        super(CONFIG_DIRECTORY, filePath);   
    }
    
    public void create() throws IOException {
        create(DEFAULT_ADDRESS, DEFAULT_PORT, DEFAULT_SCHEMA, DEFAULT_USER, DEFAULT_USER_PASSWORD);
    }
    
    public void create(String address, String port, String schema, String user, String userPassword) throws IOException {
        String contentStr = String.format("""
            \t<address>%s</address>
            \t<port>%s</port>
            \t<schema>%s</schema>
            \t<user>%s</user>
            \t<password>%s</password>""", address, port, schema, user, userPassword);
        
        super.create(contentStr); // Seta isLoaded para false automaticamente
    }
    
    public void load() {
        ConfigurationLoader loadTask = (me) -> {
            DatabaseConfigurationFile meReal = (DatabaseConfigurationFile)me;
            int nodeCount = getMainNodeList().getLength();
            for (int i = 0; i < nodeCount; i++) {
                Node n = getMainNodeList().item(i);
                if (n instanceof Element) {
                    Element e = (Element)n;
                    String tag = e.getTagName();

                    if (tag.equals("address")) {
                        meReal.setAddress(e.getTextContent());
                    } else if (tag.equals("port")) {
                        meReal.setPort(e.getTextContent());
                    } else if (tag.equals("schema")) {
                        meReal.setSchema(e.getTextContent());
                    } else if (tag.equals("user")) {
                        meReal.setUser(e.getTextContent());
                    } else if (tag.equals("password")) {
                        meReal.setUserPassword(e.getTextContent());
                    }
                }
            }
        };
        
        super.load(this, loadTask);
    }
    
    public void save() throws IOException {
        create(this.address, this.port, this.schema, this.user, this.userPassword);
    }
    
    // Setters
    public void setAddress(String address) {
        this.address = address;
        super.setLoaded(false);
    }
    
    public void setPort(String port) {
        this.port = port;
        super.setLoaded(false);

    }
    
    public void setSchema(String schema) {
        this.schema = schema;
        super.setLoaded(false);
    }
    
    public void setUser(String user) {
        this.user = user;
        super.setLoaded(false);
    }
    
    public void setUserPassword(String password) {
        this.userPassword = password;
        super.setLoaded(false);
    }
    
    // Getters 
    public String getAddress() {
        if (super.isLoaded()) {
            return this.address;
        } else {
            throw new IllegalStateException("É necessário recarregar o arquivo de configuração para obter novo endereço do banco.");
        }
    }
    
    public String getPort() {
        if (super.isLoaded()) {
            return this.port;
        } else {
            throw new IllegalStateException("É necessário recarregar o arquivo de configuração para obter nova porta do banco.");
        }
    }
    
    public String getSchema() {
        if (super.isLoaded()) {
            return this.schema;
        } else {
            throw new IllegalStateException("É necessário recarregar o arquivo de configuração para obter novo Schema do banco.");
        }
    }
    
    public String getUser() {
        if (super.isLoaded()) {
            return this.user;
        } else {
            throw new IllegalStateException("É necessário recarregar o arquivo de configuração para obter novo usuário do banco.");
        }
    }
    
    public String getUserPassword() {
        if (super.isLoaded()) {
            return this.userPassword;
        } else {
            throw new IllegalStateException("É necessário recarregar o arquivo de configuração para obter nova senha do usuário do banco.");
        }
    }
}
