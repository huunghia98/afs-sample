package com.afs.example.message;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class UnPackMessage {
    public void unpack(String message) throws ISOException {
        GenericPackager packager = new GenericPackager(
                "packager/iso87ascii.xml");

        // Setting packager
        ISOMsg isoMsg = new ISOMsg();
        isoMsg.setPackager(packager);

        // this is ISO8583 Message that we will Unpack
        String isoMessage = "010032200000000100000200000000000050000804170346123456013Example Value";

        // first, we must convert ISO8583 Message String to byte[]
        byte[] bIsoMessage = new byte[isoMessage.length()];
        for (int i = 0; i < bIsoMessage.length; i++) {
            bIsoMessage[i] = (byte) (int) isoMessage.charAt(i);
        }

        // second, we unpack the message
        isoMsg.unpack(bIsoMessage);

        // last, print the unpacked ISO8583
        System.out.println("MTI='"+isoMsg.getMTI()+"'");
        for(int i=1; i<=isoMsg.getMaxField(); i++){
            if(isoMsg.hasField(i))
                System.out.println(i+"='"+isoMsg.getString(i)+"'");
        }
    }
}
