/**
 * 
 * This software is part of the MightyBiomesAPI
 * 
 * Copyright (c) 2013 Cybermaxke
 * 
 * MightyBiomesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * MightyBiomesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MightyBiomesAPI. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.mighty.biome.api;

import java.util.List;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

public interface Biome {
	public static final Biome OCEAN = new BiomeWrapper(0);
	public static final Biome PLAINS = new BiomeWrapper(1);
	public static final Biome DESERT = new BiomeWrapper(2);
	public static final Biome EXTREME_HILLS = new BiomeWrapper(3);
	public static final Biome FOREST = new BiomeWrapper(4);
	public static final Biome TAIGA = new BiomeWrapper(5);
	public static final Biome SWAMPLAND = new BiomeWrapper(6);
	public static final Biome RIVER = new BiomeWrapper(7);
	public static final Biome NETHER = new BiomeWrapper(8);
	public static final Biome THE_END = new BiomeWrapper(9);
	public static final Biome FROZEN_OCEAN = new BiomeWrapper(10);
	public static final Biome FROZEN_RIVER = new BiomeWrapper(11);
	public static final Biome ICE_PLAINS = new BiomeWrapper(12);
	public static final Biome ICE_MOUNTAINS = new BiomeWrapper(13);
	public static final Biome MUSHROOM_ISLAND = new BiomeWrapper(14);
	public static final Biome MUSHROOM_SHORE = new BiomeWrapper(15);
	public static final Biome BEACH = new BiomeWrapper(16);
	public static final Biome DESERT_HILLS = new BiomeWrapper(17);
	public static final Biome FOREST_HILLS = new BiomeWrapper(18);
	public static final Biome TAIGA_HILLS = new BiomeWrapper(19);
	public static final Biome SMALL_MOUNTAINS = new BiomeWrapper(20);
	public static final Biome JUNGLE = new BiomeWrapper(21);
	public static final Biome JUNGLE_HILLS = new BiomeWrapper(22);

	/**
	 * Gets the id.
	 * @return id
	 */
	public int getId();

	/**
	 * Gets the temperature.
	 * @return temperature
	 */
	public float getTemperature();

	/**
	 * Clears all the spawns for the biome.
	 */
	public void clearSpawns();

	/**
	 * Clears all the spawns for the type.
	 * @param type
	 */
	public void clearSpawns(EnumCreatureType type);

	/**
	 * Gets the spawn metas for the type.
	 * @param type
	 * @return metas
	 */
	public List<BiomeMeta> getSpawns(EnumCreatureType type);

	/**
	 * Adds a new spawn meta.
	 * @param type
	 * @param meta
	 */
	public void addSpawn(EnumCreatureType type, BiomeMeta meta);

	/**
	 * Gets the minimal height.
	 * @return height
	 */
	public float getMinHeight();

	/**
	 * Gets the maximal height.
	 * @return height
	 */
	public float getMaxHeight();

	/**
	 * Gets the id of the top block.
	 * @return id
	 */
	public int getTopBlock();

	/**
	 * Gets the id of the filling block.
	 * @return id
	 */
	public int getFillingBlock();

	/**
	 * Gets if there can villages spawn in the biome.
	 * @return villager
	 */
	public boolean canGenerateVillages();
}