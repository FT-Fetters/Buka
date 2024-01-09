package xyz.ldqc.buka.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Fetters
 */
public class IoUtil {

  private IoUtil() {
  }

  public static boolean fileWriteBytes(String path, byte[] data) {
    File f = new File(path);
    if (f.isDirectory() && !f.exists()){
      return false;
    }
    if (f.exists()) {
      return false;
    }
    if (data == null || data.length == 0) {
      return false;
    }
    try(FileOutputStream os = new FileOutputStream(f)) {
      int rem = data.length;
      while (rem > 0){
        int w = Math.min(rem, 1024);
        os.write(data, data.length - rem, w);
        rem -= w;
      }
    } catch (IOException e) {
      return false;
    }
    return true;
  }

}
