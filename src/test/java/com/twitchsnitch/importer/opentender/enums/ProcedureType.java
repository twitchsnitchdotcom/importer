package com.twitchsnitch.importer.opentender.enums;

/**
 PROCEDURE TYPE
 OPEN
 RESTRICTED
 NEGOTIATED_WITH_PUBLICATION
 NEGOTIATED_WITHOUT_PUBLICATION
 COMPETITIVE_DIALOG
 DESIGN_CONTEST
 MINITENDER	framework agreement based tender
 DPS_PURCHASE	dynamic purchasing system based tender
 OUTRIGHT_AWARD	direct award to single body - general type for procedure outside eu legislation
 APPROACHING_BIDDERS	sealed bid competition between several bodies - general type for procedure outside eu legislation
 PUBLIC_CONTEST	sealed bid competition with open call - general type for procedure outside eu legislation
 NEGOTIATED	negotiated competition with open call - general type for procedure outside eu legislation
 INOVATION_PARTNERSHIP
 CONCESSION
 OTHER
 */
public enum ProcedureType {
    OPEN, RESTRICTED, NEGOTIATED_WITH_PUBLICATION, NEGOTIATED_WITHOUT_PUBLICATION, COMPETITIVE_DIALOG, DESIGN_CONTEST, MINITENDER,
    DPS_PURCHASE, OUTRIGHT_AWARD, APPROACHING_BIDDERS, PUBLIC_CONTEST, NEGOTIATED, INOVATION_PARTNERSHIP, CONCESSION;
}