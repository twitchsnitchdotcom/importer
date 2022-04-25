package com.twitchsnitch.importer.opentender.enums;

/**
 TENDER SIZE
 ABOVE_THE_THRESHOLD	subject to eu directive
 BELOW_THE_THRESHOLD	below eu directive, but regulated by national laws
 SMALL_SCALE	below national regulation limit
 */
public enum TenderSize {
    ABOVE_THE_THRESHOLD, BELOW_THE_THRESHOLD, SMALL_SCALE
}