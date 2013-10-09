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
package me.cybermaxke.mighty.biome.plugin.gen.layer;

import java.util.List;

import me.cybermaxke.mighty.biome.api.BiomeBase;

import net.minecraft.server.v1_6_R3.GenLayer;
import net.minecraft.server.v1_6_R3.GenLayerIsland;
import net.minecraft.server.v1_6_R3.GenLayerRiverInit;
import net.minecraft.server.v1_6_R3.GenLayerRiverMix;
import net.minecraft.server.v1_6_R3.GenLayerSmooth;
import net.minecraft.server.v1_6_R3.GenLayerZoom;
import net.minecraft.server.v1_6_R3.GenLayerZoomFuzzy;
import net.minecraft.server.v1_6_R3.GenLayerZoomVoronoi;
import net.minecraft.server.v1_6_R3.LayerIsland;

public class SimpleGenLayer {

	/**
	 * Creates new gen layers. The size is by default '4' and with giant biomes '6'.
	 * @param biomes
	 * @param seed
	 * @param size
	 * @return layers
	 */
	public static GenLayer[] getLayers(List<BiomeBase> biomes, long seed, int size) {
		GenLayer layer1 = new LayerIsland(1L);
		layer1 = new GenLayerZoomFuzzy(2000L, layer1);
		layer1 = new GenLayerIsland(1L, layer1);
		layer1 = new GenLayerZoom(2001L, layer1);
		layer1 = new GenLayerIsland(2L, layer1);
		layer1 = new GenLayerZoom(2002L, layer1);
		layer1 = new GenLayerIsland(3L, layer1);
		layer1 = new GenLayerZoom(2003L, layer1);
		layer1 = new GenLayerIsland(4L, layer1);
		layer1 = new SimpleGenLayerMushroomIsland(5L, layer1, biomes);

		GenLayer layer2 = layer1;
		layer2 = GenLayerZoom.a(1000L, layer2, 0);
		layer2 = new GenLayerRiverInit(100L, layer2);
		layer2 = new SimpleGenLayerZoom1(1000L, layer2, size);
		layer2 = new SimpleGenLayerRiver(1L, layer2, biomes);
		layer2 = new GenLayerSmooth(1000L, layer2);

		GenLayer layer3 = layer1;
		layer3 = GenLayerZoom.a(1000L, layer3, 0);
		layer3 = new SimpleGenLayerBiome(200L, layer3, biomes);
		layer3 = GenLayerZoom.a(1000L, layer3, 2);
		layer3 = new SimpleGenLayerHills(1000L, layer3, biomes);
		layer3 = new SimpleGenLayerZoom2(1000L, layer3, size);
		layer3 = new GenLayerSmooth(1000L, layer3);
		layer3 = new GenLayerRiverMix(100L, layer3, layer2);

		GenLayerZoomVoronoi layerZoomVoronoi = new GenLayerZoomVoronoi(10L, layer3);

		layer3.a(seed);
		layerZoomVoronoi.a(seed);
 
		return new GenLayer[] { layer3, layerZoomVoronoi };
	}
}