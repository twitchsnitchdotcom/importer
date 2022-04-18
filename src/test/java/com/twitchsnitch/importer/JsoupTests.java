package com.twitchsnitch.importer;

import com.twitchsnitch.importer.dto.sully.ChannelStatsSummaryDTO;
import com.twitchsnitch.importer.dto.sully.IndividualChannelPageDTO;
import com.twitchsnitch.importer.dto.sully.TeamsStatsSummaryDTO;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupTests {

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

        //top row done
        String followers = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(2)").get(0).text());
        String views = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(4)").get(0).text());
        String status = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(6)").get(0).text());
        String mature = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(8)").get(0).text());
        String language = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(10)").get(0).text());
        String created = cleanIt(doc.select("#pageHeaderMiddle > div.MiddleSubHeaderContainer > div > div:nth-child(12)").get(0).text());

        //ranking row
        String lastOnlineDays = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(1) > div.MiddleSubHeaderItemValue").get(0).text());

        String followerRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(2) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        String followerRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(2) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String followerRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(2) > div.MiddleSubHeaderItemValue > span > div").get(0).className());

        String followerGainRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(3) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        String followerGainRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(3) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String followerGainRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(3) > div.MiddleSubHeaderItemValue > span > div").get(0).className());

        String peakViewerRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(4) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        String peakViewerRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(4) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String peakViewerRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(4) > div.MiddleSubHeaderItemValue > span > div").get(0).className());

        String averageViewerRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(5) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        String averageViewerRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(5) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String averageViewerRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(5) > div.MiddleSubHeaderItemValue > span > div").get(0).className());

        String viewRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(6) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        String viewRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(6) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String viewRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(6) > div.MiddleSubHeaderItemValue > span > div").get(0).className());

        String viewGainRank = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(7) > div.MiddleSubHeaderItemValue").get(0).text().split(" ")[0]);
        String viewGainRankChange = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(7) > div.MiddleSubHeaderItemValue > span").get(0).text());
        String viewGainRankChangeName = cleanIt(doc.select("#pageContentSubHeader > div:nth-child(7) > div.MiddleSubHeaderItemValue > span > div").get(0).className());

        //stats

        String averageViewers = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTL > div").get(0).text());
        String averageViewersChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelBL > div").get(0).text());
        String averageViewersChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelBL > div").get(0).className().split(" ")[1]);
        String averageViewersChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div.InfoStatPanelWrapper.InfoStatPanelSpacerLeft > div > div > div.InfoStatPanelTR > div > span").get(0).text());

        String hoursWatched = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String hoursWatchedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelBL > div").get(0).text());
        String hoursWatchedChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String hoursWatchedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(2) > div > div > div.InfoStatPanelTR > div > span").get(0).text());

        String followersGained = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String followersGainedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        String followersGainedChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String followersGainedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelTR > div > span").get(0).text());

        String viewsGained = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTL > div").get(0).text());
        String viewsGainedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        String viewsGainedChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String viewsGainedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).text());

        String peakViewers = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        String peakViewersChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        String peakViewersChangeDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(4) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String peakViewersChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text());

        String hoursStreamed = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text());
        String hoursStreamedChange = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(3) > div > div > div.InfoStatPanelBL > div").get(0).text());
        String hoursStreamedDirection = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).className());
        String hoursStreamedChangePercentage = cleanIt(doc.select("body > div.RightContent > div.MainContent > div.InfoStatPanelContainerTop > div > div:nth-child(5) > div > div > div.InfoStatPanelTR > div > span").get(0).text());


        if(status.equalsIgnoreCase("Affiliate")){
            individualChannelPageDTO.setAffiliate(true);
        }
        else{
            individualChannelPageDTO.setAffiliate(false);
        }

//        individualChannelPageDTO.setAvgViewers();
//        individualChannelPageDTO.setCreatedAt();
//        individualChannelPageDTO.setFollowers();
//        individualChannelPageDTO.setFollowersGained();
//        individualChannelPageDTO.setFollowersGainedWhilePlaying();
//        individualChannelPageDTO.setLogin();
//        individualChannelPageDTO.setMature();
//        individualChannelPageDTO.setPartner();
//        individualChannelPageDTO.setPreviousAvgViewers();
//        individualChannelPageDTO.setPreviousFollowerGain();
//        individualChannelPageDTO.setPreviousMaxViewers();
//        individualChannelPageDTO.setPreviousStreamedMinutes();
//        individualChannelPageDTO.setPreviousViewMinutes();
//        individualChannelPageDTO.setPreviousViewsGained();
//        individualChannelPageDTO.setProfileImageUrl();
//        individualChannelPageDTO.setRowNumber();
//        individualChannelPageDTO.setStatus();
//        individualChannelPageDTO.setStreamedMinutes();
//        individualChannelPageDTO.setSullyId();
//        individualChannelPageDTO.setTotalViewCount();
//        individualChannelPageDTO.setTwitchId();
//        individualChannelPageDTO.setTwitchLink();
//        individualChannelPageDTO.setViewMinutes();
//        individualChannelPageDTO.setViewsGained();

        Elements teamElements = doc.select("#channelQuickLinks > div").get(0).getElementsByTag("a");
        List<String> teams = new ArrayList<>();


        for(Element team: teamElements){
            teams.add(team.attributes().get("href").replace("/team/", ""));
        }

        System.out.println(individualChannelPageDTO.toString());

    }

    private String cleanIt(String original){
        return original.replace(" hrs", "").replace("%", "").replace("th", "").replace("rd", "").replace("nd", "").replace("st", "").replace("( ", "").replace(")", "").replace(",", "");
    }
}
