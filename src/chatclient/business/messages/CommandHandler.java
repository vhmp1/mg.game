/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient.business.messages;

import chatserver.exceptions.MalformedMessageException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public class CommandHandler implements Runnable{
     private final ArrayList<CommandMessage> commands = new ArrayList<CommandMessage>();
     private final GameClient client;
     
     public CommandHandler(GameClient client){
         this.client = client;
     }
     
     public synchronized void addCommand(CommandMessage m){
         commands.add(m);
         this.notify();
     }

    @Override
    public void run() {
        while(true){
            if(commands.isEmpty()){
                synchronized(this){
                    try {
                        this.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CommandHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            try {
                commands.remove(0).handleMessage(client);
            } catch (IOException ex) {
                Logger.getLogger(CommandHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedMessageException ex) {
                Logger.getLogger(CommandHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    
     
}
