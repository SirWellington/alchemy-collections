/*
 * Copyright 2015 Aroma Tech.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sir.wellington.alchemy.collections.sets;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;

/**
 *
 * @author SirWellington
 */
@NonInstantiable
public final class Sets
{

    private final static Logger LOG = LoggerFactory.getLogger(Sets.class);

    Sets() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }

    public static <E> Set<E> create()
    {
        return new HashSet<>();
    }

    public static <E> Set<E> emptySet()
    {
        Set<E> set = create();
        return Collections.unmodifiableSet(set);
    }

    public static <E> Set<E> nullToEmpty(Set<E> set)
    {
        return set == null ? emptySet() : set;
    }

    /**
     * Just an alias for {@link #copyOf(java.util.Collection) }
     * @param <E>
     * @param collection
     * @return 
     */
    public static <E> Set<E> toSet(Collection<E> collection)
    {
        return copyOf(collection);
    }

    public static <E> Set<E> copyOf(Collection<E> collection)
    {
        if (collection == null)
        {
            return emptySet();
        }

        Set<E> set = create();
        set.addAll(collection);

        return set;
    }
}
