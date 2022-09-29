package com.cache.cache.process;

import com.cache.cache.external.Firm;
import com.cache.cache.external.FirmRepository;
import org.springframework.stereotype.Component;


public class WriteData implements Runnable{
    int writeOperationCount;
    FirmRepository firmRepository;

    int writingDatacount;

    public WriteData(int writingDatacount,int writeOperationCount, FirmRepository firmRepository) {
        this.writeOperationCount = writeOperationCount;
        this.firmRepository = firmRepository;
        this.writingDatacount=writingDatacount;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        for(int count = 0; count< writeOperationCount; count++){
            Firm newFirm = new Firm();
            newFirm.setId(count);
            newFirm.setName("Firm from loops");

            long start=System.currentTimeMillis();
            firmRepository.saveFirm(newFirm);
            long end=System.currentTimeMillis();

            writeLog("Write Thread", startTime,end-start,"Firm");
        }

    }

    private void writeLog(String threadName, long startTime,long latency,String data){
        double timeStamp= Math.floor((System.currentTimeMillis()-startTime)/1000);
        System.out.println(threadName +"|"+timeStamp+ "|"+ latency+"|"+data);
    }
}
