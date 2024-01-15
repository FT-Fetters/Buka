package xyz.ldqc.buka.boot.config.support;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.boot.config.Resource;
import xyz.ldqc.buka.boot.config.ResourceLoader;
import xyz.ldqc.buka.boot.exception.ConfigLoaderException;

/**
 * @author Fetters
 */
public class DefaultResourceLoader implements ResourceLoader {

  private static final Logger log = LoggerFactory.getLogger(DefaultResourceLoader.class);

  private final ClassLoader classLoader;

  public DefaultResourceLoader() {
    this.classLoader = Thread.currentThread().getContextClassLoader();
  }

  @Override
  public Resource load(String path) {
    InputStream resourceStream = this.classLoader.getResourceAsStream(path);
    if (resourceStream == null) {
      throw new ConfigLoaderException("No such path (" + path + ")");
    }
    log.info("load resource: \"{}\"", path);
    return new Resource(path, resourceStream);
  }
}
