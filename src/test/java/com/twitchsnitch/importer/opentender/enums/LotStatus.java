
package com.twitchsnitch.importer.opentender.enums;
/**
 LOT STATUS	status of lot to date of the publication of given information
 PREPARED	lot is prepared
 ANNOUNCED	lot is publicly announced, bids are accepted or negotiated
 AWARDED	lot is awarded and being fulfilled
 CANCELLED	lot has been cancelled
 FINISHED	lot was fulfilled and paid
 */
public enum LotStatus {
    PREPARED, ANNOUNCED, AWARDED, CANCELLED, FINISHED;
}