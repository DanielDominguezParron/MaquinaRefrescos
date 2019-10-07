package auxiliares;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class LeeProperties {
	
	HashMap<String,String> hashProperties;
	Properties prop;
	
	String filePath;

	
	// De http://www.mkyong.com/java/java-properties-file-examples/
	public LeeProperties(String fileN) {

		hashProperties = new HashMap<String,String>();
		prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(fileN);

			// load a properties file
			prop.load(input);
			
			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				//System.out.println("Key : " + key + ", Value : " + value);
				hashProperties.put(key, value);
			}
			
		} catch (IOException ex) {
			System.out.println("Error leyendo el fichero '" + fileN +"': no se ha podido acceder a las propiedades de configuracion");
			System.out.println("Fin de la ejecucion del programa");
			//ex.printStackTrace();
			System.exit(1);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}


	public HashMap<String,String> getHash() {
		return hashProperties;
	}
	
	

}
