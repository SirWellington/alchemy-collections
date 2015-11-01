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

import static java.util.Collections.EMPTY_MAP;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.Assertions.notNull;

/**
 *
 * @author SirWellington
 */
public final class MapOperations
{

    private final static Logger LOG = LoggerFactory.getLogger(MapOperations.class);

    private MapOperations() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate this class");
    }

    public static <K, V> Map<K, V> create()
    {
        return new HashMap<>();
    }

    public static boolean isEmpty(Map<?, ?> map)
    {
        return map != null && !map.isEmpty();
    }

    public static <K, V> Map<K, V> join(Map<K, V> firstMap, Map<K, V>... others)
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
        checkThat(map).is(notNull());
        Map<K, V> copy = new HashMap<>(map);
        return Collections.unmodifiableMap(copy);
    }

    public static <K, V> Map<K, V> mutableCopyOf(Map<K, V> map)
    {
        checkThat(map).is(notNull());
        return copyOf(map, () -> new HashMap<>());
    }

    public static <K, V> Map<K, V> copyOf(Map<K, V> map, Supplier<Map<K, V>> mapSupplier)
    {
        checkThat(map).is(notNull());

        checkThat(mapSupplier)
                .usingMessage("map supplier cannot be null")
                .is(notNull());

        Map<K, V> result = mapSupplier.get();
        checkThat(result)
                .usingMessage("supplier returned a null map")
                .is(notNull());

        result.putAll(map);
        return result;
    }

}
