package ome.tiles.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for the minimal metadata required to interpret tiles.
 */
public class Metadata {

  protected static final String CURRENT_VERSION = "0.1";

  private transient boolean canWrite;
  private String version = CURRENT_VERSION;
  private String uuid;
  private int tileWidth;
  private int tileHeight;
  private List<Projection> projections = new ArrayList<Projection>();

  public Metadata() {
    this(false);
  }

  public Metadata(boolean writeable) {
    canWrite = writeable;
  }

  public String getUUID() {
    return uuid;
  }

  public int getTileWidth() {
    return tileWidth;
  }

  public int getTileHeight() {
    return tileHeight;
  }

  public List<Projection> getProjections() {
    return projections;
  }

  public void setUUID(String uuid) {
    if (canWrite) {
      this.uuid = uuid;
    }
  }

  public void setTileWidth(int width) {
    if (canWrite) {
      tileWidth = width;
    }
  }

  public void setTileHeight(int height) {
    if (canWrite) {
      tileHeight = height;
    }
  }

  public void addProjection(Projection p) {
    if (canWrite) {
      projections.add(p);
    }
  }

  public void removeProjection(int index) {
    if (canWrite) {
      projections.remove(index);
    }
  }

}
