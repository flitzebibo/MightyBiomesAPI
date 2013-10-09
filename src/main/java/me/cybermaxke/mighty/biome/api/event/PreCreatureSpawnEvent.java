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
package me.cybermaxke.mighty.biome.api.event;

import java.util.List;

import me.cybermaxke.mighty.biome.api.BiomeBase;
import me.cybermaxke.mighty.biome.api.BiomeMeta;
import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PreCreatureSpawnEvent extends Event implements Cancellable {
	private final static HandlerList handlers = new HandlerList();

	private List<BiomeMeta> spawns;
	private EnumCreatureType type;
	private Location location;
	private BiomeBase biome;

	private boolean cancel;

	public PreCreatureSpawnEvent(List<BiomeMeta> spawns, EnumCreatureType type,
			Location location, BiomeBase biome) {
		this.spawns = spawns;
		this.type = type;
		this.location = location;
		this.biome = biome;
	}

	/**
	 * Gets the creature type that the world is trying to spawn.
	 * @return creatureType
	 */
	public EnumCreatureType getCreatureType() {
		return this.type;
	}

	/**
	 * Gets the biome where the creature will spawn.
	 * @return biome
	 */
	public BiomeBase getBiome() {
		return this.biome;
	}

	/**
	 * Gets the location where the creature will spawn.
	 * @return location
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Gets all the available spawns at the location and biome.
	 * @return spawns
	 */
	public List<BiomeMeta> getSpawns() {
		return this.spawns;
	}

	@Override
	public boolean isCancelled() {
		return this.cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return PreCreatureSpawnEvent.handlers;
	}

	public static HandlerList getHandlerList() {
		return PreCreatureSpawnEvent.handlers;
	}
}