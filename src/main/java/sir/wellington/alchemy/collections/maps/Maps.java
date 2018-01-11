/*
 * Copyright © 2018. Sir Wellington.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sir.wellington.alchemy.collections.maps;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.Internal;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.concurrency.*;

import static tech.sirwellington.alchemy.arguments.Arguments.*;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;

/**
 *
 * @author SirWellington
 */
@NonInstantiable
public final class Maps
{

    private final static Logger LOG = LoggerFactory.getLogger(Maps.class);

    Maps() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate this class");
    }

    /**
     * Creates a new {@link HashMap}.
     *
     * @param <K>
     * @param <V>
     * @return
     */
    @ThreadUnsafe
    public static <K, V> Map<K, V> create()
    {
        return new HashMap<>();
    }

    /**
     * Creates a {@link ThreadSafe} {@link ConcurrentHashMap}.
     * @param <K>
     * @param <V>
     * @return
     */
    @ThreadSafe
    public static <K, V> Map<K, V> createSynchronized()
    {
        return new ConcurrentHashMap<>();
    }

    public static boolean isEmpty(Map<?, ?> map)
    {
        return map == null || map.isEmpty();
    }

    public static <K, V> Map<K, V> merge(Map<K, V> firstMap, Map<K, V>... others)
    {
        Map<K, V> result = create();

        if (!isEmpty(firstMap))
        {
            result.putAll(firstMap);
        }

        if (others != null)
        {
            for (Map<K, V> map : Arrays.asList(others))
            {
                result.putAll(map);
            }
        }

        return result;
    }

    /**
     * Useful for ensuring you have an object to work with, even if its null.
     *
     * @param <K>
     * @param <V>
     * @param map
     *
     * @return An empty map if {@code map == null}, otherwise returns the map.
     */
    public static <K, V> Map<K, V> nullToEmpty(Map<K, V> map)
    {
        return isEmpty(map) ? Maps.<K, V>emptyMap() : map;
    }

    /**
     * Creates a shallow copy of the specified map, disallowing modification operations
     * like {@linkplain Map#put(java.lang.Object, java.lang.Object) Put}.
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     * @throws IllegalArgumentException
     */
    public static <K, V> Map<K, V> immutableCopyOf(Map<K, V> map) throws IllegalArgumentException
    {
        if (map == null)
        {
            return Collections.emptyMap();
        }

        Map<K, V> copy = new HashMap<>(map);
        return Collections.unmodifiableMap(copy);
    }

    /**
     * Alias for {@link #copyOf(java.util.Map) }.
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public static <K, V> Map<K, V> mutableCopyOf(Map<K, V> map)
    {
        return copyOf(map);
    }

    /**
     * Creates a shallow copy of map that is mutable.
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public static <K, V> Map<K, V> copyOf(Map<K, V> map)
    {
        Map<K, V> result = create();

        if (!isEmpty(map))
        {
            result.putAll(map);
        }

        return result;
    }

    private static <K, V> void checkNotNull(Object object, String message)
    {
        checkThat(object).usingMessage(message).is(notNull());
    }

    /**
     * This Singleton map is completely empty and immutable.
     */
    @Internal
    @Immutable
    private static final Map EMPTY_MAP = immutableCopyOf(create());

    /**
     * Returns an Empty Map that is not designed to be mutable.
     * @param <K>
     * @param <V>
     * @return
     */
    @Immutable
    public static <K, V> Map<K, V> emptyMap()
    {
        return EMPTY_MAP;
    }

}
