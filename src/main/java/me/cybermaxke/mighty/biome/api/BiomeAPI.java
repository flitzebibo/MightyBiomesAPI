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

import org.bukkit.World;

public interface BiomeAPI {

	/**
	 * Gets a new biome.
	 * @return biome
	 */
	public BiomeBase getNew(int id);

	/**
	 * Removes a biome.
	 * @param biome
	 * @return biome
	 */
	public BiomeBase remove(int id);

	/**
	 * Adds a biome to the worlds generator.
	 * @param world
	 * @param biome
	 */
	public void add(World world, BiomeBase biome);

	/**
	 * Adds biomes to the worlds generator.
	 * @param world
	 * @param biomes
	 */
	public void addAll(World world, List<BiomeBase> biomes);

	/**
	 * Removes a biome from the worlds generator.
	 * @param world
	 * @param biome
	 */
	public void remove(World world, BiomeBase biome);

	/**
	 * Clears the biomes of the worlds generator.
	 * @param world
	 * @param biome
	 */
	public void clear(World world);

	/**
	 * Gets all the biomes in the world.
	 * @param world
	 * @return biomes
	 */
	public List<BiomeBase> getAll(World world);

	/**
	 * Gets a biome using its id.
	 * @param id
	 * @return biome
	 */
	public BiomeBase get(int id);

	/**
	 * Gets the biome id at the location.
	 * @param world
	 * @param x
	 * @param z
	 * @return biomeId
	 */
	public int getId(World world, int x, int z);

	/**
	 * Gets the biome at the location.
	 * @param world
	 * @param x
	 * @param z
	 * @return biome
	 */
	public BiomeBase get(World world, int x, int z);

	/**
	 * Sets the biome at the location.
	 * @param world
	 * @param x
	 * @param z
	 * @param biome
	 */
	public void set(World world, BiomeBase biome, int x, int z);
}