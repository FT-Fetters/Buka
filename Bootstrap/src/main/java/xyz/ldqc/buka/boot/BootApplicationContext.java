package xyz.ldqc.buka.boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.ldqc.buka.boot.config.ConfigEntity;
import xyz.ldqc.buka.boot.config.ConfigEnum;
import xyz.ldqc.buka.boot.config.ConfigLoader;
import xyz.ldqc.buka.boot.config.Resource;
import xyz.ldqc.buka.boot.config.support.DefaultConfigLoader;
import xyz.ldqc.buka.boot.config.support.DefaultResourceLoader;
import xyz.ldqc.buka.data.repository.DataRepositoryApplication;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.exception.ContextException;
import xyz.ldqc.buka.receiver.RequestReceiver;
import xyz.ldqc.buka.util.FileUtil;
import xyz.ldqc.tightcall.util.StringUtil;

/**
 * 启动模块上下文
 *
 * @author Fetters
 */
public class BootApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(BootApplicationContext.class);

    private Resource configResource;

    private ConfigLoader configLoader;

    private ConfigEntity config;

    private RequestReceiver receiver;

    private DataRepositoryApplication dataRepositoryApplication;


    public void loadContext() {
        loadConfigResource();
        loadConfig();
        loadDataRepository();
        loadReceiver();
    }

    /**
     * 加载配置文件资源
     */
    private void loadConfigResource() {
        // 先判断Jar包目录下的conf目录下是否有配置文件
        File configFile = new File(FileUtil.getJarPath() + "/conf/config.properties");
        if (configFile.exists()) {
            try {
                this.configResource = new Resource(configFile.getAbsolutePath(),
                    new FileInputStream(configFile));
                log.info("Load user config");
                return;
            } catch (FileNotFoundException e) {
                throw new ContextException("Load user config fail");
            }
        }
        log.info("User config is not exist, load default config");
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        this.configResource = resourceLoader.load("config.properties");
    }

    /**
     * 从配置文件资源中加载配置项
     */
    private void loadConfig() {
        if (configResource == null) {
            // 如果配置文件资源不存在，则直接装载默认配置
            loadDefaultConfig();
            return;
        }
        this.configLoader = new DefaultConfigLoader(configResource);
        ConfigEnum[] properties = ConfigEnum.values();
        this.config = new ConfigEntity();
        // 依次遍历枚举类并尝试从配置实体中获取
        for (ConfigEnum property : properties) {
            String value = configLoader.getConfigItem(property.toString());
            if (StringUtil.isNotBlank(value)) {
                this.config.setProperty(property, value);
                log.info("config property {} set value: {}", property, value);
            } else {
                // 如果配置文件中不存在该配置，则从枚举类中获取默认值
                log.info("config property {} set default value: {}", property,
                    property.getDefaultValue());
                this.config.setPropertyDefault(property);
            }
        }
    }

    /**
     * 加载配置为默认配置
     */
    private void loadDefaultConfig() {
        ConfigEnum[] properties = ConfigEnum.values();
        this.config = new ConfigEntity();
        for (ConfigEnum property : properties) {
            config.setPropertyDefault(property);
        }
    }

    /**
     * 加载请求接收模块
     */
    private void loadReceiver() {
        int port = Integer.parseInt(config.getValue(ConfigEnum.PORT));
        this.receiver = RequestReceiver.create(port, this.dataRepositoryApplication,
            config.getValue(ConfigEnum.AUTH_KEY));
    }

    /**
     * 加载数据仓库
     */
    private void loadDataRepository() {
        DataRepositoryConfig repositoryConfig = buildDataRepositoryConfig();
        this.dataRepositoryApplication = DataRepositoryApplication.run(
            repositoryConfig);
    }

    /**
     * 构建数据仓库配置
     */
    private DataRepositoryConfig buildDataRepositoryConfig() {
        DataRepositoryConfig repositoryConfig = new DataRepositoryConfig();
        // 仓库存储地址
        String storageLocation = config.getValue(ConfigEnum.DATA_REPOSITORY_STORAGE_LOCATION);
        if (StringUtil.isBlank(storageLocation)) {
            setDefaultDataStorageLocation(repositoryConfig);
        } else {
            repositoryConfig.setStorageLocation(storageLocation);
        }
        // 仓库引擎
        String engineClass = config.getValue(ConfigEnum.DATA_REPOSITORY_ENGINE);
        repositoryConfig.setEngineClassName(engineClass);
        return repositoryConfig;
    }

    private void setDefaultDataStorageLocation(DataRepositoryConfig dataStorageLocation) {
        String defaultStorageLocation = dataStorageLocation.getDefaultStorageLocation();
        File file = new File(defaultStorageLocation);
        if (!file.exists()) {
            boolean dirs = file.mkdirs();
            if (!dirs) {
                throw new ContextException("cannot create dir: " + file.getAbsolutePath());
            }
            boolean writable = file.setWritable(true);
            if (!writable) {
                throw new ContextException("cannot create dir");
            }
        }
        log.info("set data storage location default: {}", file.getAbsoluteFile());
        dataStorageLocation.setStorageLocation(file.getAbsolutePath());
    }


}
