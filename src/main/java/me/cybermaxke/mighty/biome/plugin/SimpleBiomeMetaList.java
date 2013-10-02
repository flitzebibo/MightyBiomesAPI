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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.server.v1_6_R3.BiomeMeta;

/**
 * Make the meta list depending on the list added by the biome wrapper.
 */
public class SimpleBiomeMetaList implements List<BiomeMeta> {
	private final List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta;

	public SimpleBiomeMetaList(List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta) {
		this.meta = meta;
	}

	public SimpleBiomeMetaList(List<me.cybermaxke.mighty.biome.api.BiomeMeta> meta,
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

		return this.meta.contains(new SimpleBiomeMeta((BiomeMeta) o));
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
		return SimpleBiomeMetaList.getHandle(this.meta.get(index));
	}

	@Override
	public int indexOf(Object o) {
		if (!(o instanceof BiomeMeta)) {
			return -1;
		}

		return this.meta.indexOf(new SimpleBiomeMeta((BiomeMeta) o));
	}

	@Override
	public int lastIndexOf(Object o) {
		if (!(o instanceof BiomeMeta)) {
			return -1;
		}

		return this.meta.lastIndexOf(new SimpleBiomeMeta((BiomeMeta) o));
	}

	@Override
	public boolean isEmpty() {
		return this.meta.isEmpty();
	}

	@Override
	public Iterator<BiomeMeta> iterator() {
		return new Iterator<BiomeMeta>() {
			private final Iterator<me.cybermaxke.mighty.biome.api.BiomeMeta> it =
					SimpleBiomeMetaList.this.meta.iterator();

			@Override
			public boolean hasNext() {
				return this.it.hasNext();
			}

			@Override
			public BiomeMeta next() {
				return SimpleBiomeMetaList.getHandle(this.it.next());
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
					SimpleBiomeMetaList.this.meta.listIterator(index);

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
				return SimpleBiomeMetaList.getHandle(this.it.next());
			}

			@Override
			public int nextIndex() {
				return this.it.nextIndex();
			}

			@Override
			public BiomeMeta previous() {
				return SimpleBiomeMetaList.getHandle(this.it.previous());
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
		if (!(o instanceof BiomeMeta)) {
			return false;
		}

		return this.meta.remove(new SimpleBiomeMeta((BiomeMeta) o));
	}

	@Override
	public BiomeMeta remove(int index) {
		return SimpleBiomeMetaList.getHandle(this.meta.remove(index));
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Object[] array = c.toArray(new Object[] {});

		boolean succes = false;
		for (int i = 0; i < array.length; i++) {
			if (this.remove(array[i]) && !succes) {
				succes = true;
			}
		}

		return succes;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean succes = false;
		for (int i = 0; i < this.size(); i++) {
			if (!c.contains(this.get(i))) {
				this.remove(i);

				if (!succes) {
					succes = true;
				}
			}
		}

		return succes;
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

	public static BiomeMeta getHandle(me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		return meta == null ? null : getMeta(meta).getHandle();
	}

	public static SimpleBiomeMeta getMeta(me.cybermaxke.mighty.biome.api.BiomeMeta meta) {
		return (SimpleBiomeMeta) (meta instanceof SimpleBiomeMeta ? meta :
			new SimpleBiomeMeta(meta));
	}
}