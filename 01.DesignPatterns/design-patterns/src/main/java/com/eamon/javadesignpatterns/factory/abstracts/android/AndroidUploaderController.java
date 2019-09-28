package com.eamon.javadesignpatterns.factory.abstracts.android;

import com.eamon.javadesignpatterns.factory.abstracts.UploadController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:10
 */
public class AndroidUploaderController implements UploadController {
    @Override
    public void upload() {
        System.out.println("Android 上传控制器！");
    }
}
