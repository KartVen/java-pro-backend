package pl.kartven.javaprobackend.range.slide;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.apache.commons.imaging.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.exception.ServerProcessingException;
import pl.kartven.javaprobackend.restapi.mapper.SlideMapper;

import java.io.*;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class SlideService {

    private final SlideRepository slideRepository;
    private final SlideMapper slideMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Slide uploadImage(MultipartFile file) {
        return Option.of(file)
                .map(f -> {
                    try {
                        byte[] bytes = f.getBytes();
                        ImageFormat fFormat = Imaging.guessFormat(bytes);
                        return compressImage(f.getBytes(), (ImageFormats) fFormat);
                    } catch (IOException | ImageReadException | ImageWriteException e) {
                        return null;
                    }
                })
                .map(Slide::new)
                .map(slideRepository::saveAndFlush)
                .getOrElseThrow(ServerProcessingException::new);
    }

    private byte[] compressImage(byte[] bytes, ImageFormats format) throws IOException, ImageReadException, ImageWriteException {
        ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();
        Imaging.writeImage(Imaging.getBufferedImage(bytes), compressedOutput, format);
        return compressedOutput.toByteArray();
    }
}
