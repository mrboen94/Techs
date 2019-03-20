package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.logic.BoardLogic;
import inf112.skeleton.app.logic.Direction;
import inf112.skeleton.app.objects.PlayerToken;
import inf112.skeleton.app.RoboRally;

public class Board implements Screen {

    private static final float ZOOM_SPEED = 0.03f;
    private static final float MOVE_SPEED = 16;
    private static final int ROBOT_WIDTH = 96;
    private static final int ROBOT_HEIGHT = 96;

    // Variable used to animate sprites
    private float statetime;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private BoardLogic boardLogic;
    private RoboRally game;

    public Board(RoboRally game) {
        this.game = game;
        boardLogic = new BoardLogic(map);
        statetime = 0f;
        addPlayerToBoard(new Vector2(0,0), "playerOne");
    }
        
    @Override
    public void show() {
        map = new TmxMapLoader().load("assets/RoboRallyMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, RoboRally.WIDTH, RoboRally.HEIGHT);
    }

    @Override
    public void render(float delta) {
        renderer.setView(camera);
        renderer.render();

        statetime += delta;

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        for (PlayerToken robot : boardLogic.getPlayersList()) {
            game.batch.draw(robot.getRobotAnimation().getKeyFrame(statetime,true), robot.getX(), robot.getY(), ROBOT_WIDTH, ROBOT_HEIGHT);
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

    private void addPlayerToBoard(Vector2 startPosition, String playerName) {
        boardLogic.addPlayerToBoard(startPosition, playerName);
    }
    // Checks if tile at (xPos, yPos) is in the specified layer
    public boolean cellContainsLayer(int xPos, int yPos, String layer) {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(layer);
        return tileLayer.getCell(xPos, yPos) != null;
    }
    public boolean cellContainsLayerWithKey(int xPos, int yPos, String layer, String key) {
        return boardLogic.cellContainsLayerWithKey(xPos, yPos, layer, key);
    }
    public void moveConveyorBelts() {
        boardLogic.moveConveyorBelts();
    }
    public boolean movePlayer(String name, Direction directionToMove) {
        return boardLogic.movePlayer(name, directionToMove);
    }
    public void rotatePlayer(String name, int numberOfTimes) {
        boardLogic.rotatePlayer(name, numberOfTimes);
    }
}