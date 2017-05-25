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

public class TileKey {

  private Metadata metadata;
  private int projection;
  private int resolution;
  private int plane;
  private int horizontalCoordinate;
  private int verticalCoordinate;

  public TileKey(Metadata metadata, int projectionIndex) {
    this.metadata = metadata;
    projection = projectionIndex;
  }

  public void setResolution(int resolution) {
    this.resolution = resolution;
  }

  public void setPlane(int plane) {
    this.plane = plane;
  }

  public void setHorizontalCoordinate(int coordinate) {
    horizontalCoordinate = coordinate;
  }

  public void setVerticalCoordinate(int coordinate) {
    verticalCoordinate = coordinate;
  }

  public Metadata getMetadata() {
    return metadata;
  }

  public ProjectionType getProjectionType() {
    return metadata.getProjection(projection).getType();
  }

  public int getResolution() {
    return resolution;
  }

  public int getPlane() {
    return plane;
  }

  public int getHorizontalCoordinate() {
    return horizontalCoordinate;
  }

  public int getVerticalCoordinate() {
    return verticalCoordinate;
  }



}
