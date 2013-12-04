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

import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.generator.BlockPopulator;

import me.cybermaxke.mighty.biome.api.BiomeMinableMeta;
import me.cybermaxke.mighty.biome.api.decorator.Decorator;
import me.cybermaxke.mighty.biome.api.decorator.DecoratorSetting;
import me.cybermaxke.mighty.biome.api.decorator.DecoratorSettingType;
import me.cybermaxke.mighty.biome.api.gen.WorldGen;
import me.cybermaxke.mighty.biome.plugin.gen.SimpleWorldGen;
import me.cybermaxke.mighty.biome.plugin.structure.SimpleWorldGenVillage;
import me.cybermaxke.mighty.biome.plugin.utils.ReflectionUtils;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.BiomeDecorator;
import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.Blocks;
import net.minecraft.server.v1_7_R1.World;
import net.minecraft.server.v1_7_R1.WorldGenCactus;
import net.minecraft.server.v1_7_R1.WorldGenClay;
import net.minecraft.server.v1_7_R1.WorldGenDeadBush;
import net.minecraft.server.v1_7_R1.WorldGenFlowers;
import net.minecraft.server.v1_7_R1.WorldGenHugeMushroom;
import net.minecraft.server.v1_7_R1.WorldGenLakes;
import net.minecraft.server.v1_7_R1.WorldGenLiquids;
import net.minecraft.server.v1_7_R1.WorldGenMinable;
import net.minecraft.server.v1_7_R1.WorldGenPumpkin;
import net.minecraft.server.v1_7_R1.WorldGenReed;
import net.minecraft.server.v1_7_R1.WorldGenSand;
import net.minecraft.server.v1_7_R1.WorldGenWaterLily;
import net.minecraft.server.v1_7_R1.WorldGenerator;

public class SimpleBiomeDecorator extends BiomeDecorator {
	private final WorldGenDeadBush deathBush = new WorldGenDeadBush(Blocks.DEAD_BUSH);
	private final WorldGenLiquids waterLiquids = new WorldGenLiquids(Blocks.WATER);
	private final WorldGenLiquids lavaLiquids = new WorldGenLiquids(Blocks.LAVA);
	private final WorldGenLakes waterLakes = new WorldGenLakes(Blocks.WATER);
	private final WorldGenLakes lavaLakes = new WorldGenLakes(Blocks.LAVA);
	private final WorldGenPumpkin pumpkin = new WorldGenPumpkin();
	private final WorldGenerator clay = new WorldGenClay(4);
	private final WorldGenerator sand = new WorldGenSand(Blocks.SAND, 7);
	private final WorldGenerator gravel = new WorldGenSand(Blocks.GRAVEL, 6);
	private final WorldGenFlowers yellowFlowers = new WorldGenFlowers(Blocks.YELLOW_FLOWER);
	private final WorldGenFlowers redFlowers = new WorldGenFlowers(Blocks.RED_ROSE);
	private final WorldGenerator brownMuschrooms = new WorldGenFlowers(Blocks.BROWN_MUSHROOM);
	private final WorldGenerator redMuschrooms = new WorldGenFlowers(Blocks.RED_MUSHROOM);
	private final WorldGenerator hugeMushrooms = new WorldGenHugeMushroom();
	private final WorldGenerator reeds = new WorldGenReed();
	private final WorldGenerator cactus = new WorldGenCactus();
	private final WorldGenerator waterLily = new WorldGenWaterLily();

	private Decorator decorator;
	private World world;

	public SimpleBiomeDecorator(Decorator decorator) {
		this.decorator = decorator;
	}

	public boolean hasChance(Random random, double chance) {
		return Math.min(100.0D, ((double) random.nextInt(100)) + random.nextDouble()) <= chance;
	}

