package ome.tiles.io;

import java.io.IOException;

public interface IStorageService {

  void writeString(String path, String data) throws IOException;

  void writeBytes(String path, byte[] data) throws IOException;

  String readString(String path) throws IOException;

  byte[] readBytes(String path) throws IOException;

}
