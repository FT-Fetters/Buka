package xyz.ldqc.buka.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Fetters
 */
public class FileUtil {

  public static List<File> match(String path, String exp){
    File pf = new File(path);
    return match(pf, exp);
  }

  public static List<File> match(File pf, String exp) {
    if (!pf.exists() || !pf.isDirectory()){
      return Collections.emptyList();
    }
    File[] subFiles = pf.listFiles();
    if (subFiles == null){
      return Collections.emptyList();
    }
    List<File> r = new ArrayList<>();
    for (File subFile : subFiles) {
      if (Pattern.matches(exp, subFile.getName())){
        r.add(subFile);
      }
    }
    return r;
  }

  public static boolean isDir(File file){
    return file.exists() && file.isDirectory();
  }




}
