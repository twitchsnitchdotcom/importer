package com.twitchsnitch.importer.opentender.enums;

/**
 FORM TYPE	type of form
 PRIOR_INFORMATION_NOTICE	Non-binding information on future tender
 CONTRACT_NOTICE	Start of tendering procedure
 CONTRACT_AWARD	Award of contract to selected bidder (publication might work as contract cancellation)
 CONTRACT_CANCELLATION	Either tendering procedure, or already awarded contract has been cancelled
 CONTRACT_IMPLEMENTATION	Information onimplementation of running contract - typically framework agreement, dynamic purchasing system
 CONTRACT_UPDATE	Change of previous publication (notice, award), such as corrigendum, shift of deadlines.
 CONTRACT_AMENDMENT	Change of awarded contract (additional works, specification change..)
 OTHER	any other publication
 */
public enum FormType {
    PRIOR_INFORMATION_NOTICE, CONTRACT_NOTICE, CONTRACT_AWARD, CONTRACT_CANCELLATION, CONTRACT_IMPLEMENTATION, CONTRACT_UPDATE, CONTRACT_AMENDMENT, OTHER
}