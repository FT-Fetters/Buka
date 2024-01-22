package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
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


}
