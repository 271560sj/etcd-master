package io.xlauncher.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class ReadPropertiesUtils {

    //打印日志
    private static Log log = LogFactory.getLog(ReadPropertiesUtils.class);

    //构造properties
    private static Properties properties;

    static {
        loadProperties();
    }
    synchronized static private void loadProperties(){
        properties = new Properties();
        InputStream in = null;
        try {
            in = ReadPropertiesUtils.class.getResourceAsStream("./master-service.properties");
            properties.load(in);
        }catch (Exception e){
            log.error("ReadPropertiesUtils,loadProperties,read properties file error");
        }finally {
            try {
                if (in != null){
                    in.close();
                }
            }catch (Exception e){
                log.error("ReadPropertiesUtils,loadProperties,close properties file error");
            }
        }
    }

    public static String getProperty(String key){
        if (properties != null){
            loadProperties();
        }
        return properties.getProperty(key);
    }
}
