package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.exception.RepositoryBufferException;
import xyz.ldqc.buka.util.FileUtil;

/**
 * @author Fetters
 */
public class DataBufferPool {

  private final DataRepositoryConfig config;

  private final ConcurrentHashMap<String, RepositoryBuffer> repoBuffer;

  public DataBufferPool(DataRepositoryConfig config) {
    this.config = config;
    this.repoBuffer = new ConcurrentHashMap<>();
  }

  public RepositoryBuffer loadRepository(String name) {
    if (repoBuffer.containsKey(name)) {
      return repoBuffer.get(name);
    }
    File repoBaseDir = getBaseDir();
    List<File> matched = FileUtil.match(repoBaseDir, name);
    if (matched.isEmpty()) {
      return null;
    }
    RepositoryBuffer repositoryBuffer = new RepositoryBuffer(matched.get(0).getAbsolutePath());
    repoBuffer.put(name, repositoryBuffer);
    return repositoryBuffer;
  }

  private File getBaseDir() {
    String storageLocation = config.getStorageLocation();
    File repoBaseDir = new File(storageLocation);
    if (!FileUtil.isDir(repoBaseDir)) {
      throw new RepositoryBufferException("Repository data storage path error");
    }
    return repoBaseDir;
  }

  public RepositoryBuffer createRepository(String name) {
    RepositoryBuffer repositoryBuffer = loadRepository(name);
    if (repositoryBuffer != null) {
      throw new RepositoryBufferException("Repository " + name + " is exists");
    }
    File baseDir = getBaseDir();
    File newRepoDir = new File(baseDir.getAbsolutePath(), name);
    boolean b = newRepoDir.mkdir();
    if (!b) {
      throw new RepositoryBufferException("Can not create repository");
    }
    repositoryBuffer = new RepositoryBuffer(newRepoDir.getAbsolutePath());
    repoBuffer.put(name, repositoryBuffer);
    return repositoryBuffer;
  }

  public void deleteRepository(String name) {
    File baseDir = getBaseDir();
    File targetFileDir = new File(baseDir.getAbsolutePath(), name);
    if (!FileUtil.isDir(targetFileDir)) {
      throw new RepositoryBufferException("Repository is not exists");
    }
    try {
      repoBuffer.remove(name);
      Files.delete(targetFileDir.toPath());
    } catch (IOException e) {
      throw new RepositoryBufferException("Can not delete repository");
    }
  }

  public List<String> getAllRepositoryName(){
    String storageLocation = config.getStorageLocation();
    File file = new File(storageLocation);
    if (!FileUtil.isDir(file)){
      throw new RepositoryBufferException("May the storage location have some problems");
    }
    File[] files = file.listFiles();
    List<String> rl = new LinkedList<>();
    if (files == null || files.length == 0) {
      return Collections.emptyList();
    }
    Arrays.stream(files).forEach(f -> {
      if (FileUtil.isDir(f)){
        rl.add(f.getName());
      }
    });
    return rl;
  }
}
