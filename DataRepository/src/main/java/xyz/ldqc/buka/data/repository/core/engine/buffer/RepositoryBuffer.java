package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import xyz.ldqc.buka.data.repository.exception.BadBucketException;
import xyz.ldqc.buka.data.repository.exception.RepositoryBufferException;
import xyz.ldqc.buka.util.FileUtil;

/**
 * @author Fetters
 */
public class RepositoryBuffer {

  private final String name;

  private final String repoPath;

  private final ConcurrentHashMap<String, AbstractBucket> bucketMap;

  private final ConcurrentHashMap<String, Box> boxMap;

  public RepositoryBuffer(String repoPath) {
    File repoPathFile = new File(repoPath);
    if (!repoPathFile.exists() || !repoPathFile.isDirectory()) {
      throw new RepositoryBufferException("Repository not exists");
    }
    this.repoPath = repoPath;
    this.name = repoPathFile.getName();
    bucketMap = new ConcurrentHashMap<>();
    boxMap = new ConcurrentHashMap<>();
  }

  public String getName() {
    return name;
  }

  public List<String> listBucketName() {
    File rpf = new File(repoPath);
    if (!rpf.exists() || !rpf.isDirectory()) {
      throw new RepositoryBufferException("Repository not exist");
    }
    String matchExp = ".+_dm\\.buk";
    List<File> dmFileList = FileUtil.match(rpf, matchExp);
    return dmFileList.stream().map(f -> f.getName().replace("_dm.buk", ""))
        .collect(Collectors.toList());
  }

  public AbstractBucket loadBucket(String name) {
    if (bucketMap.containsKey(name)) {
      return bucketMap.get(name);
    }
    BadBucket badBucket = new BadBucket(name);
    badBucket.load(repoPath, name, null);
    this.bucketMap.put(name, badBucket);
    return badBucket;
  }

  public boolean hasBucket(String name) {
    if (bucketMap.containsKey(name)) {
      return true;
    }
    String targetFileName = name + "_dm.buk";
    File targetFile = new File(repoPath, targetFileName);
    return targetFile.exists();
  }

  public BadBucket createBucket(String name) {
    if (hasBucket(name)) {
      throw new BadBucketException("Bucket is exists");
    }
    BadBucket badBucket = new BadBucket(name);
    badBucket.storage(repoPath, null);
    bucketMap.put(name, badBucket);
    return badBucket;
  }

  public boolean deleteBucket(String name) {
    if (!hasBucket(name)) {
      return false;
    }
    bucketMap.remove(name);
    String dmFileName = name + "_dm.buk";
    List<File> rf = new LinkedList<>();
    rf.add(new File(repoPath, dmFileName));
    List<File> matched = FileUtil.match(repoPath, name + "_fm_\\d+\\.buk");
    rf.addAll(matched);
    try {
      for (File f : rf) {
        Files.delete(f.toPath());
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  public List<String> getAllBucketNames(){
    List<File> matched = FileUtil.match(repoPath, ".*_dm\\.buk");
    return matched.stream().map( f -> f.getName().replace("_dm.buk", "")).collect(Collectors.toList());
  }


}
