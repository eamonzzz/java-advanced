package com.eamon.javadesignpatterns.factory.abstracts.factory;

import com.eamon.javadesignpatterns.factory.abstracts.DisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.ios.IosDisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.ios.IosUploaderController;
import com.eamon.javadesignpatterns.factory.abstracts.UploadController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:17
 */
public class IosFactory implements VideoPlayerFactory {
    @Override
    public DisplayController createDisplayController() {
        return new IosDisplayController();
    }

    @Override
    public UploadController createUploadController() {
        return new IosUploaderController();
    }
}
