package com.eamon.javadesignpatterns.factory.abstracts.factory;

import com.eamon.javadesignpatterns.factory.abstracts.DisplayController;
import com.eamon.javadesignpatterns.factory.abstracts.UploadController;

/**
 * 抽象工厂是主入口，在Spring中应用的最广泛的一种设计模式，易于扩展
 *
 * @author eamon.zhang
 * @date 2019-09-27 下午3:04
 */
public interface VideoPlayerFactory {
    DisplayController createDisplayController();

    UploadController createUploadController();
}
