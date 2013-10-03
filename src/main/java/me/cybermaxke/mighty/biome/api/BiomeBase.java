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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;
import me.cybermaxke.mighty.biome.api.gen.WorldGen;

import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Zombie;

public class BiomeBase implements Biome {
	private final Map<EnumCreatureType, List<BiomeMeta>> meta =
			new HashMap<EnumCreatureType, List<BiomeMeta>>();

	private final int id;

	private Material topBlock = Material.GRASS;
	private Material fillingBlock = Material.DIRT;

	private int color = 4;

	private float minHeight = 0.1F;
	private float maxHeight = 0.3F;
	private float temperature = 0.5F;

	public BiomeBase(int id) {
		this.id = id;

		this.clearSpawns();
		this.addSpawn(EnumCreatureType.CREATURE, new BiomeMeta(Sheep.class, 12, 4, 4));
		this.addSpawn(EnumCreatureType.CREATURE, new BiomeMeta(Pig.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.CREATURE, new BiomeMeta(Chicken.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.CREATURE, new BiomeMeta(Cow.class, 8, 4, 4));
		this.addSpawn(EnumCreatureType.MONSTER, new BiomeMeta(Spider.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.MONSTER, new BiomeMeta(Zombie.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.MONSTER, new BiomeMeta(Skeleton.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.MONSTER, new BiomeMeta(Creeper.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.MONSTER, new BiomeMeta(Enderman.class, 1, 1, 4));
		this.addSpawn(EnumCreatureType.WATER_CREATURE, new BiomeMeta(Squid.class, 10, 4, 4));
		this.addSpawn(EnumCreatureType.AMBIENT, new BiomeMeta(Bat.class, 10, 8, 8));
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public boolean canGenerateVillages() {
		return false;
	}

	@Override
	public void clearSpawns() {
		for (EnumCreatureType type : EnumCreatureType.values()) {
			this.clearSpawns(type);
		}
	}

	@Override
	public void clearSpawns(EnumCreatureType type) {
		this.meta.put(type, new ArrayList<BiomeMeta>());
	}

	@Override
	public List<BiomeMeta> getSpawns(EnumCreatureType type) {
		return this.meta.get(type);
	}

	@Override
	public void addSpawn(EnumCreatureType type, BiomeMeta meta) {
		this.meta.get(type).add(meta);
	}

	@Override
	public float getMinHeight() {
		return this.minHeight;
	}

	@Override
	public float getMaxHeight() {
		return this.maxHeight;
	}

	@Override
	public float getTemperature() {
		return this.temperature;
	}

	@Override
	public Material getTopBlock() {
		return this.topBlock;
	}

	@Override
	public Material getFillingBlock() {
		return this.fillingBlock;
	}

	/**
	 * Gets the color from the mc biome.
	 * @return color
	 */
	public int getBiomeColor() {
		return this.color;
	}

	/**
	 * Sets the color of the mc biome.
	 * @param color
	 */
	public void setBiomeColor(int color) {
		this.color = color;
	}

	/**
	 * Sets the minimal height.
	 * @param height
	 */
	public void setMinHeight(float height) {
		this.minHeight = height;
	}

	/**
	 * Sets the maximal height.
	 * @param height
	 */
	public void setMaxHeight(float height) {
		this.maxHeight = height;
	}

	/**
	 * Sets the temperature.
	 * @param temperature
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	/**
	 * Sets the material of the top block.
	 * @param material
	 */
	public void setTopBlock(Material material) {
		this.topBlock = material;
	}

	/**
	 * Sets the material of the filling block.
	 * @param material
	 */
	public void setFillingBlock(Material material) {
		this.fillingBlock = material;
	}

	/**
	 * Gets the biome decorator.
	 * @return decorator
	 */
	public BiomeDecorator getNewDecorator() {
		return new BiomeDecorator(this);
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
}