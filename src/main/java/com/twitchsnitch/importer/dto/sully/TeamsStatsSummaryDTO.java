package com.twitchsnitch.importer.dto.sully;

public class TeamsStatsSummaryDTO {

    private Long averageTeams;
    private Long activeTeams;
    private Long peakTeamViewers;
    private Long peakTeams;
    private Long peakNumberOfLiveMembers;
    private Long mostActiveTeamInStreamedHours;
    private Long mostWatchedTeamInMinutes;

    public Long getAverageTeams() {
        return averageTeams;
    }

    public void setAverageTeams(Long averageTeams) {
        this.averageTeams = averageTeams;
    }

    public Long getActiveTeams() {
        return activeTeams;
    }

    public void setActiveTeams(Long activeTeams) {
        this.activeTeams = activeTeams;
    }

    public Long getPeakTeamViewers() {
        return peakTeamViewers;
    }

    public void setPeakTeamViewers(Long peakTeamViewers) {
        this.peakTeamViewers = peakTeamViewers;
    }

    public Long getPeakTeams() {
        return peakTeams;
    }

    public void setPeakTeams(Long peakTeams) {
        this.peakTeams = peakTeams;
    }

    public Long getPeakNumberOfLiveMembers() {
        return peakNumberOfLiveMembers;
    }

    public void setPeakNumberOfLiveMembers(Long peakNumberOfLiveMembers) {
        this.peakNumberOfLiveMembers = peakNumberOfLiveMembers;
    }

    public Long getMostActiveTeamInStreamedHours() {
        return mostActiveTeamInStreamedHours;
    }

    public void setMostActiveTeamInStreamedHours(Long mostActiveTeamInStreamedHours) {
        this.mostActiveTeamInStreamedHours = mostActiveTeamInStreamedHours;
    }

    public Long getMostWatchedTeamInMinutes() {
        return mostWatchedTeamInMinutes;
    }

    public void setMostWatchedTeamInMinutes(Long mostWatchedTeamInMinutes) {
        this.mostWatchedTeamInMinutes = mostWatchedTeamInMinutes;
    }
}
