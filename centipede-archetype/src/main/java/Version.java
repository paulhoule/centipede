import java.io.InputStream;
import java.util.Properties;

public class Version {
    public static String get() {
        InputStream in=Main.getClass().getResourceAsStream("version.properties");
        Properties p= new Properties();
        p.load(in);
        String value=p.getProperty("com.ontology2.haruhi.version");
    }
}
