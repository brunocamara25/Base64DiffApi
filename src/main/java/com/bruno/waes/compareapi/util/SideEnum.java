package com.bruno.waes.compareapi.util;

/**
 * 
 * Enum with the option sides.
 *
 * @author Bruno Camara
 */

public enum SideEnum {
	LEFT, RIGHT;
	
	@Override
	public String toString() {
		switch (this) {
		case LEFT:
			return "LEFT";
		case RIGHT:
			return "RIGHT";
		}
		throw new Error("An error occurred while trying to get the correct side.");
	}
}
