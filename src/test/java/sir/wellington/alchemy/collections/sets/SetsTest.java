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
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphanumericString;
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

}
