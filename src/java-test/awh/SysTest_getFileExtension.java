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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;

@RunWith(Parameterized.class)
public class SysTest_getFileExtension {
    @Parameters(name = "{1}")
    public static Collection<Object[]> generateTestParameters() {
        return Arrays.asList(new Object[][] {
            { "png", "picture.png" },
            { "gz", "archive.tar.gz" },
            { "html", "dir/file.html" },
            { "jkl", "/abc/def/ghi.jkl" },
        });
    }

    private final String expectedExtension;
    private final String actualFilename;

    public SysTest_getFileExtension(String extension, String filename) {
        expectedExtension = extension;
        actualFilename = filename;
    }

    @Test
    public void test() {
        Assert.assertEquals(expectedExtension, Sys.getFileExtension(actualFilename));
    }
}
