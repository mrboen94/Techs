package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.logic.BoardLogic;
import inf112.skeleton.app.logic.Direction;
import inf112.skeleton.app.logic.Hud;
import inf112.skeleton.app.objects.PlayerToken;
import inf112.skeleton.app.RoboRally;

public class Board implements Screen {

    private static final float ZOOM_SPEED = 0.03f;
    private static final float MOVE_SPEED = 16;
    private static final int ROBOT_WIDTH = 96;
    private static final int ROBOT_HEIGHT = 96;

    // Variable used to animate sprites
    private float statetime;

    private TiledMap map = new TmxMapLoader().load("assets/RoboRallyMap.tmx");
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private BoardLogic boardLogic;
    private RoboRally game;

    public Board(RoboRally game) {
        this.game = game;
        boardLogic = new BoardLogic(this.map);
        statetime = 0f;
    }
        
    @Override
    public void show() {
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, RoboRally.SCREEN_WIDTH, RoboRally.SCREEN_HEIGHT);
    }

    @Override
    public void render(float delta) {
        renderer.setView(camera);
        renderer.render();

        statetime += delta;

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        for (PlayerToken robot : boardLogic.getPlayersList()) {
            game.batch.draw(robot.getRobotAnimation().getKeyFrame(statetime,true), robot.getX(), robot.getY(), 
                    ROBOT_WIDTH/2, ROBOT_HEIGHT/2, ROBOT_WIDTH, ROBOT_HEIGHT, 1, 1, robot.getRotation());
            robot.update(delta);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            movePlayer("player 1", Direction.EAST);
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
        	movePlayer("player 1", Direction.WEST);
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
        	movePlayer("player 1", Direction.NORTH);
        }
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
        	movePlayer("player 1", Direction.SOUTH);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            boardLogic.moveRotateWheel();
        }

        // To check checkpoints
        if(Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            checkAllCheckpoints(); 
        }

        // To check conveyorbelts
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            moveConveyorBelts();
        }

        // To check lasers shot from robots
        if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            boardLogic.shootPlayerLaser();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            boardLogic.repairRobots();
        }

        game.batch.end();

        /*
        The code here handles the zoom- and camera movement functions. The drag-functionality might be removed if conflict arise when using
        the mouse button to click on program cards. The buttons used are WASD for camera-movement and E/Q for ZoomIn/ZoomOut.
         */
        if (Gdx.input.isTouched()) {
            camera.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
            camera.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += ZOOM_SPEED;
            camera.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= ZOOM_SPEED;
            camera.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, MOVE_SPEED, 0);
            camera.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -MOVE_SPEED, 0);
            camera.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-MOVE_SPEED, 0, 0);
            camera.update();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(MOVE_SPEED, 0, 0);
            camera.update();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void processEndOfTurns() {
        boardLogic.checkPitfalls();
        moveConveyorBelts();
        moveRotateWheel();
        boardLogic.shootPlayerLaser();
        boardLogic.activateLasersOnBoard();
        checkAllCheckpoints();
        boardLogic.checkPlayersLife();
        checkForDamageCleanup();
    }

    public void processEndOfRound() {
        boardLogic.repairRobots();
        //TODO: metode som henter inn spillere igjen som fortsatt har liv igjen
    }

    public void addPlayerToBoard(Vector2 startPosition, Vector2 deathPosition, String playerName) {
        boardLogic.addPlayerToBoard(startPosition, deathPosition, playerName);
    }
    // Checks if tile at (xPos, yPos) is in the specified layer
    public boolean cellContainsLayer(int xPos, int yPos, String layer) {
        return boardLogic.cellContainsLayer(xPos,  yPos, layer);
    }
    public boolean cellContainsLayerWithKey(int xPos, int yPos, String layer, String key) {
        return boardLogic.cellContainsLayerWithKey(xPos, yPos, layer, key);
    }
    public void checkAllCheckpoints() {
        boardLogic.checkAllCheckpoints();
    }
    public void checkForDamageCleanup() {
        boardLogic.checkForDamageCleanup();
    }
    public void doPowerdown(String name) {
        boardLogic.powerdown(name);
    }
    public BoardLogic getBoardLogic(){
        return boardLogic;
    }
    public boolean getPowerdownStatus(String name) {
        return boardLogic.getPowerdownStatus(name);
    }
    public void moveConveyorBelts() {
        boardLogic.moveConveyorBelts();
    }
    public void moveRotateWheel() {
        boardLogic.moveRotateWheel();
    }
    public boolean movePlayer(String name, Direction directionToMove) {
        return boardLogic.movePlayer(name, directionToMove);
    }
    public boolean movePlayerForward(String name) {
        return boardLogic.movePlayerForward(name);
    }
    public boolean movePlayerBackwards(String name) {
        return boardLogic.movePlayerBackwards(name);
    }
    public void rotatePlayer(String name, int numberOfTimes) {
        boardLogic.rotatePlayer(name, numberOfTimes);
    }
    public boolean playerIsDestroyed(String name) {
        return boardLogic.playerIsDestroyed(name);
    }

    /**
     * This method is used to get information about player damage over to BoardCards
     * @param name The name of the player who we want information about
     * @return The number of damage tokens received
     */
    public int getDamageTokens(String name) {
        return boardLogic.getDamageTokens(name);
    }

    /**
     * This method is used to get information about player health over to BoardCards
     * @param name The name of the player who we want information about
     * @return The number of health tokens left
     */
    public int getHealth(String name) {
        return boardLogic.getHealth(name);
    }
    
}