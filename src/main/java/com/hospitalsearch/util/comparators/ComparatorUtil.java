package com.hospitalsearch.util.comparators;

import java.util.Comparator;

import com.hospitalsearch.entity.Hospital;

public final class ComparatorUtil {
	public static Comparator<Hospital> HOSPITAL_COMPARATOR = (hospital1,hospital2) -> hospital1.getName().compareTo(hospital2.getName()) ;	
	
}
