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
package ome.tiles.cli;

import java.io.IOException;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.annotation.Arg;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import ome.tiles.metadata.IMetadataStorage;
import ome.tiles.metadata.JSONMetadataStorage;
import ome.tiles.metadata.Metadata;
import ome.tiles.metadata.TileKey;
import ome.tiles.io.DiskTileStorage;
import ome.tiles.io.FilesystemStorageService;
import ome.tiles.io.IStorageService;
import ome.tiles.io.ITileStorage;

public class ReadTile {

  @Arg
  private String inputDirectory;

  @Arg
  private int x;

  @Arg
  private int y;

  @Arg
  private int width;

  @Arg
  private int height;

  public void process() {
    // for now, assume local filesystem backend
    // can make configurable later

    try {
      long t0 = System.currentTimeMillis();
      IStorageService fs = new FilesystemStorageService(inputDirectory);
      IMetadataStorage metadataStorage = new JSONMetadataStorage();
      metadataStorage.setStorageService(fs);

      ITileStorage tileStorage = new DiskTileStorage(fs);

      Metadata metadata = metadataStorage.load();
      long t1 = System.currentTimeMillis();
      TileKey key = new TileKey(metadata, 0);
      key.setResolution(0);
      key.setPlane(0);
      key.setHorizontalCoordinate(x);
      key.setVerticalCoordinate(y);
      key.setTileWidth(width);
      key.setTileHeight(height);
      long t2 = System.currentTimeMillis();

      byte[] tile = tileStorage.readTile(key);
      long t3 = System.currentTimeMillis();
      System.out.println("initial load time = " + (t1 - t0) + " ms");
      System.out.println("key setup time = " + (t2 - t1) + " ms");
      System.out.println("tile read time = " + (t3 - t2) + " ms");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ArgumentParser parser = ArgumentParsers.newArgumentParser("read-tile");
    parser.addArgument("inputDirectory");
    parser.addArgument("x").type(Integer.class);
    parser.addArgument("y").type(Integer.class);
    parser.addArgument("width").type(Integer.class);
    parser.addArgument("height").type(Integer.class);

    ReadTile reader = new ReadTile();
    try {
      parser.parseArgs(args, reader);
    }
    catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
    }

    reader.process();
  }

}
