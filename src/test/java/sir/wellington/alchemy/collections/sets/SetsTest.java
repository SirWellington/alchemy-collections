/*
 * Copyright © 2019. Sir Wellington.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sir.wellington.alchemy.collections.sets;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import sir.wellington.alchemy.collections.lists.Lists;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.Get.one;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.StringGenerators.*;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.*;

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
        generator = alphanumericStrings();
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

    @Test
    public void testIntersectionOfWithNoParameters()
    {
        Set<Object> intersection = Sets.intersectionOf(null);
        assertThat(intersection, notNullValue());
        assertThat(intersection, is(empty()));
    }

    @Test
    public void testIntersectionOfWhenEqual()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.copyOf(first);

        Set<String> intersection = Sets.intersectionOf(first, second);
        assertThat(intersection, is(first));
        assertThat(intersection, is(second));
    }

    @Test
    public void testIntersectionOfWhenDiffered()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(generator));
        second.addAll(first);

        Set<String> expected = first;

        Set<String> intersection = Sets.intersectionOf(first, second);
        assertThat(intersection, is(expected));
    }

    @Test
    public void testIntersectionOfWhenCompletelyDiffered()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(generator));
        Set<String> expected = Sets.emptySet();

        Set<String> intersection = Sets.intersectionOf(first, second);
        assertThat(intersection, is(expected));
    }

    @Test
    public void testIntersectionOfWithEmptySet()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(generator));
        Set<String> emptySet = Sets.emptySet();
        Set<String> expected = emptySet;

        Set<String> intersection = Sets.intersectionOf(first, emptySet, second);
        assertThat(intersection, is(expected));
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
        Set<String> expected = second;

        Set<String> union = Sets.unionOf(first, second);
        assertThat(union, is(expected));
    }

    @Test
    public void testUnionOfWhenCompletelyDifferent()
    {
        Set<String> first = Sets.toSet(listOf(generator));
        Set<String> second = Sets.toSet(listOf(uuids));
        Set<String> expected = Sets.copyOf(first);
        expected.addAll(second);

        Set<String> union = Sets.unionOf(first, second);
        assertThat(union, is(expected));
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


    @Test
    public void testCreateFrom()
    {
        String first = one(strings());
        List<String> rest = listOf(strings(), 20);
        String[] restArray = rest.toArray(new String[0]);

        Set<String> expected = Sets.create();
        expected.add(first);
        expected.addAll(rest);

        Set<String> result = Sets.createFrom(first, restArray);

        assertThat(result, is(expected));
    }

    @Test
    public void testCreateFromWithOneValue()
    {
        String value = one(strings());

        Set<String> result = Sets.createFrom(value);
        assertThat(result, not(empty()));
        assertThat(result.size(), is(1));
        assertThat(result, contains(value));
    }

    @Test
    public void testCreateFromWithBadArgs()
    {
        assertThrows(() -> Sets.createFrom(null))
            .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void testCreateCanAddElementsWhenEmpty()
    {
        Set<String> emptySet = Sets.copyOf(Lists.create());
        emptySet.add(one(generator));
    }

    @Test
    public void testOneOf()
    {
        String result = Sets.oneOf(set);
        assertThat(result, isIn(set));

        Set<String> emptySet = Sets.emptySet();
        assertThrows(() -> Sets.oneOf(emptySet))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
