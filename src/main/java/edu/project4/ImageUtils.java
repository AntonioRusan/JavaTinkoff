package edu.project4;

import java.nio.file.Path;

// сохранение изображения на файловую систему
enum ImageFormat {JPEG, BMP, PNG}

public final class ImageUtils {
    private ImageUtils() {}

    void save(FractalImage image, Path filename, ImageFormat format) {}
}
