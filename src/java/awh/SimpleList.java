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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Simple list-like collection with some extra functionality.
 *
 * <p>
 * Unlike standard Java lists, this class also offers extra functionality
 * for simple usage, such as sorting or reversing of the elements.
 *
 * @param <E> Type of elements in the list.
 */
class SimpleList<E extends Comparable<? super E>> implements Iterable<E> {
    /** Actual implementation behind this wrapper. */
    private final List<E> backend = new ArrayList<>();
    
    /** Default constructor. */
    SimpleList() {}
    
    /** Append value to the end of the list.
     * 
     * @param value Value to append.
     */
    public void add(final E value) {
        Problem.whenNull(value, "element that is added");
        
        backend.add(value);
    }
    
    /** Tell number of elements in the list.
     * 
     * @return Number of elements in the list.
     */
    public int size() {
        return backend.size();
    }
    
    /** Tell whether there are no elements in the list.
     * 
     * @return True when no elements are present in the list.
     */
    public boolean isEmpty() {
        return backend.isEmpty();
    }
    
    /** Return element at the specified position in the list.
     *
     * <p>
     * For positive indices, count from beginning of the list (as with normal lists).
     * For negative indices, count from end of the list (-1 is the last element).
     *
     * @param index Position (zero based).
     * @return Element at given index.
     */
    public E get(final int index) {
        Problem.whenNotInRange(this.getClass().getName() + " index", index, -size(), size());
        
        int positiveIndex = index >= 0 ? index : size() + index;
        
        return backend.get(positiveIndex);
    }

    /** Set element at the specified position in the list.
     *
     * <p>
     * For positive indices, count from beginning of the list (as with normal lists).
     * For negative indices, count from end of the list (-1 is the last element).
     *
     * @param index Position (zero based).
     * @param newValue New value to be set.
     * @return Previous element at given index.
     */
    public E set(final int index, final E newValue) {
        Problem.whenNotInRange(this.getClass().getName() + " index", index, -size(), size());

        int positiveIndex = index >= 0 ? index : size() + index;

        return backend.set(positiveIndex, newValue);
    }
    
    /** Removes all elements from the list.  */
    public void clear() {
        backend.clear();
    }
    
    /** Naturally sorts the elements in the list (in place). */
    public void sort() {
        Collections.sort(backend);
    }

    /** Get maximum value in the list.
     *
     * @return Maximum value.
     * @throws Problem When list is empty.
     */
    public E max() {
        if (isEmpty()) {
            throw new Problem("Cannot find maximum for empty list");
        }
        int maxIndex = 0;
        for (int i = 1; i < size(); i++) {
            if (get(i).compareTo(get(maxIndex)) > 0) {
                maxIndex = i;
            }
        }
        return get(maxIndex);
    }

    /** Reverse order of elements in the list (in place). */
    public void reverse() {
        Collections.reverse(backend);
    }
    
    /** Randomly shuffles elements of the list (in place). */
    public void shuffle() {
        Collections.shuffle(backend);
    }
    
    /** Stringify elements of the list and join them.
     *
     * @param delim Delimiter between individual elements.
     * @return Elements of the list joined with given separator,
     */
    public String join(final String delim) {
        Problem.whenNull(delim, "separator");
        
        if (isEmpty()) {
            return "";
        }
        
        StringBuilder res = new StringBuilder();
        for (E e : backend) {
            res.append(e.toString());
            res.append(delim);
        }
        res.delete(res.length() - delim.length(), res.length());
        
        return res.toString();
    }
    
    /** Create printable form of this list.
     *
     * <p>
     * This function is equivalent to calling join() with comma
     * (<code>,</code>) as a delimiter.
     *
     * @return Printable from of this list.
     */
    @Override
    public String toString() {
        return String.format("[%s]", join(","));
    }
    
    /** Checks for equality of two lists.
     *
     * @param obj Other object to compare with.
     * @return Whether both lists are equal (all their elements are equal and in the same order).
     */
    @Override
    public boolean equals(final Object obj) {
        if ((obj == null) || !(obj instanceof SimpleList)) {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        SimpleList<E> other = (SimpleList<E>) obj;
        return backend.equals(other.backend);
    }
    
    /** Compute hash code of this list.
     *
     * @return Hash code of this list.
     */
    @Override
    public int hashCode() {
        return backend.hashCode();
    }

    /** Return iterator over this list.
     *
     * @return Standard Java iterator to be used in for loops etc.
     */
    @Override
    public Iterator<E> iterator() {
        return backend.iterator();
    }
}
