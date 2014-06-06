package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Leitor {
	public Properties props;
	public void getProp() throws IOException {
		props = new Properties();
		FileInputStream file = new FileInputStream(
				"./dados.properties");
		props.load(file);
		
	}
	
}
