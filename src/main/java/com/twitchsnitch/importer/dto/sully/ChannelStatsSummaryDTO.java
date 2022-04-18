package com.twitchsnitch.importer.dto.sully;

public class ChannelStatsSummaryDTO {
    
    private Long totalHoursStreamed;
    private Long totalAverageChannels;
    private Long totalPeakChannels;
    private Long totalStreams;
    private Long totalActiveChannels;
    private Long totalActiveAffiliates;
    private Long totalActivePartners;

    public Long getTotalHoursStreamed() {
        return totalHoursStreamed;
    }

    public void setTotalHoursStreamed(Long totalHoursStreamed) {
        this.totalHoursStreamed = totalHoursStreamed;
    }

    public Long getTotalAverageChannels() {
        return totalAverageChannels;
    }

    public void setTotalAverageChannels(Long totalAverageChannels) {
        this.totalAverageChannels = totalAverageChannels;
    }

    public Long getTotalPeakChannels() {
        return totalPeakChannels;
    }

    public void setTotalPeakChannels(Long totalPeakChannels) {
        this.totalPeakChannels = totalPeakChannels;
    }

    public Long getTotalStreams() {
        return totalStreams;
    }

    public void setTotalStreams(Long totalStreams) {
        this.totalStreams = totalStreams;
    }

    public Long getTotalActiveChannels() {
        return totalActiveChannels;
    }

    public void setTotalActiveChannels(Long totalActiveChannels) {
        this.totalActiveChannels = totalActiveChannels;
    }

    public Long getTotalActiveAffiliates() {
        return totalActiveAffiliates;
    }

    public void setTotalActiveAffiliates(Long totalActiveAffiliates) {
        this.totalActiveAffiliates = totalActiveAffiliates;
    }

    public Long getTotalActivePartners() {
        return totalActivePartners;
    }

    public void setTotalActivePartners(Long totalActivePartners) {
        this.totalActivePartners = totalActivePartners;
    }
}
