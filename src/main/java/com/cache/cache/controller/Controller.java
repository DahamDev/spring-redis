package com.cache.cache.controller;

import com.cache.cache.external.Firm;
import com.cache.cache.external.FirmRepository;
import com.cache.cache.process.ReadData;
import org.apache.catalina.core.ApplicationContext;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.Optional;

@RestController
@RequestMapping("firm")
public class Controller {

    @Autowired
    FirmRepository firmRepository;
    int initialDataCountRedis=1000;
    int readingCount=1000;

    @GetMapping("/test")
    public String testPoint(){
        return "Ok working";
    }

    @GetMapping("/getfirm/{id}")
    public ResponseEntity getFirm(@PathVariable int id){
        System.out.println("get request received");
        Firm newFirm = firmRepository.getFirm(id);
        return ResponseEntity.ok().body(newFirm);
    }

    @PostMapping("add")
    public ResponseEntity addFirm(@RequestBody Firm newFirm){
        firmRepository.saveFirm(newFirm);
        return ResponseEntity.ok().body("OK");
    }
    //insert the initial data to redis
    @PostMapping("insert")
    public String insertToRedis(){

        long startTime=System.currentTimeMillis();
        generateInitialData(startTime);
        return "Ok";
    }

    @PostMapping("startreading")
    public String startReading(){
        long startTime=System.currentTimeMillis();

        ReadData readData = new ReadData(readingCount,initialDataCountRedis,firmRepository);

        Thread thread1 = new Thread(readData);
        thread1.setName("Thread-1");
        thread1.start();

        Thread thread2 = new Thread(readData);
        thread2.setName("Thread-2");
        thread2.start();

        Thread thread3 = new Thread(readData);
        thread3.setName("Thread-3");
        thread3.start();

        Thread thread4 = new Thread(readData);
        thread4.setName("Thread-4");
        thread4.start();


        return "Ok";
    }


    private void generateInitialData(long startTime) {
        for(int count =0;count<initialDataCountRedis;count++){
            Firm newFirm = new Firm();
            newFirm.setId(count);
            newFirm.setName("Some Random Firm");

            long start=System.currentTimeMillis();
            firmRepository.saveFirm(newFirm);
            long end=System.currentTimeMillis();

            writeLog("Normal Thread", startTime,end-start,"Firm");
        }
    }


    private void writeLog(String threadName, long startTime,long latency,String data){
        double timeStamp= Math.floor((System.currentTimeMillis()-startTime)/1000);
        System.out.println(threadName +"|"+timeStamp+ "|"+ latency+"|"+data);
    }
}
