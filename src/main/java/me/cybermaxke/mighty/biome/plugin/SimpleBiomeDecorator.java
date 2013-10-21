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
import java.util.Random;

import org.bukkit.generator.BlockPopulator;

import me.cybermaxke.mighty.biome.api.BiomeMinableMeta;
import me.cybermaxke.mighty.biome.api.decorator.Decorator;
import me.cybermaxke.mighty.biome.api.decorator.DecoratorSetting;
import me.cybermaxke.mighty.biome.api.decorator.DecoratorSettingType;
import me.cybermaxke.mighty.biome.api.gen.WorldGen;
import me.cybermaxke.mighty.biome.plugin.gen.SimpleWorldGen;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenVillage;
import me.cybermaxke.mighty.biome.plugin.utils.ReflectionUtils;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeDecorator;
import net.minecraft.server.v1_6_R3.Block;
import net.minecraft.server.v1_6_R3.World;
import net.minecraft.server.v1_6_R3.WorldGenDeadBush;
import net.minecraft.server.v1_6_R3.WorldGenLakes;
import net.minecraft.server.v1_6_R3.WorldGenLiquids;
import net.minecraft.server.v1_6_R3.WorldGenMinable;
import net.minecraft.server.v1_6_R3.WorldGenPumpkin;
import net.minecraft.server.v1_6_R3.WorldGenerator;

public class SimpleBiomeDecorator extends BiomeDecorator {
	private final WorldGenDeadBush deathBush = new WorldGenDeadBush(Block.DEAD_BUSH.id);
	private final WorldGenLiquids waterLiquids = new WorldGenLiquids(Block.WATER.id);
	private final WorldGenLiquids lavaLiquids = new WorldGenLiquids(Block.LAVA.id);
	private final WorldGenLakes waterLakes = new WorldGenLakes(Block.WATER.id);
	private final WorldGenLakes lavaLakes = new WorldGenLakes(Block.LAVA.id);
	private final WorldGenPumpkin pumpkin = new WorldGenPumpkin();

	private Decorator decorator;
	private World world;

	public SimpleBiomeDecorator(BiomeBase biome, Decorator decorator) {
		super(biome);
		this.decorator = decorator;
	}

	public boolean hasChance(Random random, double chance) {
		return Math.min(100.0D, ((double) random.nextInt(100)) + random.nextDouble()) <= chance;
	}

