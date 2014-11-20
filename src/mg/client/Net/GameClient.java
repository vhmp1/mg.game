/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.Net;

import chatclient.business.messages.*;
import chatclient.business.messages.CommandMessage.Service;
import chatclient.uteis.Uteis;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.Entities.Player;

/**
 *
 * @author Vitor
 */
public class GameClient implements Runnable {

    private final DatagramSocket socket;
    private final CommandHandler handler;
    private final ArrayList<Player> players = new ArrayList<Player>();

    public GameClient() {
        try {
            socket = new DatagramSocket(8882);
            handler = new CommandHandler(this);
            new Thread(handler).start();

        } catch (SocketException ex) {
            throw new RuntimeException("Server not found");
        }
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public Player getPlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) {
                return p;
            }
        }

        throw new RuntimeException("Player não encontrado");
    }

    public Player getFirstPlayer() {
        return players.get(0);
    }

    public void removePlayer(int id) {
        for (Player p : players) {
            if (p.getId() == id) {
                players.remove(p);
            }
        }
    }

    public void sendLogin(String nickname) {
        byte[] bMessage = Uteis.converteStringEmVetorBytes(nickname);
        Message a = new LoginMessage(bMessage.length, bMessage);
        
        try {
            byte[] bSend = Uteis.obtemMensagemComoVetorBytes(a);
            socket.send(new DatagramPacket(bSend, bSend.length, InetAddress.getLocalHost(), 8885));
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startGame(int id, int enemyId, int mapId) {
        byte[] bId = Uteis.converteIntEmVetorBytes(id);
        byte[] bId2 = Uteis.converteIntEmVetorBytes(enemyId);
        byte[] bMap = Uteis.converteIntEmVetorBytes(mapId);
 
        byte[] bMessage = new byte[bId.length + bId2.length + bMap.length];
        System.arraycopy(bId, 0, bMessage, 0, bId.length);
        System.arraycopy(bId2, 0, bMessage, bId.length, bId2.length);
        System.arraycopy(bMap, 0, bMessage, bId.length + bId2.length, bMap.length);

        Message a = new StartGameMessage(bMessage.length, bMessage);

        try {
            byte[] bSend = Uteis.obtemMensagemComoVetorBytes(a);
            socket.send(new DatagramPacket(bSend, bSend.length, InetAddress.getLocalHost(), 8885));
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendCommand(String message, int id) {
        byte[] bId = Uteis.converteIntEmVetorBytes(id);
        byte[] bSide = Uteis.converteStringEmVetorBytes(message);

        byte[] bMessage = new byte[bId.length + bSide.length];
        System.arraycopy(bId, 0, bMessage, 0, bId.length);
        System.arraycopy(bSide, 0, bMessage, bId.length, bSide.length);

        Message a = new MoveMessage(bMessage.length, bMessage);

        try {
            byte[] bSend = Uteis.obtemMensagemComoVetorBytes(a);
            socket.send(new DatagramPacket(bSend, bSend.length, InetAddress.getLocalHost(), 8885));
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMovement(int side, int id) {
        
        byte[] bId = Uteis.converteIntEmVetorBytes(id);
        byte[] bSide = Uteis.converteIntEmVetorBytes(side);

        byte[] bMessage = new byte[bId.length + bSide.length];
        System.arraycopy(bId, 0, bMessage, 0, bId.length);
        System.arraycopy(bSide, 0, bMessage, bId.length, bSide.length);

        Message a = new MoveMessage(bMessage.length, bMessage);
        try {
            byte[] bSend = Uteis.obtemMensagemComoVetorBytes(a);
            socket.send(new DatagramPacket(bSend, bSend.length, InetAddress.getLocalHost(), 8885));
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendStatus(int status, int id, boolean state) {
        byte[] bId = Uteis.converteIntEmVetorBytes(id);
        byte[] bSide = Uteis.converteIntEmVetorBytes(status);
        
        int iState; 
        iState = state? 1 : 0;
        byte[] bState = Uteis.converteIntEmVetorBytes(iState);

        byte[] bMessage = new byte[bId.length + bSide.length + bState.length];
        System.arraycopy(bId, 0, bMessage, 0, bId.length);
        System.arraycopy(bSide, 0, bMessage, bId.length, bSide.length);
        System.arraycopy(bState, 0, bMessage, bId.length + bSide.length, bState.length);

        Message a = new StatusMessage(bMessage.length, bMessage);

        try {
            byte[] bSend = Uteis.obtemMensagemComoVetorBytes(a);
            socket.send(new DatagramPacket(bSend, bSend.length, InetAddress.getLocalHost(), 8885));
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendAbillity(int number, int id) {
        //byte[] bMessage = Uteis.converteStringEmVetorBytes(Integer.toString(id) + Integer.toString(number));
        byte[] bId = Uteis.converteIntEmVetorBytes(id);
        byte[] bSide = Uteis.converteIntEmVetorBytes(number);

        byte[] bMessage = new byte[bId.length + bSide.length];
        System.arraycopy(bId, 0, bMessage, 0, bId.length);
        System.arraycopy(bSide, 0, bMessage, bId.length, bSide.length);

        Message a = new StatusMessage(bMessage.length, bMessage);

        try {
            byte[] bSend = Uteis.obtemMensagemComoVetorBytes(a);
            socket.send(new DatagramPacket(bSend, bSend.length, InetAddress.getLocalHost(), 8885));
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(packet);

                byte service = buffer[0];
                int dataSize = (((int) buffer[1] << 8) & 0xFF00)
                        | (((int) buffer[2]) & 0x00FF);

                byte[] msg = new byte[dataSize];

                for (int i = 0; i < dataSize; i++) {
                    msg[i] = buffer[i + 3];
                }

                int recivedChecksum = (((int) buffer[dataSize + 3] << 8) & 0xFF00)
                        | (((int) buffer[dataSize + 4]) & 0x00FF);

                CommandMessage m;

                switch (Service.byteToService(service)) {
                    case MOVE:
                        m = new MoveMessage(dataSize, msg);
                        break;
                    case ABILLITY:
                        m = new AbillityMessage(dataSize, msg);
                        break;
                    case END_BATTLE:
                        m = new EndGameMessage(dataSize, msg);
                        break;
                    case LOGIN:
                        m = new LoginMessage(dataSize, msg);
                        break;
                    case START_BATTLE:
                        m = new StartGameMessage(dataSize, msg);
                        break;
                    case STATUS:
                        m = new StatusMessage(dataSize, msg);
                        break;
                    default:
                        return;
                }

                if (recivedChecksum != Uteis.calculaCheckSum(m)) {
                    System.out.println("Checksum errado");
                    return;
                }

                handler.addCommand(m);

            } catch (IOException ex) {
                socket.close();
            }
        }
    }
}
