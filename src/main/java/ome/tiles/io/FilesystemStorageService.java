package ome.tiles.io;

import java.io.File;
import java.io.IOException;
import loci.common.DataTools;
import loci.common.RandomAccessInputStream;
import loci.common.RandomAccessOutputStream;

public class FilesystemStorageService implements IStorageService {

  @Override
  public void writeString(String path, String data) throws IOException {
    mkparent(path);
    RandomAccessOutputStream out = new RandomAccessOutputStream(path);
    out.writeBytes(data);
    out.close();
  }

  @Override
  public void writeBytes(String path, byte[] data) throws IOException {
    mkparent(path);
    RandomAccessOutputStream out = new RandomAccessOutputStream(path);
    out.write(data);
    out.close();
  }

  @Override
  public String readString(String path) throws IOException {
    return DataTools.readFile(path);
  }

  @Override
  public byte[] readBytes(String path) throws IOException {
    RandomAccessInputStream s = new RandomAccessInputStream(path);
    byte[] b = new byte[(int) s.length()];
    s.readFully(b);
    s.close();
    return b;
  }

  private void mkparent(String path) {
    File parent = new File(path).getParentFile();
    if (!parent.exists()) {
      parent.mkdir();
    }
  }

}
