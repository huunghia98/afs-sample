package com.afs.example;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.*;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PackMessage {
    public String pack() {
        try {
            String message = buildISOMessage();
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String buildISOMessage() throws Exception {
        try {
            ISOPackager packager = new ISO87APackager();
            // Setting packager
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);

            // Setting MTI
            isoMsg.set(0, "0100");

            // Setting processing code
            isoMsg.set(3, "020000");

            // Setting transaction amount
            isoMsg.set(4, "5000");

            // Setting transmission date and time
            isoMsg.set(7, new SimpleDateFormat("MMddHHmmss").format(new Date()));

            // Setting system trace audit number
            isoMsg.set(11, "123456");

            // Setting data element #48
            isoMsg.set(48, "Example Value");

            // pack the ISO 8583 Message
            byte[] bIsoMsg = isoMsg.pack();

            // output ISO 8583 Message String
            String isoMessage = "";
            for (int i = 0; i < bIsoMsg.length; i++) {
                isoMessage += (char) bIsoMsg[i];
            }
            System.out.println("Packed ISO8385 Message = '"+isoMessage+"'");
            return isoMessage;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    private void printISOMessage(ISOMsg isoMsg) {
        try {
            System.out.printf("MTI = %s%n", isoMsg.getMTI());
            for (int i = 1; i <= isoMsg.getMaxField(); i++) {
                if (isoMsg.hasField(i)) {
                    System.out.printf("Field (%s) = %s%n", i, isoMsg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
}
