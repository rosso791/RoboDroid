package dais.unive.it.robot.Automation;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.Future;

import it.unive.dais.legodroid.lib.EV3;
import it.unive.dais.legodroid.lib.comm.Bytecode;
import it.unive.dais.legodroid.lib.comm.Const;

public class EV3DataHandler {

    EV3.Api api;

    public EV3DataHandler(@NonNull EV3.Api api) {
        this.api = api;
    }

    //Send string to specified EV3 mailbox block
    public void sendDataToMailBox(String mailBoxName, String message) throws IOException {
        //System.out.println(mailBoxName + " " + message);
        byte[] cmd = new byte[((mailBoxName.length() + 9) + message.length())];
        //cmd[0] = (byte) ((cmd.length - 2) & 255);
        //cmd[1] = (byte) ((cmd.length - 2) >> 8);
        cmd[0] = (byte) 0;
        cmd[1] = (byte) 0;
        cmd[2] = (byte) -127;
        cmd[3] = (byte) Const.INPUT_READEXT;
        cmd[4] = (byte) (mailBoxName.length() + 1);
        int pos = 5;
        int i = 0;
        while (i < mailBoxName.length() && i < 255) {
            cmd[pos] = (byte) mailBoxName.charAt(i);
            pos++;
            i++;
        }
        cmd[pos] = (byte) 0;
        pos++;
        cmd[pos] = (byte) ((message.length() + 1) & 255);
        pos++;
        cmd[pos] = (byte) ((message.length() + 1) >> 8);
        pos++;

        for (i = 0; i < message.length(); i++) {
            cmd[pos] = (byte) message.charAt(i);
            pos++;
        }
        cmd[pos] = (byte) 0;
        Bytecode bc = new Bytecode();
        bc.addToByteCode(cmd);
        api.sendMailbox(0, bc);
    }

    //Get EV3 global variable from specified memory index
    public Future<Integer> getGlobalVariableIntValue(int globalVariableIndex) throws IOException {

        byte[] cmd = new byte[6];
        //cmd[0] = (byte) ((cmd.length - 2) & 255);
        //cmd[1] = (byte) ((cmd.length - 2) >> 8);
        //cmd[0] = (byte) 0;
        //cmd[1] = (byte) 0;
        //cmd[2] = (byte) 0x00;
        //cmd[3] = (byte) 0x05;
        //cmd[4] = (byte) 0x00;
        cmd[0] = (byte) 0x7F;
        cmd[1] = (byte) 0x01;
        cmd[2] = (byte) 0x00;
        cmd[3] = (byte) globalVariableIndex;
        cmd[4] = (byte) 0x05;
        cmd[5] = (byte) 0x60;

        Bytecode bc = new Bytecode();
        bc.addToByteCode(cmd);

        Future<float[]> r = api.getGlobalVariableValue(5, bc);
        Future<Integer> fi;
        fi = api.execAsync(() -> (int) r.get()[0]);
        /*
        try {
            System.out.println(fi.get().byteValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */

        return fi;
    }

}