	@Override
	public void a(World world, Random random, int chunkX, int chunkZ) {
		if (this.decorator == null || this.world != null) {
			return;
		}

		this.world = world;
		this.decorator.onPreDecorate(world.getWorld(), random, chunkX, chunkZ);

		/**
		 * Generating ores.
		 */
		for (BiomeMinableMeta meta : this.decorator.getMinables()) {
			for (int i = 0; i < meta.getGroupCount(); i++) {
				int min = meta.getMinHeight();
				int max = meta.getMaxHeight();
				int size = meta.getGroupSize();

				int x = chunkX + random.nextInt(16);
				int y = random.nextInt(max - min) + min;
				int z = chunkZ + random.nextInt(16);

				WorldGenMinable gen = new WorldGenMinable(meta.getMaterial().getId(), size);
				gen.a(world, random, x, y, z);
			}
		}

		/**
		 * Generating flowers, sand, lakes, etc.
		 */
		DecoratorSetting waterLakes = this.decorator.getSetting(DecoratorSettingType.WATER_LAKES);
		DecoratorSetting lavaLakes = this.decorator.getSetting(DecoratorSettingType.LAVA_LAKES);
		DecoratorSetting sand = this.decorator.getSetting(DecoratorSettingType.SAND);
		DecoratorSetting sand2 = this.decorator.getSetting(DecoratorSettingType.SAND_2);
		DecoratorSetting clay = this.decorator.getSetting(DecoratorSettingType.CLAY);
		DecoratorSetting trees = this.decorator.getSetting(DecoratorSettingType.TREES);
		DecoratorSetting bigMushrooms =
				this.decorator.getSetting(DecoratorSettingType.BIG_MUSHROOMS);
		DecoratorSetting yellowFlowers =
				this.decorator.getSetting(DecoratorSettingType.YELLOW_FLOWERS);
		DecoratorSetting redFlowers = this.decorator.getSetting(DecoratorSettingType.RED_FLOWERS);
		DecoratorSetting grass = this.decorator.getSetting(DecoratorSettingType.GRASS);
		DecoratorSetting deathBush = this.decorator.getSetting(DecoratorSettingType.DEATH_BUSH);
		DecoratorSetting waterlilly = this.decorator.getSetting(DecoratorSettingType.WATERLILY);
		DecoratorSetting brownMushrooms =
				this.decorator.getSetting(DecoratorSettingType.BROWN_MUSHROOMS);
		DecoratorSetting redMushrooms =
				this.decorator.getSetting(DecoratorSettingType.RED_MUSHROOMS);
		DecoratorSetting reeds = this.decorator.getSetting(DecoratorSettingType.REEDS);
		DecoratorSetting pumpkins = this.decorator.getSetting(DecoratorSettingType.PUMPKINS);
		DecoratorSetting cacti = this.decorator.getSetting(DecoratorSettingType.CACTI);
		DecoratorSetting waterLiquids =
				this.decorator.getSetting(DecoratorSettingType.WATER_LIQUIDS);
		DecoratorSetting lavaLiquids =
				this.decorator.getSetting(DecoratorSettingType.LAVA_LIQUIDS);

		double chanceWaterLakes = waterLakes.getChance();
		double chanceLavaLakes = lavaLakes.getChance();
		double chanceSand = sand.getChance();
		double chanceSand2 = sand2.getChance();
		double chanceClay = clay.getChance();
		double chanceTrees = trees.getChance();
		double chanceBigMushrooms = bigMushrooms.getChance();
		double chanceYellowFlowers = yellowFlowers.getChance();
		double chanceRedFlowers = redFlowers.getChance();
		double chanceGrass = grass.getChance();
		double chanceDeathBush = deathBush.getChance();
		double chanceWaterlilly = waterlilly.getChance();
		double chanceBrownMushrooms = brownMushrooms.getChance();
		double chanceRedMushrooms = redMushrooms.getChance();
		double chanceReeds = reeds.getChance();
		double chancePumpkins = pumpkins.getChance();
		double chanceCacti = cacti.getChance();
		double chanceWaterLiquids = waterLiquids.getChance();
		double chanceLavaLiquids = lavaLiquids.getChance();

		for (int i = 0; i < waterLakes.getCount(); ++i) {
			if (this.hasChance(random, chanceWaterLakes)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = random.nextInt(random.nextInt(128));

				if (this.canPlaceLake(world, random, x, z)) {
					this.waterLakes.a(world, random, x, y, z);
				}
			}
		}

		for (int i = 0; i < lavaLakes.getCount(); ++i) {
			if (this.hasChance(random, chanceLavaLakes)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = random.nextInt(random.nextInt(120) + 8);

				if (this.canPlaceLake(world, random, x, z)) {
					this.lavaLakes.a(world, random, x, y, z);
				}
			}
		}

		for (int i = 0; i < sand.getCount(); ++i) {
			if (this.hasChance(random, chanceSand)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.g.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < clay.getCount(); ++i) {
			if (this.hasChance(random, chanceClay)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.f.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < sand2.getCount(); ++i) {
			if (this.hasChance(random, chanceSand2)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.g.a(world, random, x, world.i(x, z), z);
			}
		}

		int treesCount = trees.getCount();
		if (random.nextInt(10) == 0) {
			treesCount++;
		}

		for (int i = 0; i < treesCount; i++) {
			if (this.hasChance(random, chanceTrees)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
	
				WorldGen gen1 = this.decorator.getWorldGenTrees(random);

				WorldGenerator gen2 = gen1 == null ? this.e.a(random) : new SimpleWorldGen(gen1);
				gen2.a(1.0D, 1.0D, 1.0D);
				gen2.a(world, random, x, world.getHighestBlockYAt(x, z), z);
			}
		}

		for (int i = 0; i < bigMushrooms.getCount(); i++) {
			if (this.hasChance(random, chanceBigMushrooms)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.u.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < yellowFlowers.getCount(); i++) {
			if (this.hasChance(random, chanceYellowFlowers)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.q.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < redFlowers.getCount(); i++) {
			if (this.hasChance(random, chanceRedFlowers)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.r.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < grass.getCount(); i++) {
			if (this.hasChance(random, chanceGrass)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				WorldGen gen1 = this.decorator.getWorldGenGrass(random);

				WorldGenerator gen2 = gen1 == null ? this.e.b(random) : new SimpleWorldGen(gen1);
				gen2.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < deathBush.getCount(); i++) {
			if (this.hasChance(random, chanceDeathBush)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.deathBush.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < waterlilly.getCount(); i++) {
			if (this.hasChance(random, chanceWaterlilly)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				while (y > 0 && world.getTypeId(x, y - 1, z) == 0) {
					y--;
				}

				this.x.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < brownMushrooms.getCount(); i++) {
			if (this.hasChance(random, chanceBrownMushrooms)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = world.getHighestBlockYAt(x, z);

				this.s.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < redMushrooms.getCount(); i++) {
			if (this.hasChance(random, chanceRedMushrooms)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = world.getHighestBlockYAt(x, z);

				this.t.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < reeds.getCount(); i++) {
			if (this.hasChance(random, chanceReeds)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.v.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < pumpkins.getCount(); i++) {
			if (this.hasChance(random, chancePumpkins)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.pumpkin.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < cacti.getCount(); i++) {
			if (this.hasChance(random, chanceCacti)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.w.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < waterLiquids.getCount(); i++) {
			if (this.hasChance(random, chanceWaterLiquids)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(random.nextInt(120) + 8);
				int z = chunkZ + random.nextInt(16) + 8;

				this.waterLiquids.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < lavaLiquids.getCount(); i++) {
			if (this.hasChance(random, chanceLavaLiquids)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(random.nextInt(random.nextInt(112) + 8) + 8);
				int z = chunkZ + random.nextInt(16) + 8;

				this.lavaLiquids.a(world, random, x, y, z);
			}
		}

		for (BlockPopulator populator : this.decorator.getPopulators()) {
			populator.populate(world.getWorld(), random,
					world.getChunkAt(chunkX, chunkZ).bukkitChunk);
		}

		this.decorator.onDecorate(world.getWorld(), random, chunkX, chunkZ);
		this.world = null;
	}

	public Decorator getHandle() {
		return this.decorator;
	}

	public void setHandle(Decorator decorator) {
		this.decorator = decorator;
	}

	public boolean canPlaceLake(World world, Random random, int x, int z) {
		SimpleWorldGenVillage gen = SimpleBiomePlugin.get().getBiomeRegister()
				.getChunkProviderGenerate(world.getWorld())
				.getVillageGen();

		return gen.a(world, random, x, z);
	}

	/**
	 * Loads all the fields of the old decorator to this one.
	 * @param old
	 */
	public void init(BiomeDecorator old) {
		boolean liquids = false;

		try {
			Field field = BiomeDecorator.class.getDeclaredField("K");
			field.setAccessible(true);

			liquids = field.getBoolean(old);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (DecoratorSettingType type : DecoratorSettingType.values()) {
			switch (type) {
				case BIG_MUSHROOMS:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("J", old)));
					break;
				case RED_MUSHROOMS:
				case BROWN_MUSHROOMS:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("D", old)));
					break;
				case CACTI:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("F", old)));
					break;
				case CLAY:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("I", old)));
					break;
				case DEATH_BUSH:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("C", old)));
					break;
				case GRASS:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("B", old)));
					break;
				case WATER_LIQUIDS:
					if (liquids) {
						this.decorator.setSetting(
								new DecoratorSetting(type, 100.0D, 50));
					}
					break;
				case LAVA_LIQUIDS:
					if (liquids) {
						this.decorator.setSetting(
								new DecoratorSetting(type, 100.0D, 20));
					}
					break;
				case YELLOW_FLOWERS:
				case RED_FLOWERS:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("A", old)));
					break;
				case REEDS:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("F", old)));
					break;
				case SAND:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("H", old)));
					break;
				case SAND_2:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("G", old)));
					break;
				case TREES:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("z", old)));
					break;
				case WATERLILY:
					this.decorator.setSetting(
							new DecoratorSetting(type, 100.0D, this.getInt("y", old)));
					break;
				case PUMPKINS:
				case WATER_LAKES:
				case LAVA_LAKES:
					break;
				default:
					throw new IllegalStateException("The old values of '" + type.toString() +
							"' aren't loaded!");
			}
		}
	}

	public int getInt(String fieldName, BiomeDecorator old) {
		return ReflectionUtils.getFieldObject(BiomeDecorator.class, Integer.class, old, fieldName);
	}
}