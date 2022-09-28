package com.cache.cache.process;

import com.cache.cache.external.Firm;
import com.cache.cache.external.FirmRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReadData implements Runnable {


    FirmRepository firmRepository;

    int totalReadCount;
    int totalSamples;

    public ReadData(int totalReadCount,int totaSamples,FirmRepository firmRepository){
        this.totalReadCount=totalReadCount;
        this.totalSamples=totaSamples;
        this.firmRepository=firmRepository;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for(int counter=0;counter<totalReadCount;counter++){
            long start=System.currentTimeMillis();
            Firm readFirm = firmRepository.getFirm(counter%totalSamples);
            long end=System.currentTimeMillis();
            writeLog(Thread.currentThread().getName(), startTime,end-start,readFirm.getName());
        }
    }

    private void writeLog(String threadName, long startTime,long latency,String data){
        double timeStamp= Math.floor((System.currentTimeMillis()-startTime)/1000);
        System.out.println(threadName +"|"+timeStamp+ "|"+ latency+"|"+data);
    }
}
