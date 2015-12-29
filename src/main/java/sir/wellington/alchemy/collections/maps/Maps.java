/*
 * Copyright 2015 Sir Wellington.
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
package sir.wellington.alchemy.collections.maps;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadSafe;
import tech.sirwellington.alchemy.annotations.concurrency.ThreadUnsafe;

import static java.util.Collections.EMPTY_MAP;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;

/**
 *
 * @author SirWellington
 */
@NonInstantiable
public final class Maps
{

    private final static Logger LOG = LoggerFactory.getLogger(Maps.class);

    private Maps() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate this class");
    }

    @ThreadUnsafe
    public static <K, V> Map<K, V> create()
    {
        return new HashMap<>();
    }

    @ThreadSafe
    public static <K, V> Map<K, V> createSynchronized()
    {
        return new ConcurrentHashMap<>();
    }

    public static boolean isEmpty(Map<?, ?> map)
    {
        return map != null && !map.isEmpty();
    }

    public static <K, V> Map<K, V> merge(Map<K, V> firstMap, Map<K, V>... others)
    {
        Map<K, V> result = new HashMap<>();

        if (!isEmpty(firstMap))
        {
            result.putAll(firstMap);
        }

        if (others != null)
        {
            Arrays.asList(others)
                    .stream()
                    .filter(map -> !isEmpty(map))
                    .forEach(map -> result.putAll(map));
        }

        return result;
    }

    public static <K, V> Map<K, V> nullToEmpty(Map<K, V> map)
    {
        return isEmpty(map) ? EMPTY_MAP : map;
    }

    public static <K, V> Map<K, V> immutableCopyOf(Map<K, V> map) throws IllegalArgumentException
    {
        if (map == null)
        {
            return Collections.emptyMap();
        }

        Map<K, V> copy = new HashMap<>(map);
        return Collections.unmodifiableMap(copy);
    }

    public static <K, V> Map<K, V> mutableCopyOf(Map<K, V> map)
    {
        return copyOf(map, () -> new HashMap<>());
    }

    public static <K, V> Map<K, V> copyOf(Map<K, V> map, Supplier<Map<K, V>> mapSupplier)
    {

        checkThat(mapSupplier)
                .usingMessage("map supplier cannot be null")
                .is(notNull());

        Map<K, V> result = mapSupplier.get();
        checkThat(result)
                .usingMessage("supplier returned a null map")
                .is(notNull());

        if (!isEmpty(map))
        {
            result.putAll(map);
        }

        return result;
    }

}