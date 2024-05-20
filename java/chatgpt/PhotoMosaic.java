package chatgpt;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import origami.Origami;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import static org.opencv.imgproc.Imgproc.*;

public class PhotoMosaic {
    private static int factor = 50;

    // Parameters for the mosaic
//    static final int factor =

    static {
        Origami.init();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Start the timer
        long startTime = System.currentTimeMillis();

        String folderPath = args[0];
        String targetImagePath = args[1];

        // Load target image
        Mat targetImage = Imgcodecs.imread(targetImagePath);

        // Load folder images
        List<Mat> folderImages = loadImagesFromFolder(folderPath);

        // Cache average colors for folder images
        Map<Mat, Scalar> imageAvgColorCache = new HashMap<>();
        for (Mat image : folderImages) {
            Scalar avgColor = calculateAverageColor(image);
            imageAvgColorCache.put(image, avgColor);
        }

        // Parameters for the mosaic
        // Calculate rows and columns based on the input image
        int rows = targetImage.height() / factor;
        int cols = (int) Math.round((double) targetImage.width() / targetImage.height() * rows);

        int blockWidth = targetImage.width() / cols;
        int blockHeight = targetImage.height() / rows;

        Mat mosaic = new Mat(targetImage.size(), targetImage.type());

        // Keep track of used images to enforce diversity
        Map<Mat, Integer> imageUsageCount = new HashMap<>();
        for (Mat image : folderImages) {
            imageUsageCount.put(image, 0);
        }
        int maxUsageCount = (rows * cols) / folderImages.size();

        // Use ForkJoinPool for multi-threading
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Future<?>> futures = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    final int finalX = x;
                    final int finalY = y;
                    futures.add(forkJoinPool.submit(() -> {

                        // Extract the block from the target image
                        Rect blockRect = new Rect(finalX * blockWidth, finalY * blockHeight, blockWidth, blockHeight);
                        Mat block = new Mat(targetImage, blockRect);

                        // Calculate the average color of the block
                        Scalar blockAvgColor = calculateAverageColor(block);

                        // Find the best matching image using the cache and considering usage count
                        Mat bestMatch = findBestMatch(blockAvgColor, folderImages, imageAvgColorCache, imageUsageCount, maxUsageCount);

                        // Resize the best matching image to fit the block
                        Mat resizedMatch = new Mat();
                        resize(bestMatch, resizedMatch, new Size(blockWidth, blockHeight));

                        // Place the resized match in the mosaic
                        resizedMatch.copyTo(new Mat(mosaic, blockRect));
                    }));
                }
        }

        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            future.get();
        }

        forkJoinPool.shutdown();

        // Save the mosaic
        Imgcodecs.imwrite("mosaic.jpg", mosaic);

        // End the timer
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Display the computation time
        System.out.println("Time taken to compute the photomosaic: " + duration + " milliseconds");

    }

    public static List<Mat> loadImagesFromFolder(String folderPath) {
        List<Mat> images = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Mat image = Imgcodecs.imread(file.getAbsolutePath());
                    if (!image.empty()) {
                        // Normalize image colors
                        image = normalizeImageColors(image);
                        images.add(image);
                    }
                }
            }
        }
        return images;
    }

    public static Mat findBestMatch(Scalar blockAvgColor, List<Mat> images, Map<Mat, Scalar> imageAvgColorCache, Map<Mat, Integer> imageUsageCount, int maxUsageCount) {
        double minDifference = Double.MAX_VALUE;
        Mat bestMatch = null;

        for (Mat image : images) {
            // Skip images that have been used too many times
            if (imageUsageCount.get(image) >= maxUsageCount) {
                continue;
            }

            // Retrieve the cached average color
            Scalar imageAvgColor = imageAvgColorCache.get(image);

            // Calculate the color difference
            double difference = calculateColorDifference(blockAvgColor, imageAvgColor);

            if (difference < minDifference) {
                minDifference = difference;
                bestMatch = image;
            }
        }

        // If no match was found, select a random image to avoid a crash
        if (bestMatch == null) {
            bestMatch = images.get((int) (Math.random() * images.size()));
            adjustImageColor(bestMatch, blockAvgColor);
            // Update the average color cache
            imageAvgColorCache.put(bestMatch, calculateAverageColor(bestMatch));
        } else {
            // Update the usage count for the selected image
            imageUsageCount.put(bestMatch, imageUsageCount.get(bestMatch) + 1);
        }

        return bestMatch;
    }


    public static void adjustImageColor(Mat image, Scalar targetColor) {
        Scalar currentColor = calculateAverageColor(image);
        Scalar diff = new Scalar(
                targetColor.val[0] - currentColor.val[0],
                targetColor.val[1] - currentColor.val[1],
                targetColor.val[2] - currentColor.val[2]
        );

        List<Mat> hsvChannels = new ArrayList<>(3);
        Mat hsvImage = new Mat();
        cvtColor(image, hsvImage, COLOR_BGR2HSV);
        Core.split(hsvImage, hsvChannels);

        for (int i = 0; i < hsvChannels.size(); i++) {
            Core.add(hsvChannels.get(i), new Scalar(diff.val[i]), hsvChannels.get(i));
        }

        Core.merge(hsvChannels, hsvImage);
        cvtColor(hsvImage, image, COLOR_HSV2BGR);
    }

    public static Scalar calculateAverageColor(Mat image) {
        Mat hsvImage = new Mat();
        cvtColor(image, hsvImage, COLOR_BGR2HSV);
        Scalar avgColor = Core.mean(hsvImage);
        return avgColor;
    }

    public static double calculateColorDifference(Scalar color1, Scalar color2) {
        double diff = 0;
        for (int i = 0; i < 3; i++) {
            diff += Math.pow(color1.val[i] - color2.val[i], 2);
        }
        return Math.sqrt(diff);
    }

    public static Mat normalizeImageColors(Mat image) {
        Mat labImage = new Mat();
        cvtColor(image, labImage, COLOR_BGR2Lab);
        List<Mat> labChannels = new ArrayList<>(3);
        Core.split(labImage, labChannels);

        // Apply CLAHE (Contrast Limited Adaptive Histogram Equalization) to the L-channel
        CLAHE clahe = createCLAHE();
        clahe.setClipLimit(2.0);
        Mat lChannel = labChannels.get(0);
        clahe.apply(lChannel, lChannel);
        labChannels.set(0, lChannel);

        Core.merge(labChannels, labImage);
        Mat result = new Mat();
        cvtColor(labImage, result, COLOR_Lab2BGR);
        return result;
    }
}
