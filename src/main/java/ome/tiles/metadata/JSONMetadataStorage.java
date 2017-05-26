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

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import ome.tiles.io.IStorageService;

/**
 * Metadata storage implementation that serializes to and
 * deserializes from JSON.
 */
public class JSONMetadataStorage implements IMetadataStorage {

  private static final String RELATIVE_PATH = "metadata";

  private IStorageService storage;

  @Override
  public void setStorageService(IStorageService storage) {
    this.storage = storage;
  }

  @Override
  public void save(Metadata metadata) throws IOException {
    Gson gson = new Gson();
    String json = gson.toJson(metadata);
    if (storage != null) {
      storage.writeString(metadata.getUUID() + File.separator + RELATIVE_PATH, json);
    }
  }

  @Override
  public Metadata load() throws IOException {
    String json = storage.readString(RELATIVE_PATH);
    Gson gson = new Gson();
    return gson.fromJson(json, Metadata.class);
  }

}
