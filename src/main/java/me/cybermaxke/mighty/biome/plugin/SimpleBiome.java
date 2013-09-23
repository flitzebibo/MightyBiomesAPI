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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import me.cybermaxke.mighty.biome.api.Biome;
import me.cybermaxke.mighty.biome.api.data.EnumCreatureType;

import net.minecraft.server.v1_6_R3.BiomeBase;
import net.minecraft.server.v1_6_R3.BiomeMeta;
import net.minecraft.server.v1_6_R3.WorldGenVillage;

public class SimpleBiome implements Biome {
	private final Map<EnumCreatureType, List<me.cybermaxke.mighty.biome.api.BiomeMeta>> meta =
			new HashMap<EnumCreatureType, List<me.cybermaxke.mighty.biome.api.BiomeMeta>>();
	private final BiomeBase biome;

	@SuppressWarnings("unchecked")
	public SimpleBiome(BiomeBase biome) {
		this.biome = biome;

		for (EnumCreatureType type : EnumCreatureType.values()) {
			List<me.cybermaxke.mighty.biome.api.BiomeMeta> list =
					new ArrayList<me.cybermaxke.mighty.biome.api.BiomeMeta>();

			this.meta.put(type, list);

			try {
				Field field = this.getField(type);
				field.setAccessible(true);

				List<BiomeMeta> list1 = (List<BiomeMeta>) this.getField(type).get(biome);
				List<BiomeMeta> list2 = new MetaList(list, list1);

				field.set(biome, list2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getId() {
		return this.biome.id;
	}

	@Override
	public float getTemperature() {
		return this.biome.temperature;
	}

	@Override
	public void clearSpawns() {
		for (EnumCreatureType type : EnumCreatureType.values()) {
			this.clearSpawns(type);
		}
	}

	@Override
	public void clearSpawns(EnumCreatureType type) {
		this.meta.get(type).clear();
	}

	@Override
	public List<me.cybermaxke.mighty.biome.api.BiomeMeta> getSpawns(EnumCreatureType type) {
		return this.meta.get(type);
	}

	@Override
	public void addSpawn(EnumCreatureType type, me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		this.meta.get(type).add(meta);
	}

	@Override
	public float getMinHeight() {
		return this.biome.D;
	}

	@Override
	public float getMaxHeight() {
		return this.biome.E;
	}

	@Override
	public int getTopBlock() {
		return this.biome.A;
	}

	@Override
	public int getFillingBlock() {
		return this.biome.B;
	}

	@Override
	public boolean canGenerateVillages() {
		return WorldGenVillage.e.contains(this.biome);
	}

	@SuppressWarnings("unchecked")
	public List<BiomeMeta> getMeta(EnumCreatureType type) {
		try {
			return (List<BiomeMeta>) this.getField(type).get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Field getField(EnumCreatureType type) {
		String fieldName = null;

		switch (type) {
			case AMBIENT:
				fieldName = "M";
				break;
			case MONSTER:
				fieldName = "J";
				break;
			case WATER_CREATURE:
				fieldName = "L";
				break;
			case CREATURE:
			default:
				fieldName = "K";
				break;
		}

		try {
			return BiomeBase.class.getDeclaredField(fieldName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public class MetaList implements List<BiomeMeta> {
		private final List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta;

		public MetaList(List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta) {
			this.meta = meta;
		}

		public MetaList(List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta,
				List<BiomeMeta> defaults) {
			this.meta = meta;
			this.addAll(defaults);
		}

		@Override
		public boolean add(BiomeMeta e) {
			return this.meta.add(new SimpleBiomeMeta(e));
		}

		@Override
		public void add(int i, BiomeMeta e) {
			this.meta.add(i, new SimpleBiomeMeta(e));
		}

		@Override
		public boolean addAll(Collection<? extends BiomeMeta> c) {
			return this.addAll(this.size(), c);
		}

		@Override
		public boolean addAll(int i, Collection<? extends BiomeMeta> c) {
			List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta =
					new ArrayList<me.cybermaxke.mighty.biome.api.BiomeMeta>();

			for (BiomeMeta e : c) {
				meta.add(new SimpleBiomeMeta(e));
			}

			return this.meta.addAll(i, meta);
		}

		@Override
		public void clear() {
			this.meta.clear();
		}

		@Override
		public boolean contains(Object o) {
			if (!(o instanceof BiomeMeta)) {
				return false;
			}

			for (me.cybermaxke.mighty.biome.api.BiomeMeta meta : this.meta) {
				if (meta instanceof SimpleBiomeMeta && ((SimpleBiomeMeta) meta).getHandle() == o) {
					return true;
				}
			}

			return false;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			for (Object o : c) {
				if (!this.contains(o)) {
					return false;
				}
			}

			return true;
		}

		@Override
		public BiomeMeta get(int index) {
			return SimpleBiome.getHandle(this.meta.get(index));
		}

		@Override
		public int indexOf(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int lastIndexOf(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return this.meta.isEmpty();
		}

		@Override
		public Iterator<BiomeMeta> iterator() {
			return new Iterator<BiomeMeta>() {
				private final Iterator<me.cybermaxke.mighty.biome.api.BiomeMeta> it =
						MetaList.this.meta.iterator();

				@Override
				public boolean hasNext() {
					return this.it.hasNext();
				}

				@Override
				public BiomeMeta next() {
					return SimpleBiome.getHandle(this.it.next());
				}

				@Override
				public void remove() {
					this.it.remove();
				}

			};
		}

		@Override
		public ListIterator<BiomeMeta> listIterator() {
			return this.listIterator(0);
		}

		@Override
		public ListIterator<BiomeMeta> listIterator(final int index) {
			return new ListIterator<BiomeMeta>() {
				private final ListIterator<me.cybermaxke.mighty.biome.api.BiomeMeta> it =
						MetaList.this.meta.listIterator(index);

				@Override
				public void add(BiomeMeta e) {
					this.it.add(new SimpleBiomeMeta(e));
				}

				@Override
				public boolean hasNext() {
					return this.it.hasNext();
				}

				@Override
				public boolean hasPrevious() {
					return this.it.hasPrevious();
				}

				@Override
				public BiomeMeta next() {
					return SimpleBiome.getHandle(this.it.next());
				}

				@Override
				public int nextIndex() {
					return this.it.nextIndex();
				}

				@Override
				public BiomeMeta previous() {
					return SimpleBiome.getHandle(this.it.previous());
				}

				@Override
				public int previousIndex() {
					return this.it.previousIndex();
				}

				@Override
				public void remove() {
					this.it.remove();
				}

				@Override
				public void set(BiomeMeta e) {
					this.it.set(new SimpleBiomeMeta(e));
				}

			};
		}

		@Override
		public boolean remove(Object o) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BiomeMeta remove(int index) {
			return SimpleBiome.getHandle(this.meta.remove(index));
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BiomeMeta set(int index, BiomeMeta e) {
			return getHandle(this.meta.set(index, new SimpleBiomeMeta(e)));
		}

		@Override
		public int size() {
			return this.meta.size();
		}

		@Override
		public List<BiomeMeta> subList(int fromIndex, int toIndex) {
			List<BiomeMeta> meta = new ArrayList<BiomeMeta>();

			for (me.cybermaxke.mighty.biome.api.BiomeMeta m :
					this.meta.subList(fromIndex, toIndex)) {
				meta.add(getHandle(m));
			}

			return null;
		}

		@Override
		public BiomeMeta[] toArray() {
			BiomeMeta[] array = new BiomeMeta[this.size()];

			for (int i = 0; i < array.length; i++) {
				array[i] = this.get(i);
			}

			return array;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T[] toArray(T[] a) {
			T[] array = (T[]) Arrays.copyOf(a, this.size(), a.getClass());

			for (int i = 0; i < array.length; i++) {
				array[i] = (T) this.get(i);
			}

			return array;
		}
	}

	public static BiomeMeta getHandle(me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		return meta == null ? null : getMeta(meta).getHandle();
	}

	public static SimpleBiomeMeta getMeta(me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		return (SimpleBiomeMeta) (meta instanceof SimpleBiomeMeta ? meta :
			new SimpleBiomeMeta(meta));
	}
}