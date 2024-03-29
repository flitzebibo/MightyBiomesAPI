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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.WorldType;

import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_7_R1.generator.NormalChunkGenerator;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.BiomeDecorator;
import net.minecraft.server.v1_7_R1.Chunk;
import net.minecraft.server.v1_7_R1.ChunkProviderGenerate;
import net.minecraft.server.v1_7_R1.GenLayer;
import net.minecraft.server.v1_7_R1.GenLayerRiverMix;
import net.minecraft.server.v1_7_R1.WorldChunkManager;
import net.minecraft.server.v1_7_R1.WorldGenFactory;
import net.minecraft.server.v1_7_R1.WorldGenVillage;
import net.minecraft.server.v1_7_R1.WorldGenerator;
import net.minecraft.server.v1_7_R1.WorldProvider;
import net.minecraft.server.v1_7_R1.WorldServer;

import me.cybermaxke.mighty.biome.api.BiomeAPI;
import me.cybermaxke.mighty.biome.api.treasure.TreasureRegister;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayer;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerBiome;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerData;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerZoom1;
import me.cybermaxke.mighty.biome.plugin.gen.layer.SimpleGenLayerZoom2;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenLargeFeatureStart;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenVillageStart;
import me.cybermaxke.mighty.biome.plugin.utils.ReflectionUtils;

public class SimpleBiomeRegister implements BiomeAPI {
	private final Map<Integer, SimpleBiomeBase> biomes = new HashMap<Integer, SimpleBiomeBase>();
	private final Map<World, SimpleBiomeWorld> worlds = new HashMap<World, SimpleBiomeWorld>();

	private final Map<Integer, BiomeBase> backup = new HashMap<Integer, BiomeBase>();

