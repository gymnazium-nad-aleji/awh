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

import java.nio.file.Path;
import java.nio.file.Paths;

/** System utilities. */
public final class Sys {
    
    /** Prevent instantiation. */
    private Sys() {}

    /** Terminates the application and prints information message.
     *
     * @param format printf-like format string.
     * @param args Arguments for the formatted message.
     */
    public static void die(final String format, final Object... args) {
        Sys.die(0, format, args);
    }

    /** Terminates the application and prints information message.
    *
    * @param status Exit status.
    * @param format printf-like format string.
    * @param args Arguments for the formatted message.
    */
    public static void die(final int status, final String format, final Object... args) {
        System.out.printf(format, args);
        System.out.println();
        System.exit(status);
    }
    
    /** Get file extensions from file path.
     * 
     * @param filepath Relative or absolute file path.
     * @return File extension (last if multiple present).
     */
    public static String getFileExtension(final String filepath) {
        Problem.whenNull(filepath, "file path cannot be null");
        
        Path name = Paths.get(filepath).getFileName();
        if (name == null) {
            throw new Problem("Path '%s' does not contain last element.", filepath);
        }
        String[] parts = name.toString().split("[.]");
        if (parts.length <= 1) {
            return "";
        } else {
            return parts[parts.length - 1];
        }
    }
}
