package org.ngsdev.yadoroid;

import android.test.ActivityInstrumentationTestCase;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class org.ngsdev.yadoroid.YadoroidTest \
 * org.ngsdev.yadoroid.tests/android.test.InstrumentationTestRunner
 */
public class YadoroidTest extends ActivityInstrumentationTestCase<Yadoroid> {

    public YadoroidTest() {
        super("org.ngsdev.yadoroid", Yadoroid.class);
    }

}
