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

import org.bukkit.World;

public class BiomeDecorator {
	private final Map<Setting, Object> settings = new HashMap<Setting, Object>();
	private final BiomeBase biome;

	public BiomeDecorator(BiomeBase biome) {
		this.biome = biome;

		this.set(Setting.CACTI, -999);
		this.set(Setting.DEATH_BUSH, -999);
		this.set(Setting.REEDS, -999);
		this.set(Setting.MUSHROOMS, -999);
		this.set(Setting.WATERLILY, -999);
		this.set(Setting.BIG_MUSHROOMS, -999);
		this.set(Setting.TREES, -999);
		this.set(Setting.SAND_2, 3);
		this.set(Setting.FLOWERS, 2);
		this.set(Setting.GRASS, 1);
		this.set(Setting.SAND, 1);
		this.set(Setting.CLAY, 1);
		this.set(Setting.WATER_LAKES, 1);
		this.set(Setting.LAVA_LAKES, 1);
	}

	/**
	 * Gets the biome.
	 * @return biome
	 */
	public BiomeBase getBiome() {
		return this.biome;
	}

	/**
	 * Gets the setting for the world decorator.
	 * @param setting
	 * @return value
	 */
	public Object get(Setting setting) {
		return this.settings.get(setting);
	}

	/**
	 * Gets the setting for the world decorator.
	 * @param setting
	 * @param type
	 * @return value
	 */
	public <T> T get(Setting setting, Class<T> type) {
		return type.cast(this.get(setting));
	}

	/**
	 * Sets the setting for the world decorator.
	 * @param setting
	 * @param value
	 */
	public void set(Setting setting, Object value) {
		this.settings.put(setting, value);
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
	 * Settings that are available in the default world decorator.
	 */
	public enum Setting {
		WATERLILY,
		TREES,
		FLOWERS,
		GRASS,
		DEATH_BUSH,
		MUSHROOMS,
		REEDS,
		CACTI,
		SAND,
		SAND_2,
		CLAY,
		BIG_MUSHROOMS,
		WATER_LAKES,
		LAVA_LAKES
	}
}