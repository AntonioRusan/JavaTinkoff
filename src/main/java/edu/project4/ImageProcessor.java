package edu.project4;

// пост-обработка in-place, например, гамма-коррекция
@FunctionalInterface
public
interface ImageProcessor {
    void process(FractalImage image);
}
