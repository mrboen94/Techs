package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class TestBoard implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    
    private ArrayList<TestPlayer> playersList;
    private float robotSpriteScale = 28;
    private Sprite robotSprite;
    
    @Override
    public void show() {
        map = new TmxMapLoader().load("assets/RoboRallyMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, .3f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        
        playersList = new ArrayList<TestPlayer>();
        robotSprite = new Sprite (new Texture("assets/GreenRobot.png"));
        
        
        addPlayerToBoard(new Vector2(0,0), "playerOne");
        addPlayerToBoard(new Vector2(1,0), "playerTwo");
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();
        
        movePlayerOneAndTwo();
        
        renderer.getBatch().begin();
        
        for(TestPlayer player : playersList) {
            player.draw(renderer.getBatch());
        }
        
        renderer.getBatch().end();
    }
    
    public void addPlayerToBoard(Vector2 startPosition, String playerName) {
        TestPlayer newPlayer = new TestPlayer(robotSprite, playerName, startPosition);
        newPlayer.setSize(robotSpriteScale, robotSpriteScale);
        playersList.add(newPlayer);
    }
    
    public boolean movePlayer(Direction directionToMove, String playerName) {
        TestPlayer playerToMove = getPlayerByName(playerName);
        int xPos = playerToMove.getXPosition();
        int yPos = playerToMove.getYPosition();
        
        switch(directionToMove) {
        case EAST:
            if(cellContainsKey(xPos, yPos, "wallEast") || cellContainsKey(xPos + 1, yPos, "wallWest")) {
                return false;
            }
            if(!moveOtherPlayers(xPos + 1, yPos, playerName, directionToMove))
                return false;
            playerToMove.moveEast();
            return true;
            
        case NORTH:
            if(cellContainsKey(xPos, yPos, "wallNorth") || cellContainsKey(xPos, yPos + 1, "wallSouth")) {
                return false;
            }
            if(!moveOtherPlayers(xPos, yPos + 1, playerName, directionToMove))
                return false;
            playerToMove.moveNorth();
            return true;
            
        case SOUTH:
            if(cellContainsKey(xPos, yPos, "wallSouth") || cellContainsKey(xPos, yPos - 1, "wallNorth")) {
                return false;
            }
            if(!moveOtherPlayers(xPos, yPos - 1, playerName, directionToMove)) {
                return false;
            }
            playerToMove.moveSouth();
            return true;
            
        case WEST:
           if(cellContainsKey(xPos, yPos, "wallWest") || cellContainsKey(xPos - 1, yPos, "wallEast")) {
                return false;
            }
           if(!moveOtherPlayers(xPos - 1, yPos, playerName, directionToMove)) {
                return false;
           }
            
            playerToMove.moveWest();
            return true;
            
        default:
            break;
        
        }        
        return false;
    }
    
    /*
     * Moves player standing at (xPos, yPos) to the given direction
     */
    private boolean moveOtherPlayers(int xPos, int yPos, String playerToMove, Direction directionToMove) {
        for(TestPlayer player : playersList) {
            if(player.getXPosition() == xPos && player.getYPosition() == yPos) {
                return movePlayer(directionToMove, player.getName());
            }
        }
        return true;
    }

    private boolean cellContainsKey(int xPos, int yPos, String key) {
        TiledMapTileLayer wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        if(wallLayer.getCell(xPos, yPos) == null)
            return false;
        return wallLayer.getCell(xPos, yPos).getTile().getProperties().containsKey(key);
    }
    
    /*
     * Temporary, to move two players on the board
     */
    private void movePlayerOneAndTwo() {
        // As proof of concept:
        if(Gdx.input.isKeyJustPressed(Keys.UP)) {
            movePlayer(Direction.NORTH, "playerOne");
        } else if(Gdx.input.isKeyJustPressed(Keys.DOWN)) {
            movePlayer(Direction.SOUTH, "playerOne");
        } else if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
            movePlayer(Direction.EAST, "playerOne");
        }else if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
            movePlayer(Direction.WEST, "playerOne");
        }
        
        if(Gdx.input.isKeyJustPressed(Keys.W)) {
            movePlayer(Direction.NORTH, "playerTwo");
        } else if(Gdx.input.isKeyJustPressed(Keys.S)) {
            movePlayer(Direction.SOUTH, "playerTwo");
        } else if(Gdx.input.isKeyJustPressed(Keys.D)) {
            movePlayer(Direction.EAST, "playerTwo");
        }else if(Gdx.input.isKeyJustPressed(Keys.A)) {
            movePlayer(Direction.WEST, "playerTwo");
        }
    }
    
    private TestPlayer getPlayerByName(String playerName) {
        for(TestPlayer player : playersList) {
            if(player.getName().equals(playerName))
                return player;
        }
        throw new NoSuchElementException("There is no player in the grid by the name " + playerName);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        map.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {
    }
}
