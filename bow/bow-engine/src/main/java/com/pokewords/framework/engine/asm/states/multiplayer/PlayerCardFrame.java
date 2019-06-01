package com.pokewords.framework.engine.asm.states.multiplayer;

import com.pokewords.framework.sprites.Sprite;
import com.pokewords.framework.sprites.components.frames.AbstractFrame;
import com.pokewords.framework.sprites.components.frames.RoundedRectangleFrame;
import com.pokewords.framework.sprites.components.frames.StringFrame;
import com.pokewords.framework.views.Canvas;
import org.jetbrains.annotations.Nullable;

import java.awt.*;


public class PlayerCardFrame extends AbstractFrame {
    public final static Color READY_COLOR = new Color(17, 89, 38);
    public final static Color UNREADY_COLOR = Color.red;
    private Player player;
    private RoundedRectangleFrame cardFrame;
    private StringFrame ipStringFrame;
    private StringFrame nameStringFrame;


    public PlayerCardFrame(int layerIndex, Player player) {
        super(layerIndex);
        this.player = player;
        initFrames();
    }

    private void initFrames() {
        cardFrame = new RoundedRectangleFrame(1, Color.white, 30, 30)
                .flags(RoundedRectangleFrame.CANVAS_FLAG_FILLED);
        ipStringFrame = new StringFrame(2, player.ip)
                .color(Color.black)
                .font(new Font("微軟正黑體", Font.PLAIN, 20))
                .flags(StringFrame.CANVAS_FLAG_RENDER_BY_CENTER);
        nameStringFrame = new StringFrame(2, player.name)
                .color(Color.red)
                .font(new Font("微軟正黑體", Font.BOLD, 22))
                .flags(StringFrame.CANVAS_FLAG_RENDER_BY_CENTER);
    }

    @Override
    public void boundToSprite(@Nullable Sprite sprite) {
        super.boundToSprite(sprite);
        cardFrame.boundToSprite(sprite);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void renderItself(Canvas canvas) {
        assert getSprite() != null;

        Point cardPosition = getSprite().getPosition();
        int x = (int) cardPosition.getX();
        int y = (int) cardPosition.getY();
        Dimension cardSize = getSprite().getAreaSize();
        ipStringFrame.setPosition(new Point(x + (int) (cardSize.getWidth() / 2), y + 38));
        nameStringFrame.setPosition(new Point(x + (int) (cardSize.getWidth() / 2), y + 78));


        nameStringFrame.color(player.ready ? READY_COLOR : UNREADY_COLOR);

        canvas.render(cardFrame);
        canvas.render(ipStringFrame);
        canvas.render(nameStringFrame);
    }

    public void setMouseEntered(boolean mouseEntered) {
        if (mouseEntered)
            cardFrame.setColor(Color.LIGHT_GRAY);
        else
            cardFrame.setColor(Color.white);
    }

}
