package com.harium.propan.math;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;

public class Intersector {

    public final static float NO_COLLISION = -1;

    // Based on code at: https://stackoverflow.com/a/4579069
    public static boolean intersectBoundsSphereFast(BoundingBox box, Vector3 center, float radius) {
        float dist_squared = radius * radius;
        if (center.x < box.min.x) dist_squared -= squared(center.x - box.min.x);
        else if (center.x > box.max.x) dist_squared -= squared(center.x - box.max.x);
        if (center.y < box.min.y) dist_squared -= squared(center.y - box.min.y);
        else if (center.y > box.max.y) dist_squared -= squared(center.y - box.max.y);
        if (center.z < box.min.z) dist_squared -= squared(center.z - box.min.z);
        else if (center.z > box.max.z) dist_squared -= squared(center.z - box.max.z);
        return dist_squared > 0;
    }

    public static float squared(float x) {
        return x * x;
    }

    public static boolean intersectRayBoundsFast(Ray ray, BoundingBox aabb, Matrix4 matrix) {
        return intersectRayBounds(ray, aabb, matrix) != NO_COLLISION;
    }

    // Based on code at: http://www.opengl-tutorial.org/miscellaneous/clicking-on-objects/picking-with-custom-ray-obb-function/
    public static float intersectRayBounds(Ray ray, BoundingBox aabb, Matrix4 matrix) {
        float intersectionDistance = NO_COLLISION;

        float tMin = 0.0f;
        float tMax = 100000.0f;

        Vector3 OBBPositionWorldspace = matrix.getTranslation(new Vector3());

        Vector3 delta = OBBPositionWorldspace.sub(ray.origin);

        // Test intersection with the 2 planes perpendicular to the OBB's X axis
        {
            Vector3 xaxis = new Vector3(matrix.val[Matrix4.M00], matrix.val[Matrix4.M10], matrix.val[Matrix4.M20]);
            float e = xaxis.dot(delta);
            float f = ray.direction.dot(xaxis);

            if (Math.abs(f) > 0.001f) { // Standard case

                float t1 = (e + aabb.min.x) / f; // Intersection with the "left" plane
                float t2 = (e + aabb.max.x) / f; // Intersection with the "right" plane
                // t1 and t2 now contain distances betwen ray origin and ray-plane intersections

                // We want t1 to represent the nearest intersection,
                // so if it's not the case, invert t1 and t2
                if (t1 > t2) {
                    float w = t1;
                    t1 = t2;
                    t2 = w;
                } // swap t1 and t2

                // tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
                if (t2 < tMax)
                    tMax = t2;
                // tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
                if (t1 > tMin)
                    tMin = t1;

                // And here's the trick :
                // If "far" is closer than "near", then there is NO intersection.
                // See the images in the tutorials for the visual explanation.
                if (tMax < tMin)
                    return NO_COLLISION;

            } else { // Rare case : the ray is almost parallel to the planes, so they don't have any "intersection"
                if (-e + aabb.min.x > 0.0f || -e + aabb.max.x < 0.0f)
                    return NO_COLLISION;
            }
        }


        // Test intersection with the 2 planes perpendicular to the OBB's Y axis
        // Exactly the same thing than above.
        {
            Vector3 yaxis = new Vector3(matrix.val[Matrix4.M01], matrix.val[Matrix4.M11], matrix.val[Matrix4.M21]);

            float e = yaxis.dot(delta);
            float f = ray.direction.dot(yaxis);

            if (Math.abs(f) > 0.001f) {

                float t1 = (e + aabb.min.y) / f;
                float t2 = (e + aabb.max.y) / f;

                if (t1 > t2) {
                    float w = t1;
                    t1 = t2;
                    t2 = w;
                }

                if (t2 < tMax)
                    tMax = t2;
                if (t1 > tMin)
                    tMin = t1;
                if (tMin > tMax)
                    return NO_COLLISION;

            } else {
                if (-e + aabb.min.y > 0.0f || -e + aabb.max.y < 0.0f)
                    return NO_COLLISION;
            }
        }


        // Test intersection with the 2 planes perpendicular to the OBB's Z axis
        // Exactly the same thing than above.
        {
            Vector3 zaxis = new Vector3(matrix.val[Matrix4.M02], matrix.val[Matrix4.M12], matrix.val[Matrix4.M22]);

            float e = zaxis.dot(delta);
            float f = ray.direction.dot(zaxis);

            if (Math.abs(f) > 0.001f) {

                float t1 = (e + aabb.min.z) / f;
                float t2 = (e + aabb.max.z) / f;

                if (t1 > t2) {
                    float w = t1;
                    t1 = t2;
                    t2 = w;
                }

                if (t2 < tMax)
                    tMax = t2;
                if (t1 > tMin)
                    tMin = t1;
                if (tMin > tMax)
                    return NO_COLLISION;

            } else {
                if (-e + aabb.min.z > 0.0f || -e + aabb.max.z < 0.0f)
                    return NO_COLLISION;
            }
        }

        intersectionDistance = tMin;
        return intersectionDistance;
    }

}
