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

import me.cybermaxke.mighty.biome.plugin.SimpleBiomeMeta;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.bukkit.entity.LivingEntity;

public class BiomeMeta {
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
	 * Gets the weight.
	 * @return weight
	 */
	public int getWeight() {
		return this.weight;
	}

	/**
	 * Gets the minimal group count.
	 * @return count
	 */
	public int getMinGroupCount() {
		return this.minGroupCount;
	}

	/**
	 * Gets the maximal group count.
	 * @return count
	 */
	public int getMaxGroupCount() {
		return this.maxGroupCount;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SimpleBiomeMeta)) {
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
				.appendSuper(this.entity.hashCode())
				.append(this.weight)
				.append(this.minGroupCount)
				.append(this.maxGroupCount)
				.toHashCode();
	}
}