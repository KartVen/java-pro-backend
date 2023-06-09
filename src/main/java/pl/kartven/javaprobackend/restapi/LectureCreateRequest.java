package pl.kartven.javaprobackend.restapi;

import java.util.List;

public class LectureCreateRequest {
    String topic;
    String description;
    List<SlideCreate> slides;

    public static class SlideCreate {
        byte[] imgByte;
    }
}
