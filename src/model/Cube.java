package model;

import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Cube extends Solid {
    public Cube() {
        transform = true;
        vertexBuffer = new ArrayList<>();

        vertexBuffer.add(new Point3D(1, 1, 1));
        vertexBuffer.add(new Point3D(-1, 1, 1));
        vertexBuffer.add(new Point3D(-1, -1, 1));
        vertexBuffer.add(new Point3D(1, -1, 1));

        vertexBuffer.add(new Point3D(1, 1, -1));
        vertexBuffer.add(new Point3D(-1, 1, -1));
        vertexBuffer.add(new Point3D(-1, -1, -1));
        vertexBuffer.add(new Point3D(1, -1, -1));

        Integer[] indices = {0,1,1,2,2,3,3,0,4,5,5,6,6,7,7,4,0,4,1,5,2,6,3,7};
        indexBuffer = new ArrayList<>(Arrays.asList(indices));

        parts = new ArrayList<>();
        parts.add(new Part( 0, 24,0x00ff00));

    }
}
