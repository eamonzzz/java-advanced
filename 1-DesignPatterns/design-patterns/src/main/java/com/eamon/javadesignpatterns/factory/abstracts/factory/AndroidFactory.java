package com.eamon.javadesignpatterns.factory.abstracts.factory;

import com.eamon.javadesignpatterns.factory.abstracts.android.AndroidDisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.android.AndroidUploaderController;
import com.eamon.javadesignpatterns.factory.abstracts.DisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.UploadController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:18
 */
public class AndroidFactory implements VideoPlayerFactory {
    @Override
    public DisplayController createDisplayController() {
        return new AndroidDisplayController();
    }

    @Override
    public UploadController createUploadController() {
        return new AndroidUploaderController();
    }
}
