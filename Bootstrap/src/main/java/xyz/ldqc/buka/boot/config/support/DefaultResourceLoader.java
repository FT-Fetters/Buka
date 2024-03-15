package xyz.ldqc.buka.boot.config.support;

import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.boot.config.Resource;
import xyz.ldqc.buka.boot.config.ResourceLoader;
import xyz.ldqc.buka.boot.exception.ConfigLoaderException;
import xyz.ldqc.buka.util.StrUtil;

/**
 * Resource加载类，通过传入路径加载资源
 *
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
            throw new ConfigLoaderException(StrUtil.format("No such path - {0}", path));
        }
        log.info("load resource: \"{}\"", path);
        return new Resource(path, resourceStream);
    }
}
