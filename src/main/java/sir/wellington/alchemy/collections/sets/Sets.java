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

package sir.wellington.alchemy.collections.sets;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.arguments.*;
import tech.sirwellington.alchemy.annotations.arguments.Optional;
import tech.sirwellington.alchemy.arguments.assertions.Assertions;
import tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions;

import static tech.sirwellington.alchemy.arguments.Arguments.*;

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

    public static <E> Set<E> createFrom(@Required E first, @Optional E... rest)
    {
        checkThat(first)
            .usingMessage("first element is required")
            .is(Assertions.<E>notNull());

        Set<E> set = Sets.create();
        set.add(first);

        if (rest != null)
        {
            set.addAll(Arrays.asList(rest));
        }

        return set;
    }

    /**
     * An empty set cannot be modified or have new elements added to it.
     *
     * @param <E>
     * @return
     */
    public static <E> Set<E> emptySet()
    {
        Set<E> set = create();
        return Collections.unmodifiableSet(set);
    }

    public static <E> Set<E> nullToEmpty(Set<E> set)
    {
        return set == null ? Sets.<E>emptySet() : set;
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
        Set<E> set = create();

        if (collection == null)
        {
            return set;
        }

        set.addAll(collection);

        return set;
    }

    public static boolean isEmpty(Set<?> set)
    {
        return Lists.isEmpty(set);
    }

    public static <E> E oneOf(@NonEmpty Set<E> set)
    {
        checkThat(set).is(CollectionAssertions.<E>nonEmptySet());

        List<E> list = Lists.copy(set);
        return Lists.oneOf(list);
    }

    /**
     * Creates an Intersection of all the specified Sets.
     *
     * @param <E>
     * @param first
     * @param rest
     * @return
     */
    public static <E> Set<E> intersectionOf(@Optional Collection<E> first, Collection<E>... rest)
    {
        Set<E> intersection = Sets.copyOf(first);

        //If the first element is the empty set,
        //The result will always be the empty set.
        if (isEmpty(intersection))
        {
            return intersection;
        }

        for (Collection<E> set : rest)
        {
            intersection.retainAll(set);

            if (isEmpty(intersection))
            {
                //No need to keep going if the intersection is empty.
                break;
            }
        }

        return intersection;
    }

    /**
     * Creates a Union of all the specified collections.
     *
     * @param <E>
     * @param first
     * @param rest
     * @return
     */
    public static <E> Set<E> unionOf(@Optional Collection<E> first, Collection<E>... rest)
    {
        Set<E> union = Sets.copyOf(first);

        for (Collection<E> collection : rest)
        {
            union.addAll(collection);
        }

        return union;
    }

    public static <E> boolean containTheSameElements(Collection<E> first, Collection<E>... rest)
    {
        Set<E> unionOf = intersectionOf(first, rest);
        return !isEmpty(unionOf);
    }
}
