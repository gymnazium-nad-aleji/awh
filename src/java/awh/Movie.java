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

import java.io.File;
import java.io.IOException;
import org.jcodec.api.awt.AWTSequenceEncoder;

/** Create movie from individual frames. */
public class Movie {
    /** Actual encoder used for creating the movie. */
    private final AWTSequenceEncoder encoder;

    /** Construct with existing sequence encoder.
     *
     * @param enc Existing encoder.
     */
    public Movie(final AWTSequenceEncoder enc) {
        Problem.whenNull(enc, "encoder for new movie");

        encoder = enc;
    }

    /** Create a new movie with MP4 container, H264 video codec but no sound.
     *
     * @param path Path to the file with the video.
     * @return New movie.
     */
    public static Movie createMp4(final String path) {
        Problem.whenNull(path, "movie path");

        try {
            return new Movie(AWTSequenceEncoder.create25Fps(new File(path)));
        } catch (IOException e) {
            throw new Problem("Failed to create new movie into '%s' (%s).",
                    path, e.getMessage());
        }
    }

    /** Add a new frame to the movie.
     *
     * @param frame Image of the next frame.
     * @return Reference to itself to allow chaining.
     */
    public Movie addFrame(final Image frame) {
        Problem.whenNull(frame, "movie frame");

        try {
            encoder.encodeImage(frame.getAsAwtImageUnsafe());
        } catch (IOException e) {
            throw new Problem("Failed to add new frame: %s.", e.getMessage());
        }
        return this;
    }

    /** Finalize the movie. */
    public void finish() {
        try {
            encoder.finish();
        } catch (IOException e) {
            throw new Problem("Failed to finalize the movie: %s.", e.getMessage());
        }
    }
}
