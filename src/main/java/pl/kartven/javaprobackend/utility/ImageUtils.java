package pl.kartven.javaprobackend.utility;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.exception.structure.CompressProcessingException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

public class ImageUtils {
    private ImageUtils() {
    }

    public static byte[] compressImage(@NonNull MultipartFile image, float imageQuality)
    {
        return Try.of(() -> {
            InputStream inputStream = image.getInputStream();
            String fileExtension = Optional.ofNullable(getFileExtension(image.getOriginalFilename())).orElse("");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            BufferedImage bufferedImage = ImageIO.read(inputStream);

            Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(fileExtension);

            if (!imageWriters.hasNext())
                throw new IllegalStateException("Writers Not Found!!");

            ImageWriter imageWriter = imageWriters.next();
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            imageWriter.setOutput(imageOutputStream);

            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(imageQuality);

            imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);

            byte[] imageBytes = outputStream.toByteArray();

            inputStream.close();
            outputStream.close();
            imageOutputStream.close();
            imageWriter.dispose();

            return imageBytes;
        }).getOrElseThrow(e -> new CompressProcessingException(e.getMessage()));
    }

    public static String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf('.') != -1) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return null;
    }
}
