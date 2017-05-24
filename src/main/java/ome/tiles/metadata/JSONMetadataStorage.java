package ome.tiles.metadata;

import com.google.gson.Gson;
import java.io.IOException;
import ome.tiles.io.IStorageService;

/**
 * Metadata storage implementation that serializes to and
 * deserializes from JSON.
 */
public class JSONMetadataStorage implements IMetadataStorage {

  private IStorageService storage;

  @Override
  public void setStorageService(IStorageService storage) {
    this.storage = storage;
  }

  @Override
  public void save(Metadata metadata, String writePath) throws IOException {
    Gson gson = new Gson();
    String json = gson.toJson(metadata);
    if (storage != null) {
      storage.writeString(writePath + "/" + metadata.getUUID() + "/metadata", json);
    }
  }

  @Override
  public Metadata load(String readPath) throws IOException {
    String json = storage.readString(readPath);
    Gson gson = new Gson();
    return gson.fromJson(json, Metadata.class);
  }

}
