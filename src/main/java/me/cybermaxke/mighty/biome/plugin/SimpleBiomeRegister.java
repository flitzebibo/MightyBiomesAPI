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
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.WorldType;

import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.block.CraftBlock;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.Chunk;
import net.minecraft.server.v1_6_R3.ChunkProviderGenerate;
import net.minecraft.server.v1_6_R3.GenLayer;
import net.minecraft.server.v1_6_R3.GenLayerRiverMix;
import net.minecraft.server.v1_6_R3.WorldChunkManager;
import net.minecraft.server.v1_6_R3.WorldGenVillage;
import net.minecraft.server.v1_6_R3.WorldProvider;
import net.minecraft.server.v1_6_R3.WorldServer;

import me.cybermaxke.mighty.biome.api.BiomeAPI;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayer;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerBiome;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerZoom1;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerZoom2;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenVillage;

public class SimpleBiomeRegister implements BiomeAPI {
	private final Map<Integer, SimpleBiomeBase> biomes = new HashMap<Integer, SimpleBiomeBase>();
	private final Map<World, SimpleBiomeWorld> worlds = new HashMap<World, SimpleBiomeWorld>();

	public void load() {
		for (BiomeBase biome : BiomeBase.biomes) {
			if (biome != null) {
				this.biomes.put(biome.id, new SimpleBiomeBaseDefault(biome));
			}
		}

		this.get(BiomeBase.DESERT.id).setSandstoneVillages(true);
		this.get(BiomeBase.DESERT_HILLS.id).setSandstoneVillages(true);

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
			BiomeBase[] array2 = new BiomeBase[BiomeBase.biomes.length];

			for (int i = 0; i < array1.length; i++) {
				array2[i] = array1[i];
			}

			field1.set(null, array2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clean() {
		List<SimpleBiomeBase> biomes = new ArrayList<SimpleBiomeBase>(this.biomes.values());

		for (SimpleBiomeBase biome : biomes) {
			if (biome instanceof SimpleBiomeBase) {
				this.remove(biome.getId());
			}
		}
	}

	@Override
	public SimpleBiomeWorld getWorld(World world) {
		if (this.worlds.containsKey(world)) {
			return this.worlds.get(world);
		}

		SimpleBiomeWorld world1 = new SimpleBiomeWorld(world, this);
		this.update(world);
		this.worlds.put(world, world1);

		return world1;
	}

	@Override
	public SimpleBiomeBase getNew(int id) {
		if (id >= BiomeBase.biomes.length) {
			throw new IllegalArgumentException("The biome id has to be smaller then " +
					BiomeBase.biomes.length + "");
		}

		if (BiomeBase.biomes[id] != null) {
			throw new IllegalArgumentException("Duplicate id!");
		}

		BiomeBase biome1 = new BiomeBase(id) {};
		SimpleBiomeBase biome2 = new SimpleBiomeBase(biome1);

		try {
			Field field = CraftBlock.class.getDeclaredField("BIOME_MAPPING");
			field.setAccessible(true);

			Field field1 = CraftBlock.class.getDeclaredField("BIOMEBASE_MAPPING");
			field1.setAccessible(true);

			Object[] array = ((Object[]) field.get(null));
			BiomeBase[] array1 = ((BiomeBase[]) field1.get(null));

			array[id] = CraftBlock.biomeBaseToBiome(BiomeBase.BEACH);
			array1[id] = biome1;

			this.biomes.put(id, biome2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return biome2;
	}

	@Override
	public SimpleBiomeBase remove(int id) {
		SimpleBiomeBase biome = this.get(id);

		if (biome == null) {
			return null;
		}

		if (biome instanceof SimpleBiomeBaseDefault) {
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
		return (SimpleBiomeBase) biome;
	}

	@Override
	public SimpleBiomeBase get(int id) {
		return this.biomes.get(id);
	}

	public void update(World world) {
		WorldChunkManager manager = this.getChunkManager(world);
		GenLayer main = this.getMainLayer(manager);

		/**
		 * Already updated...
		 */
		if (this.getLayer(main, SimpleGenLayerBiome.class) != null ||
				world.getWorldType().equals(WorldType.FLAT)) {
			return;
		}

		int size = this.getDefaultSize(world);
		long seed = world.getSeed();

		List<me.cybermaxke.mighty.biome.api.BiomeBase> biomes =
				new ArrayList<me.cybermaxke.mighty.biome.api.BiomeBase>();

		switch (world.getWorldType()) {
			case FLAT:
				break;
			case LARGE_BIOMES:
			case NORMAL:
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.JUNGLE);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.JUNGLE_HILLS);
			case VERSION_1_1:
			default:
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.DESERT);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.DESERT_HILLS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.FOREST);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.FOREST_HILLS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.EXTREME_HILLS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.SWAMPLAND);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.PLAINS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.ICE_PLAINS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.ICE_MOUNTAINS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.TAIGA);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.TAIGA_HILLS);
				biomes.add(me.cybermaxke.mighty.biome.api.BiomeBase.MUSHROOM_ISLAND);
		}

		this.getProvider(world);
		this.setLayers(manager, SimpleGenLayer.getLayers(biomes, seed, size));
		this.getWorldGenVillage(world);
	}

	public void add(World world, me.cybermaxke.mighty.biome.api.BiomeBase biome) {
		this.addAll(world, Arrays.asList(biome));
	}

	public void addAll(World world, Collection<me.cybermaxke.mighty.biome.api.BiomeBase> biomes) {
		List<me.cybermaxke.mighty.biome.api.BiomeBase> biomes1 = this.getBiomes(world);
		List<BiomeBase> biomes2 = this.getSpawnBiomeList(world);

		for (me.cybermaxke.mighty.biome.api.BiomeBase biome : biomes) {
			if (biomes1.contains(biome)) {
				continue;
			}

			BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
			if (biome1 == null) {
				throw new IllegalArgumentException("Biome " + biome.getId() +
						" isn't registered!");
			}

			if (biome.getSpawnable() && !biomes2.contains(biome1)) {
				biomes2.add(biome1);
			}

			biomes1.add(biome);
		}
	}

	public void remove(World world, me.cybermaxke.mighty.biome.api.BiomeBase biome) {
		BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
		if (biome1 == null) {
			throw new IllegalArgumentException("Biome isn't registered!");
		}

		this.getSpawnBiomeList(world).remove(biome1);
		this.getBiomes(world).remove(biome);
	}

	public void removeAll(World world, Collection<me.cybermaxke.mighty.biome.api.BiomeBase> biomes) {
		List<me.cybermaxke.mighty.biome.api.BiomeBase> biomes1 = this.getBiomes(world);
		List<BiomeBase> biomes2 = this.getSpawnBiomeList(world);

		for (me.cybermaxke.mighty.biome.api.BiomeBase biome : biomes) {
			BiomeBase biome1 = BiomeBase.biomes[biome.getId()];
			if (biome1 == null) {
				throw new IllegalArgumentException("Biome " + biome.getId() +
						" isn't registered!");
			}

			if (biomes2.contains(biome1)) {
				biomes2.remove(biome1);
			}

			biomes1.remove(biome);
		}
	}

	public void clear(World world) {
		this.getSpawnBiomeList(world).clear();
		this.getBiomes(world).clear();
	}

	public List<me.cybermaxke.mighty.biome.api.BiomeBase> getAll(World world) {
		return this.getBiomes(world);
	}

	public int getId(World world, int x, int z) {
		BiomeBase biome = ((CraftWorld) world).getHandle().getBiome(x, z);
		return biome == null ? -1 : biome.id;
	}

	public me.cybermaxke.mighty.biome.api.BiomeBase get(World world, int x, int z) {
		int id = this.getId(world, x, z);
		return id == -1 ? null : this.biomes.get(id);
	}

	public void set(World world, me.cybermaxke.mighty.biome.api.BiomeBase biome, int x, int z) {
		Chunk chunk = ((CraftWorld) world).getHandle().getChunkAtWorldCoords(x, z);

		if (chunk != null) {
			chunk.m()[(z & 0xF) << 4 | x & 0xF] = (byte) biome.getId();
		}
	}

	public int getDefaultSize(World world) {
		switch (world.getWorldType()) {
			case LARGE_BIOMES:
				return 6;
			default:
				return 4;
		}
	}

	public List<me.cybermaxke.mighty.biome.api.BiomeBase> getBiomes(World world) {
		GenLayer main = this.getMainLayer(this.getChunkManager(world));

		return this.getLayer(main, SimpleGenLayerBiome.class).getBiomes();
	}

	public void setBiomeSize(World world, int size) {
		GenLayer main = this.getMainLayer(this.getChunkManager(world));

		this.getLayer(main, SimpleGenLayerZoom1.class).setAmount(size);
		this.getLayer(main, SimpleGenLayerZoom2.class).setAmount(size);
	}

	public int getBiomeSize(World world) {
		GenLayer main = this.getMainLayer(this.getChunkManager(world));

		return this.getLayer(main, SimpleGenLayerZoom1.class).getAmount();
	}

	public SimpleGenLayerBiome getBiomeLayer(World world) {
		GenLayer main = this.getMainLayer(this.getChunkManager(world));
		return this.getLayer(main, SimpleGenLayerBiome.class);
	}

	public SimpleWorldGenVillage getWorldGenVillage(World world) {
		try {
			ChunkProviderGenerate provider = this.getChunkProviderGenerate(world);

			Field field1 = ChunkProviderGenerate.class.getDeclaredField("v");
			field1.setAccessible(true);

			WorldGenVillage gen1 = (WorldGenVillage) field1.get(provider);
			if (gen1 instanceof SimpleWorldGenVillage) {
				return (SimpleWorldGenVillage) gen1;
			}

			SimpleWorldGenVillage gen2 = new SimpleWorldGenVillage();
			if (gen1 != null) {
				Class<?> clazz = gen1.getClass();

				while (clazz != null) {
					for (Field field2 : clazz.getDeclaredFields()) {
						field2.setAccessible(true);
						field2.set(gen2, field2.get(gen1));
					}

					clazz = clazz.getSuperclass();
				}
			}

			field1.set(provider, gen2);
			return gen2;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ChunkProviderGenerate getChunkProviderGenerate(World world) {
		WorldServer world1 = ((CraftWorld) world).getHandle();
		return (ChunkProviderGenerate) world1.chunkProviderServer.chunkProvider;
	}

	public SimpleBiomeWorldProvider getProvider(World world) {
		WorldProvider provider1 = ((CraftWorld) world).getHandle().worldProvider;
		if (provider1 instanceof SimpleBiomeWorldProvider) {
			return (SimpleBiomeWorldProvider) provider1;
		}

		SimpleBiomeWorldProvider provider2 = new SimpleBiomeWorldProvider(provider1);
		((CraftWorld) world).getHandle().worldProvider = provider2;

		return provider2;
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

	public List<BiomeBase> getBiomes(List<me.cybermaxke.mighty.biome.api.BiomeBase> biomes) {
		List<BiomeBase> biomes1 = new ArrayList<BiomeBase>();

		for (me.cybermaxke.mighty.biome.api.BiomeBase biome : biomes) {
			BiomeBase biome1 = BiomeBase.biomes[biome.getId()];

			if (biome1 != null) {
				biomes1.add(biome1);
			}
		}

		return biomes1;
	}
}