package com.mobile.gameworld;


import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mobile.TweenAccessors.Value;
import com.mobile.TweenAccessors.ValueAccessor;
import com.mobile.cubator.CubatorGame;
import com.mobile.gameobjects.Grass;
import com.mobile.gameobjects.Pipe;
import com.mobile.gameobjects.Player;
import com.mobile.gameobjects.ScrollHandler;
import com.mobile.gameobjects.Tile;
import com.mobile.helpers.AssetLoader;
import com.mobile.helpers.InputHandler;
import com.mobile.ui.SimpleButton;

/**
 * Created by justin on 2/3/2015.
 */
public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private int midPointY;
    private int gameHeight;
    // Game Objects
    private Player player;
    private List<Tile> scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;


    // Game Assets
    private TextureRegion bg, grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

    private TweenManager manager;
    private Value alpha = new Value();

    // Buttons
    private List<SimpleButton> menuButtons;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY){
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();
        cam = new OrthographicCamera();
        cam.setToOrtho(true, CubatorGame.worldWidth, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        initAssets();
        initGameObjects();
        setupTweens();
    }

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad)
                .start(manager);
    }

    public void initGameObjects(){
        player = myWorld.getPlayer();
        scroller = myWorld.getScroller().getTiles();
       // frontGrass = scroller.getFrontGrass();
       // backGrass = scroller.getBackGrass();
       // pipe1 = scroller.getPipe1();
       // pipe2 = scroller.getPipe2();
      //  pipe3 = scroller.getPipe3();
    }

    public void initAssets(){
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

    public void render(float runTime){
        cam.translate(player.getVelocity().cpy().scl(runTime).x, 0, 0);
        cam.update();
        batcher.setProjectionMatrix(cam.combined);
        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, CubatorGame.worldWidth, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, CubatorGame.worldWidth, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, CubatorGame.worldWidth, 52);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY + 23, CubatorGame.worldWidth, gameHeight - midPointY + 23);

        drawGrass();

        drawPipes();

        batcher.enableBlending();
        drawSkulls();

        if (myWorld.isRunning()) {
            drawPlayer(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
            drawPlayer(runTime);
            drawScore();
        } else if (myWorld.isMenu()) {
            drawBirdCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            drawPlayer(runTime);
            drawScore();
        }
        // End SpriteBatch
        batcher.end();
        drawTransition(runTime);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, CubatorGame.worldWidth, gameHeight);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

        }
    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    private void drawMenuUI() {
        batcher.draw(AssetLoader.zbLogo, CubatorGame.worldWidth / 2 - 56, midPointY - 50,
                AssetLoader.zbLogo.getRegionWidth() / 1.2f,
                AssetLoader.zbLogo.getRegionHeight() / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawPlayer(float runTime)
    {
        if (player.shouldntFlap()) {
            batcher.draw(birdMid, player.getX(), player.getY(),
                    player.getWidth() / 2.0f, player.getHeight() / 2.0f,
                    player.getWidth(), player.getHeight(), 1, 1, player.getRotation());

        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime), player.getX(),
                    player.getY(), player.getWidth() / 2.0f,
                    player.getHeight() / 2.0f, player.getWidth(), player.getHeight(),
                    1, 1, player.getRotation());
        }
    }
    private void drawBirdCentered(float runTime) {
        batcher.draw(birdAnimation.getKeyFrame(runTime), 59, player.getY() - 15,
                player.getWidth() / 2.0f, player.getHeight() / 2.0f,
                player.getWidth(), player.getHeight(), 1, 1, player.getRotation());
    }

    private void drawGrass() {
        // Draw the grass
     //   batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
      //          frontGrass.getWidth(), frontGrass.getHeight());
       // batcher.draw(grass, backGrass.getX(), backGrass.getY(),
        //        backGrass.getWidth(), backGrass.getHeight());

        for (int i=0; i < scroller.size();++i)
        {
            Tile tmp = scroller.get(i);
            batcher.draw(grass,tmp.getX() - 1,tmp.getY(),tmp.getWidth() + 2,tmp.getHeight());
        }

    }

    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

     //   batcher.draw(skullUp, pipe1.getX() - 1,
      //          pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
      //  batcher.draw(skullDown, pipe1.getX() - 1,
       //         pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

//        batcher.draw(skullUp, pipe2.getX() - 1,
  //              pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
   //     batcher.draw(skullDown, pipe2.getX() - 1,
     //           pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

       // batcher.draw(skullUp, pipe3.getX() - 1,
         //       pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
      //  batcher.draw(skullDown, pipe3.getX() - 1,
        //        pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
     //   batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
      //          pipe1.getHeight());
       // batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
        //        pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

     //   batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
      //          pipe2.getHeight());
       // batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
      //          pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

    //    batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
      //          pipe3.getHeight());
       // batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
     //           pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }
}
