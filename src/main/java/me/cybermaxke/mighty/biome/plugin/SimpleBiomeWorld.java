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
package me.cybermaxke.mighty.biome.plugin;

import java.util.Collection;
import java.util.List;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.block.Block;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.BiomeWorld;

public class SimpleBiomeWorld implements BiomeWorld {
	private final SimpleBiomeRegister register;
	private final World world;

	public SimpleBiomeWorld(World world, SimpleBiomeRegister register) {
		this.world = world;
		this.register = register;
	}

	@Override
	public World getWorld() {
		return this.world;
	}

	@Override
	public List<BiomeBase> getAll() {
		if (this.isFlat()) {
			return null;
		}

		return this.register.getAll(this.world);
	}

	@Override
	public void add(BiomeBase biome) {
		if (this.isFlat()) {
			return;
		}

		this.register.add(this.world, biome);
	}

	@Override
	public void addAll(Collection<BiomeBase> biomes) {
		if (this.isFlat()) {
			return;
		}

		this.register.addAll(this.world, biomes);
	}

	@Override
	public void remove(BiomeBase biome) {
		if (this.isFlat()) {
			return;
		}

		this.register.remove(this.world, biome);
	}

	@Override
	public void removeAll(Collection<BiomeBase> biomes) {
		if (this.isFlat()) {
			return;
		}

		this.register.removeAll(this.world, biomes);
	}

	@Override
	public int getBiomeSize() {
		if (this.isFlat()) {
			return 4;
		}

		return this.register.getBiomeSize(this.world);
	}

	@Override
	public void setBiomeSize(int size) {
		if (this.isFlat()) {
			return;
		}

		this.register.setBiomeSize(this.world, size);
	}

	@Override
	public int getSeaLevel() {
		if (this.isFlat()) {
			return 4;
		}

		return this.register.getProvider(this.world).getSeaLevel();
	}

	@Override
	public void setSeaLevel(int seaLevel) {
		if (this.isFlat()) {
			return;
		}

		this.register.getProvider(this.world).setSeaLevel(seaLevel);
	}

	@Override
	public BiomeBase get(int x, int z) {
		return this.register.get(this.world, x, z);
	}

	@Override
	public void set(int x, int z, BiomeBase biome) {
		this.register.set(this.world, biome, x, z);
	}

	@Override
	public BiomeBase get(Block block) {
		return this.get(block.getX(), block.getZ());
	}

	@Override
	public void set(Block block, BiomeBase biome) {
		this.set(block.getX(), block.getZ(), biome);
	}

	@Override
	public Environment getEnvironment() {
		return Environment.getEnvironment(this.register.getProvider(this.world).dimension);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.register.getProvider(this.world).dimension = environment.getId();
	}

	public boolean isFlat() {
		return this.world.getWorldType().equals(WorldType.FLAT);
	}
}