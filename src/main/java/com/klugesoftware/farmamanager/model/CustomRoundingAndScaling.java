package com.klugesoftware.farmamanager.model;

import java.math.RoundingMode;

public class CustomRoundingAndScaling {

	private static final int SCALE_VALUE = 2;
	
	private static final RoundingMode ROUNDING_VALUE = RoundingMode.HALF_DOWN;
	
	private CustomRoundingAndScaling(){		
	}
	
	public static int getScaleValue(){
		return SCALE_VALUE;
	}
	
	public static RoundingMode getRoundingMode(){
		return ROUNDING_VALUE;
	}
}
