package model;

import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;

public class Axis extends Solid {

    public Axis() {
        transform = false;
        vertexBuffer = new ArrayList<>();

        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(0, 0, 1));
        vertexBuffer.add(new Point3D(0, 1, 0));
        vertexBuffer.add(new Point3D(-1, 0, 0));

        Integer[] ints = new Integer[]{0, 1, 0, 2, 0, 3};
        indexBuffer = new ArrayList<>(Arrays.asList(ints));

        parts = new ArrayList<>();
        parts.add(new Part(0, 2,0xff0000));
        parts.add(new Part(2, 4,0x00ff00));
        parts.add(new Part(4, 6,0x0000ff));



    }

}
