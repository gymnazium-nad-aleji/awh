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
package awh.demo;

/** Create video from still images. */
public class PhotosToMovie {
    private static final int STILL_FRAMES = 100;
    private static final int BLEND_FRAMES = 50;

    private static void addFrame(awh.Movie movie, awh.Image image) {
        movie.addFrame(image);
        System.out.print(".");
    }

    private static void stillFrames(awh.Movie movie, awh.Image frame) {
        for (int i = 0; i < STILL_FRAMES; i++) {
            addFrame(movie, frame);
        }
    }

    private static awh.Color blendColors(awh.Color one, int oneAmount, awh.Color two, int twoAmount) {
        return awh.Color.fromRgb(
                (one.getRed() * oneAmount + two.getRed() * twoAmount) / (oneAmount + twoAmount),
                (one.getGreen() * oneAmount + two.getGreen() * twoAmount) / (oneAmount + twoAmount),
                (one.getBlue() *oneAmount + two.getBlue() * twoAmount) / (oneAmount + twoAmount)
        );
    }

    private static awh.Image loadImage(String filename) {
        awh.Image image = awh.Image.loadFromFile(filename);
        image.rescale(480, 270);
        return image;
    }

    private static void blendWith(awh.Image image, int imageRatio, awh.Image other, int otherRatio) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                awh.Color original = image.getPixel(x, y);
                awh.Color dest = other.getPixel(x, y);
                awh.Color curr = blendColors(original, imageRatio, dest, otherRatio);
                image.setPixel(x, y, curr);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            awh.Sys.die("Run with output.mp4 image1 [image2 [... imageN]]");
        }

        awh.Movie movie = awh.Movie.createMp4(args[0]);

        awh.Image previous = loadImage(args[1]);
        stillFrames(movie, previous);

        for (int i = 2; i < args.length; i++) {
            awh.Image current = loadImage(args[i]);

            for (int j = 0; j < BLEND_FRAMES; j++) {
                awh.Image frame = previous.copy();
                blendWith(frame, BLEND_FRAMES - j, current, j);
                addFrame(movie, frame);
            }

            stillFrames(movie, current);
            previous = current;
        }

        movie.finish();

        System.out.println(" done.");
    }
}
