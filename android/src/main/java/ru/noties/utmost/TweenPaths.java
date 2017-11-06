package ru.noties.utmost;

import ru.noties.utmost.paths.CatmullRom;
import ru.noties.utmost.paths.Linear;

/**
 * Collection of built-in paths.
 *
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public interface TweenPaths {
	public static final Linear linear = new Linear();
	public static final CatmullRom catmullRom = new CatmullRom();
}
