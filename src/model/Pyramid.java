package model;

import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;

public class Pyramid extends Solid {
    public Pyramid() {
        transform = true;
        vertexBuffer = new ArrayList<>();

        vertexBuffer.add(new Point3D(1, 1, -1));
        vertexBuffer.add(new Point3D(-1, 1, -1));
        vertexBuffer.add(new Point3D(-1, -1, -1));
        vertexBuffer.add(new Point3D(1, -1, -1));

        vertexBuffer.add(new Point3D(0, 0, 2));

        Integer[] indices = new Integer[]{0, 1, 1, 2, 2, 3, 3, 0,0, 4, 1, 4, 2, 4, 3, 4 };
        indexBuffer = new ArrayList<>(Arrays.asList(indices));

        parts = new ArrayList<>();
        parts.add(new Part( 0, 16,0x00ff00));

    }
}
