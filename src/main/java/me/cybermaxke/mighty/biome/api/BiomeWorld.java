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

import java.util.Collection;
import java.util.List;

import org.bukkit.World;
import org.bukkit.block.Block;

public interface BiomeWorld {

	/**
	 * Gets the parent world.
	 * @return world
	 */
	public World getWorld();

	/**
	 * Gets the biomes that are added to the world gen.
	 * @return biomes
	 */
	public List<BiomeBase> getAll();

	/**
	 * Adds a biome to the world gen.
	 * @param biome
	 */
	public void add(BiomeBase biome);

	/**
	 * Adds a collection of biomes to the world gen.
	 * @param biomes
	 */
	public void addAll(Collection<BiomeBase> biomes);

	/**
	 * Removes a biome from the world gen.
	 * @param biome
	 */
	public void remove(BiomeBase biome);

	/**
	 * Removes a collection of biomes from the world gen.
	 * @param biomes
	 */
	public void removeAll(Collection<BiomeBase> biomes);

	/**
	 * Gets the size of biomes. Normal worlds have by default '4'
	 * and large biomes worlds by default '6'.
	 * @return size
	 */
	public int getBiomeSize();

	/**
	 * Sets the size of biomes. Normal worlds have by default '4'
	 * and large biomes worlds by default '6'.
	 * @param size
	 */
	public void setBiomeSize(int size);

	/**
	 * Gets the biome at the x and y position.
	 * @param x
	 * @param z
	 * @return biome
	 */
	public BiomeBase get(int x, int z);

	/**
	 * Sets the biome at the x and y position.
	 * @param x
	 * @param z
	 * @param biome
	 */
	public void set(int x, int z, BiomeBase biome);

	/**
	 * Gets the biome of the block position.
	 * @param block
	 * @return biome
	 */
	public BiomeBase get(Block block);

	/**
	 * Sets the biome at the block position.
	 * @param block
	 * @return biome
	 */
	public void set(Block block, BiomeBase biome);
}