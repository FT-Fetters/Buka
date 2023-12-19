package xyz.ldqc.buka.boot.config.support;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.boot.config.Resource;
import xyz.ldqc.buka.boot.config.ResourceLoader;

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
      try {
        throw new NoSuchFileException(path);
      } catch (NoSuchFileException e) {
        throw new RuntimeException(e);
      }
    }
    log.info("load resource: \"{}\"", path);
    return new Resource(path, resourceStream);
  }
}
