package ome.tiles.metadata;

public class Projection {

  private ProjectionType type;
  private int horizontalTiles;
  private int verticalTiles;
  private int planes;
  private int resolutions;
  private boolean compressed;

  public ProjectionType getType() {
    return type;
  }

  public int getHorizontalTileCount() {
    return horizontalTiles;
  }

  public int getVerticalTileCount() {
    return verticalTiles;
  }

  public int getPlaneCount() {
    return planes;
  }

  public int getResolutionCount() {
    return resolutions;
  }

  public boolean isCompressed() {
    return compressed;
  }

  public void setProjectionType(ProjectionType type) {
    this.type = type;
  }

  public void setHorizontalTileCount(int tiles) {
    horizontalTiles = tiles;
  }

  public void setVerticalTileCount(int tiles) {
    verticalTiles = tiles;
  }

  public void setPlaneCount(int planes) {
    this.planes = planes;
  }

  public void setResolutionCount(int resolutions) {
    this.resolutions = resolutions;
  }

  public void setCompressed(boolean compressed) {
    this.compressed = compressed;
  }

}
