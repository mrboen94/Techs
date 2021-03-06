package inf112.skeleton.app;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import inf112.skeleton.app.logic.BoardLogic;

public class AITests {

	private static Application application;
    private TiledMap map;
    private BoardLogic board;

    @Before
    public void setUp() {
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;

        map = new TmxMapLoader().load("assets/TestMaps/mapLasers.tmx");
        board = new BoardLogic(map);
    }
    
    @Test
    public void testIfPlayerIsAi() {
    	board.addPlayerToBoard(new Vector2(2, 2), new Vector2(2, 18), "Player", true);
    	assertTrue(board.getPlayerByName("Player").isAI());
    	board.addPlayerToBoard(new Vector2(2, 3), new Vector2(2, 18), "Player1", false);
    	assertFalse(board.getPlayerByName("Player1").isAI());
    }
    
    @Test
    public void testIfPLayerIsNotAI() { 
    	board.addPlayerToBoard(new Vector2(2, 2), new Vector2(2, 18), "Player", true);
    	assertTrue(board.getPlayerByName("Player").isAI());
    }

}
