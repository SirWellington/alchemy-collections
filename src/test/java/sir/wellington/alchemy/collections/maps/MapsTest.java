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

package sir.wellington.alchemy.collections.maps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.mapOf;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphabeticString;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;

/**
 *
 * @author SirWellington
 */
@Repeat(10)
@RunWith(AlchemyTestRunner.class)
public class MapsTest
{

    private AlchemyGenerator<String> generator;

    @Before
    public void setUp()
    {
        generator = alphabeticString();
    }

    @Test
    public void testCreate()
    {
        Map<Object, Object> first = Maps.create();
        assertThat(first, notNullValue());
        assertThat(first.isEmpty(), is(true));

        Map<Object, Object> second = Maps.create();
        assertThat(second, notNullValue());
        assertThat(second, not(sameInstance(first)));
    }

    @Test
    public void testCreateSynchronized()
    {
        Map<Object, Object> result = Maps.createSynchronized();
        assertThat(result, notNullValue());
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void testIsEmpty()
    {
        Map<String, String> map = mapOf(generator, generator, 10);
        
        assertThat(Maps.isEmpty(map), is(false));
        
        map.clear();
        assertThat(Maps.isEmpty(map), is(true));
    }

    @Test
    public void testMerge()
    {
        Map<String,String> first = mapOf(generator, generator);
        Map<String, String> second = mapOf(generator, generator);
        
        Map<String, String> result = Maps.merge(first, second);
        assertThat(result.isEmpty(), is(false));
        
        Map<String,String> expected = new HashMap<>(first);
        expected.putAll(second);
        
        assertThat(result, is(expected));
    }

    @Test
    public void testNullToEmpty()
    {
        Map<Object, Object> map = Maps.nullToEmpty(null);
        assertThat(map, notNullValue());
        assertThat(map.isEmpty(), is(true));
        
        Map<String, String> nonEmptyMap = mapOf(generator, generator);
        Map<String, String> result = Maps.nullToEmpty(nonEmptyMap);
        assertThat(result, is(nonEmptyMap));
    }

    @Test
    public void testImmutableCopyOf()
    {
        Map<String, String> map = mapOf(generator, generator);
        Map<String, String> immutableCopy = Maps.immutableCopyOf(map);
        
        assertThat(immutableCopy, notNullValue());
        assertThat(immutableCopy, is(map));
        
        assertThrows(() -> immutableCopy.clear());
        assertThrows(() -> immutableCopy.remove("some key"));
        assertThrows(() -> immutableCopy.put(generator.get(), generator.get()));
    }

    @Test
    public void testMutableCopyOf()
    {
    }

    @Test
    public void testCopyOf()
    {
        Map<String, String> map = mapOf(generator, generator);
        
        Map<String, String> result = Maps.copyOf(map, () -> new LinkedHashMap<>());
        assertThat(result, notNullValue());
        assertThat(result, is(map));
        assertThat(result, is(instanceOf(LinkedHashMap.class)));
        
    }

}
