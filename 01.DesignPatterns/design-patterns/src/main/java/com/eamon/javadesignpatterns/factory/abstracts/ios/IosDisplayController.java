package com.eamon.javadesignpatterns.factory.abstracts.ios;

import com.eamon.javadesignpatterns.factory.abstracts.DisplayController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:09
 */
public class IosDisplayController implements DisplayController {

    @Override
    public void display() {
        System.out.println("IOS 上的播放器！");
    }
}
