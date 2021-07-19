package com.eamon.javadesignpatterns.factory.abstracts.factory;

import com.eamon.javadesignpatterns.factory.abstracts.DisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.UploadController;
import com.eamon.javadesignpatterns.factory.abstracts.windows.WindowsDisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.windows.WindowsUploadController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:15
 */
public class WindowsFactory implements VideoPlayerFactory {
    @Override
    public DisplayController createDisplayController() {
        return new WindowsDisplayController();
    }

    @Override
    public UploadController createUploadController() {
        return new WindowsUploadController();
    }
}
