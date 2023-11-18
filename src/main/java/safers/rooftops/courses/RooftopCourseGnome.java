package safers.rooftops.courses;

import safers.rooftops.MarkOfGrace;

public class RooftopCourseGnome extends Course {
    public RooftopCourseGnome() {
        super("Gnome Stronghold",
            // Regions.
            new int[]{9781},

            // Obstacles.
            new Obstacle[]{
                new Obstacle(23145, 0, new int[][]{{2474, 3435}}),
                new Obstacle(23134, 0, new int[][]{{2473, 3425}}),
                new Obstacle(23559, 1, new int[][]{{2473, 3422}}),
                new Obstacle(23557, 2, new int[][]{{2478, 3420}}),
                new Obstacle(23560, 2, new int[][]{{2486, 3419}}),
                new Obstacle(23135, 0, new int[][]{{2485, 3426}}),
                new Obstacle(23138, 0, new int[][]{{2484, 3431}})
            },

            new MarkOfGrace[]{
                //
                new MarkOfGrace(3098, 3281, 11405),
                new MarkOfGrace(3089, 3274, 11406),
                new MarkOfGrace(3094, 3266, 11430),
                //
                //
                new MarkOfGrace(3100, 3257, 11632), new MarkOfGrace(3097, 3259, 11632)
            }
        );
    }
}
