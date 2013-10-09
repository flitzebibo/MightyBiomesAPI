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

import me.cybermaxke.mighty.biome.api.utils.WeightedRandomItem;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.bukkit.entity.LivingEntity;

public class BiomeMeta implements WeightedRandomItem {
	private Class<? extends LivingEntity> entity;

	private int weight;
	private int minGroupCount;
	private int maxGroupCount;

	public BiomeMeta(Class<? extends LivingEntity> entity, int weight, int minGroupCount,
			int maxGroupCount) {
		this.entity = entity;
		this.weight = weight;
		this.minGroupCount = minGroupCount;
		this.maxGroupCount = maxGroupCount;
	}

	/**
	 * Gets the entity that should spawn.
	 * @return entity
	 */
	public Class<? extends LivingEntity> getEntity() {
		return this.entity;
	}

	/**
	 * Sets the entity that should spawn.
	 * @param entity
	 */
	public void setEntity(Class<? extends LivingEntity> entity) {
		this.entity = entity;
	}

	/**
	 * Gets the weight. The bigger this value, the bigger the chance
	 * that the entity will spawn.
	 * @return weight
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Sets the weight. The bigger this value, the bigger the chance
	 * that the entity will spawn.
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Gets the minimum group count.
	 * @return count
	 */
	public int getMinGroupCount() {
		return this.minGroupCount;
	}

	/**
	 * Sets the minimum group count.
	 * @param count
	 */
	public void setMinGroupCount(int count) {
		this.minGroupCount = count;
	}

	/**
	 * Gets the maximum group count.
	 * @return count
	 */
	public int getMaxGroupCount() {
		return this.maxGroupCount;
	}

	/**
	 * Sets the maximum group count.
	 * @param count
	 */
	public void setMaxGroupCount(int count) {
		this.maxGroupCount = count;
	}

	@Override
	public BiomeMeta clone() {
		return new BiomeMeta(this.entity, this.weight, this.minGroupCount, this.maxGroupCount);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BiomeMeta)) {
			return false;
		}

		BiomeMeta other = (BiomeMeta) o;
		return new EqualsBuilder()
				.append(this.entity, other.entity)
				.append(this.weight, other.weight)
				.append(this.minGroupCount, other.minGroupCount)
				.append(this.maxGroupCount, other.maxGroupCount)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.entity)
				.append(this.weight)
				.append(this.minGroupCount)
				.append(this.maxGroupCount)
				.toHashCode();
	}
}