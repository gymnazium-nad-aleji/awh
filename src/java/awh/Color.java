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

/** Color representation. */
public final class Color {
    /** Aqua HTML color. */
    public static final Color AQUA = new Color(0x00, 0xFF, 0xFF);
 
    /** Black HTML color. */
    public static final Color BLACK = new Color(0x00, 0x00, 0x00);
 
    /** Blue HTML color. */
    public static final Color BLUE = new Color(0x00, 0x00, 0xFF);
 
    /** Fuchsia HTML color. */
    public static final Color FUCHSIA = new Color(0xFF, 0x00, 0xFF);
 
    /** Gray HTML color. */
    public static final Color GRAY = new Color(0x80, 0x80, 0x80);
 
    /** Green HTML color. */
    public static final Color GREEN = new Color(0x00, 0x80, 0x00);
 
    /** Lime HTML color. */
    public static final Color LIME = new Color(0x00, 0xFF, 0x00);
 
    /** Maroon HTML color. */
    public static final Color MAROON = new Color(0x80, 0x00, 0x00);
 
    /** Navy HTML color. */
    public static final Color NAVY = new Color(0x00, 0x00, 0x80);
 
    /** Olive HTML color. */
    public static final Color OLIVE = new Color(0x80, 0x80, 0x00);
 
    /** Purple HTML color. */
    public static final Color PURPLE = new Color(0x80, 0x00, 0x80);
 
    /** Red HTML color. */
    public static final Color RED = new Color(0xFF, 0x00, 0x00);
 
    /** Silver HTML color. */
    public static final Color SILVER = new Color(0xC0, 0xC0, 0xC0);
 
    /** Teal HTML color. */
    public static final Color TEAL = new Color(0x00, 0x80, 0x80);
 
    /** White HTML color. */
    public static final Color WHITE = new Color(0xFF, 0xFF, 0xFF);
 
    /** Yellow HTML color. */
    public static final Color YELLOW = new Color(0xFF, 0xFF, 0x00);
     
    
    /** Red component in 0 to 255 range. */
    private int red;
    
    /** Green component in 0 to 255 range. */
    private int green;
    
    /** Blue component in 0 to 255 range. */
    private int blue;
    
    /** Constructor from RGB components.
     * 
     * @param r Red component in 0 to 255 range (inclusive).
     * @param g Green component in 0 to 255 range (inclusive).
     * @param b Blue component in 0 to 255 range (inclusive).
     */
    public Color(final int r, final int g, final int b) {
        Problem.whenNotInRange("red component", r, 0, 256);
        Problem.whenNotInRange("green component", g, 0, 256);
        Problem.whenNotInRange("blue component", b, 0, 256);
        
        red = r;
        green = g;
        blue = b;
    }
    
    /** Create color from RGB components.
     * 
     * @param r Red component in 0 to 255 range (inclusive).
     * @param g Green component in 0 to 255 range (inclusive).
     * @param b Blue component in 0 to 255 range (inclusive).
     * @return Constructed color.
     */
    public static Color fromRgb(final int r, final int g, final int b) {
        return new Color(r, g, b);
    }
    
    /** Create color from merged RGB components.
     *
     * @param rgb RGB as single integer, each component occupies one byte.
     * @return Constructed color.
     */
    public static Color fromMergedRgb(final int rgb) {
        return new Color(
                (rgb & 0x00ff0000) >> 16,
                (rgb & 0x0000ff00) >> 8,
                (rgb & 0x000000ff) >> 0
        );
    }
    
    /** Get red component of the color.
     * 
     * @return Red component in range 0 to 255 inclusive.
     */
    public int getRed() {
        return red;
    }

    /** Get green component of the color.
     * 
     * @return Green component in range 0 to 255 inclusive.
     */
    public int getGreen() {
        return green;
    }
    
    /** Get blue component of the color.
     * 
     * @return Blue component in range 0 to 255 inclusive.
     */
    public int getBlue() {
        return blue;
    }
    
    /** Convert the merged RGB notation.
     * 
     * @return Color as single integer, each RGB component occupies one byte.
     */
    public int toMergedRgb() {
        return (red << 16) | (green << 8) | blue;
    }

    /** Convert to awt package representation.
     * 
     * @return This color as awt class.
     */
    public java.awt.Color toAwtColor() {
        return new java.awt.Color(toMergedRgb());
    }
    
    /** Convert to HTML notation.
     *
     * @return Color in hexadecimal notation, preceded by hash sign.
     */
    public String toHtmlNotation() {
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}
