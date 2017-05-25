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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    if (canWrite) {
      uuid = UUID.randomUUID().toString();
    }
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