	@Override
	public void a(World world, Random random, BiomeBase biome, int chunkX, int chunkZ) {
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

				Block block = CraftMagicNumbers.getBlock(meta.getMaterial());
				WorldGenMinable gen = new WorldGenMinable(block, size);
				gen.a(world, random, x, y, z);
			}
		}

		/**
		 * Generating flowers, sand, lakes, etc.
		 */
		DecoratorSetting gravel = this.decorator.getSetting(DecoratorSettingType.GRAVEL);
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
		DecoratorSetting waterlily = this.decorator.getSetting(DecoratorSettingType.WATERLILY);
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

		double chanceGravel = waterLakes.getChance();
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
		double chanceWaterlily = waterlily.getChance();
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

				this.sand.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < clay.getCount(); ++i) {
			if (this.hasChance(random, chanceClay)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.clay.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < sand2.getCount(); ++i) {
			if (this.hasChance(random, chanceSand2)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.sand.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < gravel.getCount(); ++i) {
			if (this.hasChance(random, chanceGravel)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.gravel.a(world, random, x, world.i(x, z), z);
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

				WorldGenerator gen2 = gen1 == null ? biome.a(random) : new SimpleWorldGen(gen1);
				gen2.a(1.0D, 1.0D, 1.0D);
				gen2.a(world, random, x, world.getHighestBlockYAt(x, z), z);
			}
		}

		for (int i = 0; i < bigMushrooms.getCount(); i++) {
			if (this.hasChance(random, chanceBigMushrooms)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;

				this.hugeMushrooms.a(world, random, x, world.i(x, z), z);
			}
		}

		for (int i = 0; i < yellowFlowers.getCount(); i++) {
			if (this.hasChance(random, chanceYellowFlowers)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.yellowFlowers.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < redFlowers.getCount(); i++) {
			if (this.hasChance(random, chanceRedFlowers)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.redFlowers.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < grass.getCount(); i++) {
			if (this.hasChance(random, chanceGrass)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				WorldGen gen1 = this.decorator.getWorldGenGrass(random);

				WorldGenerator gen2 = gen1 == null ? biome.b(random) : new SimpleWorldGen(gen1);
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

		for (int i = 0; i < waterlily.getCount(); i++) {
			if (this.hasChance(random, chanceWaterlily)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				while (y > 0 && world.getType(x, y - 1, z) == null) {
					y--;
				}

				this.waterLily.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < brownMushrooms.getCount(); i++) {
			if (this.hasChance(random, chanceBrownMushrooms)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = world.getHighestBlockYAt(x, z);

				this.brownMuschrooms.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < redMushrooms.getCount(); i++) {
			if (this.hasChance(random, chanceRedMushrooms)) {
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = world.getHighestBlockYAt(x, z);

				this.redMuschrooms.a(world, random, x, y, z);
			}
		}

		for (int i = 0; i < reeds.getCount(); i++) {
			if (this.hasChance(random, chanceReeds)) {
				int x = chunkX + random.nextInt(16) + 8;
				int y = random.nextInt(128);
				int z = chunkZ + random.nextInt(16) + 8;

				this.reeds.a(world, random, x, y, z);
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

				this.cactus.a(world, random, x, y, z);
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
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("H", old)));
					break;
				case BROWN_MUSHROOMS:
					this.decorator.setSetting(new DecoratorSetting(
							type, 25.0D, this.getInt("q", old)));
					break;
				case RED_MUSHROOMS:
					this.decorator.setSetting(new DecoratorSetting(
							type, 12.5D, this.getInt("r", old)));
				case CACTI:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("D", old)));
					break;
				case CLAY:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("G", old)));
					break;
				case DEATH_BUSH:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("A", old)));
					break;
				case GRASS:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("z", old)));
					break;
				case WATER_LIQUIDS:
					if (liquids) {
						this.decorator.setSetting(new DecoratorSetting(type, 100.0D, 50));
					}
					break;
				case LAVA_LIQUIDS:
					if (liquids) {
						this.decorator.setSetting(new DecoratorSetting(type, 100.0D, 20));
					}
					break;
				case YELLOW_FLOWERS:
				case RED_FLOWERS:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("y", old)));
					break;
				case REEDS:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("C", old) + 10));
					break;
				case SAND:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("F", old)));
					break;
				case GRAVEL:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("E", old)));
					break;
				case TREES:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("z", old)));
					break;
				case WATERLILY:
					this.decorator.setSetting(new DecoratorSetting(
							type, 100.0D, this.getInt("v", old)));
					break;
				case PUMPKINS:
					this.decorator.setSetting(new DecoratorSetting(type, 3.125D, 1));
				case WATER_LAKES:
				case LAVA_LAKES:
				case SAND_2:
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