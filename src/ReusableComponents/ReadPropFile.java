package ReusableComponents;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class ReadPropFile {

	private Properties prop = null;

 	public ReadPropFile(String propName) {

		InputStream is = null;
		try {
			this.prop = new Properties();
 			is = this.getClass().getClassLoader().getResourceAsStream(propName);
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPropertyValue(String key) {
		return this.prop.getProperty(key);
	}
}
