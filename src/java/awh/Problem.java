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

/** Wrapper exception for all kinds of unexpected situations. */
public class Problem extends RuntimeException {
    /** Serial UID.  */
    private static final long serialVersionUID = 1L;

    /** Constructor with message.
     * 
     * @param format printf-like format describing the exception.
     * @param args Arguments to format.
     */
    public Problem(final String format, final Object... args) {
        super(String.format(format, args));
    }

    /** Throws when value is out of range.
     * 
     * @param valueName Name of the value that is being checked (such as coordinate).
     * @param value Actual value to be checked.
     * @param minInclusive Minimal value (inclusive).
     * @param maxExclusive Maximum value (exclusive).
     * @throws Problem When value is out of range.
     */
    public static void whenNotInRange(final String valueName, final long value,
            final long minInclusive, final long maxExclusive) {
        if ((value < minInclusive) || (value >= maxExclusive)) {
            throw new Problem("%s out of range, %d not in [%d, %d).",
                    valueName, value, minInclusive, maxExclusive);
        }
    }
    
    /** Throws when value is null.
     * 
     * @param value Value to be checked for nullity.
     * @param name Name of the value that is being checked.
     * @throws Problem When value is null.
     */
    public static void whenNull(final Object value, final String name) {
        if (value == null) {
            throw new Problem("%s cannot be null.", name);
        }
    }
}
