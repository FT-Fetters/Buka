import org.junit.Test;
import xyz.ldqc.buka.boot.config.Resource;
import xyz.ldqc.buka.boot.config.support.DefaultConfigLoader;
import xyz.ldqc.buka.boot.config.support.DefaultResourceLoader;

public class TestConfig {

  @Test
  public void testConfigLoader() {
    DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource;
    resource = resourceLoader.load("config.properties");
    DefaultConfigLoader configLoader = new DefaultConfigLoader(resource);
    String item = configLoader.getConfigItem("test.test");
    System.out.println(item);
  }

}
