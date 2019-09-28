package com.eamon.javadesignpatterns.factory.abstracts.ios;

import com.eamon.javadesignpatterns.factory.abstracts.UploadController;

/**
 * @author eamon.zhang
 * @date 2019-09-27 下午3:10
 */
public class IosUploaderController implements UploadController {
    @Override
    public void upload() {
        System.out.println("IOS 上传控制器！");
    }
}
