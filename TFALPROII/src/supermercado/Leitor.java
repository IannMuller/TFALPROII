package supermercado;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Leitor {

	public static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream(
				"./Dados.properties");
		props.load(file);
		return props;
	}
}
