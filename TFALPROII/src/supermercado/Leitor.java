package supermercado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Leitor {
	File file = new File("mail.properties");    
	Properties props = new Properties();
	FileInputStream fis = null;
	try {  
	    fis = new FileOutputStream(file);  
	    //lê os dados que estão no arquivo  
	    props.load(fis);    
	    fis.close();  
	}  
	catch (IOException ex) {  
	    System.out.println(ex.getMessage());  
	    ex.printStackTrace();  
	}  
	

}
}