package com.harium.propan.linear;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class OrientedBoundingBox extends BoundingBox {

    public Matrix4 transform = new Matrix4();

    public OrientedBoundingBox(Vector3 min, Vector3 max) {
        super(min, max);
    }

    public OrientedBoundingBox(float size) {
        this(new Vector3(-size / 2, -size / 2, -size / 2), new Vector3(size / 2, size / 2, size / 2));
    }

    public OrientedBoundingBox rotateX(float angle) {
        transform.rotate(Vector3.X.mul(transform), angle);
        return this;
    }

    public OrientedBoundingBox rotateY(float angle) {
        transform.rotate(Vector3.Y.mul(transform), angle);
        return this;
    }

    public OrientedBoundingBox rotateZ(float angle) {
        transform.rotate(Vector3.Z.mul(transform), angle);
        return this;
    }

    public OrientedBoundingBox translate(float x, float y, float z) {
        transform.translate(x, y, z);
        return this;
    }

}