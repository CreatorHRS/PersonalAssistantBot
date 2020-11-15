import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class BotProperties {

    private BotProperties(){ }

    private static boolean isInitialize = false;
    private static final String propertyPath =  "/etc/personal_assistant/BotSettings.properties";

    public static BotPropertyKeys key = new BotPropertyKeys();

    /**
     * Initialize properties if they was not initialized before
     * @return void
     */
    public static void initProperties(){

        if(!isInitialize) {
            isInitialize = true;
            Properties botProperties = new Properties();
            try {
                botProperties.load(new FileInputStream(propertyPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*Put all key - value to hashMap*/
            for (final String name: botProperties.stringPropertyNames())
                propertyHashMap.put(name, botProperties.getProperty(name));

        }

    }



    /**
     * Get bot property value by key
     * @param key Key for BotProperties
     * @return property value
     */
    public static String getProperty(String key){
        if(isInitialize){
            return propertyHashMap.get(key);
        }else{
            return "";
        }
    }

    /*Contain all properties*/
    private static HashMap<String, String> propertyHashMap = new HashMap<>();


    static class BotPropertyKeys{
        // Basic setting keys
        public static final String BOT_TOKEN = "botToken";
        public static final String BOT_NAME = "botName";

        // Proxy setting keys
        public static final String USE_PROXY = "useProxy";
        public static final String PROXY_SERVER = "proxyServer";
        public static final String PROXY_PORT = "proxyPort";

    }



}

