package com.twitchsnitch.importer;

import com.twitchsnitch.importer.dto.sully.channels.IndividualStreamDTO;
import com.twitchsnitch.importer.dto.sully.channels.IndividualStreamDataDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTest {

    static String document = "\n" +
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <script>\n" +
            "        const prefersDarkScheme = window.matchMedia(\"(prefers-color-scheme: dark)\");\n" +
            "        let isDarkMode = false;\n" +
            "        if (prefersDarkScheme.matches) {\n" +
            "            isDarkMode = true;\n" +
            "        }\n" +
            "\n" +
            "        try {\n" +
            "            let cookieValue = '';\n" +
            "            const parts = document.cookie.split('SullyGnomeDarkMode=');\n" +
            "\n" +
            "            if (parts.length === 2)\n" +
            "            {\n" +
            "                cookieValue = parts.pop().split(';').shift();\n" +
            "            }\n" +
            "\n" +
            "            if(cookieValue === 'dark')\n" +
            "            {\n" +
            "                isDarkMode = true;\n" +
            "            }\n" +
            "            else if(cookieValue === 'light')\n" +
            "            {\n" +
            "                isDarkMode = false;\n" +
            "            }\n" +
            "        }\n" +
            "        catch(ex) {\n" +
            "\n" +
            "        }\n" +
            "        if (isDarkMode) {\n" +
            "            document.documentElement.setAttribute('data-theme', 'dark');\n" +
            "        } else {\n" +
            "            document.documentElement.setAttribute('data-theme', '');\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "    </script>\n" +
            "\n" +
            "\n" +
            "    <script type=\"text/javascript\">\n" +
            "            var sg_a_scCode = \"c03de72a\";\n" +
            "            var sg_a_scProject = \"10710394\";\n" +
            "            var sg_a_gaCode = \"UA-69840346-1\";\n" +
            "        </script>\n" +
            "\n" +
            "    <meta name=\"robots\" content=\"noindex\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <script type=\"text/javascript\">\n" +
            "                try {\n" +
            "                    (function (i, s, o, g, r, a, m) {\n" +
            "                        i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {\n" +
            "                            (i[r].q = i[r].q || []).push(arguments)\n" +
            "                        }, i[r].l = 1 * new Date(); a = s.createElement(o),\n" +
            "                            m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)\n" +
            "                    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');\n" +
            "\n" +
            "                    ga('create', sg_a_gaCode , 'auto');\n" +
            "                    ga('send', 'pageview');\n" +
            "                }\n" +
            "                catch (ex) {\n" +
            "\n" +
            "                }\n" +
            "\n" +
            "                var sc_project = sg_a_scProject; //Update url\n" +
            "                var sc_invisible = 1;\n" +
            "                var sc_security = sg_a_scCode;\n" +
            "                var scJsHost = \"https://secure.\";\n" +
            "\n" +
            "\n" +
            "            </script>\n" +
            "    <script async type='text/javascript' src='https://secure.statcounter.com/counter/counter.js'></script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <meta charset=\"utf-8\" />\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
            "    <title>auronplay - Monday 11th April 3:40pm stream summary stats - SullyGnome</title>\n" +
            "    <meta name=\"description\" content=\"Statistics for auronplay&#x27;s stream on Monday 11th April 3:40pm.\" />\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <link rel=\"stylesheet\" href=\"/css/sullystyle.min.css?v=TxoQjmqDrK99n0k0aypFZnXpp9f86wgu8joUmJIW0JA\" />\n" +
            "    <link rel=\"stylesheet\" href=\"/lib/jquery-ui-1.12.1.custom/jquery-ui.min.css\" />\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "</head>\n" +
            "<body>\n" +
            "<script type=\"text/javascript\">\n" +
            "        var PageInfo = {\"dayranges\":[{\"Key\":3,\"Value\":\"3 days\"},{\"Key\":7,\"Value\":\"7 days\"},{\"Key\":14,\"Value\":\"14 days\"},{\"Key\":30,\"Value\":\"30 days\"},{\"Key\":90,\"Value\":\"90 days\"},{\"Key\":180,\"Value\":\"180 days\"},{\"Key\":365,\"Value\":\"365 days\"},{\"Key\":0,\"Value\":\"More\"}],\"defaultsection\":\"stream\",\"urlpart\":\"auronplay\",\"id\":24713147,\"name\":\"auronplay\",\"logo\":null,\"defaultpagesize\":100,\"defaultLanguageId\":0,\"additionalparams\":\"\",\"compareIds\":null,\"timezoneText\":\"Timezone (UTC)\",\"timezoneOffset\":0,\"sitedefaultdayrange\":7,\"dayRange\":7,\"dayRangeOffset\":0,\"latestDayId\":2762,\"twitchUrl\":\"https://www.twitch.tv/auronplay\",\"latestUpdateDay\":1649635200000,\"lateUpdateTime\":1649761200000,\"rangeInfo\":{\"dayRange\":7,\"dayRangeOffset\":0,\"isNamedRange\":false,\"namedRange\":\"\",\"month\":0,\"year\":0,\"range\":\"7\",\"startDayId\":2756,\"endDayId\":2763},\"filterInfo\":{\"minYear\":2019,\"maxYear\":2022,\"minYearMonth\":9,\"maxYearMonth\":3,\"filterUrl\":\"/channel/auronplay/{RANGE}/stream/46140632173\"},\"tablePageSizes\":[[\"10\",\"25\",\"50\",\"100\"],[\"10\",\"25\",\"50\",\"100\"]],\"timecode\":\"125175_4/12/2022 12:59:14 PM_4859a4d9-dde4-4b23-8691-16fec68bca5e_7d6da0d14be12943382b4c6edd285635\",\"language\":null,\"timezone\":\"(UTC) Coordinated Universal Time\"};\n" +
            "        var PageData = {\"name\":\"\",\"id\":0};\n" +
            "\n" +
            "        var RedirectToUrl = function (Url) {\n" +
            "            window.location.href = Url;\n" +
            "        }\n" +
            "\n" +
            "        var ToggleMoreRangeLinks = function (Container) {\n" +
            "            var el = document.getElementById(Container);\n" +
            "\n" +
            "            if (el.style.display == 'none') {\n" +
            "                el.style.display = 'block';\n" +
            "            }\n" +
            "            else {\n" +
            "                el.style.display = 'none';\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "    </script>\n" +
            "\n" +
            "<div class=\"LeftMenu\">\n" +
            "    <div class=\"LeftMenuHeader\">\n" +
            "        <div class=\"LeftMenuHeaderTopImage\">\n" +
            "            <a href=\"/\">\n" +
            "                <img src=\"/Images/gnome.png\" alt=\"Hi I'm SullyGnome!\" height=\"48\" width=\"40\" />\n" +
            "            </a>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"Back to the home page\">\n" +
            "        <a href=\"/\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/home.svg\" class=\"FontAweIcon MenuItemHome\" alt=\"Home\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Home\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"View stats on the channels which stream on Twitch\">\n" +
            "        <a href=\"/channels\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/tv.svg\" class=\"FontAweIcon\" alt=\"Channels\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Channels\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"View stats on the games streamed on Twitch\">\n" +
            "        <a href=\"/games\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/gamepad.svg\" class=\"FontAweIcon\" alt=\"Game\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Games\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"View stats on the teams which stream on Twitch\">\n" +
            "        <a href=\"/teams\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/user-plus.svg\" class=\"FontAweIcon\" alt=\"Teams\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Teams\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"Search for channels by the games they play and broadcast language\">\n" +
            "        <a href=\"/channelsearch\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/search.svg\" class=\"FontAweIcon\" alt=\"Search\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Search\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"See channels which have reached milestones\">\n" +
            "        <a href=\"/milestones\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/tasks.svg\" class=\"FontAweIcon\" alt=\"Channel milestones\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Milestones\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"Detailed analysis articles on Twitch\">\n" +
            "        <a href=\"/articlesanalysis\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/newspaper.svg\" class=\"FontAweIcon\" alt=\"Articles\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Articles\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"All about me and the site\">\n" +
            "        <a href=\"/about\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/male.svg\" class=\"FontAweIcon\" alt=\"About\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    About\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"MenuItemWrapper\" title=\"Support me :)\">\n" +
            "        <a href=\"https://www.patreon.com/sullygnome\" target=\"_blank\">\n" +
            "            <div class=\"MenuItem\">\n" +
            "                <div class=\"MenuItemPart\"><img src=\"/images/sidebar/Patreon_Mark_Primary.png\" class=\"PatreonIcon\" alt=\"Patreon\" /></div>\n" +
            "                <div class=\"MenuItemPart MenuItemPartText\">\n" +
            "                    Patreon\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </a>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"LeftMenuFooter\">\n" +
            "        <div class='DarkModeHeader'>Dark mode</div>\n" +
            "        <div class=\"DarkmodeWrapper notransition\" id='dmWrapper'>\n" +
            "            <input type=\"checkbox\" id=\"dmChk\" class=\"notransition\"/>\n" +
            "            <label for=\"dmChk\" class=\"DarkmodeToggle notransition\" onclick='SullyJS.Util.SwitchDarkMode(); return true;'>\n" +
            "                <span class=\"DarkmodeSpan notransition\" id='dmSpan'>\n" +
            "                </span>\n" +
            "            </label>\n" +
            "        </div>\n" +
            "\n" +
            "        <script type='text/javascript'>\n" +
            "                if(isDarkMode)\n" +
            "                {\n" +
            "                    document.getElementById('dmChk').checked = true;\n" +
            "                }\n" +
            "            </script>\n" +
            "        <div class=\"LeftMenuFooterContact\">\n" +
            "            <div class=\"LeftMenuFooterContact\">\n" +
            "                <div class=\"LeftMenuFooterIcon\">\n" +
            "                    <a href=\"https://twitter.com/SullyGnome\" target='_blank' title=\"Twitter\">\n" +
            "                        <img height=\"22\" width=\"22\" src=\"/Images/twitterlatest.png\" style=\"border:0\" alt=\"Message me on Twitter\" />\n" +
            "                    </a>\n" +
            "                </div>\n" +
            "                <div class=\"LeftMenuFooterIcon\">\n" +
            "                    <a href=\"/cdn-cgi/l/email-protection#dab9b5b4aebbb9ae9aa9afb6b6a3bdb4b5b7bff4b9b5b7\" target='_blank' title=\"Email\">\n" +
            "                        <img src=\"/Images/Email.png\" style=\"border:0\" alt=\"Send me an email\" />\n" +
            "                    </a>\n" +
            "                </div>\n" +
            "                <div class=\"LeftMenuFooterIcon\">\n" +
            "                    <a href=\"https://www.twitch.tv/sullygnome\" title=\"Twitch PM\" target='_blank'>\n" +
            "                        <img src=\"/Images/TwitchIcon.png\" style=\"border:0\" alt=\"Send me a Twitch PM\" />\n" +
            "                    </a>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "</div>\n" +
            "<div class=\"RightContent\">\n" +
            "    <div class=\"TopMenu\">\n" +
            "        <div class=\"SiteTileBar\">\n" +
            "            <div class=\"SiteTitleBarHeader\">\n" +
            "                <h2>SullyGnome - Twitch stats and analysis</h2>\n" +
            "            </div>\n" +
            "            <div class=\"SiteTitleBarTimezone\">\n" +
            "                <a href=\"\" onclick=\"SullyJS.Util.ShowTimezonePicker(); return false;\">Timezone: (UTC) Coordinated Universal Time</a>\n" +
            "            </div>\n" +
            "            <div class=\"SiteTitleBarSearch\">\n" +
            "                <div id=\"SearchContainer\" class=\"SiteTitleItem SiteTitleItemSearch\" style=\"position:relative\">\n" +
            "                    <img src=\"/images/sidebar/cog.svg\" class=\"SearchToggleMenuIcon\" onclick=\"SullyJS.Search.CreateSearchCombo.ToggleOptions();\" style=\"display:none\" id=\"showSearchIcon\" />\n" +
            "                    <input id=\"HeaderChannelGameSearch\" placeholder=\"Search for a game or channel\">\n" +
            "                    <div id=\"SearchToggleMenu\" style=\"display:none\" class=\"SearchToggleMenu\">\n" +
            "                        <div class=\"SearchToggleMenuText\" id=\"idSearchFilterLabel\">Filter: </div>\n" +
            "                        <div class=\"SearchToggleMenuOption\" data-search-item-type=\"1\"><img src=\"/images/sidebar/tv.svg\" alt=\"Search for channels\" title=\"Search for channels\" onmouseenter=\"SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel(1);\" onmouseleave=\"SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel();\" onclick=\"SullyJS.Search.CreateSearchCombo.SetSearchFilter(1);\" /></div>\n" +
            "                        <div class=\"SearchToggleMenuOption\" data-search-item-type=\"2\"><img src=\"/images/sidebar/gamepad.svg\" alt=\"Search for games\" title=\"Search for games\" onmouseenter=\"SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel(2);\" onmouseleave=\"SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel();\" onclick=\"SullyJS.Search.CreateSearchCombo.SetSearchFilter(2);\" /></div>\n" +
            "                        <div class=\"SearchToggleMenuOption\" data-search-item-type=\"4\"><img src=\"/images/sidebar/user-plus.svg\" alt=\"Search for teams\" title=\"Search for teams\" onmouseenter=\"SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel(4);\" onmouseleave=\"SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel();\" onclick=\"SullyJS.Search.CreateSearchCombo.SetSearchFilter(4);\" /></div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"MainContent\">\n" +
            "        <div class=\"PageHeader\">\n" +
            "            <div class=\"PageHeaderInner\">\n" +
            "                <div class=\"PageHeaderLeft\" id=\"pageHeaderLeft\" style=\"display:none\">\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FL'>\n" +
            "                        <a href='/game/Max_Payne_3' style=\"display:block\" title='View stats for Max Payne 3'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/ttv-boxart/21222_IGDB-136x190.jpg?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for Max Payne 3' data-isgamelink='' data-gamename='Max Payne 3' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FL'>\n" +
            "                        <a href='/game/Grand_Theft_Auto_Vice_City' style=\"display:block\" title='View stats for Grand Theft Auto: Vice City'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/ttv-boxart/15631_IGDB-136x190.jpg?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for Grand Theft Auto: Vice City' data-isgamelink='' data-gamename='Grand Theft Auto: Vice City' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FL'>\n" +
            "                        <a href='/game/Borderlands_3' style=\"display:block\" title='View stats for Borderlands 3'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/ttv-boxart/491318_IGDB-136x190.jpg?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for Borderlands 3' data-isgamelink='' data-gamename='Borderlands 3' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FL'>\n" +
            "                        <a href='/game/Halo_Infinite' style=\"display:block\" title='View stats for Halo Infinite'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/ttv-boxart/506416_IGDB-136x190.jpg?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for Halo Infinite' data-isgamelink='' data-gamename='Halo Infinite' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FL'>\n" +
            "                        <a href='/game/Control' style=\"display:block\" title='View stats for Control'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/ttv-boxart/506462_IGDB-136x190.jpg?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for Control' data-isgamelink='' data-gamename='Control' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"PageHeaderMiddle\" id=\"pageHeaderMiddle\">\n" +
            "                    <div class=\"PageHeaderMiddleTop\">\n" +
            "\n" +
            "                        <div class=\"PageHeaderMiddleCon\">\n" +
            "                            <div class=\"PageHeaderMiddleConInner\">\n" +
            "\n" +
            "                                <img src=\"https://static-cdn.jtvnw.net/jtv_user_pictures/ec898e4a-e0df-4dc0-a99d-7540c6dbe1e8-profile_image-150x150.png?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100\" class=\"PageHeaderMiddleTopImage\" />\n" +
            "\n" +
            "                                <div class=\"PageHeaderMiddleWithImageHeader\">\n" +
            "                                    <h1>\n" +
            "                                        <span class=\"PageHeaderMiddleWithImageHeaderP1\">auronplay</span>\n" +
            "                                        <span class=\"PageHeaderMiddleWithImageHeaderP2\">Stream starting Monday 11th April 3:40pm</span>\n" +
            "                                    </h1>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class='MiddleSubHeaderContainer'><div class='MiddleSubHeaderRow'><div class='MiddleSubHeaderItem' title='Total number of followers'>Followers:</div><div class='MiddleSubHeaderItemValue'>12,351,911</div><div class='MiddleSubHeaderItem' title='Total number of views'>Views:</div><div class='MiddleSubHeaderItemValue'>259,671,497</div><div class='MiddleSubHeaderItem'>Status:</div><div class='MiddleSubHeaderItemValue'>Partnered</div><div class='MiddleSubHeaderItem'>Mature:</div><div class='MiddleSubHeaderItemValue'>No</div><div class='MiddleSubHeaderItem' title='Broadcast language'>Language:</div><div class='MiddleSubHeaderItemValue'>Spanish</div><div class='MiddleSubHeaderItem'>Created:</div><div class='MiddleSubHeaderItemValue' title='Channel was created on this date'>3rd Sep 2019</div></div></div>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"PageHeaderRight\" id=\"pageHeaderRight\" style=\"display:none\">\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FR'>\n" +
            "                        <a href='/channel/juanjuegajuegos' style=\"display:block\" title='View stats for JuanJuegaJuegos'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/jtv_user_pictures/9d945e50-77c3-42b0-96b7-0514c52d2aa9-profile_image-150x150.jpg?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for JuanJuegaJuegos' data-ischannellink='' data-channelname='JuanJuegaJuegos' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <div class='RelatedLinksItemSmall FR'>\n" +
            "                        <a href='/channel/fuslie' style=\"display:block\" title='View stats for fuslie'>\n" +
            "                            <img src='https://static-cdn.jtvnw.net/jtv_user_pictures/517e697a-6768-4834-a703-c326cc1a74b5-profile_image-150x150.png?imenable=1&amp;impolicy=user-profile-picture&amp;imwidth=100' alt='View stats for fuslie' data-ischannellink='' data-channelname='fuslie' />\n" +
            "                        </a>\n" +
            "                        <div class='RelatedLinksButtomText'></div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"PageSubHeader\" id=\"pageSubHeaderOuter\">\n" +
            "            <div class=\"PageSubHeaderInner\">\n" +
            "                <div class=\"PageSubHeaderLeft\" id=\"pageSubHeaderLeft\">\n" +
            "                    <div title='View summary stats for auronplay over the past 7 days' class='PageSubHeaderTabFirst PageSubHeaderTab' onclick='RedirectToUrl(\"/channel/auronplay\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay'>Summary</a></div></div>\n" +
            "                    <div title='View streams for auronplay over the past 7 days' class='PageSubHeaderTab SelectedLink SelectedTab' onclick='RedirectToUrl(\"/channel/auronplay/streams\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay/streams'>Streams</a></div></div>\n" +
            "                    <div title='View games played by auronplay over the past 7 days' class='PageSubHeaderTab' onclick='RedirectToUrl(\"/channel/auronplay/games\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay/games'>Games</a></div></div>\n" +
            "                    <div title='View activity stats for auronplay over the past 7 days' class='PageSubHeaderTab' onclick='RedirectToUrl(\"/channel/auronplay/activitystats\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay/activitystats'>Activity</a></div></div>\n" +
            "                    <div title='View a stream calendar for auronplay' class='PageSubHeaderTab' onclick='RedirectToUrl(\"/channel/auronplay/calendar\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay/calendar'>Calendar</a></div></div>\n" +
            "                    <div title='Compareauronplay to other Twitch channels' class='PageSubHeaderTab' onclick='RedirectToUrl(\"/channel/auronplay/compare\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay/compare'>Compare</a></div></div>\n" +
            "                    <div title='View auronplay stats over the long term' class='PageSubHeaderTab' onclick='RedirectToUrl(\"/channel/auronplay/longtermstats\"); return false;'><div class='SubheaderLinkContainer'><a href='/channel/auronplay/longtermstats'>Long-term stats</a></div></div>\n" +
            "                    <div title='Tool to help choose a game to stream' class='PageSubHeaderTab TabLinkLeftPadded' onclick='RedirectToUrl(\"/tools/gamepicker?channel=auronplay\"); return false;'><div class='SubheaderLinkContainer'><a href='/tools/gamepicker?channel=auronplay'>Game Picker</a></div></div>\n" +
            "                    <div title='Use this tool to help build relationships by raiding channels after your finish streaming' class='PageSubHeaderTab' onclick='RedirectToUrl(\"/tools/raidfinder?channel=auronplay\"); return false;'><div class='SubheaderLinkContainer'><a href='/tools/raidfinder?channel=auronplay'>Raid Finder</a></div></div>\n" +
            "                </div>\n" +
            "                <div class=\"PageSubHeaderMiddle\" id=\"pageSubHeaderMiddle\">\n" +
            "\n" +
            "                    <div class=\"PageSubHeaderRight\" id=\"pageSubHeaderRight\">\n" +
            "                        <a href='https://www.twitch.tv/auronplay' title='View auronplay on Twitch' target='_blank' ><img src='/images/TwitchIcon.png'/></a>\n" +
            "                    </div>\n" +
            "\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "\n" +
            "        <div class='PageContentSubHeader' id=\"pageContentSubHeader\">\n" +
            "            <div class=\"MiddleSubHeaderItemCont\">\n" +
            "                <div class='MiddleSubHeaderItem PageContentSubHeaderItem'>Start:</div>\n" +
            "                <div class='MiddleSubHeaderItemValue'>Monday 11th April 3:40pm</div>\n" +
            "            </div>\n" +
            "            <div class=\"MiddleSubHeaderItemCont\">\n" +
            "                <div class='MiddleSubHeaderItem PageContentSubHeaderItem'>Length:</div>\n" +
            "                <div class='MiddleSubHeaderItemValue'>3 hours, 49 minutes</div>\n" +
            "            </div>\n" +
            "            <div class=\"MiddleSubHeaderItemCont\">\n" +
            "                <div class='MiddleSubHeaderItem PageContentSubHeaderItem'>Watch time:</div>\n" +
            "                <div class='MiddleSubHeaderItemValue'>520,681 hours</div>\n" +
            "            </div>\n" +
            "            <div class=\"MiddleSubHeaderItemCont\">\n" +
            "                <div class='MiddleSubHeaderItem PageContentSubHeaderItem'>Followers:</div>\n" +
            "                <div class='MiddleSubHeaderItemValue'>9,104</div>\n" +
            "            </div>\n" +
            "            <div class=\"MiddleSubHeaderItemCont\">\n" +
            "                <div class='MiddleSubHeaderItem PageContentSubHeaderItem'>Views:</div>\n" +
            "                <div class='MiddleSubHeaderItemValue'>438,921</div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "        <div class=\"PageQuickLinksPanel\" id=\"channelQuickLinks\">\n" +
            "            <div style=\"float:left\">\n" +
            "                <a href=\"/team/auronplay\" style=\"padding-right:5px\">Auronplay</a>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"PageContentContainer\">\n" +
            "\n" +
            "\n" +
            "            <div class=\"StreamSubHeader\">\n" +
            "                <div class=\"StreamSubHeaderLeft\">\n" +
            "                    <a href=\"/channel/auronplay/stream/46130924829\" class=\"StreamSubHeaderLinkIcon\"><img src=\"/images/fa/angle-left-solid.svg\" /></a>\n" +
            "                    <a href=\"/channel/auronplay/stream/46130924829\" class=\"StreamSubHeaderLink\">Sunday 10th April 3:42pm (3 hours, 17 minutes)</a>\n" +
            "                </div>\n" +
            "\n" +
            "                <div class=\"StreamSubHeaderRight\">\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "\n" +
            "            <div class=\"StandardPageContainer\">\n" +
            "                <div class=\"SetMinScrollable\">\n" +
            "\n" +
            "                    <div class=\"MiniPanelContainer\">\n" +
            "                        <div class=\"MiniPanelOuter\">\n" +
            "                            <div class=\"MiniPanel\">\n" +
            "                                <div class=\"MiniPanelTop\" title=\"The average number of viewers watching the stream\">\n" +
            "                                    <div class=\"MiniPanelTopLeft\" >Average viewers:</div>\n" +
            "                                    <div class=\"MiniPanelTopRight\">\n" +
            "                                        129,820\n" +
            "                                    </div>\n" +
            "                                    <img src=\"/images/sidebar/info-circle-solid.svg\" class=\"MiniPanelInfoIcon\" title=\"There is usually a high degree of variance between streams and averages can get easily skewed (by a large raid for example). This panel uses standard deviation of calculate an expected range and then displays whether or not the stream falls within this range. Please note that being on par is not a negative, particularly if the trend is positive.\" />\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelProcess\"  title=\"How this streams average viewership compares the largest in the previous 15 streams\" >\n" +
            "                                    <div class=\"MiniPanelProcessInner\">\n" +
            "                                        <div class=\"MiniPanelProcessInnerOverlay MiniPanelProcessInnerOverlayViewers\" style=\"width:89%\">\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelMiddle\" title=\"How this streams average viewership compares to previous 15 streams\">\n" +
            "                                    <div class=\"MiniPanelMiddleLeft\">Stream performance:</div>\n" +
            "                                    <div class=\"MiniPanelMiddleRight DeviationCompareAbove\">Above</div>\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelBottom\" title=\"The trend in average viewership of previous 15 streams\">\n" +
            "                                    <div class=\"MiniPanelBottomLeft\">Previous stream trend:</div>\n" +
            "                                    <div class=\"MiniPanelBottomRight DeviationCompareAbove\">Positive</div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"MiniPanelOuter\">\n" +
            "                            <div class=\"MiniPanel\">\n" +
            "                                <div class=\"MiniPanelTop\" title=\"The average number of views per hour gained during the course of this stream\">\n" +
            "                                    <div class=\"MiniPanelTopLeft\" >Views per hour:</div>\n" +
            "                                    <div class=\"MiniPanelTopRight\">\n" +
            "                                        117,045.60\n" +
            "                                    </div>\n" +
            "                                    <img src=\"/images/sidebar/info-circle-solid.svg\" class=\"MiniPanelInfoIcon\" title=\"There is usually a high degree of variance between streams and averages can get easily skewed (by a large raid for example). This panel uses standard deviation of calculate an expected range and then displays whether or not the stream falls within this range. Please note that being on par is not a negative, particularly if the trend is positive.\" />\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelProcess\"  title=\"How this streams average views per hour compares the largest in the previous 15 streams\" >\n" +
            "                                    <div class=\"MiniPanelProcessInner\">\n" +
            "                                        <div class=\"MiniPanelProcessInnerOverlay MiniPanelProcessInnerOverlayViews\" style=\"width:64%\">\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelMiddle\" title=\"How this streams average views per hour compares to previous 15 streams\">\n" +
            "                                    <div class=\"MiniPanelMiddleLeft\">Stream performance:</div>\n" +
            "                                    <div class=\"MiniPanelMiddleRight DeviationCompareSlightlyAbove\">Slightly above</div>\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelBottom\" title=\"The trend in average views per hour of the previous 15 streams\">\n" +
            "                                    <div class=\"MiniPanelBottomLeft\">Previous stream trend:</div>\n" +
            "                                    <div class=\"MiniPanelBottomRight DeviationCompareBelow\">Negative</div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"InfoPanelContainer\">\n" +
            "\n" +
            "                        <div class=\"ChartPanelWide\">\n" +
            "                            <div id=\"channelstream\" class=\"LineChartContainer\">\n" +
            "                                <div class=\"ChartHeader\">\n" +
            "                                    <div class=\"ChartTitleText\">\n" +
            "                                        <h3 class=\"ChartTitle\" id=\"lblChartTitle\">Stream overview - auronplay&#x27;s stream starting Monday 11th April 3:40pm</h3>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"ChartOptions\">\n" +
            "                                        <a href=\"\" class=\"ChartResize\" onclick=\"SullyJS.Charts.ExpandCollapseChart(this,'ChartPanelWide','CanvasWrapWide'); return false;\">Expand</a>\n" +
            "                                        <a href=\"\" class=\"ChartResize\" onclick=\"SullyJS.Charts.SaveChart(this); return false;\">Image</a>\n" +
            "                                        <a href=\"\" class=\"ChartResize ChartStyleOption\" onclick=\"SullyJS.Charts.RestyleChart(this,2); return false;\" id=\"lineStyleOption\" style=\"display:none\">Show offline</a>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <div class=\"ChartRangeRow\">\n" +
            "                                    <div id=\"pnlChartDescription\" style=\"display:none\">\n" +
            "                                        <div class=\"ChartRangeLabel\" id=\"litDescriptionLabel\"></div>\n" +
            "                                    </div>\n" +
            "                                    <div id=\"pnlChartMinLabel\" style=\"display:inline\">\n" +
            "                                        <div class=\"ChartRangeLabel\" id=\"litRangeMinLabel\"></div>\n" +
            "                                        <div class=\"ChartRangeValue\" id=\"litRangeMinValue\"></div>\n" +
            "                                    </div>\n" +
            "                                    <div id=\"pnlChartMaxLabel\" style=\"display:inline\">\n" +
            "                                        <div class=\"ChartRangeLabel\" id=\"litRangeMaxLabel\"></div>\n" +
            "                                        <div class=\"ChartRangeValue\" id=\"litRangeMaxValue\"></div>\n" +
            "                                    </div>&nbsp;\n" +
            "                                </div>\n" +
            "                                <div class=\"ChartRangeRowHidden\" style=\"display:none\">\n" +
            "                                </div>\n" +
            "                                <div class=\"CanvasWrapWide\" id=\"chartwrappanel\">\n" +
            "                                    <canvas id=\"canvasLineChart\" class=\"LineChart\"></canvas>\n" +
            "                                </div>\n" +
            "                                <div id=\"ChartSlider\" style=\"display:none\" class=\"ChartSliderContainer ChartFooter\">\n" +
            "                                    <input id=\"inSliderLeft_36275419-2b86-426b-af8d-3c810b2562f4\" style=\"display:none\" />\n" +
            "                                    <a id=\"LeftText\" class=\"ChartSliderLeft\" onclick=\"SullyJS.Charts.Line.ShowSliderCalendar(this,'Start'); return false;\">Start Text</a>\n" +
            "                                    <div class=\"ChartSliderMiddle\">\n" +
            "                                        <div id=\"MiddleContainer\" class=\"ChartSliderWrapper\"></div>\n" +
            "                                    </div>\n" +
            "                                    <input id=\"inSliderRight_2441da12-2937-467a-897a-e041def6d408\" style=\"display:none\" />\n" +
            "                                    <a id=\"RightText\" class=\"ChartSliderRight\" onclick=\"SullyJS.Charts.Line.ShowSliderCalendar(this,'End'); return false;\">End Text</a>\n" +
            "                                </div>\n" +
            "                                <div id=\"LineChartLegend\"></div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "\n" +
            "                    <br/>\n" +
            "\n" +
            "                    <div class=\"StreamGameSummaryPanelContainer\">\n" +
            "                        <div class=\"StreamGameSummaryPanel\">\n" +
            "                            <div class=\"StreamGameSummaryPanelHeader\">\n" +
            "                                <div class=\"StreamGameSummaryPanelHeaderLeft\">\n" +
            "                                    <div class=\"StreamGameSummaryPanelItemValue\">Grand Theft Auto V</div>\n" +
            "                                </div>\n" +
            "                                <div class=\"StreamGameSummaryPanelHeaderPart\">\n" +
            "                                    <div class=\"StreamGameSummaryPanelItem\">Watch time: </div><div class=\"StreamGameSummaryPanelItemValue\">498,339 hours</div>\n" +
            "                                </div>\n" +
            "                                <div class=\"StreamGameSummaryPanelHeaderPart\">\n" +
            "                                    <div class=\"StreamGameSummaryPanelItem\">Stream length: </div><div class=\"StreamGameSummaryPanelItemValue\">3 hours, 30 minutes</div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "\n" +
            "                            <div class=\"MiniPanelBreak\">\n" +
            "                                <hr />\n" +
            "                            </div>\n" +
            "\n" +
            "                            <div class=\"MiniPanelContainer\">\n" +
            "                                <img src=\"https://static-cdn.jtvnw.net/ttv-boxart/32982_IGDB-136x190.jpg\" class=\"MiniPanelLogo\" />\n" +
            "                                <div class=\"MiniPanelOuter\">\n" +
            "                                    <div class=\"MiniPanel\">\n" +
            "                                        <div class=\"MiniPanelTop\" title=\"The average number of viewers watching the stream\">\n" +
            "                                            <div class=\"MiniPanelTopLeft\" >Average viewers:</div>\n" +
            "                                            <div class=\"MiniPanelTopRight\">\n" +
            "                                                142,383\n" +
            "                                            </div>\n" +
            "                                            <img src=\"/images/sidebar/info-circle-solid.svg\" class=\"MiniPanelInfoIcon\" title=\"There is usually a high degree of variance between streams and averages can get easily skewed (by a large raid for example). This panel uses standard deviation of calculate an expected range and then displays whether or not the stream falls within this range. Please note that being on par is not a negative.\" />\n" +
            "                                        </div>\n" +
            "                                        <div class=\"MiniPanelProcess\"  title=\"How this streams average viewership compares the largest in the previous 8 streams\" >\n" +
            "                                            <div class=\"MiniPanelProcessInner\">\n" +
            "                                                <div class=\"MiniPanelProcessInnerOverlay MiniPanelProcessInnerOverlayViewers\" style=\"width:100%\">\n" +
            "                                                </div>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                        <div class=\"MiniPanelMiddle\" title=\"The maximum number of viewers watching Grand Theft Auto V during this stream\">\n" +
            "                                            <div class=\"MiniPanelMiddleLeft\">Max viewers:</div>\n" +
            "                                            <div class=\"MiniPanelMiddleRight Bold\">164,582</div>\n" +
            "                                        </div>\n" +
            "                                        <div class=\"MiniPanelBottom\" title=\"How this streams average viewers compares to the past 8 Grand Theft Auto V streams\">\n" +
            "                                            <div class=\"MiniPanelBottomLeft\">Game performance:</div>\n" +
            "                                            <div class=\"MiniPanelBottomRight DeviationCompareAbove\">Above</div>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                                <div class=\"MiniPanelOuter\">\n" +
            "                                    <div class=\"MiniPanel\">\n" +
            "                                        <div class=\"MiniPanelTop\" title=\"The average number of views per hour gained during the course of this stream\">\n" +
            "                                            <div class=\"MiniPanelTopLeft\" >Views per hour:</div>\n" +
            "                                            <div class=\"MiniPanelTopRight\">\n" +
            "                                                90,100.43\n" +
            "                                            </div>\n" +
            "                                            <img src=\"/images/sidebar/info-circle-solid.svg\" class=\"MiniPanelInfoIcon\" title=\"There is usually a high degree of variance between streams and averages can get easily skewed (by a large raid for example). This panel uses standard deviation of calculate an expected range and then displays whether or not the stream falls within this range. Please note that being on par is not a negative.\" />\n" +
            "                                        </div>\n" +
            "                                        <div class=\"MiniPanelProcess\"  title=\"How this streams average views per hour compares the largest in the previous 8 streams\" >\n" +
            "                                            <div class=\"MiniPanelProcessInner\">\n" +
            "                                                <div class=\"MiniPanelProcessInnerOverlay MiniPanelProcessInnerOverlayViews\" style=\"width:81%\">\n" +
            "                                                </div>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                        <div class=\"MiniPanelMiddle\" title=\"The number of views gained watching Grand Theft Auto V during this stream\">\n" +
            "                                            <div class=\"MiniPanelMiddleLeft\">Views:</div>\n" +
            "                                            <div class=\"MiniPanelMiddleRight Bold\">315,352</div>\n" +
            "                                        </div>\n" +
            "                                        <div class=\"MiniPanelBottom\" title=\"How this streams views per hour compares to the past 8 Grand Theft Auto V streams\">\n" +
            "                                            <div class=\"MiniPanelBottomLeft\">Game performance:</div>\n" +
            "                                            <div class=\"MiniPanelBottomRight DeviationCompareBelow\">Below</div>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div><div class=\"StreamGameSummaryPanel\">\n" +
            "                        <div class=\"StreamGameSummaryPanelHeader\">\n" +
            "                            <div class=\"StreamGameSummaryPanelHeaderLeft\">\n" +
            "                                <div class=\"StreamGameSummaryPanelItemValue\">Just Chatting</div>\n" +
            "                            </div>\n" +
            "                            <div class=\"StreamGameSummaryPanelHeaderPart\">\n" +
            "                                <div class=\"StreamGameSummaryPanelItem\">Watch time: </div><div class=\"StreamGameSummaryPanelItemValue\">22,341 hours</div>\n" +
            "                            </div>\n" +
            "                            <div class=\"StreamGameSummaryPanelHeaderPart\">\n" +
            "                                <div class=\"StreamGameSummaryPanelItem\">Stream length: </div><div class=\"StreamGameSummaryPanelItemValue\">0 hours, 34 minutes</div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "\n" +
            "                        <div class=\"MiniPanelBreak\">\n" +
            "                            <hr />\n" +
            "                        </div>\n" +
            "\n" +
            "                        <div class=\"MiniPanelContainer\">\n" +
            "                            <img src=\"https://static-cdn.jtvnw.net/ttv-boxart/509658-136x190.jpg\" class=\"MiniPanelLogo\" />\n" +
            "                            <div class=\"MiniPanelOuter\">\n" +
            "                                <div class=\"MiniPanel\">\n" +
            "                                    <div class=\"MiniPanelTop\" title=\"The average number of viewers watching the stream\">\n" +
            "                                        <div class=\"MiniPanelTopLeft\" >Average viewers:</div>\n" +
            "                                        <div class=\"MiniPanelTopRight\">\n" +
            "                                            41,885\n" +
            "                                        </div>\n" +
            "                                        <img src=\"/images/sidebar/info-circle-solid.svg\" class=\"MiniPanelInfoIcon\" title=\"There is usually a high degree of variance between streams and averages can get easily skewed (by a large raid for example). This panel uses standard deviation of calculate an expected range and then displays whether or not the stream falls within this range. Please note that being on par is not a negative.\" />\n" +
            "                                    </div>\n" +
            "                                    <div class=\"MiniPanelProcess\"  title=\"How this streams average viewership compares the largest in the previous 15 streams\" >\n" +
            "                                        <div class=\"MiniPanelProcessInner\">\n" +
            "                                            <div class=\"MiniPanelProcessInnerOverlay MiniPanelProcessInnerOverlayViewers\" style=\"width:29%\">\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"MiniPanelMiddle\" title=\"The maximum number of viewers watching Just Chatting during this stream\">\n" +
            "                                        <div class=\"MiniPanelMiddleLeft\">Max viewers:</div>\n" +
            "                                        <div class=\"MiniPanelMiddleRight Bold\">65,909</div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"MiniPanelBottom\" title=\"How this streams average viewers compares to the past 15 Just Chatting streams\">\n" +
            "                                        <div class=\"MiniPanelBottomLeft\">Game performance:</div>\n" +
            "                                        <div class=\"MiniPanelBottomRight DeviationCompareEqual\">On par</div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                            <div class=\"MiniPanelOuter\">\n" +
            "                                <div class=\"MiniPanel\">\n" +
            "                                    <div class=\"MiniPanelTop\" title=\"The average number of views per hour gained during the course of this stream\">\n" +
            "                                        <div class=\"MiniPanelTopLeft\" >Views per hour:</div>\n" +
            "                                        <div class=\"MiniPanelTopRight\">\n" +
            "                                            59,842.00\n" +
            "                                        </div>\n" +
            "                                        <img src=\"/images/sidebar/info-circle-solid.svg\" class=\"MiniPanelInfoIcon\" title=\"There is usually a high degree of variance between streams and averages can get easily skewed (by a large raid for example). This panel uses standard deviation of calculate an expected range and then displays whether or not the stream falls within this range. Please note that being on par is not a negative.\" />\n" +
            "                                    </div>\n" +
            "                                    <div class=\"MiniPanelProcess\"  title=\"How this streams average views per hour compares the largest in the previous 15 streams\" >\n" +
            "                                        <div class=\"MiniPanelProcessInner\">\n" +
            "                                            <div class=\"MiniPanelProcessInnerOverlay MiniPanelProcessInnerOverlayViews\" style=\"width:31%\">\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"MiniPanelMiddle\" title=\"The number of views gained watching Just Chatting during this stream\">\n" +
            "                                        <div class=\"MiniPanelMiddleLeft\">Views:</div>\n" +
            "                                        <div class=\"MiniPanelMiddleRight Bold\">14,960</div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"MiniPanelBottom\" title=\"How this streams views per hour compares to the past 15 Just Chatting streams\">\n" +
            "                                        <div class=\"MiniPanelBottomLeft\">Game performance:</div>\n" +
            "                                        <div class=\"MiniPanelBottomRight DeviationCompareBelow\">Below</div>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>        </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "\n" +
            "            <div class=\"ContentFooter\"></div>\n" +
            "\n" +
            "\n" +
            "\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div style=\"clear:both\"></div>\n" +
            "\n" +
            "    <div id=\"TimezonePicker\" class=\"TimezonePicker\" style=\"display:none\" onclick=\"SullyJS.Util.ShowTimezonePicker(); return false;\">\n" +
            "        <div class=\"TimezonePickerContent\" onclick=\"SullyJS.Util.ShowTimezonePrevent(event);\">\n" +
            "            <span class=\"TimezonePickerClose\" onclick=\"SullyJS.Util.ShowTimezonePicker(); return false;\">&times;</span>\n" +
            "            <p id=\"TimezonePickerHeader\"></p>\n" +
            "            <hr />\n" +
            "            <select class=\"TimezonePickerSelect\" id=\"TimezonePickerSelect\" onchange=\"SullyJS.Util.TimezonePickerSelected();\">\n" +
            "                <option value=\"\">Loading...</option>\n" +
            "            </select>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "\n" +
            "    <div id=\"LanguagePicker\" class=\"TimezonePicker\" style=\"display:none\" onclick=\"SullyJS.Util.ShowLanguagePicker(); return false;\">\n" +
            "        <div class=\"TimezonePickerContent\" onclick=\"SullyJS.Util.ShowLanguagePrevent(event);\">\n" +
            "            <span class=\"TimezonePickerClose\" onclick=\"SullyJS.Util.ShowLanguagePicker(); return false;\">&times;</span>\n" +
            "            <p id=\"LanguagePickerHeader\"></p>\n" +
            "            <hr />\n" +
            "            <select class=\"TimezonePickerSelect\" id=\"LanguagePickerSelect\" onchange=\"SullyJS.Util.LanguagePickerSelected();\">\n" +
            "            </select>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <div class=\"FooterContainer\">\n" +
            "        <div class=\"PageFooter\">\n" +
            "            <a href=\"https://SullyGnome.com\">SullyGnome.com</a> © is in no way affiliated with <a href=\"https://www.twitch.tv/\" target=\"_blank\">Twitch</a>. Please do not scrape this site, it's a hobby project with limited resources.\n" +
            "            <a href=\"/policy/tos\">Terms</a>\n" +
            "            <a href=\"/policy/cookie\">Cookies</a>\n" +
            "        </div>\n" +
            "\n" +
            "    </div>\n" +
            "</div>\n" +
            "\n" +
            "<link rel=\"icon\" type=\"image/x-icon\" href=\"https://www.sullygnome.com/images/fav/favicon.ico\" />\n" +
            "\n" +
            "\n" +
            "\n" +
            "<script data-cfasync=\"false\" src=\"/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js\"></script><script src=\"/lib/jquery/jquery-3.2.1.min.js\"></script>\n" +
            "<script src=\"/lib/jquery-ui-1.12.1.custom/jquery-ui.min.js\"></script>\n" +
            "<script src=\"/js/sully/sully.bundle.min.js?v=gZjkEj6Ay02onJ0x2VW-Vn-QKF106ovWOHe_6dlKT3Y\"></script>\n" +
            "<script src=\"/js/sully/chart.bundle.js\"></script>\n" +
            "<script src=\"/lib/nouislider/nouislider.min.js\"></script>\n" +
            "<link rel=\"stylesheet\" href=\"/lib/nouislider/nouislider.min.css\" />\n" +
            "\n" +
            "\n" +
            "\n" +
            "<script>\n" +
            "            var ShowAnalytics = true;\n" +
            "        </script>\n" +
            "\n" +
            "\n" +
            "<script>\n" +
            "\n" +
            "        if(isDarkMode)\n" +
            "        {\n" +
            "            SullyJS.Util.SetDarkMode();\n" +
            "        }\n" +
            "\n" +
            "        var wrapper = $('[id=dmWrapper]');\n" +
            "        wrapper.removeClass('notransition');\n" +
            "        wrapper.find('.notransition').removeClass('notransition');\n" +
            "\n" +
            "    </script>\n" +
            "\n" +
            "\n" +
            "<script type=\"text/javascript\">\n" +
            "        SullyJS.Charts.Line.CreateLineChart(\"channelstream\", \"channelstream\", 7, 0, 24713147, \"auronplay\", null, null,0,0,46140632173, false);\n" +
            "    </script>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<script>\n" +
            "            $('[id*=showSearchIcon]').show();\n" +
            "            SullyJS.Util.RunStartEvents();\n" +
            "            $(function () {\n" +
            "                SullyJS.Search.CreateSearchCombo();\n" +
            "                SullyJS.Pages.CreateToolTips();\n" +
            "            });\n" +
            "        </script>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    public static void main(String[] args) {
        IndividualStreamDTO individualStreamDTO = new IndividualStreamDTO();
        Document doc = Jsoup.parse(document);
        individualStreamDTO.setAverageViewers(Long.parseLong(doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div.StandardPageContainer > div > div.MiniPanelContainer > div:nth-child(1) > div > div.MiniPanelTop > div.MiniPanelTopRight").get(0).text().replace(",", "")));
        individualStreamDTO.setViewsPerHour(Double.parseDouble(doc.select("body > div.RightContent > div.MainContent > div.PageContentContainer > div.StandardPageContainer > div > div.MiniPanelContainer > div:nth-child(2) > div > div.MiniPanelTop > div.MiniPanelTopRight").get(0).text().replace(",", "")));
        Elements streamSummaryPanels = doc.getElementsByClass("StreamGameSummaryPanel");
        for (Element panel : streamSummaryPanels) {
            IndividualStreamDataDTO individualStreamDataDTO = new IndividualStreamDataDTO();
            Elements pannelItems = panel.getElementsByClass("StreamGameSummaryPanelItemValue");
            individualStreamDataDTO.setGameName(pannelItems.get(0).text());
            individualStreamDataDTO.setWatchTime(Long.parseLong(pannelItems.get(1).text().replace(",", "").replace(" hours", "")));
            individualStreamDataDTO.setStreamLength(pannelItems.get(2).text());
            Elements miniPanelOuter = panel.getElementsByClass("MiniPanelOuter");

            individualStreamDataDTO.setAverageViewers(Long.parseLong(miniPanelOuter.get(0).getElementsByClass("MiniPanelTop").get(0).getElementsByClass("MiniPanelTopRight").get(0).text().replace(",", "")));
            individualStreamDataDTO.setMaxViewers(Long.parseLong(miniPanelOuter.get(0).getElementsByClass("MiniPanelMiddle").get(0).getElementsByClass("MiniPanelMiddleRight").get(0).text().replace(",", "")));
            individualStreamDataDTO.setMaxViewersPerformance(miniPanelOuter.get(0).getElementsByClass("MiniPanelBottom").get(0).getElementsByClass("MiniPanelBottomRight").get(0).text());
            individualStreamDataDTO.setViewsPerHour(Double.parseDouble(miniPanelOuter.get(1).getElementsByClass("MiniPanelTop").get(0).getElementsByClass("MiniPanelTopRight").get(0).text().replace(",", "")));
            individualStreamDataDTO.setViews(Long.parseLong(miniPanelOuter.get(1).getElementsByClass("MiniPanelMiddle").get(0).getElementsByClass("MiniPanelMiddleRight").get(0).text().replace(",", "")));
            individualStreamDataDTO.setViewsPerformance(miniPanelOuter.get(1).getElementsByClass("MiniPanelBottom").get(0).getElementsByClass("MiniPanelBottomRight").get(0).text());
            individualStreamDTO.getGamesPlayed().add(individualStreamDataDTO);
        }

        System.out.println(individualStreamDTO.toString());

    }
}
