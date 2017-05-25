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

package ome.tiles.io;

import java.io.File;
import java.io.IOException;
import ome.tiles.metadata.TileKey;

public class DiskTileStorage implements ITileStorage {

  private IStorageService service;

  public DiskTileStorage(IStorageService service) {
    this.service = service;
  }

  @Override
  public byte[] readTile(TileKey key) throws IOException {
    String tileFile = getTileFile(key);
    return service.readBytes(tileFile);
  }

  @Override
  public void storeTile(TileKey key, byte[] tileBuffer) throws IOException {
    String tileFile = getTileFile(key);
    service.writeBytes(tileFile, tileBuffer);
  }

  private String getTileFile(TileKey key) {
    StringBuffer buf = new StringBuffer(key.getMetadata().getUUID());
    buf.append(File.separator);
    buf.append(key.getProjectionType().toString().toLowerCase());
    buf.append(File.separator);
    buf.append(key.getResolution());
    buf.append(File.separator);
    buf.append(key.getPlane());
    buf.append(File.separator);
    buf.append(key.getVerticalCoordinate());
    buf.append(File.separator);
    buf.append(key.getHorizontalCoordinate());
    return buf.toString();
  }

}
