package com.codenjoy.dojo.lunolet.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 - 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.services.EventListener;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LevelManagerTest {

    @Test
    public void initializeAndCountLevelsTest() {
        LevelManager manager = new LevelManager();

        assertEquals(0, manager.getLevelNumber());
        Level level0 = manager.getLevel();
        Assert.assertNotNull(level0);
        Assert.assertNotNull(level0.Relief);
        Assert.assertNotNull(level0.VesselStatus);

        int levelNumExpected = 1;
        while (true) {
            manager.levelUp();

            int levelNum = manager.getLevelNumber();
            if (levelNum == 0)
                break; // start of another round
            assertEquals(levelNumExpected, levelNum);

            Level level = manager.getLevel();
            Assert.assertNotNull(level);
            Assert.assertNotNull(level.Relief);
            Assert.assertNotNull(level.VesselStatus);

            levelNumExpected++;
        }
        System.out.println("Number of levels: " + levelNumExpected);
    }

    @Test
    public void initPlayerForEveryLevelTest() {
        LevelManager manager = new LevelManager();
        EventListener listener = mock(EventListener.class);
        Player player = new Player(listener);
        while (true) {
            int levelNum = manager.getLevelNumber();
            player.newHero(manager);
            Hero hero = player.getHero();
            Assert.assertNotNull(hero);
            Assert.assertNotNull(hero.getLevelRelief());
            Assert.assertNotNull(hero.getVesselStatus());
            Assert.assertEquals(levelNum, hero.getLevelNumber());

            manager.levelUp();
            if (manager.getLevelNumber() == 0)
                break; // start of another round
        }
    }
}
