package com.codenjoy.dojo.lunolet.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
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

import com.codenjoy.dojo.lunolet.TestGameSettings;
import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.utils.scorestest.AbstractScoresTest;
import org.junit.Test;

import static com.codenjoy.dojo.lunolet.services.GameSettings.Keys.CRASHED_PENALTY;
import static com.codenjoy.dojo.lunolet.services.GameSettings.Keys.LANDED_SCORE;

public class ScoresTest extends AbstractScoresTest {

    @Override
    public GameSettings settings() {
        return new TestGameSettings();
    }

    @Override
    protected Class<? extends ScoresMap> scores() {
        return Scores.class;
    }

    @Override
    protected Class<? extends Enum> eventTypes() {
        return Event.class;
    }

    @Test
    public void shouldCollectScores() {
        assertEvents("100:\n" +
                "LANDED > +10 = 110\n" +
                "LANDED > +10 = 120\n" +
                "CRASHED > -1 = 119");
    }

    @Test
    public void shouldCollectScores_whenLanded() {
        // given
        settings.integer(LANDED_SCORE, 10);

        // when then
        assertEvents("100:\n" +
                "LANDED > +10 = 110\n" +
                "LANDED > +10 = 120");
    }

    @Test
    public void shouldCollectScores_whenCrashed() {
        // given
        settings.integer(CRASHED_PENALTY, -1);

        // when then
        assertEvents("100:\n" +
                "CRASHED > -1 = 99\n" +
                "CRASHED > -1 = 98");
    }

    @Test
    public void shouldNotBeLessThanZero() {
        // given
        settings.integer(CRASHED_PENALTY, -1);

        // when then
        assertEvents("2:\n" +
                "CRASHED > -1 = 1\n" +
                "CRASHED > -1 = 0\n" +
                "CRASHED > +0 = 0");
    }

    @Test
    public void shouldClean() {
        assertEvents("100:\n" +
                "LANDED > +10 = 110\n" +
                "LANDED > +10 = 120\n" +
                "(CLEAN) > -120 = 0\n" +
                "LANDED > +10 = 10\n" +
                "LANDED > +10 = 20");
    }
}