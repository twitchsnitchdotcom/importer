package com.twitchsnitch.importer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.twitchsnitch.importer.dto.sully.*;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsoupTests {

    public final ObjectMapper objectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(module);
    }

    @Test
    public void homePageStats() throws IOException {

        FileInputStream fis = new FileInputStream("src/main/resources/homepage.html");
        String htmlString = IOUtils.toString(fis, "UTF-8");

        HomePageDTO homePageDTO = new HomePageDTO();
        Document doc = Jsoup.parse(htmlString);

        //hourswatched
        String hourswatched = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTL > div").get(0).text();
        String hourswatchedPercentage = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTR > div > span").get(0).text();
        String hourswatchedIndicatorClass = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTR > div > span").get(0).className();
        String hourswatchedChanged = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelBL > div").get(0).text();

        homePageDTO.setHoursWatched(hourswatched);
        homePageDTO.setHoursWatchedChangeAmount(hourswatchedChanged);
        homePageDTO.setHoursWatchedPercentageChange(getPercentageChanged(hourswatchedPercentage, hourswatchedIndicatorClass));

        //averageViewers
        String averageViewers = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(2) > div > div > div.InfoStatPanelTL > div").get(0).text();
        String averageViewersPercentage = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(2) > div > div > div.InfoStatPanelTR > div > span").get(0).text();
        String averageViewersIndicatorClass = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(2) > div > div > div.InfoStatPanelTR > div > span").get(0).className();
        String averageViewersChanged = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(2) > div > div > div.InfoStatPanelBL > div").get(0).text();

        homePageDTO.setAverageViewers(averageViewers);
        homePageDTO.setAverageViewersChangeAmount(averageViewersChanged);
        homePageDTO.setAverageViewersPercentageChange(getPercentageChanged(averageViewersPercentage, averageViewersIndicatorClass));

        //peakViewers
        String peakViewers = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(3) > div > div > div.InfoStatPanelTL > div").get(0).text();
        String peakViewersPercentage = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(3) > div > div > div.InfoStatPanelTR > div > span").get(0).text();
        String peakViewersIndicatorClass = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(3) > div > div > div.InfoStatPanelTR > div > span").get(0).className();
        String peakViewersChanged = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text();

        homePageDTO.setPeakViewers(peakViewers);
        homePageDTO.setPeakViewersChangeAmount(peakViewersChanged);
        homePageDTO.setPeakViewersPercentageChange(getPercentageChanged(peakViewersPercentage, peakViewersIndicatorClass));

        //peakStreams
        String peakStreams = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(4) > div > div > div.InfoStatPanelTL > div").get(0).text();
        String peakStreamsPercentage = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).text();
        String peakStreamsIndicatorClass = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).className();
        String peakStreamsChanged = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(4) > div > div > div.InfoStatPanelBL > div").get(0).text();

        homePageDTO.setPeakStreams(peakStreams);
        homePageDTO.setPeakStreamsChangeAmount(peakStreamsChanged);
        homePageDTO.setPeakStreamsPercentageChange(getPercentageChanged(peakStreamsPercentage, peakStreamsIndicatorClass));

        //averageChannels
        String averageChannels = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(5) > div > div > div.InfoStatPanelTL > div").get(0).text();
        String averageChannelsPercentage = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text();
        String averageChannelsIndicatorClass = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).className();
        String averageChannelsChanged = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(5) > div > div > div.InfoStatPanelBL > div").get(0).text();

        homePageDTO.setAverageChannels(averageChannels);
        homePageDTO.setAverageChannelsChangeAmount(averageChannelsChanged);
        homePageDTO.setAverageChannelsPercentageChange(getPercentageChanged(averageChannelsPercentage, averageChannelsIndicatorClass));

        //gamesStreamed
        String gamesStreamed = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(6) > div > div > div.InfoStatPanelTL > div").get(0).text();
        String gamesStreamedPercentage = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(6) > div > div > div.InfoStatPanelTR > div > span").get(0).text();
        String gamesStreamedIndicatorClass = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(6) > div > div > div.InfoStatPanelTR > div > span").get(0).className();
        String gamesStreamedChanged = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(6) > div > div > div.InfoStatPanelBL > div").get(0).text();

        homePageDTO.setGamesStreamed(gamesStreamed);
        homePageDTO.setGamesStreamedChangeAmount(gamesStreamedChanged);
        homePageDTO.setGamesStreamedPercentageChange(getPercentageChanged(gamesStreamedPercentage, gamesStreamedIndicatorClass));

        //viewerRatio
        String viewerRatio = doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerMiddle > div > div > div.InfoStatPanelTL > div").get(0).text();

        homePageDTO.setViewerRatio(viewerRatio);
        homePageDTO.setDayRange(7);

        //mostWatchedChannelsRows
        Elements mostWatchedChannelsRows = doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div > div > div:nth-child(1) > div:nth-child(1)").get(0).getElementsByClass("InfoStatPanelLongInner");
        List<HomePageElementDTO> mostWatchedChannels = getDTOsFromElements(mostWatchedChannelsRows);
        //mostWatchedGamesRows
        Elements mostWatchedGamesRows = doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div > div > div:nth-child(1) > div.InfoStatPanelLong.InfoStatPanelLongMiddle").get(0).getElementsByClass("InfoStatPanelLongInner");
        List<HomePageElementDTO> mostWatchedGames = getDTOsFromElements(mostWatchedGamesRows);
        //trendingGamesRows
        Elements trendingGamesRows = doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div > div > div:nth-child(1) > div:nth-child(3)").get(0).getElementsByClass("InfoStatPanelLongInner");
        List<HomePageElementDTO> trendingGames = getDTOsFromElements(trendingGamesRows);
        //fastestGrowingChannelsRows
        Elements fastestGrowingChannelsRows = doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div > div > div:nth-child(2) > div:nth-child(1)").get(0).getElementsByClass("InfoStatPanelLongInner");
        List<HomePageElementDTO> fastestGrowingChannels = getDTOsFromElements(fastestGrowingChannelsRows);
        //mostStreamedGamesRow
        Elements mostStreamedGamesRow = doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div > div > div:nth-child(2) > div.InfoStatPanelLong.InfoStatPanelLongMiddle").get(0).getElementsByClass("InfoStatPanelLongInner");
        List<HomePageElementDTO> mostStreamedGames = getDTOsFromElements(mostStreamedGamesRow);
        //mostViewedStreamsRow
        Elements mostViewedStreamsRow = doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div > div > div:nth-child(2) > div:nth-child(3)").get(0).getElementsByClass("InfoStatPanelLongInner");
        List<HomePageElementDTO> mostViewedStreams = getDTOsFromElements(mostViewedStreamsRow);


        homePageDTO.setMostViewedStreams(mostViewedStreams);
        homePageDTO.setFastestGrowingChannels(fastestGrowingChannels);
        homePageDTO.setMostStreamedGames(mostStreamedGames);
        homePageDTO.setMostViewedStreams(mostViewedStreams);
        homePageDTO.setTrendingGames(trendingGames);
        homePageDTO.setMostWatchedGames(mostWatchedGames);
        homePageDTO.setMostWatchedChannels(mostWatchedChannels);

        System.out.print(homePageDTO.toString());

        objectMapper().writeValue(new File("src/main/resources/homepage.json"), homePageDTO);

    }

    private Double getPercentageChanged(String value, String indicator) {
        if (value == null || indicator == null) {
            return null;
        }
        value = value.replace("%", "");
        if (indicator.equalsIgnoreCase("Negative")) {
            double v = Double.parseDouble(value);
            return ( v *= -1);
        } else {
            return Double.parseDouble(value);
        }
    }

    private List<HomePageElementDTO> getDTOsFromElements(Elements elements) {
        List<HomePageElementDTO> homePageElementDTOS = new ArrayList<>();
        int i = 0;
        for (Element e : elements) {
            i++;
            HomePageElementDTO homePageElementDTO = new HomePageElementDTO();

            String channelUrl = e.getElementsByTag("a").get(0).attr("href");
            String profileImage = e.getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("src");
            String displayName = e.getElementsByTag("div").get(1).getElementsByTag("div").get(0).text();

            String value = e.getElementsByClass("InfoStatPanelLongLabelMiddle").get(0).getElementsByClass("InfoStatPanelLongLabelMiddleTop").get(0).text();
            String key = e.getElementsByClass("InfoStatPanelLongLabelMiddle").get(0).getElementsByClass("InfoStatPanelLongLabelMiddleBottom").get(0).text();

            String percentageChange = null;
            String positiveOrNegative = null;
            String valueChange = null;

            try {
                percentageChange = e.getElementsByClass("InfoStatPanelLongRight").get(0).getElementsByClass("InfoStatPanelLongRightTop").get(0).getElementsByTag("span").get(0).text().replace("%", "");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("Percentage Change not available");
            }

            try {
                positiveOrNegative = e.getElementsByClass("InfoStatPanelLongRight").get(0).getElementsByClass("InfoStatPanelLongRightTop").get(0).getElementsByTag("span").get(0).className();

            } catch (IndexOutOfBoundsException exception) {
                System.out.println("positiveOrNegative Change not available");
            }

            try {
                valueChange = e.getElementsByClass("InfoStatPanelLongRight").get(0).getElementsByClass("InfoStatPanelLongRightBottom").get(0).text();
            } catch (IndexOutOfBoundsException exception) {
                System.out.println("Value Change not available");
            }


            double percentageChanged = 0;

            if (positiveOrNegative != null && percentageChange != null) {
                if (positiveOrNegative.equalsIgnoreCase("Positive")) {
                    percentageChanged = Double.parseDouble(percentageChange);
                } else {
                    percentageChanged = Double.parseDouble("-" + percentageChange);
                }
            }

            homePageElementDTO.setImage(profileImage);
            homePageElementDTO.setKey(key);
            homePageElementDTO.setName(displayName);
            homePageElementDTO.setPercentageChange(percentageChanged);
            homePageElementDTO.setValue(value);
            homePageElementDTO.setRowNumber(i);
            homePageElementDTO.setValueChange(valueChange);
            System.out.println(homePageElementDTO.toString());
            homePageElementDTOS.add(homePageElementDTO);
        }
        return homePageElementDTOS;
    }

    @Test
    public void teamStatsPage() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/team-stats-page.html");
        String htmlString = IOUtils.toString(fis, "UTF-8");

        TeamsStatsSummaryDTO teamsStatsSummaryDTO = new TeamsStatsSummaryDTO();
        Document doc = Jsoup.parse(htmlString);

        String averageTeams = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTL > div").get(0).text());
        String activeTeams = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(2) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String peakTeamViewers = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(3) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String peakTeams = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(4) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String peakLiveMembers = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(5) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String mostActive = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(6) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String mostWatched = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerMiddle > div > div > div.InfoStatPanelTL > div").get(0).text());
        System.out.println(teamsStatsSummaryDTO.toString());

    }

    @Test
    public void channelStatsPage() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/channel-stats-page.html");
        String htmlString = IOUtils.toString(fis, "UTF-8");
        ChannelStatsSummaryDTO individualChannelPageDTO = new ChannelStatsSummaryDTO();
        Document doc = Jsoup.parse(htmlString);
        String hoursStreamed = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTL > div").get(0).text());
        String averageChannels = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(2) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String peakChannels = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(3) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String streams = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(4) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String activeChannels = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(5) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String activeAffiliates = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div:nth-child(6) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String activePartners = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop.InfoStatPanelContainerTopSpacer > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerMiddle > div > div > div.InfoStatPanelTL > div").get(0).text());
        System.out.println(individualChannelPageDTO.toString());
    }

    @Test
    public void channelPageSingle() throws IOException {
        FileInputStream fis = new FileInputStream("src/main/resources/channel-single-page.html");
        String htmlString = IOUtils.toString(fis, "UTF-8");
        IndividualChannelPageDTO individualChannelPageDTO = new IndividualChannelPageDTO();

        Document doc = Jsoup.parse(htmlString);

        String displayName = doc.select("#pageHeaderMiddle > div.PageHeaderMiddleTop > div > div > div > h1 > span.PageHeaderMiddleWithImageHeaderP1").text();
        individualChannelPageDTO.setDisplayName(displayName);

        String twitchUrl = doc.select("#pageSubHeaderRight > a").attr("href");
        individualChannelPageDTO.setUrl(twitchUrl);

        String profilePic = doc.select("#pageHeaderMiddle > div.PageHeaderMiddleTop > div > div > img").attr("src");
        individualChannelPageDTO.setProfilePic(profilePic);

        //top row done
        String followers = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(2)").get(0).text());
        individualChannelPageDTO.setFollowers(Integer.parseInt(followers));

        String views = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(4)").get(0).text());
        individualChannelPageDTO.setViews(Integer.parseInt(views));

        String status = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(6)").get(0).text());
        individualChannelPageDTO.setStatus(status);

        String mature = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(8)").get(0).text());
        if (mature.equalsIgnoreCase("Yes")) {
            individualChannelPageDTO.setMature(true);
        } else {
            individualChannelPageDTO.setMature(false);
        }

        String language = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(10)").get(0).text());
        individualChannelPageDTO.setLanguage(language);

        String createdAt = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(12)").get(0).text());
        individualChannelPageDTO.setCreatedAt(createdAt);

        //ranking row
        String lastOnlineDays = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(1) > div.MiddleSubHeaderItemValue").get(0).text());
        individualChannelPageDTO.setLastOnlineDays(Integer.parseInt(lastOnlineDays.replace(" days", "")));

        String followerRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(2) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        individualChannelPageDTO.setFollowerRank(Integer.parseInt(followerRank));

        String followerRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(2) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String followerRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(2) > div.MiddleSubHeaderItemValue > span > div").get(0).className());
        individualChannelPageDTO.setFollowerRankChange(changeFromName(followerRankChange, followerRankChangeName));

        String followerGainRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(3) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        individualChannelPageDTO.setFollowerGainRank(Integer.parseInt(followerGainRank));

        String followerGainRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(3) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String followerGainRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(3) > div.MiddleSubHeaderItemValue > span > div").get(0).className());
        individualChannelPageDTO.setFollowerGainRankChange(changeFromName(followerGainRankChange, followerGainRankChangeName));

        String peakViewerRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(4) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        individualChannelPageDTO.setPeakViewerRank(Integer.parseInt(peakViewerRank));

        String peakViewerRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(4) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String peakViewerRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(4) > div.MiddleSubHeaderItemValue > span > div").get(0).className());
        individualChannelPageDTO.setPeakViewerRankChange(changeFromName(peakViewerRankChange, peakViewerRankChangeName));

        String averageViewerRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(5) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        individualChannelPageDTO.setAverageViewerRank(Integer.parseInt(averageViewerRank));

        String averageViewerRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(5) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String averageViewerRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(5) > div.MiddleSubHeaderItemValue > span > div").get(0).className());
        individualChannelPageDTO.setAverageViewerRankChange(changeFromName(averageViewerRankChange, averageViewerRankChangeName));

        String viewRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(6) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        individualChannelPageDTO.setViewRank(Integer.parseInt(viewRank));

        String viewRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(6) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String viewRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(6) > div.MiddleSubHeaderItemValue > span > div").get(0).className());
        individualChannelPageDTO.setViewRankChange(changeFromName(viewRankChange, viewRankChangeName));

        String viewGainRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(7) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        individualChannelPageDTO.setViewGainRank(Integer.parseInt(viewGainRank));

        String viewGainRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(7) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String viewGainRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(7) > div.MiddleSubHeaderItemValue > span > div").get(0).className());
        individualChannelPageDTO.setViewRankChange(changeFromName(viewGainRankChange, viewGainRankChangeName));

        //stats

        String averageViewers = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTL > div").get(0).text());
        individualChannelPageDTO.setAverageViewers(Integer.parseInt(averageViewers));

        String averageViewersChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelBL > div").get(0).text());
        individualChannelPageDTO.setAverageViewersChange(Integer.parseInt(averageViewersChange));

        String averageViewersChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelBL > div").get(0).className().split(" ")[1]);
        String averageViewersChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setAverageViewersChangePercentage(changeFromPercentage(averageViewersChangePercentage, averageViewersChangeDirection));

        String hoursWatched = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelTL > div").get(0).text());
        individualChannelPageDTO.setHoursWatched(Integer.parseInt(hoursWatched));

        String hoursWatchedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelBL > div").get(0).text());
        individualChannelPageDTO.setHoursWatchedChange(Integer.parseInt(hoursWatchedChange));

        String hoursWatchedChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String hoursWatchedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setHoursWatchedChangePercentage(changeFromPercentage(hoursWatchedChangePercentage, hoursWatchedChangeDirection));

        String followersGained = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelTL > div").get(0).text());
        individualChannelPageDTO.setFollowersGained(Integer.parseInt(followersGained));

        String followersGainedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        individualChannelPageDTO.setFollowersGainedChange(Integer.parseInt(followersGainedChange));

        String followersGainedChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String followersGainedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setFollowersGainedChangePercentage(changeFromPercentage(followersGainedChangePercentage, followersGainedChangeDirection));

        String viewsGained = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTL > div").get(0).text());
        individualChannelPageDTO.setViewsGained(Integer.parseInt(viewsGained));

        String viewsGainedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        individualChannelPageDTO.setViewsGainedChange(Integer.parseInt(viewsGainedChange));

        String viewsGainedChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String viewsGainedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setViewsGainedChangePercentage(changeFromPercentage(viewsGainedChangePercentage, viewsGainedChangeDirection));

        String peakViewers = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setPeakViewers(Double.parseDouble(peakViewers));

        String peakViewersChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        individualChannelPageDTO.setPeakViewersChange(Double.parseDouble(peakViewersChange));

        String peakViewersChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String peakViewersChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setPeakViewersChangePercentage(changeFromPercentage(peakViewersChangePercentage, peakViewersChangeDirection));

        String hoursStreamed = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setHoursStreamed(Double.parseDouble(hoursStreamed));

        String hoursStreamedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        individualChannelPageDTO.setHoursStreamedChange(Double.parseDouble(hoursStreamedChange));

        String hoursStreamedDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String hoursStreamedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        individualChannelPageDTO.setHoursStreamedChangePercentage(changeFromPercentage(hoursStreamedChangePercentage, hoursStreamedDirection));


        Elements teamElements = doc.select("#channelQuickLinks > div").get(0).getElementsByTag("a");
        Map<String, String> teams = new HashMap<>();
        for (Element e : teamElements) {
            String url = e.attr("href").replace("/team/", "");
            String name = e.text();
            teams.put(url, name);
        }
        individualChannelPageDTO.setTeams(teams);

        Element element = doc.select("#combinedPanel > div > div.PageBackgroundColor.InfoPanelCombinedMiddle > div").get(0);
        Elements streamsRows = element.getElementsByClass("InfoPanelCombinedRow");
        streamsRows.addAll(element.getElementsByClass("InfoPanelCombinedRowAlt"));
        streamsRows.remove(0); //its the header row
        List<IndividualChannelStreamDTO> channelStreams = new ArrayList<>();

        for (Element stream : streamsRows) {
            IndividualChannelStreamDTO individualChannelStreamDTO = new IndividualChannelStreamDTO();
            String streamId = stream.getElementsByTag("a").get(0).attr("href").split("/")[5];
            individualChannelStreamDTO.setSullyId(Long.parseLong(streamId));

            String start = stream.getElementsByTag("a").get(0).text();
            individualChannelStreamDTO.setDateTime(start);

            String streamLength = stream.getElementsByTag("div").get(2).text().replace(" hrs", "");
            individualChannelStreamDTO.setStreamLength(Double.parseDouble(streamLength));

            String streamAverageViewers = stream.getElementsByTag("div").get(3).text();
            individualChannelStreamDTO.setAverageViewers(Double.parseDouble(streamAverageViewers));

            String streamPeakViewers = stream.getElementsByTag("div").get(4).text();
            individualChannelStreamDTO.setPeakViewers(Integer.parseInt(streamPeakViewers));

            String streamWatchTime = stream.getElementsByTag("div").get(5).text().replace(" hrs", "");
            individualChannelStreamDTO.setWatchTime(Double.parseDouble(streamWatchTime));

            String streamFollowers = stream.getElementsByTag("div").get(6).text();
            individualChannelStreamDTO.setFollowers(Integer.parseInt(streamFollowers));

            String streamViews = stream.getElementsByTag("div").get(7).text();
            individualChannelStreamDTO.setViews(Integer.parseInt(streamViews));

            Elements gamesElements = stream.getElementsByTag("div").get(8).getElementsByTag("a");
            Map<String, String> games = new HashMap<>();
            for (Element game : gamesElements) {
                String gameName = game.attr("title").replace("View stats for ", "");
                String gameImageUrl = game.getElementsByTag("img").get(0).attr("src");
                games.put(gameName, gameImageUrl);
            }
            individualChannelStreamDTO.setGames(games);
            channelStreams.add(individualChannelStreamDTO);
        }

        individualChannelPageDTO.setIndividualChannelStreamDTOList(channelStreams);

        objectMapper().writeValue(new File("src/main/resources/channel-single-page.json"), individualChannelPageDTO);

    }

    private Integer changeFromName(String changeValue, String changeName) {
        if(changeName == null || changeValue == null){
            return null;
        }
        if (changeName.equalsIgnoreCase("NegativeChange")) {
            int v = Integer.parseInt(changeValue);
            return ( v *= -1);
        } else {
            return Integer.parseInt(changeValue);
        }
    }

    private Double changeFromPercentage(String changeValue, String changeName) {
        if(changeName == null || changeValue == null){
            return null;
        }
        if (changeName.equalsIgnoreCase("Negative")) {
            double v = Double.parseDouble(changeValue);
            return ( v *= -1);
        } else {
            return Double.parseDouble(changeValue);
        }
    }

    private String cleanIt(String original) {
        return original.replace(" hrs", "").replace("%", "").replace("th", "").replace("rd", "").replace("nd", "").replace("st", "").replace("( ", "").replace(")", "").replace(",", "");
    }
}
