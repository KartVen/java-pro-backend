package pl.kartven.javaprobackend.http;

import java.util.List;

public class LectureCreateRequest {
    String topic;
    String description;
    List<SlideCreate> slides;

    public static class SlideCreate {
        byte[] imgByte;
    }
}
