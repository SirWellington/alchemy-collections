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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphanumericString;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class SetsTest
{

    private AlchemyGenerator<String> generator;
    private Set<String> set;

    @Before
    public void setUp()
    {
        generator = alphanumericString();
        List<String> list = listOf(generator);
        set = new HashSet<>(list);
    }

    @DontRepeat
    @Test
    public void testCannotInstantiate()
    {
        assertThrows(() -> new Sets())
            .isInstanceOf(IllegalAccessException.class);
        
        assertThrows(() -> Sets.class.newInstance())
            .isInstanceOf(IllegalAccessException.class);
    }

    @Test
    public void testCreate()
    {
        Set<String> first = Sets.create();
        assertThat(first, notNullValue());
        assertThat(first, is(empty()));

        Set<Object> second = Sets.create();
        assertThat(second, notNullValue());
        assertThat(second, not(sameInstance(first)));

        first.add(generator.get());
        assertThat(first, not(empty()));
        assertThat(second, is(empty()));
    }

    @Test
    public void testEmptySet()
    {
        Set<Object> emptySet = Sets.emptySet();
        assertThat(emptySet, notNullValue());
        assertThat(emptySet, is(emptySet));
        
        assertThrows(() -> emptySet.add(generator.get()));
    }

    @Test
    public void testNullToEmpty()
    {
        Set<String> result = Sets.nullToEmpty(null);
        assertThat(result, notNullValue());
        assertThat(result, is(empty()));
        
        result = Sets.nullToEmpty(set);
        assertThat(result, is(set));
    }

    @Test
    public void testToSet()
    {
        List<String> list = listOf(generator);
        Set<String> result = Sets.toSet(list);
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        
        list.forEach(e -> assertThat(e, isIn(result)));
        result.forEach(e -> assertThat(e, isIn(list)));
    }

    @Test
    public void testCopyOf()
    {
           List<String> list = listOf(generator);
        Set<String> result = Sets.copyOf(list);
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        
        list.forEach(e -> assertThat(e, isIn(result)));
        result.forEach(e -> assertThat(e, isIn(list)));
    }
    
    @DontRepeat
    @Test
    public void testCopyOfNull()
    {
        Set<Object> result = Sets.copyOf(null);
        assertThat(result, notNullValue());
        assertThat(result, is(empty()));
    }

    @Test
    public void testIsEmpty()
    {
        Set<Object> emptySet = Sets.emptySet();
        assertTrue(Sets.isEmpty(emptySet));
        assertFalse(Sets.isEmpty(set));
    }

    @DontRepeat
    @Test
    public void testUnionOfWithNoParameters()
    {
        Set<Object> unionOf = Sets.unionOf(null);
        assertThat(unionOf, notNullValue());
        assertThat(unionOf.isEmpty(), is(true));
    }
    
    @Test
    public void testUnionOfWhenEqual()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.copyOf(first);
        
        Set<String> union = Sets.unionOf(first, second);
        assertThat(union, is(first));
        assertThat(union, is(second));
    }
    
    @Test
    public void testUnionOfWhenDifferent()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(generator));
        second.addAll(first);
        
        Set<String> union = Sets.unionOf(first, second);
        assertThat(union, is(first));
    }
    
    @Test
    public void testUnionOfWhenCompletelyDifferent()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(uuids));
        
        Set<String> union = Sets.unionOf(first, second);
        assertThat(union, is(empty()));
    }
    
    @Test
    public void testContainTheSameElements()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.copyOf(first);
        Set<String> third = Sets.copyOf(second);
        
        assertTrue(Sets.containTheSameElements(first, second, third));
        assertTrue(Sets.containTheSameElements(first, second));
        assertTrue(Sets.containTheSameElements(first, third));
    }
    
    @Test
    public void testContainTheSameElementsWhenDifferent()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(uuids));
        Set<String> third = Sets.toSet(listOf(uuids));
        
        assertFalse(Sets.containTheSameElements(first, second, third));
        assertFalse(Sets.containTheSameElements(first, second));
        assertFalse(Sets.containTheSameElements(first, third));
    }

}
