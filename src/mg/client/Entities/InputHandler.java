/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.Entities;

import chatclient.business.messages.CommandMessage.Commands;
import java.util.Timer;
import java.util.TimerTask;
import mg.client.Net.GameClient;
import org.newdawn.slick.Input;

/**
 *
 * @author Vitor
 */
public class InputHandler {

    private final GameClient client;
    private final Player player;
    private final Input input;
    private int lastKey;
    private Timer dashTimer;

    public InputHandler(GameClient client, Player player, Input input) {
        this.client = client;
        this.player = player;
        this.input = input;
        
        this.dashTimer = new Timer();
        this.dashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                lastKey = 0;
            }
        }, 1500);
    }

    public void update() {
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            if (lastKey == Input.KEY_LEFT) {
                lastKey = 0;
                client.sendStatus(Commands.DASH.ordinal(), player.getId(), true);
            } else {
                lastKey = Input.KEY_LEFT;
            }

            dashTimer.cancel();
            dashTimer = new Timer();
            dashTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    lastKey = 0;
                }
            }, 300);
        } else if (input.isKeyPressed(Input.KEY_RIGHT)) {
            if (lastKey == Input.KEY_RIGHT) {
                lastKey = 0;
                client.sendStatus(Commands.DASH.ordinal(), player.getId(), true);
            } else {
                lastKey = Input.KEY_RIGHT;
            }

            dashTimer.cancel();
            dashTimer = new Timer();
            dashTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    lastKey = 0;
                }
            }, 300);
        }

        if (input.isKeyDown(Input.KEY_LEFT)) {
            client.sendMovement(0, player.getId());
        } else if (input.isKeyDown(Input.KEY_RIGHT)) {
            client.sendMovement(1, player.getId());
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            client.sendStatus(Commands.JUMP.ordinal(), player.getId(), true);
        } else if (input.isKeyDown(Input.KEY_Z)) {
            client.sendStatus(Commands.ATTACK.ordinal(), player.getId(), true);
        } else if (input.isKeyDown(Input.KEY_A)) {
            client.sendAbillity(Player.ABILLITY_A, player.getId());
        } else if (input.isKeyDown(Input.KEY_S)) {
            client.sendAbillity(Player.ABILLITY_S, player.getId());
        } else if (input.isKeyDown(Input.KEY_D)) {
            client.sendAbillity(Player.ABILLITY_D, player.getId());
        }
    }
}
