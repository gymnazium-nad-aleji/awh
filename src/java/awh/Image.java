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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Raster image representation. */
public final class Image {
    /** Maximum size of the image in one dimension.
     *
     * <p>
     * This is an artificial limitation aimed at beginners to prevents
     * bad code running out of memory etc.
     * Use BufferedImage class directly if you hit this limitation in
     * your program.
     */
    private static final int MAX_DIMENSION = Short.MAX_VALUE;
    
    /** Actual image. */
    private BufferedImage backend;

    /** Constructor from existing image.
     *
     * @param im Existing image.
     */
    private Image(final BufferedImage im) {
        backend = im;
    }
    
    /** Load image from file on disk.
     *
     * @param path Path to the file.
     * @return Loaded image.
     * @throws Problem When image cannot be loaded.
     */
    public static Image loadFromFile(final String path) {
        Problem.whenNull(path, "image path");
        
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new Problem(String.format("Failed to load image from '%s' (%s).",
                    path, e.getMessage()));
        }
        return new Image(image);
    }
    
    /** Create empty image.
     *
     * @param width Width of new image in pixels.
     * @param height Height of new image in pixels.
     * @param bg Background color.
     * @return Created image.
     */
    public static Image createEmpty(final int width, final int height, final Color bg) {
        Problem.whenNull(bg, "background color");
        checkDimensions(width, height);
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D gr = image.createGraphics();
        gr.setBackground(bg.toAwtColor());
        gr.clearRect(0, 0, width, height);
        gr.dispose();
        
        return new Image(image);
    }
    
    /** Tell image width.
     * 
     * @return Image width in pixels.
     */
    public int getWidth() {
        return backend.getWidth();
    }

    /** Tell image height.
     *
     * @return Image height in pixels.
     */
    public int getHeight() {
        return backend.getHeight();
    }

    /** Get color at given position.
     *
     * @param x Position at X axis (zero based, left to right).
     * @param y Position at Y axis (zero based, top to bottom).
     * @return Color at given position.
     */
    public Color getPixel(final int x, final int y) {
        checkPosition(x, y);
        
        return Color.fromMergedRgb(backend.getRGB(x, y));
    }

    /** Set color at given position.
    *
    * @param x Position at X axis (zero based, left to right).
    * @param y Position at Y axis (zero based, top to bottom).
    * @param color New color to set at given position.
    */
    public void setPixel(final int x, final int y, final Color color) {
        checkPosition(x, y);
        Problem.whenNull(color, "new pixel color");
        
        backend.setRGB(x, y, color.toMergedRgb());
    }
    
    /** Rescale image to new size.
     *
     * @param newWidth New width (in pixels).
     * @param newHeight New height (in pixels).
     */
    public void rescale(final int newWidth, final int newHeight) {
        checkDimensions(newWidth, newHeight);
        
        java.awt.Image rescaled = backend.getScaledInstance(newWidth, newHeight,
                java.awt.Image.SCALE_SMOOTH);
        if (rescaled instanceof BufferedImage) {
            backend = (BufferedImage) rescaled;
        } else {
            backend = recreateImage(rescaled, BufferedImage.TYPE_INT_ARGB);
        }
    }

    /** Insert another image into this one.
     *
     * @param other Other image to insert.
     * @param x Position of left-top corner of the inserted image.
     * @param y Position of left-top corner of the inserted image.
     */
    public void pasteFrom(final Image other, final int x, final int y) {
        checkPosition(x, y);
        Problem.whenNull(backend, "image to be pasted");
        
        Graphics2D gr = backend.createGraphics();
        gr.drawImage(other.backend, x, y, null);
        gr.dispose();
    }

    /** Save image to file.
     *
     * <p>
     * The image type (JPEG, PNG, etc.) is determined from file extension
     * automatically.
     *
     * @param path Destination file path.
     */
    public void saveToFile(final String path) {
        Problem.whenNull(path, "file path");
        
        String format = determineImageFormatFromFilename(path);
        try {
            BufferedImage toSave = backend;
            if ("JPEG".equals(format)) {
                toSave = recreateImage(backend, BufferedImage.TYPE_INT_RGB);
            }
            ImageIO.write(toSave, format, new File(path));
        } catch (IOException e) {
            throw new Problem("Failed to save image to '%s' as %s (%s).",
                    path, format, e.getMessage());
        }
    }
    
    /** Convert existing image to a new type.
     *
     * @param im Image to be converted.
     * @param type New type (see constructor of BufferedImage class for available types).
     * @return Converted image.
     */
    private BufferedImage recreateImage(final java.awt.Image im, final int type) {
        BufferedImage res = new BufferedImage(im.getWidth(null), im.getHeight(null), type);
        Graphics2D gr = res.createGraphics();
        gr.drawImage(im, 0, 0, null);
        gr.dispose();
        return res;
    }
    
    /** Determine image format from a filename.
     *
     * @param path File path.
     * @return Image format recognizable by javax.imageio.ImageIO class.
     */
    private String determineImageFormatFromFilename(final String path) {
        String ext;
        try {
            ext = Sys.getFileExtension(path).toLowerCase();
        } catch (Problem e) {
            ext = "";
        }
        if ("png".equals(ext)) {
            return "PNG";
        } else if ("jpg".equals(ext) || "jpeg".equals(ext)) {
            return "JPEG";
        } else if ("gif".equals(ext)) {
            return "GIF";
        } else {
            throw new Problem("Failed to determine image format from path '%s'.", path);
        }
    }

    /** Check that given coordinates are valid for current picture.
     *
     * @param x Position on the X axis.
     * @param y Position on the Y axis.
     * @throws Problem When one of the coordinates is out of range.
     */
    private void checkPosition(final int x, final int y) {
        Problem.whenNotInRange("x coordinate", x, 0, getWidth());
        Problem.whenNotInRange("y coordinate", y, 0, getHeight());
    }
    
    /** Check that given dimensions are valid.
     *
     * @param width Image width.
     * @param height Image height.
     * @throws Problem When dimensions are either too big or negative.
     */
    private static void checkDimensions(final int width, final int height) {
        Problem.whenNotInRange("new image width", width, 1, MAX_DIMENSION);
        Problem.whenNotInRange("new image height", height, 1, MAX_DIMENSION);
    }
}
