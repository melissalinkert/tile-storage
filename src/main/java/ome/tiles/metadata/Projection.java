/*
 * Copyright (C) 2017 Glencoe Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 */

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
