package com.twitchsnitch.importer.dto.twitch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.twitch4j.helix.domain.Stream;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StreamDTO {

    private String id;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("user_login")
    private String userLogin;
    @JsonProperty("user_name")
    private String userName;
    /** ID of the game being played on the stream. */
    @JsonProperty("game_id")
    private String gameId;

    /** Name of the game being played. */
    @JsonProperty("game_name")
    private String gameName;

    /** Stream type: "live" or "" (in case of error). */
    private String type;

    /** Stream title. */
    private String title;

    /** Number of viewers watching the stream at the time of the query. */
    @JsonProperty("viewer_count")
    private Integer viewerCount;

    /** UTC timestamp on when the stream started */
    @JsonProperty("started_at")
    private Instant startedAtInstant;

    /** Ids of active tags on the stream */
    @JsonProperty("tag_ids")
    private List<UUID> tagIds = new ArrayList<>();

    /** Indicates if the broadcaster has specified their channel contains mature content that may be inappropriate for younger audiences. */
    @Accessors(fluent = true)
    @JsonProperty("is_mature")
    private Boolean isMature;

    /** Stream language. */

    private String language;

    /** Thumbnail URL of the stream. All image URLs have variable width and height. You can replace {width} and {height} with any values to get that size image */

    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViewerCount() {
        return viewerCount;
    }

    public void setViewerCount(Integer viewerCount) {
        this.viewerCount = viewerCount;
    }

    public Instant getStartedAtInstant() {
        return startedAtInstant;
    }

    public void setStartedAtInstant(Instant startedAtInstant) {
        this.startedAtInstant = startedAtInstant;
    }

    public List<UUID> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<UUID> tagIds) {
        this.tagIds = tagIds;
    }

    public Boolean getMature() {
        return isMature;
    }

    public void setMature(Boolean mature) {
        isMature = mature;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * Gets the stream uptime based on the start date.
     *
     * @return The stream uptime.
     */


    public Duration getUptime() {
        return Duration.between(startedAtInstant, Instant.now());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
          return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
