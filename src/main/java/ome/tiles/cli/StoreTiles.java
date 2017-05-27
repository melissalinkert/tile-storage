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

import loci.formats.ChannelSeparator;
import loci.formats.FormatException;
import loci.formats.FormatTools;
import loci.formats.IFormatReader;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.annotation.Arg;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import ome.tiles.metadata.IMetadataStorage;
import ome.tiles.metadata.JSONMetadataStorage;
import ome.tiles.metadata.Metadata;
import ome.tiles.metadata.Projection;
import ome.tiles.metadata.ProjectionType;
import ome.tiles.metadata.TileKey;
import ome.tiles.io.DiskTileStorage;
import ome.tiles.io.FilesystemStorageService;
import ome.tiles.io.IStorageService;
import ome.tiles.io.ITileStorage;

public class StoreTiles {

  private static final int TILE_SIZE = 1024;

  @Arg
  private String inputFile;

  @Arg
  private String destination;

  public void process() {
    // for now, assume local filesystem backend
    // can make configurable later

    IFormatReader reader = new ChannelSeparator();
    reader.setFlattenedResolutions(false);
    try {
      long t0 = System.currentTimeMillis();
      reader.setId(inputFile);
      long t1 = System.currentTimeMillis();

      Metadata metadata = new Metadata(true);
      metadata.setTileWidth(TILE_SIZE);
      metadata.setTileHeight(TILE_SIZE);
      Projection projection = new Projection();
      projection.setProjectionType(ProjectionType.XY);
      projection.setCompressed(false);
      projection.setHorizontalTileCount((int) Math.ceil(reader.getSizeX() / (double) TILE_SIZE));
      projection.setVerticalTileCount((int) Math.ceil(reader.getSizeY() / (double) TILE_SIZE));
      projection.setPlaneCount(reader.getImageCount());
      projection.setResolutionCount(reader.getResolutionCount());
      metadata.addProjection(projection);

      IStorageService fs = new FilesystemStorageService(destination);
      IMetadataStorage metadataStorage = new JSONMetadataStorage();
      metadataStorage.setStorageService(fs);
      metadataStorage.save(metadata);

      ITileStorage tileStorage = new DiskTileStorage(fs);

      byte[] tileBuffer = new byte[FormatTools.getPlaneSize(reader, TILE_SIZE, TILE_SIZE)];
      long t2 = System.currentTimeMillis();
      System.out.println("setId took " + (t1 - t0) + " ms");
      System.out.println("tile storage setup took " + (t2 - t1) + " ms");
      // only supports first pyramid for now
      boolean performDownsampling = reader.getResolutionCount() == 1;
      for (int res=0; res<reader.getResolutionCount(); res++) {
        reader.setResolution(res);
        for (int no=0; no<reader.getImageCount(); no++) {
          for (int y=0; y<reader.getSizeY(); y+=TILE_SIZE) {
            int height = (int) Math.min(TILE_SIZE, reader.getSizeY() - y);
            for (int x=0; x<reader.getSizeX(); x+=TILE_SIZE) {
              int width = (int) Math.min(TILE_SIZE, reader.getSizeX() - x);
              long t3 = System.currentTimeMillis();
              reader.openBytes(no, tileBuffer, x, y, width, height);
              long t4 = System.currentTimeMillis();

              TileKey key = new TileKey(metadata, 0);
              key.setResolution(res);
              key.setPlane(no);
              key.setHorizontalCoordinate(x);
              key.setVerticalCoordinate(y);
              tileStorage.storeTile(key, tileBuffer);
              long t5 = System.currentTimeMillis();
              System.out.println("  tile (" + x + ", " + y + ") openbytes took " + (t4 - t3) + " ms");
              System.out.println("  tile (" + x + ", " + y + ") storage took " + (t5 - t4) + " ms");
              if (performDownsampling) {
                // TODO
              }
            }
          }
        }
      }

      reader.close();
      System.out.println("output directory: " + destination + "/" + metadata.getUUID());
    }
    catch (FormatException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ArgumentParser parser = ArgumentParsers.newArgumentParser("tile-storage");
    parser.addArgument("inputFile");
    parser.addArgument("destination");

    StoreTiles store = new StoreTiles();
    try {
      parser.parseArgs(args, store);
    }
    catch (ArgumentParserException e) {
      parser.handleError(e);
      System.exit(1);
    }

    store.process();
  }

}
