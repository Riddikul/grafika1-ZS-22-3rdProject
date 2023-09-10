package model;

import transforms.Point3D;

import java.util.List;

public abstract class Solid {
    protected boolean transform;
    protected List<Part> parts;

    protected List<Point3D> vertexBuffer;
    protected List<Integer> indexBuffer;


    public boolean getTransform() {
        return transform;
    }


    public List<Part> getParts() {
        return parts;
    }
    public List<Point3D> getVertices() {
        return vertexBuffer;
    }

    public List<Integer> getIndices() {
        return indexBuffer;
    }





}