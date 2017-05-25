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
import loci.common.DataTools;
import loci.common.RandomAccessInputStream;
import loci.common.RandomAccessOutputStream;

public class FilesystemStorageService implements IStorageService {

  private String destination;

  public FilesystemStorageService(String destination) {
    this.destination = destination;
  }

  @Override
  public String getDestination() {
    return destination;
  }

  @Override
  public void writeString(String path, String data) throws IOException {
    String filename = getAbsolutePath(path);
    mkparent(filename);
    RandomAccessOutputStream out = new RandomAccessOutputStream(filename);
    out.writeBytes(data);
    out.close();
  }

  @Override
  public void writeBytes(String path, byte[] data) throws IOException {
    String filename = getAbsolutePath(path);
    mkparent(filename);
    RandomAccessOutputStream out = new RandomAccessOutputStream(filename);
    out.write(data);
    out.close();
  }

  @Override
  public String readString(String path) throws IOException {
    return DataTools.readFile(getAbsolutePath(path));
  }

  @Override
  public byte[] readBytes(String path) throws IOException {
    RandomAccessInputStream s = new RandomAccessInputStream(getAbsolutePath(path));
    byte[] b = new byte[(int) s.length()];
    s.readFully(b);
    s.close();
    return b;
  }

  private String getAbsolutePath(String path) {
    File f = new File(getDestination());
    return f.getAbsolutePath() + File.separator + path;
  }

  private void mkparent(String path) {
    File parent = new File(path).getParentFile();
    parent.mkdirs();
  }

}
