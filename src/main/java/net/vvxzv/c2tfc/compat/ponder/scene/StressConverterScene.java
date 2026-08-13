package net.vvxzv.c2tfc.compat.ponder.scene;

import net.createmod.ponder.api.scene.PositionUtil;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.VectorUtil;
import net.minecraft.core.Direction;

public class StressConverterScene {
    public static void introduction(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("stress_converter", "");
        scene.configureBasePlate(0, 0, 5);

        PositionUtil grid = util.grid();
        VectorUtil vector = util.vector();

        scene.world().showSection(util.select().everywhere(), Direction.UP);
        scene.overlay().showText(60).text("").pointAt(vector.of(2.5, 3.5, 2.5));
        scene.idle(70);
        scene.overlay().showText(60).text("").pointAt(vector.of(3.5, 3.5, 2.5));
        scene.idle(70);
        scene.overlay().showText(60).text("").pointAt(vector.of(0.5, 1.5, 2.5));
        scene.idle(70);
        scene.markAsFinished();
    }
}
