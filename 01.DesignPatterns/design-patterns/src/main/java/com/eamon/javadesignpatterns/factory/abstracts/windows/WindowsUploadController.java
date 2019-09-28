package com.eamon.javadesignpatterns.factory.abstracts.windows;

import com.eamon.javadesignpatterns.factory.abstracts.UploadController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:09
 */
public class WindowsUploadController implements UploadController {
    @Override
    public void upload() {
        System.out.println("Windows 上传控制器！");
    }
}
