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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sir.wellington.alchemy.collections.sets.Sets;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.generator.StringGenerators;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.NumberGenerators.positiveIntegers;
import static tech.sirwellington.alchemy.generator.StringGenerators.strings;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class ListsTest 
{
    private AlchemyGenerator<String> generator;

    @Before
    public void setUp()
    {
        generator = StringGenerators.alphanumericString();
    }

    @DontRepeat
    @Test
    public void testCannotInstantiate()
    {
        assertThrows(() -> Lists.class.newInstance())
            .isInstanceOf(IllegalAccessException.class);
    }

    @Test
    public void testCreate()
    {
        List<Object> result = Lists.create();
        assertThat(result, notNullValue());
        assertThat(result, is(empty()));
    }

    @Test
    public void testCreateReturnsDifferent()
    {
        
        List<String> first = Lists.create();
        List<String> second = Lists.create();
        assertThat(first, not(sameInstance(second)));
        
        first.add(generator.get());
        assertThat(second, is(empty()));
    }

    @Test
    public void testCopy()
    {
        List<String> list = listOf(generator);
        
        List<String> result = Lists.copy(list);
        assertThat(result, not(sameInstance(list)));
        assertThat(result, is(list));
        
        Set<String> set = new HashSet<>(list);
        
        set.forEach(e -> assertThat(e, isIn(list)));
    }

    @Test
    public void testEmptyList()
    {
        List<Object> result = Lists.emptyList();
        assertThat(result, notNullValue());
        assertThat(result, is(empty()));
        
        String string = one(strings());
        assertThrows(() -> result.add(string));
    }

    @Test
    public void testToList()
    {
        List<Integer> numbers = listOf(positiveIntegers());
        Set<Integer> set = new HashSet<>(numbers);
        numbers.clear();
        numbers.addAll(set);
        
        List<Integer> result = Lists.toList(set);
        assertThat(result, not(empty()));
        numbers.forEach(n -> assertThat(n, isIn(result)));
        result.forEach(n -> assertThat(n, isIn(numbers)));
    }

    @Test
    public void testOneOf()
    {
        //Edge Cases
        assertThrows(() -> Lists.oneOf(null))
            .isInstanceOf(IllegalArgumentException.class);
        
        assertThrows(() -> Lists.oneOf(Lists.emptyList()))
            .isInstanceOf(IllegalArgumentException.class);
            
        List<String> strings = listOf(generator);
        String result = Lists.oneOf(strings);
        assertThat(result, isIn(strings));
        
        long start = System.currentTimeMillis();
        Set<String> set = Sets.create();
        for(String string : strings)
        {
            set.add(Lists.oneOf(strings));
        }
        long end = System.currentTimeMillis();
        System.out.println("oneOf took " + (end - start) + "ms");
        assertThat(set.size(), greaterThan(1));
    }
    

}