	public void load() {
		List<BiomeBase> stronghold = Arrays.asList(
				BiomeBase.DESERT,
				BiomeBase.FOREST,
				BiomeBase.EXTREME_HILLS,
				BiomeBase.SWAMPLAND,
				BiomeBase.TAIGA,
				BiomeBase.ICE_PLAINS,
				BiomeBase.ICE_MOUNTAINS,
				BiomeBase.DESERT_HILLS,
				BiomeBase.FOREST_HILLS,
				BiomeBase.SMALL_MOUNTAINS,
				BiomeBase.JUNGLE,
				BiomeBase.JUNGLE_HILLS);

		for (BiomeBase biome : BiomeBase.n()) {
			if (biome != null) {
				this.backup.put(biome.id, this.getClone(biome, biome.id));
				this.biomes.put(biome.id, new SimpleBiomeBaseDefault(biome));
			}
		}

		for (BiomeBase biome : stronghold) {
			this.get(biome.id).setGeneratingStronghold(true);
		}

		for (Object biome : WorldGenVillage.e) {
			this.get(((BiomeBase) biome).id).setGeneratingVillages(true);
		}

		this.get(BiomeBase.DESERT.id).setSandstoneVillages(true);
		this.get(BiomeBase.DESERT.id).setGeneratingPyramid(true);
		this.get(BiomeBase.DESERT_HILLS.id).setSandstoneVillages(true);
		this.get(BiomeBase.DESERT_HILLS.id).setGeneratingPyramid(true);
		this.get(BiomeBase.JUNGLE.id).setGeneratingJungleTemple(true);
		this.get(BiomeBase.JUNGLE_HILLS.id).setGeneratingJungleTemple(true);
		this.get(BiomeBase.SWAMPLAND.id).setGeneratingWitchHouse(true);

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
			BiomeBase[] array2 = new BiomeBase[BiomeBase.n().length];

			for (int i = 0; i < array1.length; i++) {
				array2[i] = array1[i];
			}

			field1.set(null, array2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * Registering new structure gen.
		 */
		try {
			Method method = WorldGenFactory.class
					.getDeclaredMethod("b", Class.class, String.class);
			method.setAccessible(true);
			method.invoke(null, SimpleWorldGenVillageStart.class, "Village");
			method.invoke(null, SimpleWorldGenLargeFeatureStart.class, "Temple");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clean() {
		List<SimpleBiomeBase> biomes = new ArrayList<SimpleBiomeBase>(this.biomes.values());

		for (SimpleBiomeBase biome : biomes) {
			if (!(biome instanceof SimpleBiomeBaseDefault)) {
				this.remove(biome.getId());
			}
		}
	}

	@Override
	public TreasureRegister getTreasureRegister() {
		return SimpleBiomePlugin.get().getTreasureRegister();
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
		if (id >= BiomeBase.n().length) {
			throw new IllegalArgumentException("The biome id has to be smaller then " +
					BiomeBase.n().length + "");
		}

		if (BiomeBase.n()[id] != null) {
			throw new IllegalArgumentException("Duplicate id!");
		}

		BiomeBase biome1 = new BiomeBase(id) {};
		return this.setBiome(biome1);
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
			BiomeBase.n()[id] = null;
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

	@Override
	public List<me.cybermaxke.mighty.biome.api.BiomeBase> getAll() {
		return new ArrayList<me.cybermaxke.mighty.biome.api.BiomeBase>(this.biomes.values());
	}

	@Override
	public me.cybermaxke.mighty.biome.api.BiomeBase getDefaultClone(int id,
			me.cybermaxke.mighty.biome.api.BiomeBase biome) {
		if (id >= BiomeBase.n().length) {
			throw new IllegalArgumentException("The biome id has to be smaller then " +
					BiomeBase.n().length + "");
		}

		if (BiomeBase.n()[id] != null) {
			throw new IllegalArgumentException("Duplicate id!");
		}

		return this.setBiome(this.getClone(this.backup.get(biome.getId()), id));
	}

	public SimpleBiomeBase setBiome(BiomeBase biome) {
		SimpleBiomeBase biome2 = new SimpleBiomeBase(biome);

		int id = biome.id;
		try {
			Field field = CraftBlock.class.getDeclaredField("BIOME_MAPPING");
			field.setAccessible(true);

			Field field1 = CraftBlock.class.getDeclaredField("BIOMEBASE_MAPPING");
			field1.setAccessible(true);

			Object[] array = ((Object[]) field.get(null));
			BiomeBase[] array1 = ((BiomeBase[]) field1.get(null));

			array[id] = CraftBlock.biomeBaseToBiome(BiomeBase.BEACH);
			array1[id] = biome;

			this.biomes.put(id, biome2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return biome2;
	}
 
	public <T extends BiomeBase> T getClone(T biome, int id) {
		try {
			Class<?> clazz = biome.getClass();

			Constructor<?> constructor = clazz.getDeclaredConstructor(int.class);
			constructor.setAccessible(true);

			BiomeBase old = BiomeBase.n()[id];
			T clone = (T) constructor.newInstance(id);
			BiomeBase.n()[id] = old;

			while (clazz != null) {
				for (Field field : clazz.getDeclaredFields()) {
					field.setAccessible(true);

					if (WorldGenerator.class.isAssignableFrom(field.getType())) {
						continue;
					} else if (BiomeDecorator.class.isAssignableFrom(field.getType())) {
						continue;
					} else if (List.class.isAssignableFrom(field.getType())) {
						continue;
					} else {
						if (Modifier.isFinal(field.getModifiers())) {
							continue;
						}

						field.set(clone, field.get(biome));
					}
				}

				clazz = clazz.getSuperclass();
			}

			return clone;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void update(World world) {
		WorldChunkManager manager = this.getChunkManager(world);

		/**
		 * Already updated...
		 */
		if (this.getMainLayer(manager) != null || world.getWorldType().equals(WorldType.FLAT)) {
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

		this.getChunkProviderGenerate(world);
		this.getProvider(world);
		this.setLayers(manager, SimpleGenLayer.getLayers(biomes, seed, size));
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

			BiomeBase biome1 = BiomeBase.n()[biome.getId()];
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
		BiomeBase biome1 = BiomeBase.n()[biome.getId()];
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
			BiomeBase biome1 = BiomeBase.n()[biome.getId()];
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
		return this.getMainLayer(this.getChunkManager(world)).getData().getBiomes();
	}

	public void setBiomeSize(World world, int size) {
		this.getMainLayer(this.getChunkManager(world)).getData().setSize(size);
	}

	public int getBiomeSize(World world) {
		return this.getMainLayer(this.getChunkManager(world)).getData().getSize();
	}

	public SimpleBiomeChunkProviderGenerate getChunkProviderGenerate(World world) {
		WorldServer w = ((CraftWorld) world).getHandle();
		NormalChunkGenerator gen = (NormalChunkGenerator) w.chunkProviderServer.chunkProvider;

		try {
			Field field = NormalChunkGenerator.class.getDeclaredField("provider");
			field.setAccessible(true);

			ChunkProviderGenerate gen1 = (ChunkProviderGenerate) field.get(gen);

			if (gen1 instanceof SimpleBiomeChunkProviderGenerate) {
				return (SimpleBiomeChunkProviderGenerate) gen1;
			}
	
			Field mfield = Field.class.getDeclaredField("modifiers");
			mfield.setAccessible(true);
			mfield.set(field, field.getModifiers() & ~Modifier.FINAL);

			SimpleBiomeChunkProviderGenerate gen2 = new SimpleBiomeChunkProviderGenerate(w, gen1);
			field.set(gen, gen2);

			return gen2;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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

	public List<BiomeBase> getSpawnBiomeList(World world) {
		return ReflectionUtils.getFieldObject(WorldChunkManager.class, List.class,
				this.getChunkManager(world), "g");
	}

	public SimpleGenLayerData getMainLayer(WorldChunkManager manager) {
		GenLayer layer = ReflectionUtils.getFieldObject(
				WorldChunkManager.class, GenLayer.class, manager, "d");

		if (layer instanceof SimpleGenLayerData) {
			return (SimpleGenLayerData) layer;
		} else {
			return null;
		}
	}

	public void setLayers(WorldChunkManager manager, GenLayer[] layers) {
		try {
			ReflectionUtils.setFieldObject(WorldChunkManager.class, manager, "d", layers[0]);
			ReflectionUtils.setFieldObject(WorldChunkManager.class, manager, "e", layers[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BiomeBase> getBiomes(List<me.cybermaxke.mighty.biome.api.BiomeBase> biomes) {
		List<BiomeBase> biomes1 = new ArrayList<BiomeBase>();

		for (me.cybermaxke.mighty.biome.api.BiomeBase biome : biomes) {
			BiomeBase biome1 = BiomeBase.n()[biome.getId()];

			if (biome1 != null) {
				biomes1.add(biome1);
			}
		}

		return biomes1;
	}
}