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

public class Biomes {
	private static BiomeAPI API;

	/**
	 * Gets the plugin without checking if its enabled.
	 * @return api
	 */
	public static BiomeAPI get() {
		return Biomes.API;
	}

	/**
	 * Sets the api, called when the api is enabled.
	 * @param api
	 */
	public static void set(BiomeAPI api) {
		Biomes.API = api;
	}
}