package fr.carbonit.bender;

import io.vavr.collection.Vector;

public class BenderApp {
    public static void main(String[] args) {
        /*
            @ Start
            $ Booth
            # Obstacle
            X BreakableObstacle
            N, S, E, W PathModifier
            I CircuitInverter
            B Beer
            T Teleporter
         */

        System.out.println(Position.of(3, 2).move(Direction.North));
        final CityMap cityMap = CityMap.fromLines(Vector.of(
                "##########",
                "#@   B   #",
                "#   XX T #",
                "#   N    #",
                "# T     $#",
                "##########"
        ));

        System.out.println(cityMap);

        System.out.println(Tile.fromCode(' '));
    }
}
