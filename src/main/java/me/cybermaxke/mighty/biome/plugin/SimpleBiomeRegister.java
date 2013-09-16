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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

import org.bukkit.craftbukkit.v1_6_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R2.block.CraftBlock;

import net.minecraft.server.v1_6_R2.BiomeBase;
import net.minecraft.server.v1_6_R2.Chunk;
import net.minecraft.server.v1_6_R2.GenLayer;
import net.minecraft.server.v1_6_R2.GenLayerRiverMix;
import net.minecraft.server.v1_6_R2.WorldChunkManager;
import net.minecraft.server.v1_6_R2.WorldGenVillage;

import me.cybermaxke.mighty.biome.api.Biome;
import me.cybermaxke.mighty.biome.api.BiomeAPI;
import me.cybermaxke.mighty.biome.api.BiomeWrapper;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayer;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerBiome;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerData;

public class SimpleBiomeRegister implements BiomeAPI {
	private final Map<Integer, Biome> biomes = new HashMap<Integer, Biome>();

	public void load() {
		for (BiomeBase biome : BiomeBase.biomes) {
			if (biome != null && !(biome instanceof SimpleBiomeBase)) {
				this.biomes.put(biome.id, new SimpleBiome(biome));
			}
		}

		/**
		 * Increase the maximum mapping size.
		 */
		try {
			Field field = CraftBlock.class.getDeclaredField("BIOME_MAPPING");
			field.setAccessible(true);

			Field field1 = CraftBlock.class.getDeclaredField("BIOMEBASE_MAPPING");
			field1.setAccessible(true);

			Field mfield = Field.class.getDeclaredField("modifiers");
			mfield.setAccessible(true);
			mfield.set(field, field.getModifiers() & ~Modifier.FINAL);
			mfield.set(field1, field1.getModifiers() & ~Modifier.FINAL);

			BiomeBase[] array1 = ((BiomeBase[]) field1.get(null));
			BiomeBase[] array2 = new BiomeBase[256];

			for (int i = 0; i < array1.length; i++) {
				array2[i] = array1[i];
			}

			field1.set(null, array2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clean() {
		List<Biome> biomes = new ArrayList<Biome>(this.biomes.values());
		for (Biome biome : biomes) {
			if (biome instanceof me.cybermaxke.mighty.biome.api.BiomeBase) {
				this.remove(biome.getId());
			}
		}
	}

	@Override
	public <T extends me.cybermaxke.mighty.biome.api.BiomeBase> T register(T biome) {
		if (BiomeBase.biomes[biome.getId()] != null) {
			throw new IllegalArgumentException("Duplicate id!");
		}

		try {
			Field field = CraftBlock.class.getDeclaredField("BIOME_MAPPING");
			field.setAccessible(true);

			Field field1 = CraftBlock.class.getDeclaredField("BIOMEBASE_MAPPING");
			field1.setAccessible(true);

			Object[] array = ((Object[]) field.get(null));
			BiomeBase[] array1 = ((BiomeBase[]) field1.get(null));

			if (array1[biome.getId()] != null) {
				throw new IllegalArgumentException("Duplicate Id!");
			}

			array[biome.getId()] = CraftBlock
					.biomeBaseToBiome(BiomeBase.biomes[biome.getBiomeColor()]);
			array1[biome.getId()] = BiomeBase.BEACH;

			SimpleBiomeBase biome1 = new SimpleBiomeBase(biome);
			array1[biome.getId()] = biome1;

			this.biomes.put(biome.getId(), biome);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return biome;
	}

	@Override
	public me.cybermaxke.mighty.biome.api.BiomeBase remove(int id) {
		Biome biome = this.get(id);

		if (biome == null) {
			return null;
		}

		if (biome instanceof SimpleBiome || biome instanceof BiomeWrapper) {
			throw new IllegalArgumentException("You can't remove a default biome!");
		}

		try {
			Field field = CraftBlock.class.getDeclaredField("BIOME_MAPPING");
			field.setAccessible(true);

			Object[] array = ((Object[]) field.get(null));

			array[id] = null;
			BiomeBase.biomes[id] = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.biomes.remove(id);
		return (me.cybermaxke.mighty.biome.api.BiomeBase) biome;
	}

	@Override
	public Biome get(int id) {
		return this.biomes.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(World world, Biome biome) {
		BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
		if (biome1 == null) {
			throw new IllegalArgumentException("Biome isn't registered!");
		}

		SimpleGenLayerBiome layer = this.getBiomeLayer(world);

		List<BiomeBase> biomes1 = layer.getBiomes();
		List<BiomeBase> biomes = new ArrayList<BiomeBase>();
		if (biomes1 != null) {
			biomes.addAll(biomes1);
		}

		biomes.add(biome1);
		this.setBiomes(world, biomes);
		if (biome instanceof BiomeWrapper) {
			this.getSpawnBiomeList(world).add(biome1);
		}

		if (biome.canGenerateVillages()) {
			if (!WorldGenVillage.e.contains(biome1)) {
				WorldGenVillage.e.add(biome1);
			}
		}
	}

	@Override
	public void addAll(World world, List<Biome> biomes) {
		SimpleGenLayerBiome layer = this.getBiomeLayer(world);

		List<BiomeBase> biomes1 = layer.getBiomes();
		List<BiomeBase> biomes2 = new ArrayList<BiomeBase>();
		if (biomes1 != null) {
			biomes2.addAll(biomes1);
		}
		
		for (Biome biome : biomes) {
			BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
			if (biome1 == null) {
				throw new IllegalArgumentException("Biome " + biome.getId() +
						" isn't registered!");
			}

			biomes2.add(biome1);
			if (biome instanceof BiomeWrapper) {
				this.getSpawnBiomeList(world).add(biome1);
			}
		}

		this.setBiomes(world, biomes2);
	}

	@Override
	public void remove(World world, Biome biome) {
		BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
		if (biome1 == null) {
			throw new IllegalArgumentException("Biome isn't registered!");
		}

		SimpleGenLayerBiome layer = this.getBiomeLayer(world);

		List<BiomeBase> biomes = new ArrayList<BiomeBase>();
		biomes.addAll(layer.getBiomes());
		biomes.remove(biome1);

		this.getSpawnBiomeList(world).remove(biome1);
		this.setBiomes(world, biomes);
	}

	@Override
	public void clear(World world) {
		this.getSpawnBiomeList(world).clear();
		this.setBiomes(world, new ArrayList<BiomeBase>(), this.getSize(world));
	}

	@Override
	public List<Biome> getAll(World world) {
		List<Biome> biomes = new ArrayList<Biome>();

		SimpleGenLayerBiome layer = this.getBiomeLayer(world);
		for (BiomeBase biome : layer.getBiomes()) {
			Biome biome1 = this.get(biome.id);
			if (biome1 != null) {
				biomes.add(biome1);
			}
		}

		return biomes;
	}

	@Override
	public int getId(World world, int x, int z) {
		BiomeBase biome = ((CraftWorld) world).getHandle().getBiome(x, z);
		return biome == null ? -1 : biome.id;
	}

	@Override
	public Biome get(World world, int x, int z) {
		int id = this.getId(world, x, z);
		return id == -1 ? null : this.biomes.get(id);
	}

	@Override
	public void set(World world, Biome biome, int x, int z) {
		Chunk chunk = ((CraftWorld) world).getHandle().getChunkAtWorldCoords(x, z);

		if (chunk != null) {
			chunk.m()[(z & 0xF) << 4 | x & 0xF] = (byte) biome.getId();
		}
	}

	public int getSize(World world) {
		SimpleGenLayerData layer = this.getDataLayer(world);

		if (layer != null && layer.getSize() > 0) {
			return layer.getSize();
		}

		return this.getSize(world, 0);
	}

	public int getSize(World world, int size) {
		if (size > 0) {
			return size;
		}

		switch (world.getWorldType()) {
			case LARGE_BIOMES:
				return 6;
			default:
				return 4;
		}
	}

	public SimpleGenLayerData getDataLayer(World world) {
		GenLayer main = this.getMainLayer(this.getChunkManager(world));
		return this.getLayer(main, SimpleGenLayerData.class);
	}

	public SimpleGenLayerBiome getBiomeLayer(World world) {
		GenLayer main = this.getMainLayer(this.getChunkManager(world));
		return this.getLayer(main, SimpleGenLayerBiome.class);
	}

	public void setBiomeSize(World world, int size) {
		this.setBiomes(world, this.getBiomeLayer(world).getBiomes(), this.getSize(world, size));
	}

	public void setBiomes(World world, List<BiomeBase> biomes) {
		this.setBiomes(world, biomes, this.getSize(world));
	}

	public void setBiomes(World world, List<BiomeBase> biomes, int size) {
		WorldChunkManager manager = this.getChunkManager(world);
		this.setLayers(manager, SimpleGenLayer.getLayers(biomes, world.getSeed(), size));
	}

	public WorldChunkManager getChunkManager(World world) {
		return ((CraftWorld) world).getHandle().worldProvider.e;
	}

	@SuppressWarnings("unchecked")
	public List<BiomeBase> getSpawnBiomeList(World world) {
		WorldChunkManager manager = this.getChunkManager(world);

		try {
			Field field = WorldChunkManager.class.getDeclaredField("g");
			field.setAccessible(true);
			return (List<BiomeBase>) field.get(manager);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public GenLayer getMainLayer(WorldChunkManager manager) {
		try {
			Field field = WorldChunkManager.class.getDeclaredField("d");
			field.setAccessible(true);

			return (GenLayer) field.get(manager);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setLayers(WorldChunkManager manager, GenLayer[] layers) {
		try {
			Field field1 = WorldChunkManager.class.getDeclaredField("d");
			field1.setAccessible(true);

			Field field2 = WorldChunkManager.class.getDeclaredField("e");
			field2.setAccessible(true);

			field1.set(manager, layers[0]);
			field2.set(manager, layers[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends GenLayer> T getLayer(GenLayer layer, Class<T> clazz) {
		if (clazz.isInstance(layer)) {
			return (T) layer;
		}

		try {
			Field field = GenLayer.class.getDeclaredField("a");
			field.setAccessible(true);

			Field field1 = GenLayerRiverMix.class.getDeclaredField("b");
			field1.setAccessible(true);

			GenLayer layer1 = layer;
			while (layer1 != null) {
				if (clazz.isInstance(layer1)) {
					return (T) layer1;
				} else if (layer1 instanceof GenLayerRiverMix) {
					layer1 = (GenLayer) field1.get(layer1);
				} else {
					layer1 = (GenLayer) field.get(layer1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<BiomeBase> getBiomes(List<Biome> biomes) {
		List<BiomeBase> biomes1 = new ArrayList<BiomeBase>();

		for (Biome biome : biomes) {
			BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
			if (biome1 != null) {
				biomes1.add(biome1);
			}
		}

		return biomes1;
	}
}