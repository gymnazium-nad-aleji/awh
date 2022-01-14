/*
 * MIT License
 * Copyright (c) 2018 Vojtech Horky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package awh;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

public class SimpleListTest {
    private SimpleList<String> alphabet;
    private final SimpleList<String> empty = new SimpleList<>();
    private SimpleList<String> unorderedLetters;


    @Before
    public void setUp() {
        alphabet = new SimpleList<>();
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("D");
        alphabet.add("E");

        unorderedLetters = new SimpleList<>();
        unorderedLetters.add("A");
        unorderedLetters.add("M");
        unorderedLetters.add("B");
        unorderedLetters.add("Z");
        unorderedLetters.add("Q");

    }
    
    @Test
    public void getFirstElement() {
       Assert.assertEquals("A", alphabet.get(0));
    }
    
    @Test
    public void getLastElement() {
       Assert.assertEquals("E", alphabet.get(4));
    }
    
    @Test
    public void getLastElementBackwards() {
        Assert.assertEquals("E", alphabet.get(-1));
    }
    
    @Test
    public void getFirstElementBackwards() {
        Assert.assertEquals("A", alphabet.get(-5));
    }
    
    @Test
    public void joinWorks() {
        Assert.assertEquals("A,B,C,D,E", alphabet.join(","));
    }

    @Test
    public void setFirstElement() {
        Assert.assertEquals("A", alphabet.set(0, "X"));
        Assert.assertEquals("X", alphabet.get(0));
    }

    @Test
    public void setLastElement() {
        Assert.assertEquals("E", alphabet.set(4, "X"));
        Assert.assertEquals("X", alphabet.get(4));
    }

    @Test
    public void setFirstElementBackwards() {
        Assert.assertEquals("A", alphabet.set(-5, "X"));
        Assert.assertEquals("X", alphabet.get(0));
    }

    @Test
    public void setLastElementBackwards() {
        Assert.assertEquals("E", alphabet.set(-1, "X"));
        Assert.assertEquals("X", alphabet.get(4));
    }

    @Test
    public void joinOnEmpty() {
        Assert.assertEquals("", empty.join(","));
    }
}
