import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Created by mahmoud on 2/26/15.
 */
public class TwitterProperties {

    public String consumerKey, consumerSecret, authKey, authSecret;

    public TwitterProperties()  {

        Properties prop = new Properties();
        String propFileName = "config";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
                consumerKey = prop.getProperty("consumer_key");
                consumerSecret = prop.getProperty("consumer_secret");
                authKey = prop.getProperty("auth_key");
                authSecret = prop.getProperty("auth_secret");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args){
            TwitterProperties properties = new TwitterProperties();
            System.out.println(properties.consumerKey);
    }
}
