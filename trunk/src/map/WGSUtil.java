package map;
import android.graphics.Point;
public class WGSUtil {
	public static Point tokyoToWGS(final double longitude,final double latitude) {
		final double pi = Math.PI;
		final double rd = pi / 180;
		double b = latitude;
		double l = longitude;
		final double h = 697.681000;
		final double a = 6377397.155;
		final double f = 1 / 299.152813;
		final int a_ = 6378137;
		final double f_ = 1 / 298.257223;
		final int dx = -148;
		final int dy = +507;
		final int dz = +681;
		b *= rd;
		l *= rd;

		final double e2 = 2 * f - f * f;
		final double bda = 1 - f;
		final double da = a_ - a;
		final double df = f_ - f;
		final double sb = Math.sin(b);
		final double cb = Math.cos(b);
		final double sl = Math.sin(l);
		final double cl = Math.cos(l);

		double rn = 1 / Math.sqrt(1 - e2 * sb * sb);
		final double rm = a * (1 - e2) * rn * rn * rn;
		rn *= a;

		double db = -dx * sb * cl - dy * sb * sl + dz * cb + da * rn * e2 * sb
				* cb / a + df * (rm / bda + rn * bda) * sb * cb;
		db /= rm + h;
		double dl = -dx * sl + dy * cl;
		dl /= (rn + h) * cb;
		//final double dh = dx * cb * cl + dy * cb * sl + dz * sb - da * a / rn
		//		+ df * bda * rn * sb * sb;

		final double retY = (b + db) / rd;
		final double retX = (l + dl) / rd;
		//final double height = h + dh;
		return new Point(new Integer(Double.toString(retX)), new Integer(Double.toString(retY)));
	}
	
	final Point tokyoToWGS(final Point point) {
		return tokyoToWGS(point.x, point.x);
	}
}
