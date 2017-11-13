package com.harium.propan.linear;

import org.junit.Assert;
import org.junit.Test;

public class OrientedBoundingBoxTest {

    private static final float EPSILON = 0.1f;

    @Test
    public void testInit() {
        OrientedBoundingBox box = new OrientedBoundingBox(1);
        Assert.assertEquals(-0.5f, box.min.x, EPSILON);
        Assert.assertEquals(-0.5f, box.min.y, EPSILON);
        Assert.assertEquals(-0.5f, box.min.z, EPSILON);
        Assert.assertEquals(+0.5f, box.max.x, EPSILON);
        Assert.assertEquals(+0.5f, box.max.y, EPSILON);
        Assert.assertEquals(+0.5f, box.max.z, EPSILON);
    }

}
