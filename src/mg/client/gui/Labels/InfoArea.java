/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import mg.client.GUI.Buttons.UpStateButton;
import mg.client.Entities.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Vitor
 */
public class InfoArea extends AbstractComponent {

    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private static Image BACKGROUND;
    private final Player player;
    private final InfoLabel nickLabel;
    private final InfoLabel lifeLabel;
    private final InfoLabel manaLabel;
    private final InfoLabel presistLabel;
    private final InfoLabel mresistLabel;
    private final InfoLabel strenghtLabel;
    private final InfoLabel intelligenceLabel;
    private final InfoLabel dexterityLabel;
    private final InfoLabel classLabel;
    private final InfoLabel levelLabel;
    private final InfoLabel expLabel;
    private final UpStateButton[] upStateBtns;
    private final Image image;

    static {
        try {
            BACKGROUND = new Image("/resources/lobby/info_area.png");
        } catch (SlickException ex) {

        }
    }

    public InfoArea(GUIContext container, int posx, int posy, Player player) throws SlickException {
        super(container);
        this.width = 319;
        this.height = 195;
        this.posx = posx;
        this.posy = posy;
        this.player = player;
        nickLabel = new InfoLabel(container, 13, 50);
        lifeLabel = new InfoLabel(container, 13, 50);
        manaLabel = new InfoLabel(container, 13, 50);
        presistLabel = new InfoLabel(container, 13, 50);
        mresistLabel = new InfoLabel(container, 13, 50);
        strenghtLabel = new InfoLabel(container, 13, 50);
        intelligenceLabel = new InfoLabel(container, 13, 50);
        dexterityLabel = new InfoLabel(container, 13, 50);
        classLabel = new InfoLabel(container, 13, 50);
        levelLabel = new InfoLabel(container, 13, 50);
        expLabel = new InfoLabel(container, 13, 50);
        image = new Image("/resources/character/" + player.getCaste() + "/image.png");      
        upStateBtns = new UpStateButton[5];
        upStateBtns[0] = new UpStateButton(container, 250, 38, player, "Physical Resist");
        upStateBtns[1] = new UpStateButton(container, 525, 38, player, "Magic Resist");
        upStateBtns[2] = new UpStateButton(container, 250, 56, player, "Strenght");
        upStateBtns[3] = new UpStateButton(container, 525, 56, player, "Intelligence");
        upStateBtns[4] = new UpStateButton(container, 250, 74, player, "Dexterity");

    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        loadCharacterInfo();
        
        image.draw(posx + 123, posy + 10);
        BACKGROUND.draw(posx, posy);
        nickLabel.setLocation(380, 106);
        nickLabel.render(container, g);
        
        lifeLabel.setLocation(331, 18);
        lifeLabel.render(container, g);

        manaLabel.setLocation(507, 18);
        manaLabel.render(container, g);

        levelLabel.setLocation(331, 90);
        levelLabel.render(container, g);

        classLabel.setLocation(507, 72);
        classLabel.render(container, g);

        dexterityLabel.setLocation(331, 72);
        dexterityLabel.render(container, g);

        intelligenceLabel.setLocation(507, 54);
        intelligenceLabel.render(container, g);

        mresistLabel.setLocation(507, 36);
        mresistLabel.render(container, g);

        presistLabel.setLocation(331, 36);
        presistLabel.render(container, g);

        strenghtLabel.setLocation(331, 54);
        strenghtLabel.render(container, g);

        expLabel.setLocation(507, 90);
        expLabel.render(container, g);

        for (int i = 0; i < 5; i++) {
            upStateBtns[i].render(container, g);
        }

    }

    @Override
    public void setLocation(int x, int y) {
        this.posx = x;
        this.posy = y;
    }

    @Override
    public int getX() {
        return this.posx;
    }

    @Override
    public int getY() {
        return this.posy;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void loadCharacterInfo() {
        nickLabel.setText(player.getNickname());
        lifeLabel.setText(Integer.toString(player.getLife()));
        manaLabel.setText(Integer.toString(player.getMana()));
        levelLabel.setText(Integer.toString(player.getLevel()));
        classLabel.setText(player.getCaste());
        dexterityLabel.setText(Integer.toString(player.getDexterity()));
        intelligenceLabel.setText(Integer.toString(player.getIntelligence()));
        mresistLabel.setText(Integer.toString(player.getMagicResist()));
        presistLabel.setText(Integer.toString(player.getPhysicalResist()));
        strenghtLabel.setText(Integer.toString(player.getStrength()));
        expLabel.setText(player.getFormatExp());
    }
}
