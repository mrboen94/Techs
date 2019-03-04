package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;

/**
 * Save at second checkpoint
 *
 * Power Down button
 *
 * Life tokens == number of life
 *
 * Rench == repair one damage and upgrade/option card
 *
 * Conveyor belt move before each turn
 *
 * Locked register
 * card is locked and has to be used for each turn
 * can only be removed if register gets fixed
 *
 * Program Sheet
 * 1) Each player gets (9 - damage) cards start of each turn
 *
 * 2) 5 of these cards are selected for programming
 *       *selected by numbers on keyboard 1-9
 *       *Hurry up timer?
 */
public class ProgramSheet implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    @Override
    public void show() {
        map = new TmxMapLoader().load("assets/ProgramSheet/ProgramSheet.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, .3f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();

    }

    @Override
    public void resize(int i, int i1) {

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
}
