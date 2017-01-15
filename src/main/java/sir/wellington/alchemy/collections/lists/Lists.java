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

 
package sir.wellington.alchemy.collections.lists;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Optional;
import tech.sirwellington.alchemy.annotations.arguments.Required;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptyList;

/**
 * Operations built around {@linkplain List Lists}.
 * 
 * @author SirWellington
 */
@NonInstantiable
public final class Lists 
{
    private final static Logger LOG = LoggerFactory.getLogger(Lists.class);
   
    Lists() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate this class");
    }
    
    /**
     * Determines whether the specified collection is empty or not.
     * @param list
     * @return 
     */
    public static boolean isEmpty(@Optional Collection<?> list)
    {
        return list == null || list.isEmpty();
    }
    
    /**
     * Determines whether the specified collection is not empty.
     * 
     * @param list
     * @return 
     */
    public static boolean notEmpty(@Optional Collection<?> list)
    {
        return !isEmpty(list);
    }

    /**
     * Creates an {@link ArrayList}.
     * 
     * @param <E>
     * 
     * @return 
     */
    public static <E> List<E> create()
    {
        return new ArrayList<>();
    }

    public static <E> List<E> createFrom(@Required E first, @Optional E... rest)
    {
        checkThat(first)
            .usingMessage("missing first value")
            .is(notNull());

        List<E> list = Lists.create();
        
        list.add(first);
        
        if (rest != null)
        {
            list.addAll(Arrays.asList(rest));
        }

        return list;
    }
    
    /**
     * Creates a copy of the 
     * @param <E>
     * @param collection
     * @return 
     */
    public static <E> List<E> copy(@Optional Collection<E> collection)
    {
        List<E> list = create();
        if (isEmpty(collection))
        {
            return list;
        }

        list.addAll(collection);
        return list;
    }

    /**
     * Creates a new Empty List that is immutable, so no new values
     * can be added to it.
     * 
     * @param <E>
     * @return 
     */
    public static <E> List<E> emptyList()
    {
        List<E> list = create();
        return Collections.unmodifiableList(list);
    }

    public static <E> List<E> toList(Collection<E> set)
    {
        return copy(set);
    }
    
    public static <E> E oneOf(@NonEmpty List<E> list)
    {
        checkThat(list)
            .usingMessage("List cannot be empty")
            .is(notNull())
            .is(nonEmptyList());
        
        Random random = new Random();
        int index = random.nextInt(list.size());
        
        return list.get(index);
    }

    public static <E> List<E> combine(@Optional List<E> first, @Optional List<E>...additional)
    {
        List<E> result = Lists.create();
   
        if (!isEmpty(first))
        {
            result.addAll(first);
        }

        if (additional != null)
        {
            for (List<E> list : additional)
            {
                if (!isEmpty(list))
                {
                    result.addAll(list);
                }
            }
        }
        
        return result;
    }
    
    public static <E> List<E> nullToEmpty(@Optional List<E> list)
    {
        return list == null ? emptyList() : list;
    }
    
    public static <E> List<E> immutableCopyOf(@Optional List<E> list)
    {
        if (isEmpty(list))
        {
            return emptyList();
        }
        
        return Collections.unmodifiableList(list);
    }
    
    /**
     * Gets the first element in the list.
     * 
     * @param <E>
     * @param list Cannot be null or empty;
     * @return
     * @throws IllegalArgumentException If the list is empty
     */
    public static <E> E first(@NonEmpty List<E> list) throws IllegalArgumentException
    {
        checkThat(list)
            .is(notNull())
            .is(nonEmptyList());
        
        return list.get(0);
    }
    
    /**
     * Gets the last element in the list.
     * 
     * @param <E>
     * @param list Cannot be null or empty.
     * @return
     * @throws IllegalArgumentException If the list is empty
     */
    public static <E> E last(@NonEmpty List<E> list) throws IllegalArgumentException
    {
        checkThat(list)
            .is(notNull())
            .is(nonEmptyList());
        
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }
}
