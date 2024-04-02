package xyz.ldqc.buka.data.repository.core.engine.buffer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import xyz.ldqc.buka.data.repository.config.DataRepositoryConfig;
import xyz.ldqc.buka.data.repository.exception.BoxException;
import xyz.ldqc.buka.data.repository.exception.RepositoryBufferException;
import xyz.ldqc.buka.exception.IllegalFileException;
import xyz.ldqc.buka.util.EntryUtil;
import xyz.ldqc.buka.util.FileUtil;
import xyz.ldqc.buka.util.StrUtil;

/**
 * @author Fetters
 */
public class DataBufferPool {

    private final DataRepositoryConfig config;

    private final ConcurrentHashMap<String, RepositoryBuffer> repoBuffer;

    private final ConcurrentHashMap<String, Box> boxBuffer;

    public DataBufferPool(DataRepositoryConfig config) {
        this.config = config;
        this.repoBuffer = new ConcurrentHashMap<>();
        this.boxBuffer = new ConcurrentHashMap<>();
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

    public List<String> getAllRepositoryName() {
        String storageLocation = config.getStorageLocation();
        File file = new File(storageLocation);
        if (!FileUtil.isDir(file)) {
            throw new RepositoryBufferException("May the storage location have some problems");
        }
        File[] files = file.listFiles();
        List<String> rl = new LinkedList<>();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        Arrays.stream(files).forEach(f -> {
            if (FileUtil.isDir(f)) {
                rl.add(f.getName());
            }
        });
        return rl;
    }

    public void cacheBox(Box box) {
        boxBuffer.put(box.getBoxName(), box);
    }

    public Box loadBox(String name) {
        Box box = boxBuffer.get(name);
        if (box == null) {
            box = loadBoxFromDisk(name);
        }
        return box;
    }

    /**
     * 从磁盘中加载Box
     *
     * @param name Box名称
     * @return Box
     */
    private Box loadBoxFromDisk(String name) {
        File baseDir = getBaseDir();
        File boxFile = new File(baseDir.getAbsolutePath() + "/box", name);
        if (!FileUtil.isFile(boxFile)) {
            throw new IllegalFileException("Box file is not exists or not is file");
        }
        try {
            return BoxFactory.load(boxFile.toPath());
        } catch (IOException e) {
            throw new BoxException(StrUtil.format("Fail load box from disk: {0}", e.getMessage()));
        }
    }

    public List<Entry<String, Boolean>> listBoxFromDisk() {
        File baseDir = getBaseDir();
        File boxDir = new File(baseDir.getAbsolutePath() + "/box");
        if (!FileUtil.isDir(boxDir)) {
            throw new IllegalFileException("Box dir is not exists or not is dir");
        }
        List<File> boxFiles = FileUtil.match(boxDir, "+\\.box");
        List<Entry<String, Boolean>> boxNameList = new LinkedList<>();
        boxFiles.forEach(box -> {
            String boxName = box.getName().split("\\.")[0];
            Box cacheBox = boxBuffer.get(boxName);
            boxNameList.add(EntryUtil.of(boxName, cacheBox != null));
        });
        return boxNameList;
    }

}
