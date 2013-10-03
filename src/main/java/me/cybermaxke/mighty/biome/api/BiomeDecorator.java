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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.gen.WorldGen;

import org.bukkit.World;

public class BiomeDecorator {
	private final Map<Setting, Integer> settings = new HashMap<Setting, Integer>();

	/**
	 * Gets the setting for the world decorator.
	 * @param setting
	 * @return value
	 */
	public int get(Setting setting) {
		return this.settings.get(setting);
	}

	/**
	 * Sets the setting for the world decorator.
	 * @param setting
	 * @param value
	 */
	public void set(Setting setting, int value) {
		this.settings.put(setting, value);
	}

	/**
	 * Called by the world generator to decorate the biome. Before any other grass, trees, flowers,
	 * etc are generated.
	 * @param world
	 * @param random
	 * @param chunkX
	 * @param chunkZ
	 */
	public void onPreDecorate(World world, Random random, int chunkX, int chunkZ) {

	}

	/**
	 * Called by the world generator to decorate the biome.
	 * @param world
	 * @param random
	 * @param chunkX
	 * @param chunkZ
	 */
	public void onDecorate(World world, Random random, int chunkX, int chunkZ) {

	}

	/**
	 * Gets the world gen for trees,
	 * if its 'null' then the default will be used.
	 * @param random
	 * @return worldGen
	 */
	public WorldGen getWorldGenTrees(Random random) {
		return null;
	}

	/**
	 * Gets the world gen for grass,
	 * if its 'null' then the default will be used.
	 * @param random
	 * @return worldGen
	 */
	public WorldGen getWorldGenGrass(Random random) {
		return null;
	}

	/**
	 * Settings that are available in the default world decorator.
	 */
	public enum Setting {
		WATERLILY,
		TREES,
		YELLOW_FLOWERS,
		RED_FLOWERS,
		GRASS,
		DEATH_BUSH,
		BROWN_MUSHROOMS,
		RED_MUSHROOMS,
		REEDS,
		CACTI,
		SAND,
		SAND_2,
		CLAY,
		BIG_MUSHROOMS,
		WATER_LAKES,
		LAVA_LAKES,
		PUMPKINS,
		WATER_LIQUIDS,
		LAVA_LIQUIDS
	}
}