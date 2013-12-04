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

import net.minecraft.server.v1_7_R1.EnumGenLayerSpecial;
import net.minecraft.server.v1_7_R1.GenLayer;
import net.minecraft.server.v1_7_R1.GenLayerCleaner;
import net.minecraft.server.v1_7_R1.GenLayerDesert;
import net.minecraft.server.v1_7_R1.GenLayerIsland;
import net.minecraft.server.v1_7_R1.GenLayerPlains;
import net.minecraft.server.v1_7_R1.GenLayerRegionHills;
import net.minecraft.server.v1_7_R1.GenLayerRiver;
import net.minecraft.server.v1_7_R1.GenLayerRiverMix;
import net.minecraft.server.v1_7_R1.GenLayerSmooth;
import net.minecraft.server.v1_7_R1.GenLayerSpecial;
import net.minecraft.server.v1_7_R1.GenLayerTopSoil;
import net.minecraft.server.v1_7_R1.GenLayerZoom;
import net.minecraft.server.v1_7_R1.GenLayerZoomFuzzy;
import net.minecraft.server.v1_7_R1.GenLayerZoomVoronoi;
import net.minecraft.server.v1_7_R1.LayerIsland;

public class SimpleGenLayer {

	/**
	 * Creates new gen layers. The size is by default '4' and with giant biomes '6'.
	 * @param biomes
	 * @param seed
	 * @param size
	 * @return layers
	 */
	public static GenLayer[] getLayers(List<BiomeBase> biomes, long seed, int size) {
		SimpleGenData genData = new SimpleGenData();
		genData.setSize(size);
		genData.getBiomes().addAll(biomes);

		GenLayer layer1 = new LayerIsland(1L);
		layer1 = new GenLayerZoomFuzzy(2000L, layer1);
		layer1 = new GenLayerIsland(1L, layer1);
		layer1 = new GenLayerZoom(2001L, layer1);
		layer1 = new GenLayerIsland(2L, layer1);
		layer1 = new GenLayerIsland(50L, layer1);
		layer1 = new GenLayerIsland(70L, layer1);
		layer1 = new GenLayerTopSoil(2L, layer1);
		layer1 = new GenLayerIsland(3L, layer1);
		layer1 = new GenLayerSpecial(2L, layer1, EnumGenLayerSpecial.COOL_WARM);
		layer1 = new GenLayerSpecial(2L, layer1, EnumGenLayerSpecial.HEAT_ICE);
		layer1 = new GenLayerSpecial(3L, layer1, EnumGenLayerSpecial.PUFFERFISH);
		layer1 = new GenLayerZoom(2002L, layer1);
		layer1 = new GenLayerZoom(2003L, layer1);
		layer1 = new GenLayerIsland(4L, layer1);
		layer1 = new SimpleGenLayerMushroomIsland(5L, layer1, biomes);
		layer1 = new SimpleGenLayerDeepOcean(4L, layer1, genData);

		GenLayer layer2 = layer1;
		layer2 = GenLayerZoom.b(1000L, layer2, 0);
		layer2 = new GenLayerCleaner(100L, layer2);

		GenLayer layer3 = layer1;
		layer3 = new SimpleGenLayerBiome(200L, layer3, genData);
		layer3 = GenLayerZoom.b(1000L, layer3, 2);
		layer3 = new GenLayerDesert(1000L, layer3);

		GenLayer layer4 = layer2;
		layer4 = GenLayerZoom.b(1000L, layer2, 2);
		layer3 = new GenLayerRegionHills(1000L, layer3, layer4);

		layer2 = GenLayerZoom.b(1000L, layer2, 2);
		layer2 = new SimpleGenLayerZoom1(1000L, layer2, genData);
		layer2 = new GenLayerRiver(1L, layer2);
		layer2 = new GenLayerSmooth(1000L, layer2);

		layer3 = new GenLayerPlains(1001L, layer3);
		layer3 = new SimpleGenLayerZoom2(1000L, layer3, genData);
		layer3 = new GenLayerSmooth(1000L, layer3);
		layer3 = new GenLayerRiverMix(100L, layer3, layer2);

		SimpleGenLayerData layerData = new SimpleGenLayerData(1000L, layer3, genData);
		GenLayerZoomVoronoi layerZoomVoronoi = new GenLayerZoomVoronoi(10L, layer3);

		layerData.a(seed);
		layerZoomVoronoi.a(seed);

		return new GenLayer[] { layerData, layerZoomVoronoi };
	}
}