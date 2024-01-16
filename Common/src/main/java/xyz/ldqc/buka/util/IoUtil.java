package xyz.ldqc.buka.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import xyz.ldqc.tightcall.buffer.SimpleByteData;

/**
 * @author Fetters
 */
public class IoUtil {

  private static final int BUF_SIZE = 1024;

  private IoUtil() {
  }

  public static boolean fileWriteBytes(String path, byte[] data, boolean cover) {
    File f = new File(path);
    if (f.isDirectory() && !f.exists()){
      return false;
    }
    if (f.exists()) {
      if (cover){
        try {
          Files.delete(f.toPath());
        } catch (IOException e) {
          return false;
        }
      }else {
        return false;
      }
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

  public static byte[] fileReadBytes(String path){
    File f = new File(path);
    if (f.isDirectory() || !f.exists()){
      return new byte[0];
    }
    try(FileInputStream is = new FileInputStream(f)) {
      byte[] buf = new byte[BUF_SIZE];
      int total = is.available();
      SimpleByteData byteData = new SimpleByteData(total);

      while (total > 0){
        if (total < BUF_SIZE){
          buf = new byte[is.available()];
        }
        int read = is.read(buf);
        byteData.writeBytes(buf);
        total -= read;
      }
      return byteData.readBytes();
    } catch (IOException e) {
      return new byte[0];
    }
  }

}
