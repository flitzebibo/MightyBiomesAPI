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

import me.cybermaxke.mighty.biome.api.treasure.TreasureRegister;

import org.bukkit.World;

public interface BiomeAPI {

	/**
	 * Gets a new biome.
	 * @return biome
	 */
	public BiomeBase getNew(int id);

	/**
	 * Gets a new cloned biome with the id.
	 * @param id
	 * @param biome
	 * @return clone
	 */
	public BiomeBase getDefaultClone(int id, BiomeBase biome);

	/**
	 * Removes a biome.
	 * @param biome
	 * @return biome
	 */
	public BiomeBase remove(int id);

	/**
	 * Gets a biome using its id.
	 * @param id
	 * @return biome
	 */
	public BiomeBase get(int id);

	/**
	 * Gets all the available biomes.
	 * @return biomes
	 */
	public List<BiomeBase> getAll();

	/**
	 * Gets the biome world for the world.
	 * @param world
	 * @return biomeWorld
	 */
	public BiomeWorld getWorld(World world);

	/**
	 * Gets a register you can use to modify treasures in structures.
	 * @return treasureManager
	 */
	public TreasureRegister getTreasureRegister();
}