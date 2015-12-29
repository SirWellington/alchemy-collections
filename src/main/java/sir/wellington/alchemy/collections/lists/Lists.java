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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.annotations.arguments.NonEmpty;
import tech.sirwellington.alchemy.annotations.arguments.Nullable;

import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.Assertions.notNull;
import static tech.sirwellington.alchemy.arguments.assertions.CollectionAssertions.nonEmptyList;

/**
 *
 * @author SirWellington
 */
@NonInstantiable
public final class Lists 
{
    private final static Logger LOG = LoggerFactory.getLogger(Lists.class);
   
    private Lists() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate this class");
    }
    
    public static boolean isEmpty(@Nullable Collection<?> list)
    {
        return list == null || list.isEmpty();
    }

    public static <E> List<E> create()
    {
        return new ArrayList<>();
    }

    public static <E> List<E> copy(@Nullable Collection<E> collection)
    {
        List<E> list = create();
        if (isEmpty(collection))
        {
            return list;
        }

        list.addAll(collection);
        return list;
    }

    public static <E> List<E> emptyList()
    {
        List<E> list = create();
        return Collections.unmodifiableList(list);
    }

    public static <E> List<E> toList(Set<E> set)
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

    public static <E> List<E> combine(@Nullable List<E> first, @Nullable List<E>...additional)
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
    
}
