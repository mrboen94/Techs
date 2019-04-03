package inf112.skeleton.app.tiles;

import inf112.skeleton.app.logic.Direction;

/**
 * the same laser spans over multiple tiles
 */
public interface IBoardLaser extends ITile {

    /**
     *
     * @return how far the laser shoots
     */
    int getDistance();

    void setDistance(int distance);

    /**
     *
     * @return direction of the laser
     */
    Direction getDirection();

    void setDirection(Direction direction);
}