package com.eamon.concurrent.threaddump;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@RestController
public class ThreadController {

    @GetMapping("/loop")
    public String dumpWhile(){
        new Thread(new WhileThread()).start();
        return "ok";
    }

    @GetMapping("/dead")
    public String dumpDeadLock(){
        Thread a = new ThreadRunA();
        Thread b = new ThreadRunB();
        a.start();
        b.start();
        return "ok";
    }
}
class WhileThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Thread");
        }
    }
}
