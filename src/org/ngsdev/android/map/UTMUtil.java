package org.ngsdev.android.map;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;
import com.google.android.maps.GeoPoint;
import android.graphics.Point;
public class UTMUtil {
	private static final int E6 = 1000000;
	private static final double a = 6378137;
	private static final double f = 1.0 / 298.257222101;
	private static final double e2 = 2 * f - f * f;
	private static final double e4 = e2 * e2;
	private static final double e6 = e4 * e2;
	private static final double e8 = e6 * e2;
	private static final double e10 = e8 * e2;
	private static final double lambda0 = (139 + 44 / 60.0 + 28.8759 / 3600) * PI / 180;
	private static final double a_ = 1 + 3 / 4 * e2 + 45 / 64 * e4 + 175 / 256 * e6 + 11025 / 16384
			* e8 + 43659 / 65536 * e10;
	private static final double b_ = 3 / 4 * e2 + 15 / 16 * e4 + 525 / 512 * e6 + 2205 / 2048 * e8
			+ 72765 / 65536 * e10;
	private static final double c_ = 15 / 64 * e4 + 105 / 256 * e6 + 2205 / 4096 * e8 + 10395
			/ 16384 * e10;
	private static final double d_ = 35 / 512 * e6 + 315 / 2048 * e8 + 31185 / 131072 * e10;
	private static final double e_ = 315 / 16384 * e8 + 3465 / 65536 * e10;
	private static final double f_ = 693 / 131072 * e10;
	private static final double e_2 = e2 / (1 - e2);
	private static final double m0 = .9996;
	public static Point toUTM(final int longitude, final int latitude) {
		final double lambda = longitude/E6 * PI / 180;
		final double phi = latitude/E6 * PI / 180;
		final double largeB = a
				* (1 - e2)
				* (a_ * phi - b_ / 2 * sin(2 * phi) + c_ / 4 * sin(4 * phi) - d_ / 6 * sin(6 * phi)
						+ e_ / 8 * sin(8 * phi) - f_ / 10 * sin(10 * phi));
		final double sinPhi = sin(phi);
		final double largeN = a / sqrt(1 - e2 * sinPhi * sinPhi);
		final double t = tan(phi);
		final double t2 = t * t;
		final double t4 = t * t * t * t;
		final double t6 = t * t * t * t * t * t;
		final double cosPhi = cos(phi);
		final double eta2 = e_2 * cosPhi * cosPhi;
		final double eta4 = eta2 * eta2;
		final double l = lambda - lambda0;
		final double l2 = l * l;
		final double l3 = l2 * l;
		final double l4 = l3 * l;
		final double l5 = l4 * l;
		final double l6 = l5 * l;
		final double l7 = l6 * l;
		final double l8 = l7 * l;
		final double x = largeN * l * cosPhi + largeN * l3 / 6 * pow(cos(phi * (1 - t2 + eta2)), 3)
				+ largeN * l5 / 120
				* pow(cos(phi * (5 - 18 * t2 + t4 + 14 * eta2 - 58 * t2 * eta2)), 5) + largeN * l7
				/ 5040 * pow(cos(phi * (61 - 479 * t2 + 179 * t4 * t6)), 7);
		final double y = largeB + largeN * l2 / 2 * sinPhi * cosPhi + largeN * l4 / 24 * sinPhi
				* pow(cos(phi * (5 - t2 + 9 * eta2 + 4 * eta4)), 3) + largeN * l6 / 720 * sinPhi
				* pow(cos(phi * (61 - 58 * t2 + t4 + 270 * eta2 - 330 * t2 * eta2)), 5) + largeN
				* l8 / 40320 * sinPhi * pow(cos(phi * (1385 - 3111 * t2 + 543 * t4 - t6)), 7);
		final double largeX = (m0 * x + 500000) * E6;
		final double largeY = m0 * y * E6;
		final int intX = (int) largeX;
		final int intY = (int) largeY;
		return new Point(intX, intY);
	}
	public static Point toUTM(final Point point) {
		return toUTM(point.x, point.y);
	}
}
