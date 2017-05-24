package ome.tiles.metadata;

import java.io.IOException;

import ome.tiles.io.IStorageService;

public interface IMetadataStorage {

  void setStorageService(IStorageService storage);

  Metadata load(String readPath) throws IOException;

  void save(Metadata metadata, String writePath) throws IOException;

}
