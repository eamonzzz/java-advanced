package com.eamon.javadesignpatterns.factory.abstracts.factory;

import com.eamon.javadesignpatterns.factory.abstracts.DisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.UploadController;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:20
 */
public class VideoPlayerFactoryTest {

    @Test
    public void VideoPlayer() {
        VideoPlayerFactory factory = new WindowsFactory();

        // IOS
//        factory = new IosFactory();
//        // Android
//        factory = new AndroidFactory();

        UploadController uploadController = factory.createUploadController();
        DisplayController displayController = factory.createDisplayController();

        uploadController.upload();
        displayController.display();

    }
}