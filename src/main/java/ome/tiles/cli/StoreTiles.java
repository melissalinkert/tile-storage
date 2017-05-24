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
import java.util.UUID;

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
import ome.tiles.io.FilesystemStorageService;


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
      reader.setId(inputFile);

      Metadata metadata = new Metadata(true);
      metadata.setUUID(UUID.randomUUID().toString());
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

      IMetadataStorage metadataStorage = new JSONMetadataStorage();
      metadataStorage.setStorageService(new FilesystemStorageService());
      metadataStorage.save(metadata, destination);

      //ITileStorage tileStorage = new DiskTileStorage();

      byte[] tileBuffer = new byte[FormatTools.getPlaneSize(reader, TILE_SIZE, TILE_SIZE)];
      // only supports first pyramid for now
      for (int res=0; res<reader.getResolutionCount(); res++) {
        reader.setResolution(res);
        for (int no=0; no<reader.getImageCount(); no++) {
          for (int y=0; y<reader.getSizeY(); y+=TILE_SIZE) {
            int height = (int) Math.min(TILE_SIZE, reader.getSizeY() - y);
            for (int x=0; x<reader.getSizeX(); x+=TILE_SIZE) {
              int width = (int) Math.min(TILE_SIZE, reader.getSizeX() - x);
              reader.openBytes(no, tileBuffer, x, y, width, height);

              /*
              TileKey key = new TileKey(metadata);
              key.setPyramid(0);
              key.setResolution(0);
              key.setPlane(no);
              key.setProjection(TileProjection.XY);
              key.setHorizontalCoordinate(x);
              key.setVerticalCoordinate(y);
              // this should perform any necessary downsampling?
              tileStorage.storeTile(key, tileBuffer);
              */
            }
          }
        }
      }

      reader.close();
    }
    catch (FormatException e) {
    }
    catch (IOException e) {
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
