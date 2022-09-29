package com.cache.cache.controller;

import com.cache.cache.external.Firm;
import com.cache.cache.external.FirmRepository;
import com.cache.cache.process.ReadData;
import com.cache.cache.process.WriteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("firm")
public class Controller {

    @Autowired
    FirmRepository firmRepository;
    @Value("${redisapp.initialcount}")
    int initialDataCountRedis;

    @Value("${redisapp.readingcount}")
    int readingCount;
    @Value("${redisapp.writing.datacount}")
    int writingDatacount;

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
        generateInitialData();
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

    @PostMapping("startreadwrite")
    public String startReadingWriting(){

        long startTime=System.currentTimeMillis();

        ReadData readData = new ReadData(readingCount,initialDataCountRedis,firmRepository);
        WriteData writeData = new WriteData(writingDatacount,readingCount,firmRepository);

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

        Thread thread5 = new Thread(writeData);
        thread5.setName("Thread-5");
        thread5.start();

        return "Ok";
    }


    private void generateInitialData() {

        WriteData writeData = new WriteData(writingDatacount,initialDataCountRedis,firmRepository);
        System.out.println("Generated initial data");
        writeData.run();
    }



}
