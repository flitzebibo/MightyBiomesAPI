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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.bukkit.Material;

public class BiomeOreMeta {
	private Material material;

	private int groupSize;
	private int groupCount;
	private int minHeight;
	private int maxHeight;

	public BiomeOreMeta(Material material) {
		this(material, 8, 20, 0, 128);
	}

	public BiomeOreMeta(Material material, int groupSize, int groupCount, int minHeight,
			int maxHeight) {
		this.material = material;
		this.groupSize = groupSize;
		this.groupCount = groupCount;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	/**
	 * Gets the block material.
	 * @return material
	 */
	public Material getMaterial() {
		return this.material;
	}

	/**
	 * Sets the block material.
	 * @param material
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Gets the amount of times the regerator will try to generate a group of ores.
	 * @return count
	 */
	public int getGroupCount() {
		return this.groupCount;
	}

	/**
	 * Sets the amount of times the regerator will try to generate a group of ores.
	 * @param count
	 */
	public void setGroupCount(int count) {
		this.groupCount = count;
	}

	/**
	 * Gets the amount of blocks that will be generated at a location.
	 * @return size
	 */
	public int getGroupSize() {
		return this.groupSize;
	}

	/**
	 * Sets the amount of blocks that will be generated at a location.
	 * @param size
	 */
	public void setGroupSize(int size) {
		this.groupSize = size;
	}

	/**
	 * Gets the minimum height were the ores can generate.
	 * @return minHeight
	 */
	public int getMinHeight() {
		return this.minHeight;
	}

	/**
	 * Sets the minimum height were the ores can generate.
	 * @param minHeight
	 */
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	/**
	 * Gets the maximum height were the ores can generate.
	 * @return maxHeight
	 */
	public int getMaxHeight() {
		return this.maxHeight;
	}

	/**
	 * Sets the maximum height were the ores can generate.
	 * @param maxHeight
	 */
	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BiomeOreMeta)) {
			return false;
		}

		BiomeOreMeta other = (BiomeOreMeta) o;
		return new EqualsBuilder()
				.append(this.material, other.material)
				.append(this.groupSize, other.groupSize)
				.append(this.groupCount, other.groupCount)
				.append(this.minHeight, other.minHeight)
				.append(this.maxHeight, other.maxHeight)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.material)
				.append(this.groupSize)
				.append(this.groupCount)
				.append(this.minHeight)
				.append(this.maxHeight)
				.toHashCode();
	}
}