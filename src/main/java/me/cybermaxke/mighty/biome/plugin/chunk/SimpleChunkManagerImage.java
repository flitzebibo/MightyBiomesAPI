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
package me.cybermaxke.mighty.biome.plugin.chunk;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Color;

import net.minecraft.server.v1_7_R1.BiomeBase;
import net.minecraft.server.v1_7_R1.BiomeCache;
import net.minecraft.server.v1_7_R1.ChunkPosition;
import net.minecraft.server.v1_7_R1.IntCache;
import net.minecraft.server.v1_7_R1.WorldChunkManager;

public class SimpleChunkManagerImage extends WorldChunkManager {
	private Map<Color, BiomeBase> biomes = new HashMap<Color, BiomeBase>();
	private Map<Long, BiomeBase> biomeSections = new HashMap<Long, BiomeBase>();

	private BufferedImage image;
	private BiomeBase fillingBiome;

	private int height;
	private int width;

	public SimpleChunkManagerImage(BufferedImage image, Map<Color, BiomeBase> biomes,
			BiomeBase fillingBiome) {
		this.biomes = biomes;
		this.image = image;
		this.height = image.getHeight();
		this.width = image.getWidth();
		this.fillingBiome = fillingBiome == null ? BiomeBase.OCEAN : fillingBiome;
		this.load();
	}

	public long getKey(int x, int z) {
		return (long) x << 32 | z & 0xFFFFFFFFL;
	}

	public void setSize(int height, int width) {
		this.height = height;
		this.width = width;

		BufferedImage image = new BufferedImage(this.height, this.width, 2);

		Graphics2D grap = image.createGraphics();
		grap.drawImage(this.image, 0, 0, this.width, this.height, null);
		grap.dispose();

		this.image = image;
		this.load();
	}

	public BiomeBase getBiome(int x, int z) {
		if (this.width / 2 > x || this.height / 2 > z) {
			return this.fillingBiome;
		}

		long key = this.getKey(x, z);
		return this.biomeSections.get(key);
	}

	public void load() {
		for (int i = -this.height / 2; i < this.height / 2; i++) {
			for (int j = -this.width / 2; j < this.width / 2; j++) {
				Color color = Color.fromRGB(this.image.getRGB(i, j));

				double red = color.getRed();
				double green = color.getGreen();
				double blue = color.getBlue();

				double nearestDistance = Double.MAX_VALUE;

				Color nearestColor = null;
				for (Color color1 : this.biomes.keySet()) {
					double red1 = Math.pow(color1.getRed() - red, 2.0);
					double green1 = Math.pow(color1.getGreen() - green, 2.0);
					double blue1 = Math.pow(color1.getBlue() - blue, 2.0);

					double temp = Math.sqrt(red1 + green1 + blue1);

					if (temp == 0.0D) {
						nearestColor = color1;
						break;
					} else if (temp < nearestDistance) {
						nearestDistance = temp;
				        nearestColor = color;
				    }
				}

				BiomeBase biome = this.biomes.get(nearestColor);
				this.biomeSections.put(this.getKey(i, j), biome);
			}
		}
	}

	public BiomeCache getCache() {
		try {
			Field field = WorldChunkManager.class.getDeclaredField("f");
			field.setAccessible(true);

			return (BiomeCache) field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public BiomeBase[] getBiomes(BiomeBase[] biomes, int x, int z, int width, int length) {
		IntCache.a();

		if (biomes == null || biomes.length < width * length) {
			biomes = new BiomeBase[width * length];
		}

		for (int x1 = 0; x1 < width; x1++) {
			for (int z1 = 0; z1 < length; z1++) {
				biomes[z1 + x1 * width] = this.getBiome(x + x1, z + z1);
			}
		}

		return biomes;
	}

	@Override
	public BiomeBase[] getBiomeBlock(BiomeBase[] biomes, int x, int z, int width, int length) {
		return this.a(biomes, x, z, width, length, true);
	}

	@Override
	public float[] getWetness(float[] wetness, int x, int z, int width, int length) {
		IntCache.a();

		if (wetness == null || wetness.length < width * length) {
			wetness = new float[width * length];
		}

		for (int x1 = 0; x1 < width; x1++) {
			for (int z1 = 0; z1 < length; z1++) {
				float wet = this.getBiome(x + x1, z + z1).g() / 65536.0F;

				if (wet > 1.0F) {
					wet = 1.0F;
				}

				wetness[z1 + x1 * width] = wet;
			}
		}

		return wetness;
	}

	@Override
	public BiomeBase[] a(BiomeBase[] biomes, int x, int z, int width, int length, boolean cacheFlag) {
		IntCache.a();

		if (biomes == null || biomes.length < width * length) {
			biomes = new BiomeBase[width * length];
		}

		if (cacheFlag && width == 16 && length == 16 && (x & 0xF) == 0 && (z & 0xF) == 0) {
			System.arraycopy(this.getCache().d(x, z), 0, biomes, 0, width * length);
		} else {
			this.getBiomes(biomes, x, z, width, length);
		}

		return biomes;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean a(int i1, int i2, int i3, List list) {
		IntCache.a();

		int i = i1 - i3 >> 2;
		int j = i2 - i3 >> 2;
		int k = i1 + i3 >> 2;
		int m = i2 + i3 >> 2;

		int n = k - i + 1;
		int i4 = m - j + 1;

		BiomeBase[] biomes = this.getBiomes(null, i, j, n, i4);
		for (int i5 = 0; i5 < n * i4; i5++) {
			if (!list.contains(biomes[i5])) {
				return false;
			}
		}

		return true;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ChunkPosition a(int i1, int i2, int i3, List list, Random random) {
		IntCache.a();

		int i = i1 - i3 >> 2;
		int j = i2 - i3 >> 2;
		int k = i1 + i3 >> 2;
		int m = i2 + i3 >> 2;

		int n = k - i + 1;
		int i4 = m - j + 1;

		BiomeBase[] biomes = this.getBiomes(null, i, j, n, i4);
		ChunkPosition chunkPosition = null;

		int i5 = 0;
		for (int i6 = 0; i6 < n * i4; i6++) {
			int i7 = i + i6 % n << 2;
			int i8 = j + i6 / n << 2;

			if (!list.contains(biomes[i6]) || (chunkPosition != null &&
					random.nextInt(i5 + 1) != 0)) {
				continue;
			}

			chunkPosition = new ChunkPosition(i7, 0, i8);
			i5++;
		}

		return chunkPosition;
	}
}