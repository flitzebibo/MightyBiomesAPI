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

import org.bukkit.Material;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

public interface BiomeBase {
	public static final BiomeBase OCEAN = new BiomeWrapper(0);
	public static final BiomeBase PLAINS = new BiomeWrapper(1);
	public static final BiomeBase DESERT = new BiomeWrapper(2);
	public static final BiomeBase EXTREME_HILLS = new BiomeWrapper(3);
	public static final BiomeBase FOREST = new BiomeWrapper(4);
	public static final BiomeBase TAIGA = new BiomeWrapper(5);
	public static final BiomeBase SWAMPLAND = new BiomeWrapper(6);
	public static final BiomeBase RIVER = new BiomeWrapper(7);
	public static final BiomeBase NETHER = new BiomeWrapper(8);
	public static final BiomeBase THE_END = new BiomeWrapper(9);
	public static final BiomeBase FROZEN_OCEAN = new BiomeWrapper(10);
	public static final BiomeBase FROZEN_RIVER = new BiomeWrapper(11);
	public static final BiomeBase ICE_PLAINS = new BiomeWrapper(12);
	public static final BiomeBase ICE_MOUNTAINS = new BiomeWrapper(13);
	public static final BiomeBase MUSHROOM_ISLAND = new BiomeWrapper(14);
	public static final BiomeBase MUSHROOM_SHORE = new BiomeWrapper(15);
	public static final BiomeBase BEACH = new BiomeWrapper(16);
	public static final BiomeBase DESERT_HILLS = new BiomeWrapper(17);
	public static final BiomeBase FOREST_HILLS = new BiomeWrapper(18);
	public static final BiomeBase TAIGA_HILLS = new BiomeWrapper(19);
	public static final BiomeBase SMALL_MOUNTAINS = new BiomeWrapper(20);
	public static final BiomeBase JUNGLE = new BiomeWrapper(21);
	public static final BiomeBase JUNGLE_HILLS = new BiomeWrapper(22);

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
	 * Sets the temperature.
	 * @param temperature
	 */
	public void setTemperature(float temperature);

	/**
	 * Gets the humidity
	 * @return humidity
	 */
	public float getHumitidy();

	/**
	 * Sets the humidity
	 * @param humidity
	 */
	public void setHumitidy(float humidity);

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
	 * Gets the color from the mc biome.
	 * @return color
	 */
	public int getBiomeColor();

	/**
	 * Sets the color of the mc biome.
	 * @param color
	 */
	public void setBiomeColor(int color);

	/**
	 * Gets the minimal height.
	 * @return height
	 */
	public float getMinHeight();

	/**
	 * Sets the minimal height.
	 * @param height
	 */
	public void setMinHeight(float height);

	/**
	 * Gets the maximal height.
	 * @return height
	 */
	public float getMaxHeight();

	/**
	 * Sets the maximal height.
	 * @param height
	 */
	public void setMaxHeight(float height);

	/**
	 * Gets the material of the top block.
	 * @return material
	 */
	public Material getTopBlock();

	/**
	 * Sets the material of the top block.
	 * @param material
	 */
	public void setTopBlock(Material material);

	/**
	 * Gets the material of the filling block.
	 * @return material
	 */
	public Material getFillingBlock();

	/**
	 * Sets the material of the filling block.
	 * @param material
	 */
	public void setFillingBlock(Material material);

	/**
	 * Gets if there can villages spawn in the biome.
	 * @return villager
	 */
	public boolean getGenerateVillages();

	/**
	 * Sets if there can villages spawn in the biome.
	 * @param villager
	 */
	public void setGenerateVillages(boolean generate);

	/**
	 * Gets if players are allowed to spawn in this biome.
	 * @return spawnable
	 */
	public boolean getSpawnable();

	/**
	 * Sets if players are allowed to spawn in this biome.
	 * @param spawnable
	 */
	public void setSpawnable(boolean spawnable);

	/**
	 * Gets the biome decorator.
	 * @return decorator
	 */
	public BiomeDecorator getDecorator();

	/**
	 * Sets the biome decorator.
	 * @param decorator
	 */
	public void setDecorator(BiomeDecorator decorator);
}