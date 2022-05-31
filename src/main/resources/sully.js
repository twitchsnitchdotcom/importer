function setCookie(n, t, i) {
    var r, u;
    i ? (r = new Date,
    r.setTime(r.getTime() + i * 864e5),
    u = "; expires=" + r.toGMTString()) : u = "";
    document.cookie = n + "=" + t + u + "; path=/"
}
function CreateUUID() {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(n) {
        var t = Math.random() * 16 | 0
          , i = n == "x" ? t : t & 3 | 8;
        return i.toString(16)
    })
}
var SullyJS = {}, redirectOccuring, appendUrlForSearchDefault, appendUrlForSearchFull;
SullyJS.Charts = {};
SullyJS.Twitch = {};
SullyJS.Tables = {};
SullyJS.Classifier = {};
SullyJS.Classifier.Options = {};
SullyJS.AdvancedSearch = {};
SullyJS.AdvancedSearch.Preferences = {};
SullyJS.AdvancedSearch.Preferences.AddedItems = [];
SullyJS.TwitchAPI = {};
SullyJS.Calendar = {};
SullyJS.Charts.Bar = {};
SullyJS.Charts.Line = {};
SullyJS.Charts.Pie = {};
SullyJS.Charts.Bubble = {};
SullyJS.Charts.Cache = {};
SullyJS.Games = {};
SullyJS.Games.GamePicker = {};
SullyJS.Search = {};
SullyJS.Pages = {};
SullyJS.Pages.Channel = {};
SullyJS.Pages.Channels = {};
SullyJS.Pages.Community = {};
SullyJS.Pages.Communities = {};
SullyJS.Pages.Default = {};
SullyJS.Pages.Games = {};
SullyJS.Pages.Game = {};
SullyJS.Pages.Milestones = {};
SullyJS.Pages.Viewers = {};
SullyJS.Pages.DirectoryBrowser = {};
SullyJS.TwitchFunctions = {};
SullyJS.Util = {};
SullyJS.Util.Formatting = {};
SullyJS.Util.Url = {};
SullyJS.Util.Dates = {};
SullyJS.Util.Timezone = {};
SullyJS.Util.Panels = {};
SullyJS.Util.Panels.Current = {};
SullyJS.Util.RangeInfo = {};
SullyJS.Tools = {};
SullyJS.Tools.RaidFinder = {};
SullyJS.Users = {};
SullyJS.Users.Lists = {};
SullyJS.Util.RunStartEvents = function() {
    if (typeof SullyStart != "undefined")
        for (var n = 0; n < SullyStart.length; n++)
            SullyStart[n]()
}
;
SullyJS.Util.SwitchDarkMode = function() {
    SullyJS.Util.IsDarkMode() ? SullyJS.Util.SetLightMode(!0) : SullyJS.Util.SetDarkMode(!0)
}
;
SullyJS.Util.IsDarkMode = function() {
    return document.documentElement.getAttribute("data-theme") === "dark"
}
;
SullyJS.Util.SetDarkMode = function(n) {
    SullyJS.Util.SetDarkLightMode("dark", n)
}
;
SullyJS.Util.SetLightMode = function(n) {
    SullyJS.Util.SetDarkLightMode("light", n)
}
;
SullyJS.Util.SetDarkLightMode = function(n, t) {
    document.documentElement.setAttribute("data-theme", n);
    typeof SullyJS.Charts != "undefined" && typeof Chart != "undefined" && typeof Chart.defaults != "undefined" && SullyJS.Charts.SetGlobalProps();
    t && setCookie("SullyGnomeDarkMode", n, 700)
}
;
SullyJS.Util.Formatting.MakeStringSafe = function(n) {
    return n.replace("'", "")
}
;
SullyJS.Util.Formatting.DeltaBetweenColours = function(n, t, i, r) {
    return (r > 1 && (r = 1),
    r < -1 && (r = -1),
    r == 0) ? "rgba(" + Math.round(t[0]) + ", " + Math.round(t[1]) + ", " + Math.round(t[2]) + ", 1)" : r > 0 ? "rgba(" + SullyJS.Util.Formatting.DeltaBetweenValues(t[0], n[0], r) + ", " + SullyJS.Util.Formatting.DeltaBetweenValues(t[1], n[1], r) + ", " + SullyJS.Util.Formatting.DeltaBetweenValues(t[2], n[2], r) + ", 1)" : (r = -r,
    "rgba(" + SullyJS.Util.Formatting.DeltaBetweenValues(t[0], i[0], r) + ", " + SullyJS.Util.Formatting.DeltaBetweenValues(t[1], i[1], r) + ", " + SullyJS.Util.Formatting.DeltaBetweenValues(t[2], i[2], r) + ", 1)")
}
;
SullyJS.Util.Formatting.DeltaBetweenValues = function(n, t, i) {
    var r;
    return n == t ? Math.round(n) : n > t ? (r = (n - t) * i,
    Math.round(n - r)) : (r = (t - n) * i,
    Math.round(t + r))
}
;
SullyJS.Util.Formatting.ConvertToYearsAndDays = function(n, t, i, r) {
    var h, c, s;
    if (typeof n != "undefined" && n != null) {
        var u = parseInt(n / 60)
          , e = n - u * 60
          , f = "hours"
          , o = "mins";
        return i ? (f = "h",
        f = "m") : ((u == 1 || u == -1) && (f = "hour"),
        (e == 1 || e == -1) && (o = "min")),
        r && e != 0 ? SullyJS.Util.Formatting.AddThousandSeperator(u) + " " + f + ", " + e + " " + o : SullyJS.Util.Formatting.AddThousandSeperator(u) + " " + f
    }
}
;
SullyJS.Util.Formatting.AddThousandSeperator = function(n, t, i) {
    var u;
    if (typeof n != "undefined" && n != null) {
        (typeof i == "undefined" || i == null) && (i = 2);
        n.toString().indexOf(".") > -1 && (n = SullyJS.Util.Formatting.XDecimalPlaces(n, !1, i));
        n += "";
        for (var f = n.split("."), r = f[0], o = f.length > 1 ? "." + f[1] : "", e = /(\d+)(\d{3})/; e.test(r); )
            r = r.replace(e, "$1,$2");
        return u = r + o,
        n > 0 && typeof t != "undefined" && t == !0 && (u = "+" + u),
        u
    }
}
;
SullyJS.Util.Formatting.XDecimalPlaces = function(n, t, i) {
    if (typeof n != "undefined" && n != null) {
        (typeof i == "undefined" || i == null) && (i = 2);
        var r = n;
        return n != "" && n.toString().indexOf(",") == -1 && (r = parseFloat(n.toString()).toFixed(i)),
        n > 0 && typeof t != "undefined" && t == !0 ? "+" + r : r
    }
}
;
SullyJS.Util.Formatting.StandardNumberClass = function(n) {
    return typeof n == "undefined" || n == null ? "" : n == 0 ? "" : n < 0 ? "Negative" : "Positive"
}
;
SullyJS.Util.Formatting.AddOrdinal = function(n) {
    var t = n.toString().replace(/,/g, "");
    return n + SullyJS.Util.Formatting.GetOrdinal(t)
}
;
SullyJS.Util.Formatting.GetOrdinal = function(n) {
    n = n.toString().replace(/,/g, "");
    var t = n % 10
      , i = n % 100;
    return t == 1 && i != 11 ? "st" : t == 2 && i != 12 ? "nd" : t == 3 && i != 13 ? "rd" : "th"
}
;
SullyJS.Util.Formatting.HoursToSummaryText = function(n, t) {
    return n == null || n == -1 ? "Unknown" : n == 0 || t == "true" ? "Live now!" : n <= 23 ? n.toString() + " hours" : n < 720 ? Math.floor(n / 24) + " days" : "> 30 days"
}
;
SullyJS.Util.Formatting.ValidateNumber = function(n) {
    var t = window.event ? n.keyCode : n.which;
    return n.keyCode === 8 || n.keyCode === 46 ? !0 : t < 48 || t > 57 ? !1 : !0
}
;
SullyJS.Util.Timezone.ShowHideTimezone = function() {
    var n = $("[id*=picktimezone]");
    n.is(":visible") ? n.hide() : n.find("option").length > 0 ? SullyJS.Util.Timezone.ShowTimezone() : SullyJS.Util.Timezone.PopulateTimezonePicker()
}
;
SullyJS.Util.Timezone.ShowTimezone = function() {
    var n = $("[id*=picktimezone]");
    n.show()
}
;
SullyJS.Util.Timezone.PopulateTimezonePicker = function() {
    var n = $("[id*=picktimezone]");
    $.ajax({
        url: "/api/general/timezone/getzones",
        dataType: "json",
        ContentType: "application/json; charset=utf-8",
        success: function(t) {
            n.find("option").remove();
            for (var i = 0; i < t.zones.length; i++)
                t.zones[i].key == t.selected ? n.append("<option value='" + t.zones[i].key + "' selected>" + t.zones[i].value + "<\/option>") : n.append("<option value='" + t.zones[i].key + "'>" + t.zones[i].value + "<\/option>");
            SullyJS.Util.Timezone.ShowTimezone()
        }
    })
}
;
SullyJS.Util.SetTimezone = function() {
    $("[id*=timeZoneText]").text("Timezone (Updating)");
    $("[id*=picktimezone]").hide();
    $.ajax({
        url: "/api/general/timezone/setzone/" + $("[id*=picktimezone]").find("option:selected").val(),
        dataType: "json",
        type: "POST",
        ContentType: "application/json; charset=utf-8",
        success: function() {
            SullyJS.Util.UpdateTimezoneCompleted()
        }
    })
}
;
SullyJS.Util.UpdateTimezoneCompleted = function() {
    window.location.reload(!0)
}
;
SullyJS.Util.ShowTimezonePicker = function() {
    var t, n;
    $("[id=TimezonePickerHeader]").html("Currently selected timezone: <b>" + PageInfo.timezone + "<\/b>");
    t = $("[id=TimezonePicker]");
    t.toggle();
    n = $("[id=TimezonePickerSelect]");
    n.find("option").length <= 1 && $.ajax({
        url: "/api/general/timezone/getzones",
        dataType: "json",
        ContentType: "application/json; charset=utf-8",
        success: function(t) {
            n.find("option").remove();
            for (var i = 0; i < t.zones.length; i++)
                t.zones[i].key == t.selected ? n.append("<option value='" + t.zones[i].key + "' selected>" + t.zones[i].value + "<\/option>") : n.append("<option value='" + t.zones[i].key + "'>" + t.zones[i].value + "<\/option>");
            n.show()
        }
    })
}
;
SullyJS.Util.SetUTC = function() {
    $.ajax({
        url: "/api/general/timezone/setzone/UTC",
        dataType: "json",
        type: "POST",
        ContentType: "application/json; charset=utf-8",
        success: function() {
            window.location.reload(!0)
        }
    })
}
;
SullyJS.Util.TimezonePickerSelected = function() {
    var n = $("[id=TimezonePickerSelect]"), t;
    n.find("option:selected").val() != "" && (t = $("[id=TimezonePicker]"),
    t.toggle(),
    $.ajax({
        url: "/api/general/timezone/setzone/" + n.find("option:selected").val(),
        dataType: "json",
        type: "POST",
        ContentType: "application/json; charset=utf-8",
        success: function() {
            window.location.reload(!0)
        }
    }))
}
;
SullyJS.Util.ShowTimezonePrevent = function(n) {
    n.stopPropagation()
}
;
SullyJS.Util.ShowLanguagePicker = function() {
    var t = "All languages", n = $("[id=LanguagePickerSelect]"), i;
    n.find("option").length <= 1 && (n.find("option").remove(),
    n.append('<option value="" selected="selected">All languages<\/option>'),
    n.append('<option value="asl">American Sign Language<\/option>'),
    n.append('<option value="ar">Arabic<\/option>'),
    n.append('<option value="bg">Bulgarian<\/option>'),
    n.append('<option value="ca">Catalan<\/option>'),
    n.append('<option value="zh">Chinese<\/option>'),
    n.append('<option value="zh-HK">Chinese (Hong Kong SAR)<\/option>'),
    n.append('<option value="zh-TW">Chinese (Taiwan)<\/option>'),
    n.append('<option value="cs">Czech<\/option>'),
    n.append('<option value="da">Danish<\/option>'),
    n.append('<option value="nl">Dutch<\/option>'),
    n.append('<option value="en">English<\/option>'),
    n.append('<option value="fi">Finnish<\/option>'),
    n.append('<option value="fr">French<\/option>'),
    n.append('<option value="de">German<\/option>'),
    n.append('<option value="el">Greek<\/option>'),
    n.append('<option value="hi">Hindi<\/option>'),
    n.append('<option value="hu">Hungarian<\/option>'),
    n.append('<option value="id">Indonesian<\/option>'),
    n.append('<option value="it">Italian<\/option>'),
    n.append('<option value="ja">Japanese<\/option>'),
    n.append('<option value="ko">Korean<\/option>'),
    n.append('<option value="ms">Malaysian<\/option>'),
    n.append('<option value="no">Norwegian<\/option>'),
    n.append('<option value="other">Other<\/option>'),
    n.append('<option value="pl">Polish<\/option>'),
    n.append('<option value="pt">Portuguese<\/option>'),
    n.append('<option value="pt-BR">Portuguese (Brazil)<\/option>'),
    n.append('<option value="ro">Romanian<\/option>'),
    n.append('<option value="ru">Russian<\/option>'),
    n.append('<option value="sk">Slovak<\/option>'),
    n.append('<option value="es">Spanish<\/option>'),
    n.append('<option value="es-MX">Spanish (Mexico)<\/option>'),
    n.append('<option value="sv">Swedish<\/option>'),
    n.append('<option value="th">Thai<\/option>'),
    n.append('<option value="tr">Turkish<\/option>'),
    n.append('<option value="uk">Ukrainian<\/option>'),
    n.append('<option value="vi">Vietnamese<\/option>'),
    n.show());
    PageInfo.language.id != "" ? (t = PageInfo.language.displayName,
    n.val(PageInfo.language.twitchText)) : n.val("");
    $("[id=LanguagePickerHeader]").html("Currently showing stats for: <b>" + t + "<\/b>");
    i = $("[id=LanguagePicker]");
    i.toggle()
}
;
SullyJS.Util.LanguagePickerSelected = function() {
    var n = $("[id=LanguagePickerSelect]")
      , t = $("[id=LanguagePicker]");
    t.toggle();
    SullyJS.Util.Url.ReplaceQueryStringAndReload("language", n.find("option:selected").val())
}
;
SullyJS.Util.ShowLanguagePrevent = function(n) {
    n.stopPropagation()
}
;
redirectOccuring = !1;
SullyJS.Util.Url.ValidateRange = function(n) {
    return n == null || n == "" ? PageInfo.sitedefaultdayrange : n
}
;
SullyJS.Util.Url.UrlEncode = function(n) {
    return n = n.replace(/\//g, " "),
    n = n.replace(/\\/g, " "),
    encodeURIComponent(n)
}
;
SullyJS.Util.Url.RedirectToUrl = function(n) {
    redirectOccuring == !1 && (redirectOccuring = !0,
    window.location.href = n)
}
;
SullyJS.Util.Url.ReplaceUrlStateUnlogged = function(n, t) {
    SullyJS.Util.Url.ReplaceUrlState(n, t, !0)
}
;
SullyJS.Util.Url.ReplaceUrlState = function(n, t, i) {
    try {
        history.replaceState({}, t, n)
    } catch (r) {}
    (i == null || i != !0) && SullyJS.Util.Url.UpdateAnalyticsCode(n)
}
;
SullyJS.Util.Url.PushUrlState = function(n, t, i) {
    try {
        history.pushState({}, t, n)
    } catch (r) {}
    (i == null || i != !0) && SullyJS.Util.Url.UpdateAnalyticsCode(n)
}
;
SullyJS.Util.Url.GetExperiment = function() {
    return ""
}
;
SullyJS.Util.Url.GetExperimentVarient = function() {
    return 0
}
;
SullyJS.Util.Url.IsRedirectExperiment = function() {
    try {
        if (SullyJS.Util.Url.GetExperiment() == "8J4RyjeeT_CaKR3nCOHjug" && SullyJS.Util.Url.GetExperimentVarient() == "1")
            return !0
    } catch (n) {}
    return !1
}
;
SullyJS.Util.Url.GetTimeCode = function() {
    return PageInfo.timecode.toString()
}
;
SullyJS.Util.Url.ReplaceQueryStringAndReload = function(n, t) {
    var i = window.location.href
      , r = new RegExp("([?&])" + n + "=.*?(&|$)","i")
      , f = i.indexOf("?") !== -1 ? "&" : "?"
      , u = "";
    u = i.match(r) ? t == "" ? i.replace(r, "") : i.replace(r, "$1" + n + "=" + t + "$2") : i + f + n + "=" + t;
    window.location = u
}
;
var sc_project = sg_a_scProject
  , sc_invisible = 1
  , sc_security = sg_a_scCode
  , scJsHost = "https:" == document.location.protocol ? "https://secure." : "http://www.";
SullyJS.Util.Url.AddAnalyticsCode = function() {
    var n, i, t;
    if (ShowAnalytics == !0) {
        try {
            n = SullyJS.Util.Url.GetExperiment();
            n != "" && (i = SullyJS.Util.Url.GetExperimentVarient(),
            cxApi.setChosenVariation(i, n))
        } catch (r) {}
        try {
            (function(n, t, i, r, u, f, e) {
                n.GoogleAnalyticsObject = u;
                n[u] = n[u] || function() {
                    (n[u].q = n[u].q || []).push(arguments)
                }
                ;
                n[u].l = 1 * new Date;
                f = t.createElement(i);
                e = t.getElementsByTagName(i)[0];
                f.async = 1;
                f.src = r;
                e.parentNode.insertBefore(f, e)
            }
            )(window, document, "script", "//www.google-analytics.com/analytics.js", "ga");
            ga("create", sg_a_gaCode, "auto");
            ga("send", "pageview")
        } catch (r) {}
        try {
            t = $.extend(t || {}, {
                dataType: "script",
                cache: !1,
                url: "https://secure.statcounter.com/counter/counter.js"
            });
            $.ajax(t)
        } catch (r) {}
    }
}
;
SullyJS.Util.Url.UpdateAnalyticsCode = function(n) {
    if (ShowAnalytics == !0) {
        $("[id*=scTrackingBlock]").remove();
        try {
            n != null ? ga("send", "pageview", n) : ga("send", "pageview")
        } catch (i) {}
        try {
            var t = $.extend(t || {}, {
                dataType: "script",
                cache: !1,
                url: "https://secure.statcounter.com/counter/counter.js"
            });
            $.ajax(t)
        } catch (i) {}
    }
}
;
SullyJS.Util.Formatting.FirstDataDateUTC = new Date(Date.UTC(2015, 8, 1, 0, 0, 0));
SullyJS.Util.Formatting.FirstDataDateId = 348;
SullyJS.Util.Formatting.MonthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
SullyJS.Util.Formatting.DayNames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
SullyJS.Util.Formatting.MonthNamesShort = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
SullyJS.Util.Formatting.DayNamesShort = ["Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"];
SullyJS.Util.Dates.DisplayUTCAsText = function(n) {
    return SullyJS.Util.Formatting.DayNamesShort[n.getUTCDay()] + " " + n.getUTCDate() + " " + SullyJS.Util.Formatting.MonthNamesShort[n.getUTCMonth()] + " " + (n.getUTCFullYear() - 2e3)
}
;
SullyJS.Util.Dates.CreateUtcDate = function(n) {
    var t = new Date(0);
    return t.setUTCSeconds(n),
    t
}
;
SullyJS.Util.Dates.AddDays = function(n, t) {
    var i = new Date(n);
    return i.setDate(i.getDate() + t),
    i
}
;
SullyJS.Util.Dates.ConvertLocalToUTC = function(n, t) {
    var i = new Date(n.getUTCFullYear(),n.getUTCMonth(),n.getUTCDate(),n.getUTCHours(),n.getUTCMinutes(),n.getUTCSeconds());
    return i.setHours(i.getUTCHours() - t),
    i
}
;
SullyJS.Util.Dates.CheckIfDatesOverlap = function(n, t, i, r) {
    return n <= i && i <= t ? !0 : n <= r && r <= t ? !0 : i < n && t < r ? !0 : !1
}
;
SullyJS.Util.Dates.CreateParseableDate = function(n) {
    var i = n.getUTCMonth() + 1, t;
    return i < 10 && (i = "0" + i),
    t = n.getUTCDate(),
    t < 10 && (t = "0" + t),
    n.getUTCFullYear() + "-" + i + "-" + t
}
;
SullyJS.Util.Dates.ConvertUTCTimeToLocalTime = function(n) {
    var t = new Date(n)
      , i = t.getTimezoneOffset() / 60;
    return t.setHours(t.getHours() + i),
    t
}
;
SullyJS.Util.Dates.GetMonthYearText = function(n, t) {
    return t == 0 ? n.toString() : SullyJS.Util.Formatting.MonthNames[t - 1] + " " + n.toString()
}
;
SullyJS.Util.Dates.GetTimeAMPM = function(n) {
    var t = n.getHours()
      , i = n.getMinutes()
      , r = t >= 12 ? "pm" : "am";
    return t = t % 12,
    t = t ? t : 12,
    i = i < 10 ? "0" + i : i,
    t + ":" + i + " " + r
}
;
SullyJS.Util.Dates.GetTimeAMPMUTC = function(n) {
    var t = n.getUTCHours()
      , i = n.getUTCMinutes()
      , r = t >= 12 ? "pm" : "am";
    return t = t % 12,
    t = t ? t : 12,
    i = i < 10 ? "0" + i : i,
    t + ":" + i + " " + r
}
;
SullyJS.Util.Dates.HourString = function(n) {
    return n < 10 ? "0" + n + ":00" : n + ":00"
}
;
SullyJS.Util.Panels.PickPanel = function(n, t, i) {
    var r, u;
    panels.current[n].index = t;
    panels.current[n].autotime = (new Date).getTime();
    r = 1e3;
    i ? ($("[data-panelgroup=" + n + "group]").hide(),
    $("[data-panelindex=" + t + "index]").show()) : $("[data-panelgroup=" + n + "group]").fadeOut(r).promise().done(function() {
        $("[data-panelindex=" + t + "index]").fadeIn(r)
    });
    i || (u = panels.current[n].autotime,
    setTimeout(function() {
        SullyJS.Util.Panels.AutoSwitch(n, u)
    }, panels.current[n].autotimeout))
}
;
SullyJS.Util.Panels.AutoSwitch = function(n, t) {
    if (panels.current[n].autotime == t) {
        var i = panels.current[n].index + 1;
        i >= panels.current[n].panels && (i = 0);
        SullyJS.Util.Panels.PickPanel(n, i, !1)
    }
}
;
SullyJS.Util.RangeInfo = function(n) {
    var t = {};
    return (n == null || n == "") && (n = PageInfo.rangeInfo.sitedefaultdayrange),
    t.range = n,
    t.isNamedRange = !1,
    n == 3 || n == 7 || n == 14 || n == 30 || n == 90 || n == 180 || n == 365 ? (t.dayRange = n,
    t.dayRangeOffset = 0,
    t.rangeName = n.toString()) : (t.dayRange = 0,
    t.dayRangeOffset = 0,
    t.isNamedRange = !0,
    t.rangeName = n.length == 4 ? n.toString() : n.substring(0, 4) + " " + n.substring(4, n.length - 4)),
    t
}
;
SullyJS.Search.OptionsOpen = !1;
SullyJS.Search.OptionsChannels = !0;
SullyJS.Search.OptionsGames = !0;
SullyJS.Search.OptionsCommunities = !1;
SullyJS.Search.OptionsTeams = !0;
appendUrlForSearchDefault = "";
appendUrlForSearchFull = "";
SullyJS.Search.CreateSearchCombo = function() {
    var i, n, t;
    PageInfo != null && PageInfo.dayRange != null && (i = PageInfo.sitedefaultdayrange,
    n = PageInfo.dayRange,
    n > 0 && i != n && (appendUrlForSearchDefault = "/" + n),
    t = "",
    PageInfo.rangeInfo != null && PageInfo.rangeInfo.isNamedRange ? (t = PageInfo.rangeInfo.range,
    appendUrlForSearchFull = "/" + t) : appendUrlForSearchFull = appendUrlForSearchDefault);
    $("[id*=HeaderChannelGameSearch]").autocomplete({
        source: function(n, t) {
            $.ajax({
                url: "/api/standardsearch/" + n.term + "/" + SullyJS.Search.OptionsGames + "/" + SullyJS.Search.OptionsChannels + "/" + SullyJS.Search.OptionsCommunities + "/" + SullyJS.Search.OptionsTeams,
                dataType: "json",
                ContentType: "application/json; charset=utf-8",
                success: function(n) {
                    t(n)
                }
            })
        },
        selectFirst: !0,
        minLength: 2,
        delay: 0,
        autoFocus: !0,
        select: function(n, t) {
            return t == null || t.item == null || isNaN(t.item.value) || t.item.value == 0 || ($("[id*=HeaderChannelGameSearch]").text(t.item.displaytext),
            window.location.href = t.item.itemtype == 1 ? "/channel/" + t.item.siteurl + appendUrlForSearchFull : t.item.itemtype == 3 ? "/community/" + t.item.siteurl + appendUrlForSearchDefault : t.item.itemtype == 4 ? "/team/" + t.item.siteurl + appendUrlForSearchDefault : "/game/" + t.item.siteurl + appendUrlForSearchDefault),
            !1
        },
        position: {
            my: "left+0 top+0"
        },
        focus: function(n) {
            n.preventDefault()
        },
        close: function() {}
    }).autocomplete("instance")._renderItem = function(n, t) {
        var i = t.displaytext;
        return i.length > 30 && (i = i.substring(0, 30) + "..."),
        t.itemtype == 1 ? $("<li class='SearchItemChannel'>").append("<div class='SearchItemInner' title='" + t.displaytext + " (Channel)'><a href='/channel/" + t.siteurl + appendUrlForSearchFull + "'><img src='" + t.boxart + "' class='SearchItemBoxArt'><\/img><span class='SearchItemText'>" + i + "<\/span><\/a><a href='/channel/" + t.siteurl + appendUrlForSearchFull + "' style='float:right; padding-right:3px'><img class='SearchItemRightText SearchItemTypeLogo' src='/images/sidebar/tv.svg'><\/img><\/a><\/div>").appendTo(n) : t.itemtype == 3 ? $("<li class='SearchItemCommunity'>").append("<div class='SearchItemInner' title='" + t.displaytext + " (Community)'><a href='/community/" + t.siteurl + appendUrlForSearchDefault + "'><img src='" + t.boxart + "' class='SearchItemBoxArt'><\/img><span class='SearchItemText'>" + i + "<\/span><\/a><a href='/community/" + t.siteurl + appendUrlForSearchDefault + "' style='float:right; padding-right:3px'><img class='SearchItemRightText SearchItemTypeLogo' src='/images/sidebar/user-plus.svg'><\/img><\/a><\/div>").appendTo(n) : t.itemtype == 4 ? $("<li class='SearchItemTeam'>").append("<div class='SearchItemInner' title='" + t.displaytext + " (Team)'><a href='/team/" + t.siteurl + appendUrlForSearchDefault + "'><img src='" + t.boxart + "' class='SearchItemBoxArt'><\/img><span class='SearchItemText'>" + i + "<\/span><\/a><a href='/team/" + t.siteurl + appendUrlForSearchDefault + "' style='float:right; padding-right:3px'><img class='SearchItemRightText SearchItemTypeLogo' src='/images/sidebar/users.svg'><\/img><\/a><\/div>").appendTo(n) : $("<li class='SearchItemGame'>").append("<div class='SearchItemInner' title='" + t.displaytext + " (Game)'><a href='/game/" + t.siteurl + appendUrlForSearchDefault + "'><img src='" + t.boxart + "' class='SearchItemBoxArt'><\/img><span class='SearchItemText'>" + i + "<\/span><a href='/game/" + t.siteurl + appendUrlForSearchFull + "' style='float:right; padding-right:3px'><img class='SearchItemRightText SearchItemTypeLogo' src='/images/sidebar/gamepad.svg'><\/img><\/a><\/div>").appendTo(n)
    }
    ;
    $("#SearchInput").focus(function() {
        $(this).autocomplete("search")
    });
    $("#SearchInput").keypress(function(n) {
        var t = n.keyCode ? n.keyCode : n.which;
        if (t == 13)
            return !1
    })
}
;
SullyJS.Search.CreateSearchCombo.ToggleOptions = function() {
    SullyJS.Search.OptionsOpen = !SullyJS.Search.OptionsOpen;
    var n = $("ul.ui-autocomplete");
    SullyJS.Search.OptionsOpen ? $("[id*=SearchToggleMenu]").show() : ($("[id*=SearchToggleMenu]").hide(),
    $("[id*=HeaderChannelGameSearch]").focus())
}
;
SullyJS.Search.CreateSearchCombo.SetSearchFilter = function(n) {
    SullyJS.Search.OptionsChannels = !1;
    SullyJS.Search.OptionsGames = !1;
    SullyJS.Search.OptionsCommunities = !1;
    SullyJS.Search.OptionsTeams = !1;
    var t = $("[id*=HeaderChannelGameSearch]");
    $(".SearchToggleMenuOptionSelected").removeClass("SearchToggleMenuOptionSelected");
    switch (n) {
    case 1:
        SullyJS.Search.OptionsChannels = !0;
        $(".SearchToggleMenuOption").filter("[data-search-item-type=1]").addClass("SearchToggleMenuOptionSelected");
        break;
    case 2:
        SullyJS.Search.OptionsGames = !0;
        $(".SearchToggleMenuOption").filter("[data-search-item-type=2]").addClass("SearchToggleMenuOptionSelected");
        break;
    case 3:
        SullyJS.Search.OptionsCommunities = !0;
        $(".SearchToggleMenuOption").filter("[data-search-item-type=3]").addClass("SearchToggleMenuOptionSelected");
        break;
    case 4:
        SullyJS.Search.OptionsTeams = !0;
        $(".SearchToggleMenuOption").filter("[data-search-item-type=4]").addClass("SearchToggleMenuOptionSelected")
    }
    SullyJS.Search.CreateSearchCombo.ToggleOptions();
    t.val.length > 0 && t.autocomplete("search");
    t.focus()
}
;
SullyJS.Search.CreateSearchCombo.SetSearchFilterLabel = function(n) {
    var t = "Filter:";
    switch (n) {
    case 1:
        t = "Channels:";
        break;
    case 2:
        t = "Games:";
        break;
    case 3:
        t = "Communities:";
        break;
    case 4:
        t = "Teams:"
    }
    $("[id*=idSearchFilterLabel]").text(t)
}
;
SullyJS.AdvancedSearch.SearchAdd = function(n, t, i) {
    for (var r, f = !1, u = 0; u < SullyJS.AdvancedSearch.Preferences.AddedItems.length; u++)
        SullyJS.AdvancedSearch.Preferences.AddedItems[u].Id == n && (f = !0);
    f == !1 && SullyJS.AdvancedSearch.Preferences.AddedItems.length < 20 && (r = {},
    r.Id = n,
    r.Name = t,
    r.Logo = i,
    SullyJS.AdvancedSearch.Preferences.AddedItems.push(r));
    SullyJS.AdvancedSearch.BuildSelectedItems()
}
;
SullyJS.AdvancedSearch.GetComparedItems = function() {
    return SullyJS.AdvancedSearch.Preferences.AddedItems
}
;
SullyJS.AdvancedSearch.GetComparedItemsSeperatedByComma = function() {
    for (var n = "", i = SullyJS.AdvancedSearch.GetComparedItems(), t = 0; t < i.length; t++)
        t > 0 && (n = n + ","),
        n = n + i[t].Id.toString();
    return n
}
;
SullyJS.AdvancedSearch.HasAddedItems = function() {
    return SullyJS.AdvancedSearch.Preferences.AddedItems.length > 0 ? !0 : !1
}
;
SullyJS.AdvancedSearch.SearchAddInitial = function(n) {
    for (var i, t = 0; t < n.length; t++) {
        var r = n[t].Id
          , f = n[t].Name
          , e = n[t].Logo
          , u = !1;
        for (t = 0; t < SullyJS.AdvancedSearch.Preferences.AddedItems.length; t++)
            SullyJS.AdvancedSearch.Preferences.AddedItems[t].Id == r && (u = !0);
        u == !1 && (i = {},
        i.Id = r,
        i.Name = f,
        i.Logo = e,
        SullyJS.AdvancedSearch.Preferences.AddedItems.push(i))
    }
    SullyJS.AdvancedSearch.BuildSelectedItems()
}
;
SullyJS.AdvancedSearch.SearchClear = function() {
    SullyJS.AdvancedSearch.Preferences.AddedItems = [];
    SullyJS.AdvancedSearch.BuildSelectedItems()
}
;
SullyJS.AdvancedSearch.SearchRemove = function(n) {
    for (var t = 0; t < SullyJS.AdvancedSearch.Preferences.AddedItems.length; t++)
        if (SullyJS.AdvancedSearch.Preferences.AddedItems[t].Id == n) {
            SullyJS.AdvancedSearch.Preferences.AddedItems.splice(t, 1);
            SullyJS.AdvancedSearch.BuildSelectedItems();
            return
        }
}
;
SullyJS.AdvancedSearch.BuildSelectedItems = function() {
    var n, t;
    if (SullyJS.AdvancedSearch.Preferences.ButtonContainer != null)
        for (SullyJS.AdvancedSearch.Preferences.ButtonContainer.children().remove(),
        n = 0; n < SullyJS.AdvancedSearch.Preferences.AddedItems.length; n++)
            t = SullyJS.Util.Formatting.MakeStringSafe(SullyJS.AdvancedSearch.Preferences.AddedItems[n].Name),
            SullyJS.AdvancedSearch.Preferences.ButtonContainer.append("<div class='SearchCompareButton'><div class= 'SearchCompareButtonLogo'> <img src='" + SullyJS.AdvancedSearch.Preferences.AddedItems[n].Logo + "' title='" + t + "' /><\/div><div class='SearchCompareButtonName'>" + t + "<\/div><div class='SearchCompareButtonAction' onclick='SullyJS.AdvancedSearch.SearchRemove(" + SullyJS.AdvancedSearch.Preferences.AddedItems[n].Id + ");return false;'><a class='SearchCompareButtonActionRemove' href='#' onclick='SullyJS.AdvancedSearch.SearchRemove(" + SullyJS.AdvancedSearch.Preferences.AddedItems[n].Id + ");return false;'>x<\/a><\/div><\/div>")
}
;
SullyJS.AdvancedSearch.SearchAddItem = function(n, t, i) {
    SullyJS.AdvancedSearch.SearchAdd(n, $(t).text(), i)
}
;
SullyJS.AdvancedSearch.SearchAddItemManually = function(n, t, i) {
    SullyJS.AdvancedSearch.SearchAdd(n, t, i)
}
;
SullyJS.AdvancedSearch.ItemSearch = function(n, t, i, r) {
    i == null && (i = 1);
    var u = !1
      , f = !1;
    i == 2 ? f = !0 : u = !0;
    SullyJS.AdvancedSearch.Preferences.ButtonContainer = t;
    SullyJS.AdvancedSearch.Preferences.SearchType = i;
    n.autocomplete({
        source: function(n, t) {
            $.ajax({
                url: "/api/standardsearch/" + n.term + "/" + u + "/" + f + "/false/false",
                dataType: "json",
                ContentType: "application/json; charset=utf-8",
                success: function(n) {
                    t(n)
                }
            })
        },
        selectFirst: !0,
        minLength: 2,
        delay: 0,
        autoFocus: !0,
        select: function(n, t) {
            n.preventDefault();
            t == null || t.item == null || isNaN(t.item.value) || t.item.value == 0 || (t.item.itemtype == 1 ? SullyJS.AdvancedSearch.SearchAdd(t.item.value, t.item.displaytext, t.item.boxart) : SullyJS.AdvancedSearch.SearchAdd(t.item.value, t.item.displaytext, t.item.boxart));
            $("[id*=compareInput]").val("");
            $("[id*=inGameSearch]").val("");
            r != null && r(t.item.value)
        },
        focus: function(n) {
            n.preventDefault()
        }
    }).autocomplete("instance")._renderItem = function(n, t) {
        return SullyJS.AdvancedSearch.Preferences.SearchType == 2 ? $("<li class='SearchItemChannel'>").append("<div class='SearchItemInner'><a href='/channel/" + t.siteurl + "'><img src='" + t.boxart + "' class='SearchItemBoxArt' onclick='SullyJS.AdvancedSearch.SearchAddItem(" + t.value + ',this,"' + +t.boxart + "\"); return false;'><\/img><span class='SearchItemText'>" + t.displaytext + "<\/span><\/a><a href='/channel/" + t.siteurl + "' style='float:right; padding-right:3px' onclick='SullyJS.AdvancedSearch.SearchAddItem(" + t.value + ",this); return false;'><div class='SearchItemRightText'>" + t.description + "<\/div><\/a><\/div>").appendTo(n) : $("<li class='SearchItemGame'>").append("<div class='SearchItemInner'><a href='/game/" + t.siteurl + "' onclick='SullyJS.AdvancedSearch.SearchAddItem(" + t.value + ',this,"' + t.boxart + "\"); return false;'><img src='" + t.boxart + "' class='SearchItemBoxArt'><\/img><span class='SearchItemText'>" + t.displaytext + "<\/span><\/a><\/div>").appendTo(n)
    }
    ;
    n.focus(function() {
        $(this).autocomplete("search")
    });
    n.keypress(function(n) {
        var t = n.keyCode ? n.keyCode : n.which;
        if (t == 13)
            return !1
    })
}
;
SullyJS.Pages.DirectoryBrowser.Url = "dbrowser";
SullyJS.Pages.DirectoryBrowser.Initialise = function() {
    var i, t, n;
    PageData.currentDayRange = PageData.dayRange;
    PageData.ClearOnLoad = !0;
    $("[id=divDirectoryDatePicker]").find("td").addClass("unselectable");
    SullyJS.Pages.DirectoryBrowser.SetDirectoryBrowserPage();
    PageData.AtBottom = !1;
    SullyJS.Pages.DirectoryBrowser.SetScroll();
    i = SullyJS.Util.Dates.CreateUtcDate(parseInt(PageData.minDate) / 1e3);
    t = SullyJS.Util.Dates.CreateUtcDate(parseInt(PageData.maxDate) / 1e3);
    i.setUTCHours(i.getUTCHours() + PageData.timezoneDiff);
    t.setUTCHours(t.getUTCHours() + PageData.timezoneDiff);
    PageData.MaxAllowedDate = t;
    PageData.MinAllowedDate = i;
    $("#directoryDatePicker").datepicker({
        minDate: i,
        maxDate: t,
        changeMonth: !0,
        changeYear: !0,
        dateFormat: "DD, d MM, yy",
        showOn: "both",
        buttonImageOnly: !0,
        buttonText: "Select date",
        buttonImage: "/lib/images/calendar-edit.png",
        onSelect: function() {
            var n = $("#directoryDatePicker").datepicker("getDate")
              , t = PageData.SelectedDate.getUTCHours()
              , i = PageData.SelectedDate.getUTCMinutes()
              , r = new Date(Date.UTC(n.getFullYear(), n.getMonth(), n.getDate(), t, i, 0, 0));
            SullyJS.Pages.DirectoryBrowser.SetSelectedDate(r)
        }
    });
    n = t;
    PageData.startDate != "" && (n = SullyJS.Util.Dates.CreateUtcDate(parseInt(PageData.startDate) / 1e3),
    n.setUTCHours(n.getUTCHours() + PageData.timezoneDiff),
    PageData.startDate = n);
    SullyJS.Pages.DirectoryBrowser.SetSelectedHour(n.getHours(), n.getMinutes());
    $("#directoryDatePicker").datepicker("setDate", n);
    SullyJS.Pages.DirectoryBrowser.SetSelectedDate(n)
}
;
SullyJS.Pages.DirectoryBrowser.SetDirectoryBrowserPage = function() {
    return
}
;
SullyJS.Pages.DirectoryBrowser.UpdateTitle = function() {
    var n = "Twitch directory archive - ", i = $("[id*=divDirectoryBrowserSubHeader"), r, t;
    PageData.id != 0 ? (n = PageData.name + ", directory archive - ",
    r = SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrlGames(PageData.SelectedDate),
    i.empty().append("<a href='" + r + "'>Show all games<\/a><span class='LeftPaddingSpan'> (Currently showing channels for " + PageData.name + ")<\/span>"),
    i.show()) : (i.empty().append("<span>Showing games<\/span>"),
    i.show());
    PageData.SelectedDate != null && (t = PageData.SelectedDate,
    n += SullyJS.Util.Formatting.DayNames[t.getUTCDay()],
    n += " " + SullyJS.Util.Formatting.AddOrdinal(t.getUTCDate()),
    n += " " + SullyJS.Util.Formatting.MonthNames[t.getUTCMonth()],
    n += " " + SullyJS.Util.Dates.GetTimeAMPMUTC(t));
    SullyJS.Pages.SetPageTitle(n)
}
;
SullyJS.Pages.DirectoryBrowser.ClearData = function() {
    $("#divDirectoryBrowser").empty()
}
;
SullyJS.Pages.DirectoryBrowser.GetGames = function(n, t) {
    if (PageData.AtBottom != !0) {
        PageData.Processing = !0;
        PageData.BrowserDate = n;
        PageData.PageIndex = t;
        var i = n;
        i.setUTCHours(i.getUTCHours() - PageData.timezoneDiff);
        PageData.id == 0 ? $.ajax({
            type: "GET",
            "async": "true",
            url: "/api/general/" + SullyJS.Pages.DirectoryBrowser.Url + "/getgames/" + i.toISOString() + "/" + PageData.PageSize + "/" + t + "?tc=" + SullyJS.Util.Url.UrlEncode(PageInfo.timecode),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            processData: !0,
            success: function(n) {
                SullyJS.Pages.DirectoryBrowser.AddGames(n)
            }
        }) : $.ajax({
            type: "GET",
            "async": "true",
            url: "/api/general/" + SullyJS.Pages.DirectoryBrowser.Url + "/getgamechannels/" + i.toISOString() + "/" + PageData.PageSize + "/" + t + "/" + PageData.id + "?tc=" + SullyJS.Util.Url.UrlEncode(PageInfo.timecode),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            processData: !0,
            success: function(n) {
                SullyJS.Pages.DirectoryBrowser.AddGameChannels(n)
            }
        })
    }
}
;
SullyJS.Pages.DirectoryBrowser.AddGames = function(n) {
    var r, i;
    for (PageData.ClearOnLoad && (SullyJS.Pages.DirectoryBrowser.ClearData(),
    PageData.ClearOnLoad = !1),
    r = $("#divDirectoryBrowser"),
    i = 0; i < n.data.length; i++) {
        var t = n.data[i]
          , u = SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrlGameChannels(PageData.BrowserDate, t.url)
          , f = "<div class='DirectoryBrowserGameContainer'><a href='" + u + "'><img src='" + t.logo + "' data-isgamelink='' data-gamename='View channels streaming " + t.name + "'><\/img><\/a><div class='DirectoryBrowserText'>" + t.name + "<\/div>            <div class='DirectoryBrowserLabel'>Viewers:<\/div><div class='DirectoryBrowserValue'>" + SullyJS.Util.Formatting.AddThousandSeperator(t.viewers) + "<\/div><br/>            <div class='DirectoryBrowserLabel'> Channels:<\/div><div class='DirectoryBrowserValue'>" + SullyJS.Util.Formatting.AddThousandSeperator(t.channels) + "<\/div><\/div>";
        r.append(f)
    }
    PageData.PageSize > n.data.length && (PageData.AtBottom = !0);
    PageData.Processing = !1;
    SullyJS.Pages.CreateToolTips()
}
;
SullyJS.Pages.DirectoryBrowser.AddGameChannels = function(n) {
    var u, i, t, r;
    for (PageData.ClearOnLoad && (SullyJS.Pages.DirectoryBrowser.ClearData(),
    PageData.ClearOnLoad = !1),
    u = $("#divDirectoryBrowser"),
    i = 0; i < n.data.length; i++)
        t = n.data[i],
        r = "<div class='DirectoryBrowserGameContainer'><a href='/channel/" + t.url + "'><img src='" + t.logo + "' data-ischannellink='' data-channelname='View detailed stats for " + t.displayname + "' class='DirectoryBrowserImage'><\/img><\/a><div class='DirectoryBrowserText'>" + t.displayname + "<\/div>            <div class='DirectoryBrowserLabelLarge'>Viewers:<\/div><div class='DirectoryBrowserValue'>" + SullyJS.Util.Formatting.AddThousandSeperator(t.viewers) + "<\/div><br/>            <div class='DirectoryBrowserLabelLarge' title='Followers gained in the previous 15 minutes'>Follower gain:<\/div><div class='DirectoryBrowserValue " + SullyJS.Util.Formatting.StandardNumberClass(t.followersgained) + "' title='Followers gained in the previous 15 minutes'>" + SullyJS.Util.Formatting.AddThousandSeperator(t.followersgained, !0) + "<\/div>",
        r += "<\/div > ",
        u.append(r);
    PageData.PageSize > n.data.length && (PageData.AtBottom = !0);
    PageData.Processing = !1;
    SullyJS.Pages.CreateToolTips()
}
;
SullyJS.Pages.DirectoryBrowser.SetScroll = function() {
    PageData.Processing = !1;
    $(document).ready(function() {
        $(document).scroll(function() {
            if (PageData.Processing)
                return !1;
            $(window).scrollTop() >= $(document).height() - $(window).height() - 100 && SullyJS.Pages.DirectoryBrowser.GetGames(PageData.BrowserDate, PageData.PageIndex + 1)
        })
    })
}
;
SullyJS.Pages.DirectoryBrowser.SetSelectedDate = function(n) {
    n = SullyJS.Pages.DirectoryBrowser.KeepTimeValid(n);
    SullyJS.Pages.DirectoryBrowser.SetSelectedHour(n.getUTCHours(), n.getUTCMinutes());
    var t = new Date(n.getUTCFullYear(),n.getUTCMonth(),n.getUTCDate(),0,0,0,0);
    $("#directoryDatePicker").datepicker("setDate", t);
    SullyJS.Pages.DirectoryBrowser.GetNewData(n);
    SullyJS.Util.Url.ReplaceUrlState(SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl(n), SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl(n));
    $(".PageSubHeaderTabFirst").find("a").attr("href", "/" + SullyJS.Pages.DirectoryBrowser.Url + "/" + n.getTime());
    $(".PageSubHeaderTabFirst").attr("onclick", 'SullyJS.Util.Url.RedirectToUrl("/' + SullyJS.Pages.DirectoryBrowser.Url + "/" + n.getTime() + '")')
}
;
SullyJS.Pages.DirectoryBrowser.SetSelectedHour = function(n, t) {
    $("[id*=tblDirectoryTimePicker]").find("td").removeClass("DirectoryBrowserCalendarSelected");
    $("[id*=tblDirectoryTimePicker]").find("[hour=" + n + "][min=" + t + "]").addClass("DirectoryBrowserCalendarSelected")
}
;
SullyJS.Pages.DirectoryBrowser.KeepTimeValid = function(n) {
    return n < PageData.MinAllowedDate && (n = PageData.MinAllowedDate),
    n > PageData.MaxAllowedDate && (n = new Date(PageData.MaxAllowedDate.getTime())),
    n
}
;
SullyJS.Pages.DirectoryBrowser.SelectHour = function(n, t) {
    var i = $("#directoryDatePicker").datepicker("getDate")
      , r = new Date(Date.UTC(i.getFullYear(), i.getMonth(), i.getDate(), parseInt(n), parseInt(t), 0, 0));
    SullyJS.Pages.DirectoryBrowser.SetSelectedDate(r)
}
;
SullyJS.Pages.DirectoryBrowser.GetNewData = function(n) {
    PageData.AtBottom = !1;
    PageData.Processing = !1;
    PageData.ClearOnLoad = !0;
    SullyJS.Pages.DirectoryBrowser.CheckAllowedRange();
    PageData.PageSize = 100;
    PageData.SelectedDate = n;
    SullyJS.Pages.DirectoryBrowser.UpdateTitle();
    SullyJS.Pages.DirectoryBrowser.GetGames(n, 0)
}
;
SullyJS.Pages.DirectoryBrowser.SelectHourByLink = function(n) {
    SullyJS.Pages.DirectoryBrowser.SelectHour($(n).attr("hour"), $(n).attr("min"))
}
;
SullyJS.Pages.DirectoryBrowser.CheckAllowedRange = function() {
    var r = $("#directoryDatePicker").datepicker("getDate"), t;
    if (r.setHours(PageData.MaxAllowedDate.getHours()),
    $("[id*=tblDirectoryTimePicker]").find("td").prop("onclick", null).off("click").removeClass("DirectoryBrowserCalendarEnabled"),
    r.getFullYear() == PageData.MaxAllowedDate.getFullYear() && r.getMonth() == PageData.MaxAllowedDate.getMonth() && r.getDate() == PageData.MaxAllowedDate.getDate()) {
        var u = $("[id*=tblDirectoryTimePicker]").find("td")
          , n = PageData.MaxAllowedDate.getUTCHours()
          , i = PageData.MaxAllowedDate.getUTCMinutes();
        for (t = 0; t < n; t++)
            $("[id*=tblDirectoryTimePicker]").find("[hour = " + t + "]").click(function() {
                SullyJS.Pages.DirectoryBrowser.SelectHourByLink(this)
            }).addClass("DirectoryBrowserCalendarEnabled");
        for (i < 45 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=45]").addClass("DirectoryBrowserCalendarDisabled"),
        i < 30 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=30]").addClass("DirectoryBrowserCalendarDisabled"),
        i < 15 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=15]").addClass("DirectoryBrowserCalendarDisabled"),
        i >= 45 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=45]").click(function() {
            SullyJS.Pages.DirectoryBrowser.SelectHourByLink(this)
        }).addClass("DirectoryBrowserCalendarEnabled"),
        i >= 30 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=30]").click(function() {
            SullyJS.Pages.DirectoryBrowser.SelectHourByLink(this)
        }).addClass("DirectoryBrowserCalendarEnabled"),
        i >= 15 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=15]").click(function() {
            SullyJS.Pages.DirectoryBrowser.SelectHourByLink(this)
        }).addClass("DirectoryBrowserCalendarEnabled"),
        i >= 0 && $("[id*=tblDirectoryTimePicker]").find("[hour = " + n + "][min=0]").click(function() {
            SullyJS.Pages.DirectoryBrowser.SelectHourByLink(this)
        }).addClass("DirectoryBrowserCalendarEnabled"),
        t = n + 1; t < 24; t++)
            $("[id*=tblDirectoryTimePicker]").find("[hour = " + t + "]").addClass("DirectoryBrowserCalendarDisabled")
    } else
        $("[id*=tblDirectoryTimePicker]").find("td").removeClass("DirectoryBrowserCalendarDisabled").addClass("DirectoryBrowserCalendarEnabled"),
        $("[id*=tblDirectoryTimePicker]").find("td").click(function() {
            SullyJS.Pages.DirectoryBrowser.SelectHourByLink(this)
        })
}
;
SullyJS.Pages.DirectoryBrowser.IncrementTime = function(n, t, i) {
    var r = new Date(PageData.SelectedDate.getTime());
    n != 0 && r.setDate(r.getDate() + n);
    t != 0 && r.setHours(r.getHours() + t);
    i != 0 && r.setMinutes(r.getMinutes() + i);
    r.setHours(r.getHours() + PageData.timezoneDiff);
    SullyJS.Pages.DirectoryBrowser.SetSelectedDate(r)
}
;
SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl = function(n) {
    return PageData.id == 0 ? SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrlGames(n) : SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrlGameChannels(n, PageData.gameUrl)
}
;
SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrlGames = function(n) {
    return n == null ? "/" + SullyJS.Pages.DirectoryBrowser.Url : "/" + SullyJS.Pages.DirectoryBrowser.Url + "/" + n.getTime()
}
;
SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrlGameChannels = function(n, t) {
    return "/" + SullyJS.Pages.DirectoryBrowser.Url + "/" + n.getTime() + "/" + t
}
;
SullyJS.Pages.DirectoryBrowser.LoadChannels = function() {
    PageData.ClearOnLoad = !0;
    PageData.id = 0;
    PageData.name = "";
    PageData.gameUrl = "";
    SullyJS.Util.Url.ReplaceUrlState(SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl(PageData.BrowserDate), SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl(PageData.BrowserDate));
    SullyJS.Pages.DirectoryBrowser.UpdateTitle();
    SullyJS.Pages.DirectoryBrowser.GetGames(PageData.BrowserDate, 0)
}
;
SullyJS.Pages.DirectoryBrowser.LoadGameChannels = function(n, t, i) {
    PageData.ClearOnLoad = !0;
    PageData.id = n;
    PageData.name = t;
    PageData.gameUrl = i;
    SullyJS.Util.Url.PushUrlState(SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl(PageData.BrowserDate), SullyJS.Pages.DirectoryBrowser.CreateFriendlyUrl(PageData.BrowserDate));
    SullyJS.Pages.DirectoryBrowser.UpdateTitle();
    SullyJS.Pages.DirectoryBrowser.GetGames(PageData.BrowserDate, 0)
}
;
SullyJS.Pages.Game.SetCompare = function() {
    var e = $("[id*=compareInput]"), o = $("[id*=compareButtons]"), i, r, u, n, t, s, f;
    if (SullyJS.AdvancedSearch.ItemSearch(e, o, 1),
    SullyJS.AdvancedSearch.HasAddedItems())
        SullyJS.AdvancedSearch.BuildSelectedItems();
    else {
        if (i = [],
        PageInfo.additionalparams == null || PageInfo.additionalparams == "")
            for (r = {},
            r.Id = PageInfo.id,
            r.Name = PageInfo.name,
            r.Logo = PageInfo.logo,
            i.push(r),
            n = 0; n < 2; n++)
                PageData.relatedGames != null && PageData.relatedGames.length > n && (t = {},
                t.Id = PageData.relatedGames[n].id,
                t.Name = PageData.relatedGames[n].name,
                t.Logo = PageData.relatedGames[n].logo,
                i.push(t));
        else
            for (u = JSON.parse(PageInfo.additionalparams),
            n = 0; n < u.length; n++)
                t = {},
                t.Id = u[n].id,
                t.Name = u[n].name,
                t.Logo = u[n].logo,
                i.push(t);
        SullyJS.AdvancedSearch.SearchAddInitial(i)
    }
    s = $("[id*=compareButtonUpdate]");
    f = $("[id*=compareUpdate]");
    f.button();
    f.show()
}
;
SullyJS.Pages.Game.UpdateCompare = function() {
    for (var r, i = SullyJS.AdvancedSearch.GetComparedItems(), t = "", n = 0; n < i.length; n++)
        t = n == 0 ? t + "" + i[n].Id : t + "_" + i[n].Id;
    r = "/game/" + PageInfo.urlpart + "/" + PageInfo.dayRange.toString() + "/compare/" + t;
    SullyJS.Util.Url.RedirectToUrl(r)
}
;
SullyJS.Pages.Game.SetCompareData = function() {}
;
SullyJS.Pages.GameLinkAttributes = ["data-viewminutes", "data-streamedminutes", "data-maxchannels"];
SullyJS.Pages.ChannelLinkAttributes = ["data-viewminutes", "data-streamedminutes", "data-maxviewers", "data-avgviewers"];
SullyJS.Pages.CommunityLinkAttributes = ["data-viewminutes", "data-streamedminutes", "data-maxviewers", "data-avgviewers", "data-channels"];
SullyJS.Pages.GameLinkAttributesFriendlyNames = ["Watch time", "Streamed time", "Peak channels"];
SullyJS.Pages.ChannelLinkAttributesFriendlyNames = ["Watch time", "Streamed time", "Peak viewers", "Average viewers"];
SullyJS.Pages.CommunityLinkAttributesFriendlyNames = ["Watch time", "Streamed time", "Peak viewers", "Average viewers", "Channels"];
SullyJS.Pages.GameLinkAttributesFormat = ["M", "M", "N"];
SullyJS.Pages.ChannelLinkAttributesFormat = ["M", "M", "N", "N"];
SullyJS.Pages.CommunityLinkAttributesFormat = ["M", "M", "N", "N", "N"];
SullyJS.Pages.CreateToolTips = function() {
    $(document).tooltip({
        show: !1,
        hide: !1,
        track: !0,
        items: "[data-ischannellink], [data-isgamelink],[data-iscommunitylink]",
        content: function() {
            var n = $(this);
            return n.is("[data-ischannellink]") ? SullyJS.Pages.CreateStandardChannelTooltip(n) : n.is("[data-isgamelink]") ? SullyJS.Pages.CreateStandardGameTooltip(n) : n.is("[data-iscommunitylink]") ? SullyJS.Pages.CreateStandardCommunityTooltip(n) : void 0
        }
    })
}
;
SullyJS.Pages.CreateStandardGameTooltip = function(n) {
    var u = "ToolTipRow", f = !1, t = "", e, r, o, s;
    for (i = 0; i < SullyJS.Pages.GameLinkAttributes.length; i++)
        e = SullyJS.Pages.GameLinkAttributes[i],
        r = n.attr(e),
        r != null && r != "" && (f = !0,
        o = SullyJS.Pages.GameLinkAttributesFriendlyNames[i],
        s = SullyJS.Pages.GameLinkAttributesFormat[i],
        r != "" && (t = t + "<div class='" + u + "'><div class='ToolTipLeft'>" + o + "<\/div><div class='ToolTipRight'>" + r + "<\/div><\/div>"));
    return t = t + "<\/div>",
    f && (u = "ToolTipRowLarge"),
    "<div class='ToolTipContainer'><div class='" + u + "'>" + n.attr("data-gamename") + "<\/div>" + t
}
;
SullyJS.Pages.CreateStandardChannelTooltip = function(n) {
    var u = "ToolTipRow", f = !1, t = "", e, r, o, s;
    for (i = 0; i < SullyJS.Pages.ChannelLinkAttributes.length; i++)
        e = SullyJS.Pages.ChannelLinkAttributes[i],
        r = n.attr(e),
        r != null && r != "" && (f = !0,
        o = SullyJS.Pages.ChannelLinkAttributesFriendlyNames[i],
        s = SullyJS.Pages.ChannelLinkAttributesFormat[i],
        r != "" && (t = t + "<div class='" + u + "'><div class='ToolTipLeft'>" + o + "<\/div><div class='ToolTipRight'>" + r + "<\/div><\/div>"));
    return t = t + "<\/div>",
    f && (u = "ToolTipRowLarge"),
    "<div class='ToolTipContainer'><div class='" + u + "'>" + n.attr("data-channelname") + "<\/div>" + t
}
;
SullyJS.Pages.CreateStandardCommunityTooltip = function(n) {
    var u = "ToolTipRow", f = !1, t = "", e, r, o, s;
    for (i = 0; i < SullyJS.Pages.CommunityLinkAttributes.length; i++)
        e = SullyJS.Pages.CommunityLinkAttributes[i],
        r = n.attr(e),
        r != null && r != "" && (f = !0,
        o = SullyJS.Pages.CommunityLinkAttributesFriendlyNames[i],
        s = SullyJS.Pages.CommunityLinkAttributesFormat[i],
        r != "" && (t = t + "<div class='" + u + "'><div class='ToolTipLeft'>" + o + "<\/div><div class='ToolTipRight'>" + r + "<\/div><\/div>"));
    return t = t + "<\/div>",
    f && (u = "ToolTipRowLarge"),
    "<div class='ToolTipContainer'><div class='" + u + "'>" + n.attr("data-communityname") + "<\/div>" + t
}
;
SullyJS.Pages.SetPageTitle = function(n, t, i) {
    var r = $("[id*=pageHeaderMiddle]");
    r.empty();
    r.append("<h1 id='pagetitleh2'>" + n + "<\/h1>");
    typeof i != "undefined" && i != null && r.append(i)
}
;
SullyJS.Pages.FilterRedirect = function() {
    return !1
}
;
SullyJS.Pages.Channel.SetCalendar = function(n) {
    n.addClass("SetMinScrollable");
    $(document).ready(function() {
        SullyJS.Calendar.CreateChannelStreams();
        SullyJS.Calendar.CreateStreamTime($("[id*=channelStreamTimeCalender]"), "Stream time", "streamtime", "m");
        SullyJS.Calendar.CreateStreamTime($("[id*=channelStreamFollowersCalender]"), "Followers gained", "followersgained", "n+");
        SullyJS.Calendar.CreateStreamTime($("[id*=channelStreamViewersCalender]"), "Average viewers", "avgviewers", "n");
        SullyJS.Calendar.CreateStreamTime($("[id*=channelStreamMaxViewersCalender]"), "Max viewers", "maxviewers", "n")
    })
}
;
SullyJS.Pages.Channel.SetCompare = function() {
    var e = $("[id*=compareInput]"), o = $("[id*=compareButtons]"), i, r, u, n, t, s, f;
    if (SullyJS.AdvancedSearch.ItemSearch(e, o, 2),
    SullyJS.AdvancedSearch.HasAddedItems())
        SullyJS.AdvancedSearch.BuildSelectedItems();
    else {
        if (i = [],
        PageInfo.additionalparams == null || PageInfo.additionalparams == "")
            for (r = {},
            r.Id = PageInfo.id,
            r.Name = PageInfo.name,
            r.Logo = PageInfo.logo,
            i.push(r),
            n = 0; n < 2; n++)
                PageData.compareRelatedChannels != null && PageData.compareRelatedChannels.length > n && (t = {},
                t.Id = PageData.compareRelatedChannels[n].id,
                t.Name = PageData.compareRelatedChannels[n].name,
                t.Logo = PageData.compareRelatedChannels[n].logo,
                i.push(t));
        else
            for (u = JSON.parse(PageInfo.additionalparams),
            n = 0; n < u.length; n++)
                t = {},
                t.Id = u[n].id,
                t.Name = u[n].name,
                t.Logo = u[n].logo,
                i.push(t);
        SullyJS.AdvancedSearch.SearchAddInitial(i)
    }
    s = $("[id*=compareButtonUpdate]");
    f = $("[id*=compareUpdate]");
    f.button();
    f.show()
}
;
SullyJS.Pages.Channel.UpdateCompare = function() {
    for (var r, i = SullyJS.AdvancedSearch.GetComparedItems(), t = "", n = 0; n < i.length; n++)
        t = n == 0 ? t + "" + i[n].Id : t + "_" + i[n].Id;
    r = "/channel/" + PageInfo.urlpart + "/" + PageInfo.dayRange.toString() + "/compare/" + t;
    SullyJS.Util.Url.RedirectToUrl(r)
}
;
SullyJS.Pages.Channel.SetCompareData = function() {}
;
SullyJS.Charts.CheckCache = function(n) {
    return SullyJS.Charts.Cache[n] != "undefined" && SullyJS.Charts.Cache[n] != null ? SullyJS.Charts.Cache[n] : null
}
;
SullyJS.Charts.AddToCache = function(n, t) {
    SullyJS.Charts.Cache[n] = t
}
;
SullyJS.Charts.SetGlobalProps = function() {
    var u, t, n, i, r;
    for (Chart.defaults.global.defaultFontFamily = "Sans-Serif 12px",
    Chart.defaults.global.legend.labels.usePointStyle = !0,
    Chart.defaults.global.plugins.datalabels.display = !1,
    SullyJS.Util.IsDarkMode() ? (Chart.defaults.global.defaultFontColor = "#CDD1D5",
    Chart.defaults.scale.gridLines.color = "#5E5E5E") : (Chart.defaults.global.defaultFontColor = "#202020",
    Chart.defaults.scale.gridLines.color = "#B6B7B8"),
    u = $("canvas"),
    t = 0; t < u.length; t++)
        if (n = u[t].Chart,
        typeof n != "undefined" && n != null && typeof n.options != "undefined" && typeof n.options.scales != "undefined") {
            if (typeof n.options.scales.xAxes != "undefined" && n.options.scales.xAxes != null)
                for (i = 0; i < n.options.scales.xAxes.length; i++)
                    n.options.scales.xAxes[i].gridLines.color = Chart.defaults.scale.gridLines.color;
            if (typeof n.options.scales.yAxes != "undefined" && n.options.scales.yAxes != null)
                for (r = 0; r < n.options.scales.yAxes.length; r++)
                    n.options.scales.yAxes[r].gridLines.color = Chart.defaults.scale.gridLines.color;
            n.update()
        }
}
;
SullyJS.Charts.GetChartDisplayDate = function(n) {
    return SullyJS.Util.Dates.DisplayUTCAsText(SullyJS.Charts.GetChartDisplayDateObject(n))
}
;
SullyJS.Charts.GetChartDisplayDateObject = function(n) {
    var t = n - SullyJS.Util.Formatting.FirstDataDateId;
    return SullyJS.Util.Dates.AddDays(SullyJS.Util.Formatting.FirstDataDateUTC, t)
}
;
SullyJS.Charts.ExpandCollapseChart = function(n, t, i) {
    var f = "ChartPanel";
    t != null && (f = t);
    var r = $(n).closest("." + f + ",.ChartPanelFullSize")
      , u = r.find("[id=chartwrappanel]")
      , o = r.height()
      , s = $(n).closest(".ChartPanel,.ChartPanelFullSize,.ChartPanelQuater,.ChartPanelWide")
      , e = s.find("[id*=canvas]")[0].Chart;
    r.attr("isexpanded") == "true" ? (r.attr("isexpanded", "false"),
    r.removeClass("ChartPanelFullSize"),
    r.addClass(f),
    $(n).text("Expand"),
    i != null && (i == "CanvasWrapWide" || i == "CanvasWrapWideShort") && u.length > 0 && (u.addClass(i),
    u.removeClass("CanvasWrap"),
    e.chart.options.maintainAspectRatio = !1,
    e.chart.update())) : (r.attr("isexpanded", "true"),
    r.addClass("ChartPanelFullSize"),
    r.removeClass(f),
    $(n).text("Collapse"),
    i != null && (i == "CanvasWrapWide" || i == "CanvasWrapWideShort") && u.length > 0 && (u.addClass("CanvasWrap"),
    u.removeClass(i),
    e.chart.options.maintainAspectRatio = !0,
    e.chart.update()));
    SullyJS.Tables.PostResizeEvent(o, 0, r, r.find("[id*=canvas]")[0])
}
;
SullyJS.Charts.SaveChart = function(n) {
    var r = $(n).closest(".ChartPanel,.ChartPanelFullSize,.ChartPanelQuater,.ChartPanelWide"), t = r.find("[id*=canvas]")[0].Chart, i;
    t.chart.options.chartArea = {};
    t.chart.options.chartArea.backgroundColor = "white";
    t.chart.update();
    i = t.chart.canvas.toDataURL("image/png");
    saveAs(i, "chart.png");
    t.chart.options.chartArea.backgroundColor = null;
    t.chart.update()
}
;
SullyJS.Charts.RestyleChart = function(n, t) {
    var h, i;
    if (t == 2) {
        var o = $(n).closest(".LineChartContainer")
          , f = o.find("[id*=canvasLineChart]")[0]
          , s = "series";
        $(n).text() == "Show offline" ? ($(n).text("Hide offline"),
        s = "linear") : $(n).text("Show offline");
        for (h in f.Chart.options.scales.xAxes)
            f.Chart.options.scales.xAxes[h].distribution = s;
        return f.Chart.update(),
        !1
    }
    var o = $(n).closest(".ChartPanel")
      , r = o.find("[id*=barStyleOption]")
      , e = r[0].regenerationOptions
      , u = 0;
    for (i = 0; i < r[0].styleOptions.length; i++)
        r[0].styleOptions[i].value == r[0].pickedStyleOption && (u = i);
    for (u += 1,
    u >= r[0].styleOptions.length && (u = 0),
    i = 0; i < e.StyleOptions.length; i++)
        e.StyleOptions[i].picked = i == u ? !0 : !1;
    SullyJS.Charts.Bar.RegenerateBarChart(e)
}
;
SullyJS.Charts.CreateWatermarkOptions = function() {
    var n = {};
    return n.image = new Image,
    n.image.src = "/images/watermark.png",
    n.alignToChartArea = !0,
    n.x = 5,
    n.y = 5,
    n.width = 140,
    n.height = 35,
    n.opacity = .8,
    n.alignX = "left",
    n.alignY = "top",
    n.position = "back",
    n
}
;
SullyJS.Charts.Line.GenerateStandardLineChart = function(n, t, i) {
    var h, u, c, o, r, l, a, s, v, y;
    if (SullyJS.Charts.SetGlobalProps(),
    h = $("[id=" + n + "]"),
    u = h.find("[id*=canvasLineChart]")[0],
    u != null) {
        for (c = u.getContext("2d"),
        typeof u.Chart != "undefined" && u.Chart != null && u.Chart.destroy(),
        t.options.tooltips.mode = "index",
        t.options.scales.yAxes[0].ticks.callback = function(n, t, i) {
            return SullyJS.Charts.Line.FormatYAxisLabel(n, t, i)
        }
        ,
        t.options.scales.yAxes.length > 1 && (t.options.scales.yAxes[1].ticks.callback = function(n, t, i) {
            return SullyJS.Charts.Line.FormatYAxisLabel(n, t, i)
        }
        ),
        t.options.scales.yAxes[0].gridLines = {},
        t.options.scales.xAxes[0].gridLines = {},
        t.options.scales.yAxes[0].gridLines.borderDash = [3, 3],
        t.options.scales.xAxes[0].gridLines.borderDash = [3, 3],
        t.options.tooltips.callbacks = {},
        t.options.tooltips.callbacks.label = function(n, t) {
            return SullyJS.Charts.Line.FormatToolTip(n, t)
        }
        ,
        t.custom.footerLabelIndex != null && t.custom.footerLabelIndex.length > 0 && (SullyJS.Charts.Line.CreateToolTipFooterIndex(t.custom),
        t.options.tooltips.callbacks.footer = function(n, i) {
            return SullyJS.Charts.Line.IndexFooterTooltips(n, i, t.custom)
        }
        ),
        t.custom.reverseTooltips == !0 && (t.options.tooltips.itemSort = function(n, t) {
            return t.datasetIndex - n.datasetIndex
        }
        ),
        t.custom.tooltiplabels != null && t.custom.tooltiplabels.length > 0 && (t.options.tooltips.callbacks.title = function(n) {
            var i = t;
            return i.custom.tooltiplabels != null && i.custom.tooltiplabels.length >= n[0].index ? i.custom.tooltiplabels[n[0].index] : ""
        }
        ),
        o = 0; o < t.data.datasets.length; o++)
            if (r = t.data.datasets[o],
            r.negative != null && r.negative.reverseNegatives === !0 && r.negative.negatives.length > 0)
                for (l = r.borderColor,
                a = r.backgroundColor,
                r.borderColor = [],
                r.backgroundColor = [],
                s = 0; s < r.data.length; s++)
                    r.negative.negatives.indexOf(s) >= 0 ? (r.borderColor.push(r.negative.borderColor),
                    r.backgroundColor.push(r.negative.backgroundColor)) : (r.borderColor.push(l),
                    r.backgroundColor.push(a));
        if (t.options.watermark = SullyJS.Charts.CreateWatermarkOptions(),
        t.options.height = "200px",
        v = new Chart(c,t),
        u.Chart = v,
        y = i[11],
        y === !0) {
            var f = h.find("[id*=ChartSlider]")[0]
              , e = $(f).find("[id*=MiddleContainer]")[0]
              , p = t.custom.range.startDayId
              , w = t.custom.range.endDayId;
            if (i[8] = 0,
            i[9] = 0,
            SullyJS.Charts.Line.SetSliderText(f, p, w),
            e.noUiSlider == null) {
                $(f).hide();
                noUiSlider.create(e, {
                    start: [t.custom.range.startDayId, t.custom.range.endDayId],
                    step: 1,
                    connect: !0,
                    behaviour: "drag",
                    range: {
                        min: SullyJS.Util.Formatting.FirstDataDateId,
                        max: i[12]
                    }
                });
                e.noUiSlider.on("change", function(n) {
                    SullyJS.Charts.Line.UpdateChart(u.Chart, f, n, i, e.noUiSlider)
                });
                e.noUiSlider.on("set", function(n) {
                    SullyJS.Charts.Line.UpdateChart(u.Chart, f, n, i, e.noUiSlider)
                });
                e.noUiSlider.on("update", function(n) {
                    SullyJS.Charts.Line.SetSliderText(f, n[0], n[1])
                })
            }
            $(f).show()
        }
        u.Chart.Updating = !1
    }
}
;
SullyJS.Charts.Line.UpdateChart = function(n, t, i, r, u) {
    if (n && n.Updating != !0) {
        n.Updating = !0;
        var f = i[1] - i[0]
          , e = r[12] - i[1];
        f == 0 && (f = 1,
        i[0] = i[0] - 1);
        SullyJS.Charts.Line.SetSliderText(t, i[0], i[1]);
        SullyJS.Charts.Line.CreateLineChart(r[0], r[1], f, -e, r[4], r[5], r[6], r[7], r[8], r[9], r[10], r[11], r[12], r[13]);
        u.options.start[0] = i[0];
        u.options.start[1] = i[1]
    }
}
;
SullyJS.Charts.Line.ShowSliderCalendar = function(n, t) {
    var r = $(n).closest("[id*=ChartSlider]").find("[id*=MiddleContainer]")[0].noUiSlider, u = r.options.range.min, f = r.options.range.max, s = SullyJS.Charts.GetChartDisplayDateObject(u), h = SullyJS.Charts.GetChartDisplayDateObject(f), e = r.options.start[0], o = r.options.start[1], c = SullyJS.Charts.GetChartDisplayDateObject(e), l = SullyJS.Charts.GetChartDisplayDateObject(o), i;
    if (t == "Start") {
        i = $(n).closest("[id*=ChartSlider]").find("[id*=inSliderLeft]");
        i.datepicker("destroy");
        i.datepicker({
            minDate: s,
            maxDate: h,
            changeMonth: !0,
            changeYear: !0
        }).datepicker("setDate", c).datepicker("show").on("input change", function(t) {
            SullyJS.Charts.Line.ShowSliderCalendarOnChange(n, t.target.value, "Start", u, f, e, o)
        })
    } else {
        i = $(n).closest("[id*=ChartSlider]").find("[id*=inSliderRight]");
        i.datepicker("destroy");
        i.datepicker({
            minDate: s,
            maxDate: h,
            changeMonth: !0,
            changeYear: !0
        }).datepicker("setDate", l).datepicker("show").on("input change", function(t) {
            SullyJS.Charts.Line.ShowSliderCalendarOnChange(n, t.target.value, "End", u, f, e, o)
        })
    }
}
;
SullyJS.Charts.Line.ShowSliderCalendarOnChange = function(n, t, i, r, u, f, e) {
    var h = t.split("/"), y = new Date(Date.UTC(h[2], h[0] - 1, h[1], 0, 0, 0)), c = Math.round((y - SullyJS.Util.Formatting.FirstDataDateUTC) / 864e5) + SullyJS.Util.Formatting.FirstDataDateId, o = f, s = e, l, a, v;
    i == "Start" ? o = c : s = c;
    o > s && (l = o,
    o = s,
    s = l);
    a = $(n).closest("[id*=ChartSlider]");
    v = a.find("[id*=MiddleContainer]")[0].noUiSlider;
    v.set([o, s])
}
;
SullyJS.Charts.Line.SetSliderText = function(n, t, i) {
    $(n).find("[id*=LeftText]").text(SullyJS.Charts.GetChartDisplayDate(t));
    $(n).find("[id*=RightText]").text(SullyJS.Charts.GetChartDisplayDate(i))
}
;
SullyJS.Charts.Line.CreateLineChart = function(n, t, i, r, u, f, e, o, s, h, c, l, a, v, y) {
    var p;
    (typeof e == "undefined" || e == null || e == "") && (e = " ");
    (typeof o == "undefined" || o == null || o == "") && (o = " ");
    (typeof s == "undefined" || s == null) && (s = 0);
    (typeof h == "undefined" || h == null) && (h = 0);
    (typeof c == "undefined" || c == null || c == "") && (c = " ");
    (typeof f == "undefined" || f == null || f == "") && (f = " ");
    (typeof r == "undefined" || r == null) && (r = 0);
    (typeof y == "undefined" || y == null) && (y = 0);
    p = [];
    p.push(n);
    p.push(t);
    p.push(i);
    p.push(r);
    p.push(u);
    p.push(f);
    p.push(e);
    p.push(o);
    p.push(s);
    p.push(h);
    p.push(c);
    p.push(l);
    p.push(a);
    p.push(v);
    p.push(y);
    var w = "" + t + "/" + i.toString() + "/" + r.toString() + "/" + u + "/" + encodeURIComponent(f) + "/" + e + "/" + o + "/" + s + "/" + h + "/" + c + "/" + y.toString() + "/"
      , k = SullyJS.Charts.CheckCache(w)
      , b = $("[id=" + n + "]");
    k != null ? SullyJS.Charts.Line.CreateLineChartCreated(b, k, p) : $.ajax({
        type: "GET",
        "async": "true",
        url: "/api/charts/linecharts/getconfig/" + w,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        processData: !0,
        headers: {
            Timecode: SullyJS.Util.Url.GetTimeCode()
        },
        success: function(t) {
            SullyJS.Charts.AddToCache(w, t);
            var i = $("[id=" + n + "]");
            SullyJS.Charts.Line.CreateLineChartCreated(i, t, p)
        }
    });
    v == 2 && SullyJS.Charts.Line.ConfigureStyleOption(b, v);
    b.show()
}
;
SullyJS.Charts.Line.CreateLineChartCreated = function(n, t, i) {
    n[0] != null && (SullyJS.Charts.Line.SetChartLegend(n, t),
    SullyJS.Charts.Line.GenerateStandardLineChart(n[0].id, t, i))
}
;
SullyJS.Charts.Line.FormatYAxisLabel = function(n) {
    return SullyJS.Util.Formatting.AddThousandSeperator(n)
}
;
SullyJS.Charts.Line.FormatToolTip = function(n, t) {
    var i = t.datasets[n.datasetIndex]
      , r = i.label;
    return (i.negative != null && i.negative.reverseNegatives === !0 && i.negative.negatives.length > 0 && i.negative.negatives.indexOf(n.index) >= 0 && (r = i.negative.label),
    typeof i != "undefined" && i.hideTooltip == !0) ? "" : typeof i != "undefined" && i.hidetooltipvalue == !0 ? " " + r : n.yLabel.toString() == "NaN" ? "" : " " + SullyJS.Util.Formatting.AddThousandSeperator(n.yLabel) + " " + r
}
;
SullyJS.Charts.Line.CreateToolTipFooterIndex = function(n) {
    for (var i = {}, t = 0; t < n.footerLabelIndex.length; t++)
        i["" + n.footerLabelIndex[t] + ""] = n.footerLabels[n.footerLabelIndex[t]];
    n.footerLabels = i
}
;
SullyJS.Charts.Line.IndexFooterTooltips = function(n, t, i) {
    if (i.enabledFooterLabels) {
        var r = i.footerLabelIndex[n[0].index];
        if (r != null)
            return i.footerLabels[r]
    }
    return ""
}
;
SullyJS.Charts.Line.FormatTitle = function() {
    return ""
}
;
SullyJS.Charts.Line.SetChartLegend = function(n, t) {
    n.find("[id*=lblChartTitle]").text(t.custom.title);
    n.find("[id*=litRangeMinLabel]").text(t.custom.minvalue);
    n.find("[id*=litRangeMaxLabel]").text(t.custom.maxvalue);
    t.custom.description != null && t.custom.description != "" ? (n.find("[id*=pnlChartDescription]").css("display", "inline"),
    n.find("[id*=litDescriptionLabel]").text(t.custom.description)) : n.find("[id*=pnlChartDescription]").hide();
    (t.custom.maxvalue == null || t.custom.maxvalue == "") && n.find("[id*=pnlChartMaxLabel]").hide();
    (t.custom.minvalue == null || t.custom.minvalue == "") && n.find("[id*=pnlChartMinLabel]").hide();
    t.custom.hideRangeRow === !0 ? (n.find(".ChartRangeRow").hide(),
    n.find(".ChartRangeRowHidden").show()) : (n.find(".ChartRangeRow").show(),
    n.find(".ChartRangeRowHidden").hide())
}
;
SullyJS.Charts.Line.ConfigureStyleOption = function(n, t) {
    var i = n.find("[id=lineStyleOption]");
    t == 2 && (i.show(),
    n.find(".ChartOptions").removeClass("ChartOptions").addClass("ChartOptionsWide"),
    n.find(".ChartTitleText").removeClass("ChartTitleText").addClass("ChartTitleTextOptions"))
}
;
SullyJS.Charts.Pie.GenerateStandardPieChart = function(n, t) {
    var r, i, u, f;
    (SullyJS.Charts.SetGlobalProps(),
    r = $("[id=" + n + "]"),
    i = r.find("[id*=canvasPieChart]")[0],
    i != null) && (u = i.getContext("2d"),
    typeof i.Chart != "undefined" && i.Chart != null && i.Chart.destroy(),
    t.options.aspectRatio = 2,
    t.options.animation.onComplete = function() {
        SullyJS.Charts.Pie.ShowSegmentLabels(this, t.options.valueformat)
    }
    ,
    t.options.hover = {},
    t.options.hover.animationDuration = 0,
    t.options.tooltips = {},
    t.options.tooltips.callbacks = {},
    t.options.tooltips.callbacks.label = function(n, i) {
        return SullyJS.Charts.Pie.FormatToolTip(n, i, t.options.valueformat)
    }
    ,
    t.options.watermark = SullyJS.Charts.CreateWatermarkOptions(),
    f = new Chart(u,t),
    i.Chart = f)
}
;
SullyJS.Charts.Pie.CreatePieChart = function(n, t, i, r, u, f, e, o, s, h, c, l) {
    var v, a, y;
    SullyJS.Charts.SetGlobalProps();
    v = $("[id=" + n + "]");
    (typeof e == "undefined" || e == null || e == "") && (e = " ");
    (typeof o == "undefined" || o == null || o == "") && (o = " ");
    (typeof s == "undefined" || s == null) && (s = 0);
    (typeof h == "undefined" || h == null) && (h = 0);
    (typeof c == "undefined" || c == null || c == "") && (c = " ");
    (typeof f == "undefined" || f == null || f == "") && (f = " ");
    (typeof l == "undefined" || l == null) && (l = 0);
    a = "" + t + "/" + i.toString() + "/" + u + "/" + encodeURIComponent(f) + "/" + e + "/" + o + "/" + s + "/" + h + "/" + c + "/" + l + "/";
    y = SullyJS.Charts.CheckCache(a);
    y != null ? SullyJS.Charts.Pie.CreatePieChartCreated(v, y) : $.ajax({
        type: "GET",
        "async": "true",
        url: "/api/charts/piecharts/getconfig/" + a,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        processData: !0,
        headers: {
            Timecode: SullyJS.Util.Url.GetTimeCode()
        },
        success: function(n) {
            SullyJS.Charts.AddToCache(a, n);
            SullyJS.Charts.Pie.CreatePieChartCreated(v, n)
        }
    })
}
;
SullyJS.Charts.Pie.CreatePieChartCreated = function(n, t) {
    n[0] != null && (n.show(),
    SullyJS.Charts.Pie.SetChartLegend(n, t),
    SullyJS.Charts.Pie.GenerateStandardPieChart(n[0].id, t))
}
;
SullyJS.Charts.Pie.FormatYAxisLabel = function(n) {
    return SullyJS.Util.Formatting.AddThousandSeperator(n)
}
;
SullyJS.Charts.Pie.FormatToolTip = function(n, t, i) {
    var r = t.datasets[n.datasetIndex].data[n.index];
    return r = i == "Q" ? SullyJS.Util.Formatting.ConvertToYearsAndDays(r * 15, !1, !1, !0) : SullyJS.Util.Formatting.AddThousandSeperator(r),
    " " + r + " " + t.labels[n.index]
}
;
SullyJS.Charts.Pie.SetChartLegend = function(n, t) {
    n.find("[id*=lblChartTitle]").text(t.custom.title);
    n.find("[id*=litRangeMinLabel]").text(t.custom.minvalue);
    n.find("[id*=litRangeMaxLabel]").text(t.custom.maxvalue);
    t.custom.description != null && t.custom.description != "" ? (n.find("[id*=pnlChartDescription]").css("display", "inline"),
    n.find("[id*=litDescriptionLabel]").text(t.custom.description)) : n.find("[id*=pnlChartDescription]").hide();
    (t.custom.maxvalue == null || t.custom.maxvalue == "") && n.find("[id*=pnlChartMaxLabel]").hide();
    (t.custom.minvalue == null || t.custom.minvalue == "") && n.find("[id*=pnlChartMinLabel]").hide()
}
;
SullyJS.Charts.Pie.ShowSegmentLabels = function(n, t) {
    var i = 5;
    chartInstance = n.chart;
    ctx = chartInstance.ctx;
    ctx.save();
    ctx.fillStyle = "black";
    ctx.textBaseline = "middle";
    ctx.textAlign = "right";
    ctx.font = "11px Sans-Serif";
    Chart.helpers.each(n.data.datasets.forEach(function(r, u) {
        var f = n.getDatasetMeta(u), e = 0, c = Math.PI / 2, o, s;
        for (var h in r.data)
            e += h;
        Chart.helpers.each(f.data.forEach(function(n, r) {
            var f = n._chart.config.data.datasets[u].data[r], v, c, y, a, w;
            if (f = t == "Q" ? SullyJS.Util.Formatting.ConvertToYearsAndDays(f * 15, !1, !1, !0) : SullyJS.Util.Formatting.AddThousandSeperator(f),
            v = n._chart.config.data.labels[n._index] + " " + f,
            l = .9 * n._model.outerRadius - n._model.innerRadius,
            o = n._model.x,
            s = n._model.y,
            c = n._model.endAngle - n._model.startAngle,
            y = c * 57.2957795,
            y > i) {
                var e = n._model.startAngle + c / 2
                  , l = n._model.outerRadius * .95
                  , h = n._model.startAngle * 57.2957795
                  , p = n._model.endAngle * 57.2957795;
                Math.abs(p - h) >= 359.99 && (e = (h + 90) / 57.2957795);
                a = [n._model.x + Math.cos(e) * l, n._model.y + Math.sin(e) * l];
                ctx.save();
                ctx.translate(a[0], a[1]);
                ctx.rotate(e);
                w = h + (p - h) / 2;
                w > 90 && (ctx.rotate(180 / 57.2957795),
                ctx.textAlign = "left");
                ctx.fillText(v, 0, 0, 200);
                ctx.restore()
            }
        }), n)
    }), n);
    ctx.restore()
}
;
SullyJS.Charts.Bar.GenerateStandardBarChart = function(n, t) {
    var u, r, f, i, e;
    if (SullyJS.Charts.SetGlobalProps(),
    u = $("[id=" + n + "]"),
    r = u.find("[id*=canvasBarChart]")[0],
    r != null) {
        for (f = r.getContext("2d"),
        typeof r.Chart != "undefined" && r.Chart != null && r.Chart.destroy(),
        t.options.hover = {},
        t.options.hover.animationDuration = 0,
        i = 0; i < t.options.scales.yAxes.length; i++)
            typeof t.options.scales.yAxes[i].ticks == "undefined" && (t.options.scales.yAxes[i].ticks = {}),
            t.options.scales.yAxes[i].ticks.callback = {},
            t.options.scales.yAxes[i].ticks.callback = t.options.scales.yAxes[i].ticks.startsWithOneOnly === !0 ? function(n, t, i) {
                if ((n.toString().startsWith("1") || n == 0) && n != 1)
                    return SullyJS.Charts.Bar.FormatYAxisLabel(n, t, i)
            }
            : function(n, t, i) {
                return SullyJS.Charts.Bar.FormatYAxisLabel(n, t, i)
            }
            ,
            t.options.scales.yAxes[i].gridLines = {},
            t.options.scales.yAxes[i].gridLines.callback = {},
            t.options.scales.yAxes[i].gridLines.borderDash = [3, 3];
        t.options.scales.xAxes[0].gridLines = {};
        t.options.scales.xAxes[0].gridLines.borderDash = [3, 3];
        t.options.tooltips.callbacks = {};
        t.options.tooltips.callbacks.label = function(n, t) {
            return SullyJS.Charts.Bar.FormatToolTip(n, t)
        }
        ;
        t.data.datasets.length > 1 && t.options.scales.yAxes == 1 && (t.options.tooltips.callbacks.footer = function(n, t) {
            var i = 0;
            return n.forEach(function(n) {
                i += t.datasets[n.datasetIndex].data[n.index]
            }),
            "Total: " + SullyJS.Util.Formatting.AddThousandSeperator(i)
        }
        );
        t.custom.tooltiplabels != null && t.custom.tooltiplabels.length > 0 && (t.options.tooltips.callbacks.title = function(n) {
            var i = t;
            return i.custom.tooltiplabels != null && i.custom.tooltiplabels.length >= n[0].index ? i.custom.tooltiplabels[n[0].index] : ""
        }
        );
        t.options.watermark = SullyJS.Charts.CreateWatermarkOptions();
        e = new Chart(f,t);
        r.Chart = e
    }
}
;
SullyJS.Charts.Bar.RegenerateBarChart = function(n) {
    SullyJS.Charts.Bar.CreateBarChart(n.ContainerId, n.ChartKey, n.DayRange, n.RangeOffset, n.Id, n.Name, n.Option1, n.Option2, n.Option3, n.Option4, n.CompareIds, n.StyleOptions, n.LanguageId)
}
;
SullyJS.Charts.Bar.CreateBarChart = function(n, t, i, r, u, f, e, o, s, h, c, l, a) {
    var v, y, k, d, p, w, b, g;
    if (SullyJS.Charts.SetGlobalProps(),
    v = {},
    v.ContainerId = n,
    v.ChartKey = t,
    v.DayRange = i,
    v.RangeOffset = r,
    v.Id = u,
    v.Name = f,
    v.Option1 = e,
    v.Option2 = o,
    v.Option3 = s,
    v.Option4 = h,
    v.CompareIds = c,
    v.StyleOptions = l,
    v.LanguageId = a,
    y = $("[id=" + n + "]"),
    (typeof e == "undefined" || e == null || e == "") && (e = " "),
    (typeof o == "undefined" || o == null || o == "") && (o = " "),
    (typeof s == "undefined" || s == null) && (s = 0),
    (typeof h == "undefined" || h == null) && (h = 0),
    (typeof c == "undefined" || c == null || c == "") && (c = " "),
    (typeof f == "undefined" || f == null || f == "") && (f = " "),
    (typeof l == "undefined" || l == null || l == "") && (l = []),
    (typeof a == "undefined" || a == null) && (a = 0),
    y.find("[id*=barStyleOption]")[0].regenerationOptions = v,
    k = 0,
    d = 0,
    l.length > 1) {
        for (p = 0; p < l.length; p++)
            (p == 0 || l[p].picked == !0) && (k = l[p].value,
            d = p);
        w = d + 1;
        w >= l.length && (w = 0);
        y.find("[id*=barStyleOption]").text(l[w].text);
        y.find("[id*=barStyleOption]").show();
        y.find(".ChartOptions").removeClass("ChartOptions").addClass("ChartOptionsWide");
        y.find(".ChartTitleText").removeClass("ChartTitleText").addClass("ChartTitleTextOptions")
    } else
        y.find("[id*=barStyleOption]").hide(),
        y.find(".ChartOptions").removeClass("ChartOptionsWide").addClass("ChartOptions"),
        y.find(".ChartTitleTextOptions").removeClass("ChartTitleTextOptions").addClass("ChartTitleText");
    y.find("[id*=barStyleOption]")[0].pickedStyleOption = k;
    y.find("[id*=barStyleOption]")[0].styleOptions = l;
    b = "" + t + "/" + i.toString() + "/" + u + "/" + encodeURIComponent(f) + "/" + e + "/" + o + "/" + s + "/" + h + "/" + c + "/" + y.find("[id*=barStyleOption]")[0].pickedStyleOption + "/" + a + "/";
    g = SullyJS.Charts.CheckCache(b);
    g != null ? SullyJS.Charts.Bar.CreateBarChartCreated(y, g) : $.ajax({
        type: "GET",
        "async": "true",
        url: "/api/charts/barcharts/getconfig/" + b,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        processData: !0,
        headers: {
            Timecode: SullyJS.Util.Url.GetTimeCode()
        },
        success: function(n) {
            SullyJS.Charts.AddToCache(b, n);
            SullyJS.Charts.Bar.CreateBarChartCreated(y, n)
        }
    })
}
;
SullyJS.Charts.Bar.CreateBarChartCreated = function(n, t) {
    n[0] != null && (n.show(),
    SullyJS.Charts.Bar.SetChartLegend(n, t),
    SullyJS.Charts.Bar.GenerateStandardBarChart(n[0].id, t))
}
;
SullyJS.Charts.Bar.FormatYAxisLabel = function(n) {
    return SullyJS.Util.Formatting.AddThousandSeperator(n)
}
;
SullyJS.Charts.Bar.FormatToolTip = function(n, t) {
    return " " + SullyJS.Util.Formatting.AddThousandSeperator(n.yLabel) + " " + t.datasets[n.datasetIndex].label
}
;
SullyJS.Charts.Bar.SetChartLegend = function(n, t) {
    n.find("[id*=lblChartTitle]").text(t.custom.title);
    n.find("[id*=litRangeMinLabel]").text(t.custom.minvalue);
    n.find("[id*=litRangeMaxLabel]").text(t.custom.maxvalue);
    t.custom.description != null && t.custom.description != "" ? (n.find("[id*=pnlChartDescription]").css("display", "inline"),
    n.find("[id*=litDescriptionLabel]").text(t.custom.description)) : n.find("[id*=pnlChartDescription]").hide();
    (t.custom.maxvalue == null || t.custom.maxvalue == "") && n.find("[id*=pnlChartMaxLabel]").hide();
    (t.custom.minvalue == null || t.custom.minvalue == "") && n.find("[id*=pnlChartMinLabel]").hide();
    t.custom.hideRangeRow === !0 ? (n.find(".ChartRangeRow").hide(),
    n.find(".ChartRangeRowHidden").show()) : (n.find(".ChartRangeRow").show(),
    n.find(".ChartRangeRowHidden").hide())
}
;
SullyJS.Charts.Bubble.HSLToRGB = function(n, t, i) {
    var u, f, e;
    if (t == 0)
        u = f = e = i;
    else {
        var o = function(n, t, i) {
            return (i < 0 && (i += 1),
            i > 1 && (i -= 1),
            i < 1 / 6) ? n + (t - n) * 6 * i : i < 1 / 2 ? t : i < 2 / 3 ? n + (t - n) * (2 / 3 - i) * 6 : n
        }
          , r = i < .5 ? i * (1 + t) : i + t - i * t
          , s = 2 * i - r;
        u = o(s, r, n + 1 / 3);
        f = o(s, r, n);
        e = o(s, r, n - 1 / 3)
    }
    return [Math.round(u * 255), Math.round(f * 255), Math.round(e * 255)]
}
;
SullyJS.Charts.Bubble.RandomColour = function() {
    var n = Math.random()
      , t = (15 + 70 * Math.random()) / 100
      , i = (65 + 10 * Math.random()) / 100;
    return SullyJS.Charts.Bubble.HSLToRGB(n, t, i)
}
;
SullyJS.Charts.Bubble.SimplifyLabel = function(n) {
    return n == "PLAYERUNKNOWN'S BATTLEGROUNDS" ? "PUBG" : n == "Unlisted on Twitch" ? "" : n
}
;
SullyJS.Charts.Bubble.SavedRandomColour = function(n, t, i) {
    var r = t.config.custom.colourLookup[n];
    return i == null && (i = 1),
    "rgba(" + r[0] + "," + r[1] + "," + r[2] + "," + i + ")"
}
;
SullyJS.Charts.Bubble.StartStopAnimation = function(n) {
    var t = SullyJS.Charts.Bubble.GetRegenOptions(n);
    t.animated != !1 && (t.animating ? SullyJS.Charts.Bubble.StopAnimatedBubbleChart(n) : SullyJS.Charts.Bubble.StartAnimatedBubbleChart(SullyJS.Charts.Bubble.GetChart(n), t, !0))
}
;
SullyJS.Charts.Bubble.SetOption = function(n, t) {
    var i = SullyJS.Charts.Bubble.GetRegenOptions(t.canvas);
    i.Option3 != n && (i.Option3 = n,
    i.animated && !i.animating && SullyJS.Charts.Bubble.EnsureAnimationData(t, i, !0))
}
;
SullyJS.Charts.Bubble.GetRegenOptions = function(n) {
    return SullyJS.Charts.Bubble.GetToggle(n)[0].regenerationOptions
}
;
SullyJS.Charts.Bubble.GetToggle = function(n) {
    var t = $(n).parents("[id=bubbleChartOverallContainer]");
    return t.find("[id=bubbleStartStopToggle]")
}
;
SullyJS.Charts.Bubble.GetChart = function(n) {
    var t = $(n).parents("[id=bubbleChartOverallContainer]");
    return t.find("[id*=canvasBubbleChart]")[0].Chart
}
;
SullyJS.Charts.Bubble.StopAnimatedBubbleChart = function(n) {
    var u = $(n).parents("[id=bubbleChartOverallContainer]"), f = SullyJS.Charts.Bubble.GetChart(n), r = SullyJS.Charts.Bubble.GetToggle(n), t = SullyJS.Charts.Bubble.GetRegenOptions(n), i;
    r.text("Start");
    i = new Date;
    t.currentAnimation = i.getTime();
    t.animating = !1
}
;
SullyJS.Charts.Bubble.StartAnimatedBubbleChart = function(n, t, i) {
    var r = new Date, f, e, u;
    t.currentAnimation = r.getTime();
    t.animating = !0;
    f = SullyJS.Charts.Bubble.GetToggle($(n.canvas));
    f.text("Pause");
    r = new Date;
    t.currentAnimation = r.getTime();
    n.config.animation.data = {};
    SullyJS.Charts.Bubble.EnsureAnimationData(n, t);
    e = t.currentAnimation;
    u = n.config.animation.updateRate;
    i == !0 && (u = 0);
    setTimeout(function() {
        SullyJS.Charts.Bubble.AnimatedBubbleChart(n, t, e)
    }, u)
}
;
SullyJS.Charts.Bubble.EnsureAnimationData = function(n, t, i) {
    for (var r, u = 0, f = 0; f <= 10; f++)
        r = t.Option3 + f,
        (n.config.animation.data[r] == null || n.config.animation.data[r] == "") && (n.config.animation.data[r] = "",
        u == 0 && (u = r));
    u > 0 && $.ajax({
        type: "GET",
        "async": "true",
        url: SullyJS.Charts.Bubble.GetChartUrl(t, u, !0),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        processData: !0,
        headers: {
            Timecode: SullyJS.Util.Url.GetTimeCode()
        },
        success: function(t) {
            for (var i in t.configs)
                n.config.animation.data[i] = t.configs[i];
            SullyJS.Charts.Bubble.StoreLookupData(n, t)
        }
    });
    i == !0 && SullyJS.Charts.Bubble.DelayedUpdateBubbleChartData(n, t)
}
;
SullyJS.Charts.Bubble.DelayedUpdateBubbleChartData = function(n, t) {
    if (n.config.animation.data[t.Option3] == null || n.config.animation.data[t.Option3] == "") {
        setTimeout(function() {
            SullyJS.Charts.Bubble.DelayedUpdateBubbleChartData(n, t)
        }, 10);
        return
    }
    SullyJS.Charts.Bubble.UpdateBubbleChartData(n, t, n.config.animation.data[t.Option3]);
    n.update()
}
;
SullyJS.Charts.Bubble.AnimatedBubbleChart = function(n, t, i) {
    var r, u;
    if (i == t.currentAnimation) {
        if (r = t.currentAnimation,
        n.config.animation.data[t.Option3 + n.config.animation.step] == "") {
            setTimeout(function() {
                SullyJS.Charts.Bubble.AnimatedBubbleChart(n, t, r)
            }, 5);
            return
        }
        if (n.config.animation.data[t.Option3 + n.config.animation.step] == null) {
            SullyJS.Charts.Bubble.EnsureAnimationData(n, t);
            setTimeout(function() {
                SullyJS.Charts.Bubble.AnimatedBubbleChart(n, t, r)
            }, 5);
            return
        }
        u = n.config.animation.data[t.Option3 + n.config.animation.step];
        t.Option3 += n.config.animation.step;
        SullyJS.Charts.Bubble.UpdateBubbleChartData(n, t, u);
        t.animating && t.Option3 >= t.Option1 && t.Option3 <= t.Option2 ? (setTimeout(function() {
            SullyJS.Charts.Bubble.AnimatedBubbleChart(n, t, r)
        }, n.config.animation.updateRate),
        t.Option3 == t.Option2 && SullyJS.Charts.Bubble.StopAnimatedBubbleChart(n.canvas)) : t.animating = !1;
        n.update();
        SullyJS.Charts.Bubble.EnsureAnimationData(n, t)
    }
}
;
SullyJS.Charts.Bubble.StoreLookupData = function(n, t) {
    for (var i in t.custom.colourLookup)
        n.config.custom.colourLookup[i] = t.custom.colourLookup[i];
    for (i in t.custom.labelLookup)
        n.config.custom.labelLookup[i] = t.custom.labelLookup[i];
    for (i in t.custom.secondLabelLookup)
        n.config.custom.secondLabelLookup[i] = t.custom.secondLabelLookup[i]
}
;
SullyJS.Charts.Bubble.UpdateBubbleChartData = function(n, t, i) {
    var u, f, a, r, e, c;
    SullyJS.Charts.Bubble.StoreLookupData(n, i);
    var o = {}
      , s = {}
      , h = {};
    for (r = 0; r < i.data.datasets[0].data.length; r++)
        u = i.data.datasets[0].data[r],
        o[u.id] = u,
        s[u.id] = u;
    if ((typeof t.tmpSkips == "undefined" || t.tmpSkips == null) && (t.tmpSkips = 0),
    i.data.datasets[0].data.length == 0) {
        if (t.Option3 += 1,
        t.tmpSkips += 1,
        t.tmpSkips > 200)
            return;
        SullyJS.Charts.Bubble.UpdateBubbleChartData(n, t, n.config.animation.data[t.Option3])
    }
    for (t.tmpSkips = 0,
    r = 0; r < n.data.datasets[0].data.length; r++)
        try {
            u = n.data.datasets[0].data[r];
            h[u.id] = u;
            typeof o[u.id] == "undefined" || o[u.id] == null ? (u.x = 0,
            u.y = 0,
            u.v = 0,
            u.vValue = 0) : (f = o[u.id],
            u.x = f.x,
            u.y = f.y,
            u.v = f.v,
            u.vValue = f.vValue,
            u.id = f.id)
        } catch (l) {
            a = l
        }
    for (r = 0; r < s.length; r++)
        (typeof h[r] == "undefined" || h[r] == null) && n.data.datasets[0].data[r] == i.data.datasets[0].data[r];
    for (r = 0; r < h.length; r++)
        (typeof s[r] == "undefined" || s[r] == null) && n.data.datasets[0].data[r] == null;
    e = $("[id=" + t.ContainerId + "]");
    t.NonSlider ? (SullyJS.Charts.Bubble.SetOption(t.Option3, n),
    SullyJS.Charts.Bubble.SetChartLegend(e, i)) : (SullyJS.Charts.Bubble.SetChartLegend(e, i),
    SullyJS.Charts.Bubble.SetSliderText(e, t.Option3),
    c = e.find("[id*=MiddleContainer]")[0].noUiSlider,
    c.set(t.Option3))
}
;
SullyJS.Charts.Bubble.GenerateStandardBubbleChart = function(n, t, i) {
    var o, r, h, u, s;
    if (SullyJS.Charts.SetGlobalProps(),
    o = $("[id=" + n + "]"),
    r = o.find("[id*=canvasBubbleChart]")[0],
    r != null) {
        for (h = r.getContext("2d"),
        typeof r.Chart != "undefined" && r.Chart != null && r.Chart.destroy(),
        t.options.hover = {},
        t.options.hover.animationDuration = 0,
        t.animation = {},
        t.animation.step = 1,
        t.animation.updateRate = 250,
        t.animation.updateSteps = 10,
        u = 0; u < t.options.scales.yAxes.length; u++)
            typeof t.options.scales.yAxes[u].ticks == "undefined" && (t.options.scales.yAxes[u].ticks = {}),
            t.options.scales.yAxes[u].ticks.callback = {},
            t.options.scales.yAxes[u].ticks.callback = function(n, t, i) {
                return SullyJS.Charts.Bubble.FormatYAxisLabel(n, t, i)
            }
            ,
            t.options.scales.yAxes[u].gridLines = {},
            t.options.scales.yAxes[u].gridLines.borderDash = [3, 3];
        if (t.options.scales.xAxes[0].gridLines = {},
        t.options.scales.xAxes[0].gridLines.borderDash = [3, 3],
        t.options.scales.xAxes[0].ticks.callback = function(n, t, i) {
            return SullyJS.Charts.Bubble.FormatXAxisLabel(n, t, i)
        }
        ,
        typeof t.options.scales.yAxes[0].ticks == "undefined" && (t.options.scales.yAxes[0].ticks = {}),
        typeof t.options.scales.xAxes[0].ticks == "undefined" && (t.options.scales.xAxes[0].ticks = {}),
        t.options.tooltips.callbacks = {},
        t.options.tooltips.callbacks.label = function(n, t) {
            return SullyJS.Charts.Bubble.FormatToolTip(r, n, t)
        }
        ,
        t.options.tooltips.callbacks.labelColor = function(n, t) {
            return SullyJS.Charts.Bubble.ToolTipColor(n, t)
        }
        ,
        t.data.datasets.length > 1 && t.options.scales.yAxes == 1 && (t.options.tooltips.callbacks.footer = function(n, t) {
            var i = 0;
            return n.forEach(function(n) {
                i += t.datasets[n.datasetIndex].data[n.index]
            }),
            "Total: " + SullyJS.Util.Formatting.AddThousandSeperator(i)
        }
        ),
        t.custom.tooltiplabels != null && t.custom.tooltiplabels.length > 0 && (t.options.tooltips.callbacks.title = function(n) {
            var i = t;
            return i.custom.tooltiplabels != null && i.custom.tooltiplabels.length >= n[0].index ? i.custom.tooltiplabels[n[0].index] : ""
        }
        ),
        t.options.elements = {},
        t.options.elements.point = {},
        t.options.elements.point.radius = function(n) {
            var i = n.dataset.data[n.dataIndex]
              , r = n.chart.width
              , u = Math.abs(i.v)
              , f = (t.options.custom.maxSize - t.options.custom.minSize) / 1e3
              , e = t.options.custom.minSize / 1e3 + f * u;
            return r * e
        }
        ,
        t.options.elements.point.backgroundColor = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return SullyJS.Charts.Bubble.SavedRandomColour(t.sid, n.chart, .75)
        }
        ,
        t.options.elements.point.borderColor = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return SullyJS.Charts.Bubble.SavedRandomColour(t.sid, n.chart)
        }
        ,
        t.options.elements.point.hoverBackgroundColor = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return SullyJS.Charts.Bubble.SavedRandomColour(t.sid, n.chart, .75)
        }
        ,
        t.options.elements.point.hoverBorderColor = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return SullyJS.Charts.Bubble.SavedRandomColour(t.sid, n.chart)
        }
        ,
        t.options.animation = {},
        t.options.animation.duration = t.animation.updateRate,
        t.options.animation.numSteps = t.animation.updateSteps,
        t.options.animation.easing = "linear",
        t.options.plugins = {},
        t.options.plugins.datalabels = {},
        t.options.plugins.datalabels.display = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return t.v > .1 ? !0 : !1
        }
        ,
        t.options.plugins.datalabels.anchor = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return t.v < 50 ? "center" : "center"
        }
        ,
        t.options.plugins.datalabels.align = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return t.v < 50 ? "center" : "center"
        }
        ,
        t.options.plugins.datalabels.color = function(n) {
            var t = n.dataset.data[n.dataIndex];
            return t.v < 50 ? n.dataset.backgroundColor : "white"
        }
        ,
        t.options.plugins.datalabels.font = function() {
            return {
                weight: "bold"
            }
        }
        ,
        t.options.plugins.datalabels.formatter = function() {
            return ""
        }
        ,
        t.options.watermark = SullyJS.Charts.CreateWatermarkOptions(),
        s = new Chart(h,t),
        r.Chart = s,
        t.options.plugins.datalabels.formatter = function(n) {
            var t = r.Chart.config.custom.labelLookup[n.id]
              , i = r.Chart.config.custom.secondLabelLookup[n.sid];
            return typeof i == "undefined" ? t : t + " (" + SullyJS.Charts.Bubble.SimplifyLabel(i) + ")"
        }
        ,
        !0 && !i.NonSlider) {
            var e = o.find("[id*=ChartSlider]")[0]
              , f = $(e).find("[id*=MiddleContainer]")[0]
              , c = i.Option1
              , l = i.Option2;
            if (f.noUiSlider == null) {
                $(e).hide();
                noUiSlider.create(f, {
                    start: c,
                    step: 1,
                    connect: !0,
                    behaviour: "tap-drag",
                    range: {
                        min: c,
                        max: l
                    }
                });
                f.noUiSlider.on("change", function(n) {
                    SullyJS.Charts.Bubble.SetOption(parseInt(n[0]), r.Chart)
                });
                f.noUiSlider.on("set", function(n) {
                    SullyJS.Charts.Bubble.SetOption(parseInt(n[0]), r.Chart)
                });
                f.noUiSlider.on("update", function(n) {
                    SullyJS.Charts.Bubble.SetSliderText(e, n[0])
                })
            }
            $(e).show()
        }
        SullyJS.Charts.Bubble.StartAnimatedBubbleChart(s, i)
    }
}
;
SullyJS.Charts.Bubble.RegenerateBubbleChart = function(n) {
    SullyJS.Charts.Bubble.CreateBubbleChart(n.ContainerId, n.ChartKey, n.DayRange, n.RangeOffset, n.Id, n.Name, n.Option1, n.Option2, n.Option3, n.Option4, n.CompareIds, n.NonSlider)
}
;
SullyJS.Charts.Bubble.GetChartUrl = function(n, t, i) {
    var c = n.DayRange, v = n.RangeOffset, l = n.Id, r = n.Name, e = n.Option1, o = n.Option2, u = n.Option3, s = n.Option4, f = n.CompareIds, a = n.ChartKey, h;
    return t != null && (u = t),
    (typeof e == "undefined" || e == null) && (e = 0),
    (typeof o == "undefined" || o == null) && (o = 0),
    (typeof u == "undefined" || u == null) && (u = 0),
    (typeof s == "undefined" || s == null) && (s = 0),
    (typeof f == "undefined" || f == null || f == "") && (f = " "),
    (typeof r == "undefined" || r == null || r == "") && (r = " "),
    h = "/api/charts/bubblecharts/getconfig/",
    i && (h = "/api/charts/bubblecharts/getconfigmulti/"),
    h + a + "/" + c.toString() + "/" + l + "/" + encodeURIComponent(r) + "/" + e + "/" + o + "/" + u + "/" + s + "/" + f
}
;
SullyJS.Charts.Bubble.CreateBubbleChart = function(n, t, i, r, u, f, e, o, s, h, c, l) {
    var a, v, y, p;
    SullyJS.Charts.SetGlobalProps();
    a = {};
    a.ContainerId = n;
    a.ChartKey = t;
    a.DayRange = i;
    a.RangeOffset = r;
    a.Id = u;
    a.Name = f;
    a.Option1 = e;
    a.Option2 = o;
    a.Option3 = s;
    a.Option4 = h;
    a.CompareIds = c;
    a.animated = !0;
    a.animating = !0;
    typeof l != "undefined" && l == !0 && (a.NonSlider = !0);
    v = $("[id=" + n + "]");
    v.find("[id*=bubbleStartStopToggle]")[0].regenerationOptions = a;
    y = SullyJS.Charts.Bubble.GetChartUrl(a);
    p = SullyJS.Charts.CheckCache(y);
    p != null ? SullyJS.Charts.Bubble.CreateBubbleChartCreated(v, p, a) : $.ajax({
        type: "GET",
        "async": "true",
        url: y,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        processData: !0,
        headers: {
            Timecode: SullyJS.Util.Url.GetTimeCode()
        },
        success: function(n) {
            SullyJS.Charts.AddToCache(y, n);
            SullyJS.Charts.Bubble.CreateBubbleChartCreated(v, n, a)
        }
    })
}
;
SullyJS.Charts.Bubble.CreateBubbleChartCreated = function(n, t, i) {
    n[0] != null && (n.show(),
    SullyJS.Charts.Bubble.SetChartLegend(n, t),
    SullyJS.Charts.Bubble.GenerateStandardBubbleChart(n[0].id, t, i))
}
;
SullyJS.Charts.Bubble.FormatYAxisLabel = function(n) {
    return SullyJS.Util.Formatting.AddThousandSeperator(n)
}
;
SullyJS.Charts.Bubble.FormatXAxisLabel = function(n) {
    return SullyJS.Util.Formatting.AddThousandSeperator(n)
}
;
SullyJS.Charts.Bubble.FormatToolTip = function(n, t, i) {
    var r = []
      , u = n.Chart.config.custom.labelLookup[i.datasets[t.datasetIndex].data[t.index].id]
      , f = n.Chart.config.custom.secondLabelLookup[i.datasets[t.datasetIndex].data[t.index].sid];
    return typeof u != "undefined" && r.push(u),
    typeof f != "undefined" && r.push(f),
    r.push(SullyJS.Util.Formatting.AddThousandSeperator(i.datasets[t.datasetIndex].data[t.index].y) + " " + i.datasets[t.datasetIndex].yLabel),
    r.push(SullyJS.Util.Formatting.AddThousandSeperator(i.datasets[t.datasetIndex].data[t.index].x) + " " + i.datasets[t.datasetIndex].xLabel),
    r.push(SullyJS.Util.Formatting.AddThousandSeperator(i.datasets[t.datasetIndex].data[t.index].vValue) + " " + i.datasets[t.datasetIndex].vLabel),
    r
}
;
SullyJS.Charts.Bubble.ToolTipColor = function(n, t) {
    var i = SullyJS.Charts.Bubble.SavedRandomColour(t.data.datasets[n.datasetIndex].data[n.index].sid, t);
    return {
        borderColor: i,
        backgroundColor: i
    }
}
;
SullyJS.Charts.Bubble.SetChartLegend = function(n, t) {
    n.find("[id*=lblChartTitle]").text(t.custom.title);
    t.custom.description != null && t.custom.description != "" ? (n.find("[id*=pnlChartDescription]").css("display", "inline"),
    n.find("[id*=litDescriptionLabel]").text(t.custom.description)) : n.find("[id*=pnlChartDescription]").hide()
}
;
SullyJS.Charts.Bubble.SetSliderText = function(n, t) {
    $(n).find("[id*=LeftText]").text(SullyJS.Charts.GetChartDisplayDate(t))
}
;
SullyJS.Charts.Bubble.ShowSliderCalendar = function(n) {
    var t = $(n).closest("[id*=ChartSlider]").find("[id*=MiddleContainer]")[0].noUiSlider
      , i = t.options.range.min
      , r = t.options.range.max
      , o = SullyJS.Charts.GetChartDisplayDateObject(i)
      , s = SullyJS.Charts.GetChartDisplayDateObject(r)
      , u = t.options.start[0]
      , f = t.options.start[1]
      , h = SullyJS.Charts.GetChartDisplayDateObject(u)
      , c = SullyJS.Charts.GetChartDisplayDateObject(f)
      , e = $(n).closest("[id*=ChartSlider]").find("[id*=inSliderLeft]");
    e.datepicker("destroy");
    e.datepicker({
        minDate: o,
        maxDate: s,
        changeMonth: !0,
        changeYear: !0
    }).datepicker("setDate", h).datepicker("show").on("input change", function(t) {
        SullyJS.Charts.Bubble.ShowSliderCalendarOnChange(n, t.target.value, "Start", i, r, u, f)
    })
}
;
SullyJS.Charts.Bubble.ShowSliderCalendarOnChange = function(n, t, i, r, u, f, e) {
    var y = new Date(t), h = SullyJS.Charts.GetChartDisplayDateObject(r), p = new Date(h.getFullYear(),h.getMonth(),h.getDate(),0,0,0), w = Math.abs(y.getTime() - p.getTime()), b = Math.ceil(w / 864e5), c = b + r, o = f, s = e, l, a, v;
    i == "Start" ? o = c : s = c;
    o > s && (l = o,
    o = s,
    s = l);
    a = $(n).closest("[id*=ChartSlider]");
    v = a.find("[id*=MiddleContainer]")[0].noUiSlider;
    v.set(o)
}
;
SullyJS.Tables.AppendDayRange = function(n, t, i) {
    return n == "/" || t == 0 || t == 7 || t == null ? n : typeof i != "undefined" && i != "channel" && t != 3 && t != 7 && t != 14 && t != 30 && t != 90 && t != 190 && t != 365 ? n : n + "/" + t
}
;
SullyJS.Tables.PostResizeEvent = function(n, t, i, r) {
    if (!(t > 5))
        if (i.height() == n)
            setTimeout(function() {
                SullyJS.Tables.PostResizeEvent(n, t + 1, i, r)
            }, 10);
        else {
            var u = i.width()
              , f = $(r);
            f.width(u)
        }
}
;
SullyJS.Tables.CleanTableQuery = function(n) {
    n.columns = null
}
;
SullyJS.Tables.ParseTableData = function(n) {
    return n
}
;
SullyJS.Tables.CreateStandardTableSettings = function(n, t) {
    var f = {
        url: n,
        data: function(n) {
            SullyJS.Tables.CleanTableQuery(n)
        },
        dataSrc: function(n) {
            var t = SullyJS.Tables.ParseTableData(n), r, i;
            for ((typeof t.progressProps == "undefined" || t.progressProps == null) && (t.progressProps = []),
            r = {},
            i = 0; i < t.progressProps.length; i++)
                r[t.progressProps[i].key] = t.progressProps[i].value;
            for (i = 0; i < t.data.length; i++)
                t.data[i].progressProps = r;
            return t.data
        }
    }, r = !1, u, i;
    try {
        u = /bot|google|baidu|bing|msn|duckduckbot|teoma|slurp|yandex/i.test(navigator.userAgent);
        u && (r = !0)
    } catch (e) {}
    return i = {},
    i.processing = !0,
    i.serverSide = !0,
    i.ajax = f,
    i.searching = !1,
    i.ordering = !1,
    i.responsive = r,
    i.details = !1,
    i.oLanguage = {},
    i.oLanguage.sProcessing = "Loading...",
    i.columns = [],
    i.bDestroy = !0,
    i.lengthMenu = PageInfo.tablePageSizes,
    i.bLengthChange = !0,
    typeof t == "undefined" || t == null ? i.iDisplayLength = SullyJS.Tables.GetDefaultPageSize() : (i.iDisplayLength = t,
    i.lengthMenu[0].includes(t) || (i.lengthMenu[0].push(t),
    i.lengthMenu[1].push(t))),
    i.oLanguage.sLengthMenu = "_MENU_",
    i.dom = '<"top TableTopMenu"Bl> rt <"bottom"ip><"clear">',
    i.columnDefs = [{
        sorting: ["desc", "asc"],
        targets: ["_all"]
    }],
    i.columnDefs.className = "dt-head-left",
    i.buttons = [{
        extend: "csv",
        className: "TableExportLinkButton"
    }, {
        extend: "excel",
        className: "TableExportLinkButton"
    }],
    i.pagingType = "simple_numbers_end_ellipses",
    i.fnServerData = function(n, t, i, r) {
        var u = new Date;
        r.jqXHR = $.ajax({
            dataType: "json",
            type: "GET",
            url: SullyJS.Tables.AppendStandardTableProps(r.ajax.url, t),
            success: i,
            headers: {
                Timecode: SullyJS.Util.Url.GetTimeCode()
            }
        })
    }
    ,
    i
}
;
SullyJS.Tables.AppendStandardTableProps = function(n, t) {
    for (var r = 0, u = "desc", f = 0, e = 0, o = 10, i = 0; i < t.length; i++)
        if (t.length > 0)
            switch (t[i].name) {
            case "draw":
                r = t[i].value;
                break;
            case "start":
                e = t[i].value;
                break;
            case "length":
                o = t[i].value;
                break;
            case "order":
                t[i].value.length > 0 && (u = t[i].value[0].dir,
                f = t[i].value[0].column)
            }
    return n + "/" + r + "/" + f + "/" + u + "/" + e + "/" + o
}
;
SullyJS.Tables.GetDefaultPageSize = function() {
    try {
        if (typeof PageInfo.defaultpagesize != "undefined" && PageInfo.defaultpagesize != null && PageInfo.defaultpagesize != "")
            return PageInfo.defaultpagesize
    } catch (n) {}
    return 50
}
;
SullyJS.Tables.InitialiseTable = function(n, t, i) {
    var r = n.find("table[id*=tblControl]")
      , u = r.DataTable(t);
    if (typeof i == "undefined" || i == !1)
        r.on("length.dt", function(n, t, i) {
            PageInfo.defaultpagesize = i
        });
    return SullyJS.Tables.InitialiseTableFixSize(n),
    u
}
;
SullyJS.Tables.InitialiseTableFixSize = function(n) {
    (navigator.userAgent.search("Chrome") < 0 || navigator.userAgent.search(" Edge") >= 0) && (n.find("[id*=tblDescrption]").addClass("DataTableDescriptionNotChrome"),
    n.find(".bottom").addClass("DataTableDescriptionNotChrome"))
}
;
SullyJS.Tables.ClearTable = function(n, t) {
    var i = null;
    return (typeof t == "undefined" || t == null) && (t = "NOTSET"),
    $.fn.DataTable.isDataTable(n.find("table[id*=tblControl]")) && (i = n.find("table[id*=tblControl]").DataTable().table(0).order(),
    n.find("table[id*=tblControl]").DataTable().destroy()),
    n.find("[id*=tblControl_info]").remove(),
    n.find("[id*=tblControl_paginate]").remove(),
    i
}
;
SullyJS.Tables.CreateStandardColumn = function(n, t, i, r, u, f, e, o, s, h) {
    var l = {}, c;
    return l.data = n,
    l.searchable = !1,
    l.name = n,
    SullyJS.Tables.CreateColumnHeader(l, t, s),
    (typeof r == "undefined" || r == null) && (r = -1),
    (typeof u == "undefined" || u == null) && (u = []),
    i != null ? (c = "",
    i == "N" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.AddThousandSeperator(s[n]);
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "M" ? l.fnCreatedCell = function(i, f, s, h, c) {
        var l = SullyJS.Util.Formatting.ConvertToYearsAndDays(s[n]).split(" ")[0];
        l = SullyJS.Tables.FormatCellValue(l, s, n);
        l += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        l += SullyJS.Tables.AppendPercentageDifference(s, n);
        l += "<\/div>";
        l = SullyJS.Tables.AppendProgressBar(i, f, s, h, c, l, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(l))
    }
    : i == "MShort" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.ConvertToYearsAndDays(s[n], !1, !0);
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "MM" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.ConvertToYearsAndDays(s[n], !1, !1, !0);
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "D" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.AddThousandSeperator(s[n]);
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "D1" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.AddThousandSeperator(s[n].toFixed(1), !1, 1);
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "D2" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.AddThousandSeperator(s[n].toFixed(2));
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "P" ? l.fnCreatedCell = function(i, f, s, h, l) {
        c = SullyJS.Util.Formatting.AddThousandSeperator(s[n].toFixed(2)) + "%";
        c = SullyJS.Tables.FormatCellValue(c, s, n);
        c += SullyJS.Tables.AppendPercentageComplete(s, e, o);
        c += SullyJS.Tables.AppendPercentageDifference(s, n);
        c += "<\/div>";
        c = SullyJS.Tables.AppendProgressBar(i, f, s, h, l, c, n, r, t, u);
        $(i).html(SullyJS.Tables.WrappedCell(c))
    }
    : i == "B" && (l.fnCreatedCell = function(t, i, r) {
        c = n == "partner" && r[n] == !0 ? f : n == "mature" && r[n] == !0 ? f : "";
        typeof h != "undefined" && h === !0 && (c = c + "<div class='TableCellCentredText'><\/div>");
        $(t).html(c)
    }
    )) : typeof h != "undefined" && h === !0 && (l.fnCreatedCell = function(t, i, r) {
        r[n] != null && $(t).html(r[n] + "<div class='TableCellCentredText'><\/div>")
    }
    ),
    l
}
;
SullyJS.Tables.CreateColumnHeader = function(n, t, i) {
    n.title = typeof i != "undefined" && i != null ? typeof i.icon != "undefined" || i.icon != null ? i.icon == "info" ? "<p title='" + i.tooltip + "'>" + t + "<img src='/images/fa/info-circle-solid.svg' class='TableHeaderIcon'><\/img><\/p>" : "<p title='" + i.tooltip + "'>" + t + "<\/p>" : "<p title='" + i.tooltip + "'>" + t + "<\/p>" : t
}
;
SullyJS.Tables.AppendPercentageComplete = function(n, t, i) {
    var u, f, r;
    return i == "undefined" || i == null || i == "" ? "" : (u = n[t],
    f = n[i],
    u == null || u == "" || u == 0 || f == null || f == "" || f == 0) ? "" : (r = f / u * 100,
    r = parseInt(r),
    r > 100 && (r = 100),
    " (" + r + " %)")
}
;
SullyJS.Tables.FormatCellValue = function(n, t, i) {
    let u = "<div class='TableCellTop'><div class='TableCellValue'>" + n + "<\/div>"
      , r = null;
    if (typeof t != "undefined" && typeof t.progressProps != "undefined" && typeof t.progressProps[i] != "undefined" && typeof t.progressProps[i] != null)
        r = t.progressProps[i];
    else
        return u;
    if (r == "undefined" || r == null || r == "")
        return u;
    var f = t[i]
      , e = r;
    return f == null || f == "" || f == 0 || e == null || e == "" || e == 0 ? u : e == f ? "<div class='TableCellTop'><div class='TableCellValue'><b>" + n + "<\/b><\/div>" : u
}
;
SullyJS.Tables.WrappedCell = function(n) {
    return "<div>" + n + "<\/div>"
}
;
SullyJS.Tables.AppendPercentageDifference = function(n, t) {
    var u, r, i;
    return typeof n["previous" + t] != "undefined" ? (u = n[t],
    r = n["previous" + t],
    u == null || r == null || u == 0) ? "" : u > r ? (i = Math.abs(u - r) / r * 100,
    i = i > 1e4 ? ">10,000" : SullyJS.Util.Formatting.AddThousandSeperator(i, !1, 1),
    "<div class='TablePrevPercent'>(" + i + "%)<img src='/images/fa/angle-up-solid-positive.svg'><\/div>") : r > u ? (i = Math.abs(r - u) / r * 100,
    i = i > 1e4 ? ">10,000" : SullyJS.Util.Formatting.AddThousandSeperator(i, !1, 1),
    "<div class='TablePrevPercent'>(-" + i + "%)<img src='/images/fa/angle-down-solid-negative.svg'><\/div>") : "" : ""
}
;
SullyJS.Tables.AppendProgressBar = function(n, t, i, r, u, f, e, o, s, h) {
    var c, l, a, v, k, w, y, b, p;
    if (o == null && (o = -1),
    c = null,
    l = "TableProgressBarPositive",
    o == 1 ? l = "TableProgressBarPositive" : o == 2 ? l = "TableProgressBarNeutral" : o == 3 ? l = "TableProgressBarNegative" : o == 4 ? l = "TableProgressBarPositive" : o == 5 && (l = "TableProgressBarPositive"),
    o > 0 && (i.progressProps[e] != "undefined" && i.progressProps[e] != null || h.length > 0)) {
        if (a = 0,
        h.length > 0)
            for (v = 0; v < h.length; v++)
                i[h[v]] != "undefined" && i[h[v]] != null && (a += i[h[v]]);
        else
            a = i.progressProps[e];
        k = !1;
        t < 0 && (k = !0,
        t = -t,
        l = "TableProgressBarNegative");
        a < 0 ? c = 0 : a < 0 || a == 0 || t == 0 ? c = 0 : t > a ? (c = 0,
        (o == 4 || o == 1) && (c = 100)) : (c = t / a * 100,
        c = c > 100 ? 100 : SullyJS.Util.Formatting.XDecimalPlaces(c),
        o == 4 && (c = 100 - c))
    }
    return o == 0 || o > 0 && c == null ? f += "<div class='TableProgressBarEmpty'><\/div>" : o > 0 && (w = "",
    w = h.length == 0 ? s + " as percentage of largest (" + c + "%)" : s + " as percentage, compare to this row (" + c + "%)",
    o == 5 && typeof i["previous" + e] != "undefined" && (y = i[e],
    b = i["previous" + e],
    y != null && b != null && y != 0 && y < b && (l = "TableProgressBarNegative")),
    p = "<div style='width:" + c + "%;' class='" + l + "' title='" + w + "'><\/div>",
    p = "<div class='TableProgressBarBackground'>" + p + "<\/div>",
    f += p),
    f
}
;
SullyJS.Tables.CreateLinkColumn = function(n, t, i, r, u, f, e, o, s, h) {
    var c = SullyJS.Tables.CreateStandardColumn(n, t);
    return c.fnCreatedCell = function(n, t, c) {
        var y = i.replace("{0}", c[r]), a, l, p, v;
        o != null && (y = y.replace("{1}", c[o]));
        a = "";
        e != null && (a = e);
        f != null && (a = a.replace("{0}", c[f]));
        l = y;
        o != null && (l = l.replace("{1}", c[o]));
        p = "";
        PageInfo.language != null && PageInfo.language.twitchText != "" && l.includes("{Append_Lan}") && (p = "?language=" + PageInfo.language.twitchText);
        l = l.replace("{Append_Lan}", "") + p;
        v = "<a href='" + l + "' title='" + a + "'>" + c[u] + "<\/a>";
        s != null && (v = "<a href='" + l + "' title='" + a + "'>" + c[u],
        h != null && (v += " " + h + " "),
        v += "<br/>",
        v += c[s],
        +"</a>");
        $(n).html(v)
    }
    ,
    c
}
;
SullyJS.Tables.CreateMultiLinkColumn = function(n, t, i) {
    var r = SullyJS.Tables.CreateStandardColumn(n, t);
    return r.fnCreatedCell = function(t, r, u) {
        for (var e = u[n].toString().split("|"), o = "", f = 0; f < e.length; f++)
            e[f] == "..." ? o += "..." : (f > 0 && (o += ", "),
            o += "<a href='" + i.replace("{0}", e[f + 1]) + "'>" + e[f] + "<\/a>"),
            f++;
        $(t).html(o)
    }
    ,
    r
}
;
SullyJS.Tables.CreateMultiImageGameLinkColumn = function(n, t, i) {
    var r = SullyJS.Tables.CreateStandardColumn(n, t);
    return r.fnCreatedCell = function(t, r, u) {
        for (var e = u[n].toString().split("|"), o = "", f = 0; f < e.length; f++)
            e[f] != "" && (o += e[f] == "..." ? "..." : "<div class='RelatedLinksItemMediumCell' style='float:left'><a href='" + i.replace("{0}", e[f + 1]) + "' data-gamename='" + e[f] + "' data-isgamelink=''><img src='" + e[f + 2] + "' ><\/img><\/a><\/div>"),
            f++,
            f++;
        $(t).html(o)
    }
    ,
    r
}
;
SullyJS.Tables.CreateIconColumn = function(n, t, i, r, u, f, e, o, s, h) {
    f == null && (f = "DataTableIcon");
    (typeof h == "undefined" || h == !1) && (h = !1);
    var c = SullyJS.Tables.CreateStandardColumn(n, t);
    return c.fnCreatedCell = function(n, t, c) {
        var v = "", l, a;
        r != "" && (v = i.replace("{0}", c[r]));
        e != null && (u = c[e]);
        l = "";
        s != null && (l = s);
        o != null && (l = l.replace("{0}", c[o]));
        a = "_self";
        h == !0 && (a = "_blank");
        r != "" ? $(n).html("<a href='" + v + "' target='" + a + "'><img class='" + f + "' src='" + u + "' title='" + l + "'><\/img><\/a>") : $(n).html("<img class='" + f + "' src='" + u + "' title='" + l + "'><\/img>")
    }
    ,
    c
}
;
SullyJS.Tables.CreatePreviewColumnShowHide = function(n, t) {
    var i = $(n).parent().find("[id=previewLarge]");
    t ? i.show() : i.hide()
}
;
SullyJS.Tables.CreatePreviewColumn = function(n, t, i, r, u) {
    u == null && (u = "DataTableIcon");
    var f = SullyJS.Tables.CreateStandardColumn(n, t);
    return f.fnCreatedCell = function(n, t, f) {
        var o = f[i]
          , s = f[r]
          , e = "";
        $(n).html("<img class='" + u + "' src='" + o + "' title='" + e + "' onmouseout='SullyJS.Tables.CreatePreviewColumnShowHide(this,false);return false;' onmouseover='SullyJS.Tables.CreatePreviewColumnShowHide(this,true);return false;'><\/img><img id='previewLarge' class='" + u + " TablePreviewImageCentered' src='" + s + "' title='" + e + "'><\/img>")
    }
    ,
    f
}
;
SullyJS.Tables.CreateBarProcess = function(n, t, i, r) {
    r == null && (r = "DataTableIcon");
    var u = SullyJS.Tables.CreateStandardColumn(n, t);
    return u.fnCreatedCell = function(n, t, r) {
        for (var u, e, o = 0, f = 0; f < i.length; f++)
            o = o + r[i[f]];
        for (u = "<div class='tableBarWrapper'><div class='tableBarHeader'><\/div><div class='tableBar'>",
        f = 0; f < i.length; f++)
            e = r[i[f]] / o * 100,
            isNaN(e) && (e = 0),
            u = u + "<div class='tableBPosition" + f + "' style='width:" + e + "%'>",
            e >= 25 && (u = u + "<p>" + SullyJS.Util.Formatting.XDecimalPlaces(e, !1, 0) + "%<\/p>"),
            u = u + "<\/div>";
        u = u + "<\/div><\/div>";
        $(n).html(u)
    }
    ,
    u
}
;
SullyJS.Tables.CreateTrend = function(n, t, i, r) {
    var u = SullyJS.Tables.CreateStandardColumn(n, t);
    return u.fnCreatedCell = function(t, i, u) {
        var e;
        (typeof r == "undefined" || r == null) && (r = [.01, 1]);
        var o = 0
          , s = 45
          , f = u[n];
        f > r[1] ? f = r[1] : f < -r[1] && (f = -r[1]);
        o = f >= 0 ? f < r[0] ? 0 : -s * (f / r[1]) : f > -r[0] ? 0 : s * (f / -r[1]);
        e = "<div class='tableTrendWrapper'><div class='tableTrendHeader'><\/div><div class='tableTrend'>";
        e = e + "<img src='/images/fa/arrow-right-solid.svg' style='transform: rotate(" + o + "deg);'><\/img>";
        e = e + "<\/div><\/div>";
        $(t).html(e)
    }
    ,
    u.className = i,
    u
}
;
SullyJS.Tables.GetPickedLanguage = function(n) {
    var i = n.find("[id*=tableLanguageSelect]")
      , t = i.val();
    return (typeof t == "undefined" || t == null || t == "") && (t = "000"),
    i.show(),
    t
}
;
SullyJS.Tables.ShowTableLanguagePicker = function(n) {
    var t = n.find("[id*=tableLanguageSelect]");
    t.show()
}
;
SullyJS.Tables.LanguageChange = function(n) {
    var i = $(n).parents(".DataTableContainer:first")
      , u = i.find("[id*=tableLanguageSelect]")
      , f = u.val()
      , t = i.find("table")
      , r = t.DataTable().ajax.url()
      , e = r.lastIndexOf("/")
      , o = r.substring(0, e) + "/" + f.toString();
    t.DataTable().ajax.url(o);
    t.DataTable().ajax.reload()
}
;
SullyJS.Tables.FindStandardTable = function(n) {
    return n != null ? n.find("table") : $("table")
}
;
SullyJS.Tables.RefreshTable = function(n) {
    n.DataTable().ajax.reload()
}
;
SullyJS.Tables.CreateGamesTable = function(n, t, i, r, u, f) {
    var o = $("[id*=" + n + "]")
      , s = 0;
    typeof f != "undefined" ? s = f : (s = SullyJS.Tables.GetPickedLanguage(o),
    SullyJS.Tables.ShowTableLanguagePicker(o));
    (u == null || u == "") && (u = " ");
    var h = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t))
      , c = h.range
      , e = SullyJS.Tables.CreateStandardTableSettings("/api/tables/gametables/getgames/" + h.range.toString() + "/" + u.toString() + "/" + s.toString());
    e.ordering = !0;
    e.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    e.columns[e.columns.length - 1].orderable = !1;
    e.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/game/{0}{Append_Lan}", c), "url", "", "DataTableGameIcon", "logo", "name", "View detailed stats for {0}", !1));
    e.columns[e.columns.length - 1].orderable = !1;
    e.columns.push(SullyJS.Tables.CreateLinkColumn("name", "Game", SullyJS.Tables.AppendDayRange("/game/{0}{Append_Lan}", c), "url", "name", "name", "View detailed stats for {0}"));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("maxchannels", "Peak channels", "N", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("uniquechannels", "Streamers", "N", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("avgchannels", "Average channels", "N", 5));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("avgratio", "Average viewer ratio", "D", 5, null, null, null, null));
    h.isNamedRange || (e.columns.push(SullyJS.Tables.CreateStandardColumn("viewsgained", "Views gained", "N", 1)),
    e.columns.push(SullyJS.Tables.CreateStandardColumn("vphs", "VPR", "D1", 1, null, null, null, null, {
        tooltip: "Number of views gained per hour streamed by all channels"
    })));
    e.columns.push(SullyJS.Tables.CreateIconColumn("name", "", "https://www.twitch.tv/directory/game/{0}", "name", "/images/TwitchIcon.png", null, null, null, null, null, !1));
    e.columns[e.columns.length - 1].orderable = !1;
    e.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(o, e)
}
;
SullyJS.Tables.CreateGamesChangeTable = function(n, t, i, r, u) {
    var e = $("[id*=" + n + "]")
      , o = 0;
    typeof u != "undefined" ? o = u : (o = SullyJS.Tables.GetPickedLanguage(e),
    SullyJS.Tables.ShowTableLanguagePicker(e));
    var s = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t))
      , h = s.range
      , f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/gametables/getgameschange/" + s.range.toString() + "/" + o.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/game/{0}{Append_Lan}", h), "url", "", "DataTableGameIcon", "logo", "name", "View detailed stats for {0}", !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("name", "Game", SullyJS.Tables.AppendDayRange("/game/{0}{Append_Lan}", h), "url", "name", "name", "View detailed stats for {0}"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changeviewerminutes", "Change watch time (hours)", "M", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changestreamedminutes", "Change stream time (hours)", "M", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changemaxviewers", "Change peak viewers", "N", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changemaxchannels", "Change peak channels", "N", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changeuniquechannels", "Change streamers", "N", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changeaverageviewers", "Change average viewers", "N", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changeaveragechannels", "Change average channels", "N", 5));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("changeaverageratio", "Change average viewer ratio", "D", 5));
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", "https://www.twitch.tv/directory/game/{0}", "name", "/images/TwitchIcon.png", null, null, null, null, null, !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(e, f)
}
;
SullyJS.Tables.CreateGameChannelTable = function(n, t, i, r, u, f, e) {
    var c = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t)), l = c.range, s = $("[id*=" + n + "]"), h = 0, o;
    typeof e != "undefined" ? h = e : (h = SullyJS.Tables.GetPickedLanguage(s),
    SullyJS.Tables.ShowTableLanguagePicker(s));
    o = SullyJS.Tables.CreateStandardTableSettings("/api/tables/gametables/getgamechannels/" + c.range.toString() + "/" + i.toString() + "/" + SullyJS.Util.Url.UrlEncode(r) + "/" + h.toString());
    o.ordering = !0;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", l), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", l), "url", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    o.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    o.columns[o.columns.length - 1].orderable = !1;
    o.order = [[u, f]];
    SullyJS.Tables.InitialiseTable(s, o)
}
;
SullyJS.Tables.CreateGamePeakViewerChannels = function(n, t) {
    var i = SullyJS.Tables.CreateStandardTableSettings("/Service.svc/GetGamePeakViewerChannels?gameid=" + t.toString());
    i.columns.push(SullyJS.Tables.CreateIconColumn("Logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", DayRange), "url", "", "DataTableChannelIcon", "Logo", "ChannelName"));
    i.columns.push(SullyJS.Tables.CreateStandardColumn("Number", ""));
    i.columns.push(SullyJS.Tables.CreateLinkColumn("ChannelName", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", DayRange), "ChannelName", "ChannelName"));
    i.columns.push(SullyJS.Tables.CreateStandardColumn("TotalHours", "Watch time"));
    i.columns.push(SullyJS.Tables.CreateStandardColumn("MaxViewers", "Peak viewers"));
    i.columns.push(SullyJS.Tables.CreateStandardColumn("AverageViewers", "Average viewers"));
    i.columns.push(SullyJS.Tables.CreateStandardColumn("TotalStreamHours", "Stream time"));
    i.columns.push(SullyJS.Tables.CreateIconColumn("Url", "", "{0}", "Url", "/images/TwitchIcon.png"));
    SullyJS.Tables.InitialiseTable(n, i)
}
;
SullyJS.Tables.CreateChannelTable = function(n, t, i, r, u) {
    var e = $("[id*=" + n + "]"), s = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t)), h = s.range, o = 0, f;
    typeof u != "undefined" ? o = u : (o = SullyJS.Tables.GetPickedLanguage(e),
    SullyJS.Tables.ShowTableLanguagePicker(e));
    f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/getchannels/" + s.range.toString() + "/" + o.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", h), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", h), "url", "displayname"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 5));
    f.columns[f.columns.length - 1].orderSequence = ["desc"];
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 5));
    f.columns[f.columns.length - 1].orderSequence = ["desc"];
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 5));
    f.columns[f.columns.length - 1].orderSequence = ["desc"];
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 5));
    f.columns[f.columns.length - 1].orderSequence = ["desc"];
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 5));
    f.columns[f.columns.length - 1].orderSequence = ["desc"];
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 5));
    f.columns[f.columns.length - 1].orderSequence = ["desc"];
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewsgained", "Views gained", "N", 5));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered", null, null, null, !0));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature", null, null, null, !0));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language", null, null, null, null, null, null, null, !0));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png"));
    f.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(e, f)
}
;
SullyJS.Tables.CreateMilestonesTable = function(n, t, i, r, u, f, e) {
    var s = $("[id*=" + n + "]"), h = SullyJS.Tables.GetPickedLanguage(s), o;
    SullyJS.Tables.ShowTableLanguagePicker(s);
    o = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/getmilestones/" + t + "/" + i.toString() + "/" + r.toString() + "/" + u.toString() + "/" + h.toString());
    o.ordering = !0;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "displayname"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    o.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    o.order = [[f, e]];
    SullyJS.Tables.InitialiseTable(s, o, !0)
}
;
SullyJS.Tables.CreateNewPartnersTable = function(n, t, i, r, u) {
    var e = $("[id*=" + n + "]"), o = SullyJS.Tables.GetPickedLanguage(e), f;
    SullyJS.Tables.ShowTableLanguagePicker(e);
    f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/newpartners/" + t + "/" + i.toString() + "/" + o.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "displayname"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    f.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    f.order = [[r, u]];
    SullyJS.Tables.InitialiseTable(e, f, !0)
}
;
SullyJS.Tables.CreateChannelCompareTable = function(n, t, i, r, u) {
    t = SullyJS.Util.Url.ValidateRange(t);
    var e = $("[id*=" + n + "]")
      , f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/comparechannels/" + t + "/" + u.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t, "channel"), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t, "channel"), "url", "displayname"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    f.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    f.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(e, f, !0)
}
;
SullyJS.Tables.CreateChannelStreams = function(n, t, i, r, u, f) {
    var s, o, h, e, c;
    t = SullyJS.Util.Url.ValidateRange(t);
    s = $("[id*=" + n + "]");
    o = " ";
    typeof f != "undefined" && f.length > 0 && (o = f);
    h = null;
    e = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/streams/" + t + "/" + u.toString() + "/" + o.toString(), h);
    e.ordering = !0;
    typeof f != "undefined" && f.length > 0 && (e.columns.push(SullyJS.Tables.CreateIconColumn("channellogo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t, "channel"), "channelurl", "", "DataTableChannelIcon", "channellogo", "channeldisplayname")),
    i += 1);
    e.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    e.columns[e.columns.length - 1].orderable = !1;
    typeof f != "undefined" && f.length > 0 && e.columns.push(SullyJS.Tables.CreateLinkColumn("channeldisplayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t, "channel"), "channelurl", "channeldisplayname"));
    c = SullyJS.Tables.AppendDayRange("/channel/{0}", t, "channel") + "/stream/{1}";
    e.columns.push(SullyJS.Tables.CreateLinkColumn("starttime", "Stream start time", c, "channelurl", "starttime", "channeldisplayname", "View detailed stream stats", "streamId", "endtime", "to:"));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("streamUrl", "Stream URL"));
    e.columns[e.columns.length - 1].orderable = !1;
    e.columns[e.columns.length - 1].visible = !1;
    e.columns.push(SullyJS.Tables.CreateStandardColumn("length", "Stream length", "MM", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Avg viewers", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("followergain", "Followers gained", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("followersperhour", "Followers per hour", "D", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewgain", "Views", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewsperhour", "Views per hour", "D", 1));
    e.columns.push(SullyJS.Tables.CreateMultiImageGameLinkColumn("gamesplayed", "Games", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
    e.columns[e.columns.length - 1].orderable = !1;
    e.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(s, e)
}
;
SullyJS.Tables.CreateChannelGames = function(n, t, i, r, u, f) {
    var s, o, h, e;
    t = SullyJS.Util.Url.ValidateRange(t);
    s = $("[id*=" + n + "]");
    o = " ";
    typeof f != "undefined" && f.length > 0 && (o = f);
    h = null;
    e = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/games/" + t + "/" + u.toString() + "/" + o.toString(), h);
    e.ordering = !0;
    e.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    e.columns[e.columns.length - 1].orderable = !1;
    e.columns.push(SullyJS.Tables.CreateMultiImageGameLinkColumn("gamesplayed", "Game", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("streamtime", "Stream time (hours)", "M", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewtime", "Total watch time (hours)", "M", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewsgained", "Views", "N", 1));
    e.columns.push(SullyJS.Tables.CreateStandardColumn("viewsperhour", "Views per hour", "D", 1));
    e.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(s, e)
}
;
SullyJS.Tables.CreateGamePicker = function(n, t, i, r, u, f, e, o, s, h, c) {
    var a = $("[id*=" + n + "]"), k, l, y, p;
    a.DataTable != null && a.DataTable.destroy != null && (a.DataTable.destroy(),
    a.empty());
    var v = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t))
      , d = v.range
      , w = u.replace(/{D}/g, v.rangeName.toString())
      , b = "";
    b = v.isNamedRange ? "{C} followers per hour in {D}".replace(/{C}/g, h.toString()).replace(/{D}/g, v.rangeName.toString()) : "{C} followers per hour past {D} days".replace(/{C}/g, h.toString()).replace(/{D}/g, v.rangeName.toString());
    w == "" ? a.find("[id*=tableDescription]").hide() : a.find("[id*=tableDescription]").text(w);
    k = null;
    l = SullyJS.Tables.CreateStandardTableSettings("/api/tables/gametables/getpickedgames/" + v.range.toString() + "/" + i.toString() + "/" + s.toString() + "/" + o.toString() + "/" + c.toString(), k);
    l.ordering = !0;
    l.bInfo = !1;
    l.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    l.columns[l.columns.length - 1].orderable = !1;
    l.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/game/{0}", d, "game"), "url", "", "RelatedLinksItemMediumCell", "logo", "name", "View detailed stats for {0}", !1));
    l.columns[l.columns.length - 1].orderable = !1;
    l.columns.push(SullyJS.Tables.CreateStandardColumn("estimatedposition", "Estimated directory position", "", 4));
    i > 0 && l.columns.push(SullyJS.Tables.CreateStandardColumn("historicfollowersperhour", b, "D", 1));
    l.columns.push(SullyJS.Tables.CreateStandardColumn("standardisedfollowergain", "Followers per hour (estimated)", "D", 1));
    y = ["inrangechannels", "abovechannels", "belowchanels"];
    p = ["inrangeviewers", "aboveviewers", "belowviewers"];
    l.columns.push(SullyJS.Tables.CreateStandardColumn("aboveviewers", "Viewers in larger channels", "N", 3, p));
    l.columns.push(SullyJS.Tables.CreateStandardColumn("inrangeviewers", "Viewers in similar viewership", "N", 2, p));
    l.columns.push(SullyJS.Tables.CreateStandardColumn("belowviewers", "Viewers in equal/smaller", "N", 1, p));
    l.columns.push(SullyJS.Tables.CreateStandardColumn("abovechannels", "Larger channels", "N", 3, y));
    l.columns.push(SullyJS.Tables.CreateStandardColumn("inrangechannels", "Channels with similar viewership", "N", 2, y));
    l.columns.push(SullyJS.Tables.CreateStandardColumn("belowchanels", "Equal/Smaller channels", "N", 1, y));
    l.order = [[f, e]];
    SullyJS.Tables.InitialiseTable(a, l)
}
;
SullyJS.Tables.AdvancedChannelSearch = function(n, t, i, r, u, f, e, o, s, h, c, l, a, v, y, p, w, b, k, d) {
    n.DataTable != null && n.DataTable.destroy != null && (n.DataTable.destroy(),
    n.empty());
    s == "" && (s = " ");
    var g = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/advancedsearch/" + t.toString() + "/" + i.toString() + "/" + r.toString() + "/" + u.toString() + "/" + f.toString() + "/" + e.toString() + "/" + o.toString() + "/" + s.toString() + "/" + h.toString() + "/" + c.toString() + "/" + l.toString() + "/" + a.toString() + "/" + v.toString() + "/" + y.toString() + "/" + p.toString() + "/" + w.toString() + "/" + b.toString() + "/" + k.toISOString() + "/" + d.toString());
    g.ajax.data = function(n) {
        SullyJS.Tables.CleanTableQuery(n);
        n.languageId = r;
        n.minfollowers = u;
        n.maxfollowers = f;
        n.minviewers = e;
        n.maxviewers = o;
        n.games = s;
        n.matchall = h;
        n.onlineonly = l;
        n.cType_Community = a;
        n.cType_Aff = v;
        n.cType_Partnered = y;
        n.cTypeMat_Mature = p;
        n.cTypeMat_NotMature = w
    }
    ;
    g.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    g.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", "/channel/{0}", "name", "", "DataTableChannelIcon", "logo", "displayname"));
    g.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", "/channel/{0}", "url", "displayname"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("status", "Status"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    g.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    g.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    SullyJS.Tables.InitialiseTable(n, g)
}
;
SullyJS.Tables.CreateTeamsTable = function(n, t, i, r, u) {
    var e = $("[id*=" + n + "]")
      , f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/teamtables/getteams/" + t + "/" + i.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/team/{0}", t), "twitchurl", "", "DataTableGameIcon", "logo", "name", "View detailed stats for {0}", !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("name", "Team", SullyJS.Tables.AppendDayRange("/team/{0}", t), "twitchurl", "name", "name", "View detailed stats for {0}"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("watchtime", "Watch time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamtime", "Stream time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxchannels", "Peak members", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgchannels", "Average members", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("members", "Members", "N", 1));
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", "https://www.twitch.tv/team/{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, null, !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.order = [[r, u]];
    SullyJS.Tables.InitialiseTable(e, f)
}
;
SullyJS.Tables.CreateTeamChannelTable = function(n, t, i, r, u, f, e) {
    var s = $("[id*=" + n + "]"), h = SullyJS.Tables.GetPickedLanguage(s), o;
    SullyJS.Tables.ShowTableLanguagePicker(s);
    o = SullyJS.Tables.CreateStandardTableSettings("/api/tables/teamtables/getteamchannels/" + t + "/" + i.toString() + "/" + r.toString() + "/" + encodeURIComponent(u) + "/" + h.toString());
    o.ordering = !0;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    o.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    o.columns[o.columns.length - 1].orderable = !1;
    o.order = [[f, e]];
    SullyJS.Tables.InitialiseTable(s, o)
}
;
SullyJS.Tables.CreateCommunityTable = function(n, t, i, r, u) {
    var e = $("[id*=" + n + "]")
      , f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/communitytables/getcommunities/" + t + "/" + i.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/community/{0}", t), "name", "", "DataTableGameIcon", "logo", "name", "View detailed stats for {0}", !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("name", "Community", SullyJS.Tables.AppendDayRange("/community/{0}", t), "name", "name", "name", "View detailed stats for {0}"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxchannels", "Peak members", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgchannels", "Average members", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("channels", "Members", "N", 1));
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", "https://www.twitch.tv/communities/{0}", "name", "/images/TwitchIcon.png", null, null, null, null, null, !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.order = [[r, u]];
    SullyJS.Tables.InitialiseTable(e, f)
}
;
SullyJS.Tables.CreatCommunityChannelTable = function(n, t, i, r, u, f, e) {
    var s = $("[id*=" + n + "]"), h = SullyJS.Tables.GetPickedLanguage(s), o;
    SullyJS.Tables.ShowTableLanguagePicker(s);
    o = SullyJS.Tables.CreateStandardTableSettings("/api/tables/communitytables/getcommunitychannels/" + t + "/" + i.toString() + "/" + r.toString() + "/" + encodeURIComponent(u) + "/" + h.toString());
    o.ordering = !0;
    o.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "displayname"));
    o.columns[o.columns.length - 1].orderable = !1;
    o.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("followersgainedwhileplaying", "Followers gained (while playing " + u + ")", "N", 1));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    o.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    o.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    o.columns[o.columns.length - 1].orderable = !1;
    o.order = [[f, e]];
    SullyJS.Tables.InitialiseTable(s, o)
}
;
SullyJS.Tables.CreateGamesListTable = function(n, t, i, r, u) {
    var s = $("[id*=" + n + "]")
      , e = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t))
      , o = e.range
      , f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/gametables/getgameslist/" + e.range.toString() + "/" + u.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/game/{0}", o), "url", "", "DataTableGameIcon", "logo", "name", "View detailed stats for {0}", !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("name", "Game", SullyJS.Tables.AppendDayRange("/game/{0}", o), "url", "name", "name", "View detailed stats for {0}"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxchannels", "Peak channels", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("uniquechannels", "Streamers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgchannels", "Average channels", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgratio", "Average viewer ratio", "D", 1, null, null, null, null));
    e.isNamedRange || (f.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1)),
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewsgained", "Views gained", "N", 1)),
    f.columns.push(SullyJS.Tables.CreateStandardColumn("fphs", "FPR", "D", 1, null, null, null, null, {
        tooltip: "Number of followers gained per hour streamed by all channels"
    })),
    f.columns.push(SullyJS.Tables.CreateStandardColumn("vphs", "VPR", "D", 1, null, null, null, null, {
        tooltip: "Number of views gained per hour streamed by all channels"
    })));
    f.columns.push(SullyJS.Tables.CreateIconColumn("name", "", "https://www.twitch.tv/directory/game/{0}", "name", "/images/TwitchIcon.png", null, null, null, null, null, !1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateListOptionsColumn("name", "", u, "id", n));
    f.columns[f.columns.length - 1].orderable = !1;
    f.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(s, f)
}
;
SullyJS.Tables.CreateChannelListTable = function(n, t, i, r, u) {
    var s = $("[id*=" + n + "]")
      , e = SullyJS.Util.RangeInfo(SullyJS.Util.Url.ValidateRange(t))
      , o = e.range
      , f = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/getchannelslist/" + e.range.toString() + "/" + u.toString());
    f.ordering = !0;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", o), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", o), "url", "displayname"));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewminutes", "Watch time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Stream time (hours)", "M", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followers", "Followers", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("followersgained", "Followers gained", "N", 1));
    f.columns.push(SullyJS.Tables.CreateStandardColumn("viewsgained", "Views gained", "N", 1));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("partner", "Partnered", "B", null, null, "Partnered"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    f.columns[f.columns.length - 1].orderable = !1;
    f.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    f.columns.push(SullyJS.Tables.CreateListOptionsColumn("displayname", "", u, "id", n));
    f.columns[f.columns.length - 1].orderable = !1;
    f.order = [[i, r]];
    SullyJS.Tables.InitialiseTable(s, f)
}
;
SullyJS.Tables.CreateListOptionsColumn = function(n, t, i, r, u) {
    var f = SullyJS.Tables.CreateStandardColumn(n, t);
    return f.fnCreatedCell = function(n, t, f) {
        var e = "<a href='#' onclick='SullyJS.Users.Lists.RemoveItemFromList(" + i + "," + f[r] + ',"' + u + "\"); return false;'>Remove<\/a>";
        $(n).html(e)
    }
    ,
    f
}
;
SullyJS.Tables.CreateTwitchMonthly = function(n, t, i, r) {
    var e = $("[id*=" + n + "]"), f = 0, u;
    typeof r != "undefined" && (f = r);
    u = SullyJS.Tables.CreateStandardTableSettings("/api/tables/generaltables/getmonthly/" + f.toString(), 12);
    u.ordering = !0;
    u.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    u.columns[u.columns.length - 1].orderable = !1;
    u.columns.push(SullyJS.Tables.CreateLinkColumn("name", "Month", "/{0}{Append_Lan}", "url", "name", "name", "View detailed stats for {0}"));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("watchedminutes", "Hours watched (hours)", "M", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Average viewers", "N", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("maxviewers", "Peak viewers", "N", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("avgchannels", "Average channels", "N", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("maxchannels", "Peak channels", "N", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("streamedminutes", "Hours streamed (hours)", "M", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("games", "Games streamed", "N", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("affiliates", "Active affiliates", "N", 5));
    u.columns.push(SullyJS.Tables.CreateStandardColumn("partners", "Active partners", "N", 5));
    u.order = [[t, i]];
    SullyJS.Tables.InitialiseTable(e, u)
}
;
SullyJS.TwitchAPI.Vods = {};
SullyJS.TwitchAPI.Vods.CachedBroadcastData = {};
SullyJS.TwitchAPI.Vods.CachedHLData = {};
SullyJS.TwitchAPI.Vods.FindVodsInDateRange = function(n, t, i) {
    for (var r, f = [], u = 0; u < n.length; u++)
        r = n[u],
        r.BeginDate == null && (r.BeginDate = new Date(Date.parse(r.recorded_at)),
        r.EndDate = new Date,
        r.EndDate = new Date(r.BeginDate.getTime() + r.length * 1e3)),
        SullyJS.Util.Dates.CheckIfDatesOverlap(r.BeginDate, r.EndDate, t, i) && f.push(r);
    return f
}
;
SullyJS.TwitchFunctions.CreateLinkToGame = function(n, t, i) {
    (typeof i == "undefined" || i == null) && (i = "");
    var r = "Go to " + t + " on Twitch";
    return "<a title='" + r + "' href='https://www.twitch.tv/directory/game/" + n + "' target='_blank' class='" + i + "'><img title='" + r + "' class='DataTableIcon' src='/Images/TwitchIcon.png' border='0'><\/a>"
}
;
SullyJS.TwitchFunctions.CreateLinkToChannel = function(n, t, i) {
    (typeof i == "undefined" || i == null) && (i = "");
    var r = "Go to " + t + " on Twitch";
    return "<a title='" + r + "' href='" + n + "' target='_blank'  class='" + i + "'><img title='" + r + "' class='DataTableIcon' src='/Images/TwitchIcon.png' border='0'><\/a>"
}
;
SullyJS.TwitchFunctions.GetChannelNameFromUrl = function(n) {
    return n.replace("https://www.twitch.tv/", "")
}
;
SullyJS.TwitchFunctions.CreateLinkToCommunity = function(n, t, i) {
    (typeof i == "undefined" || i == null) && (i = "");
    var r = "Go to " + t + " on Twitch";
    return "<a title='" + r + "' href='https://www.twitch.tv/communities/" + n + "' target='_blank' class='" + i + "'><img title='" + r + "' class='DataTableIcon' src='/Images/TwitchIcon.png' border='0'><\/a>"
}
;
SullyJS.TwitchFunctions.AffiliateStreamedDays = 7;
SullyJS.TwitchFunctions.AffiliateStreamedMinutes = 500;
SullyJS.TwitchFunctions.AffiliateAverageViewers = 3;
SullyJS.TwitchFunctions.AffiliateFollowers = 50;
SullyJS.TwitchFunctions.MetsAffiliateCriteria = function(n, t, i, r) {
    return n != null && t != null && i != null && r != null && n >= SullyJS.TwitchFunctions.AffiliateStreamedDays && t >= SullyJS.TwitchFunctions.AffiliateStreamedMinutes && i >= SullyJS.TwitchFunctions.AffiliateAverageViewers && r >= SullyJS.TwitchFunctions.AffiliateFollowers ? !0 : !1
}
;
SullyJS.Games.GamePicker.CreatePickedGamesTable = function(n, t) {
    var i = $("[id*=gamepickerfilters]");
    SullyJS.Tables.CreatePickedGames("gamepickertablecontainer", t, "", "", 0, "desc");
    SullyJS.Pages.CreateToolTips()
}
;
SullyJS.Tables.CreatePickedGames = function(n, t, i, r, u, f) {
    var o = $("[id*=gamepickerresultscontainer]"), k, g, h, c, l, a, v, y;
    o.DataTable != null && o.DataTable.destroy != null && (o.DataTable.destroy(),
    o.empty());
    o.find("[id*=tableDescription]").text("");
    var nt = SullyJS.Util.RangeInfo(7)
      , p = nt.range
      , tt = t.viewership
      , w = t.channelId
      , it = t.channelGameDays
      , b = "false";
    w > 0 && (b = "true");
    var rt = t.trendViewershipRecent
      , ut = t.trendViewership3Day
      , ft = t.trendChannelsRecent
      , et = t.trendChannels3Day
      , ot = t.maxPosition
      , st = t.viewersMin
      , ht = t.viewersMax
      , ct = t.channelsMin
      , lt = t.channelsMax
      , at = t.languageId
      , e = SullyJS.Tables.CreateStandardTableSettings("/api/tables/gametables/getgamepickergames/" + tt + "/" + w + "/" + it + "/" + b + "/" + rt + "/" + ut + "/" + ft + "/" + et + "/" + ot + "/" + st + "/" + ht + "/" + ct + "/" + lt + "/" + at, null);
    e.ordering = !0;
    e.bInfo = !1;
    e.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    e.columns[e.columns.length - 1].orderable = !1;
    e.columns.push(SullyJS.Tables.CreateIconColumn("name", "", SullyJS.Tables.AppendDayRange("/game/{0}", p, "game"), "url", "", "RelatedLinksItemMediumCell", "logo", "name", "View detailed stats for {0}", !1));
    k = SullyJS.Tables.CreateLinkColumn("name", "Game", SullyJS.Tables.AppendDayRange("/game/{0}", p), "url", "name", "name", "View detailed stats for {0}");
    e.columns.push(k);
    var s = [.01, 1]
      , d = SullyJS.Tables.CreateStandardColumn("estposition", "Estimated position", "N", 1);
    d.className = "tableTrendLeft tableLeftPadded";
    e.columns.push(d);
    g = SullyJS.Tables.CreateStandardColumn("viewerratio", "Viewer ratio", "D1", 1);
    e.columns.push(g);
    h = SullyJS.Tables.CreateStandardColumn("averageviewers", "Average viewers", "N", 1);
    h.className = "tableTrendLeft tableLeftPadded";
    e.columns.push(h);
    e.columns.push(SullyJS.Tables.CreateStandardColumn("peraverageviewers", "% Twitch viewers", "P", 1));
    c = SullyJS.Tables.CreateTrend("gametrendviewersrecent", "Viewer recent trend", null, s);
    c.className = "tableTrendLeft";
    e.columns.push(c);
    e.columns.push(SullyJS.Tables.CreateBarProcess("viewersabove", "Above/Below", ["viewersabove", "viewerssame", "viewersbelow"], ""));
    l = SullyJS.Tables.CreateTrend("gametrendviewers3day", "3 day trend", null, s);
    l.className = "tableTrendRight";
    e.columns.push(l);
    a = SullyJS.Tables.CreateStandardColumn("averagechannels", "Average channels", "N", 1);
    a.className = "tableLeftPadded";
    e.columns.push(a);
    e.columns.push(SullyJS.Tables.CreateStandardColumn("peraveragechannels", "% Twitch channels", "P", 1));
    v = SullyJS.Tables.CreateTrend("gametrendchannelsrecent", "Channel recent trend", null, s);
    v.className = "tableTrendLeft";
    e.columns.push(v);
    e.columns.push(SullyJS.Tables.CreateBarProcess("channelsabove", "Above/Below", ["channelsabove", "channelssame", "channelsbelow"], ""));
    y = SullyJS.Tables.CreateTrend("gametrendchannels3day", "3 day trend", null, s);
    y.className = "tableTrendRight";
    e.columns.push(y);
    e.order = [[u, f]];
    var vt = "Game"
      , yt = "Position & ratio"
      , pt = "Viewers"
      , wt = "Channels"
      , bt = "Viewer trend"
      , kt = "Channel trend"
      , dt = SullyJS.Tables.InitialiseTable(o, e);
    dt.on("draw", function(n, t) {
        var i = $("[id*=gamepickerresultscontainer]").find("table").find("thead");
        i.find("[id=groupRow]").length == 0 && i.prepend("<tr id='groupRow'><th colspan='1'><\/th><th colspan='2' class='tableColGroupHeader'>" + vt + "<\/th><th colspan='2' class='tableColGroupHeader'>" + yt + "<\/th><th colspan='2' class='tableColGroupHeader'>" + pt + "<\/th><th colspan='3' class='tableColGroupHeader'>" + bt + "<\/th><th colspan='2' class='tableColGroupHeader'>" + wt + "<\/th><\/th><th colspan='3' class='tableColGroupHeader'>" + kt + "<\/th><\/tr>");
        $("[id*=gamepickerresultscontainer]").find("[id*=tableDescription]").text("Matched " + t._iRecordsTotal + " games")
    })
}
;
SullyJS.Calendar.CreateStreamTime = function(n, t, i, r) {
    if (t = "[" + t + "]",
    n[0] != null) {
        var u = n;
        u[0].EventList = [];
        u.fullCalendar({
            header: {
                left: "",
                center: "title",
                right: ""
            },
            editable: !1,
            eventLimit: !1,
            displayEventTime: !0,
            allDayDefault: !0,
            displayEventEnd: !0,
            timeFormat: "h:mma",
            nextDayThreshold: "00:00:01",
            contentHeight: "auto",
            titleFormat: t,
            timezone: "UTC",
            eventRender: function(n, t) {
                var f = {}, e, o;
                f.Event = n;
                f.Element = t;
                e = SullyJS.Calendar.GetValueRange(i, f.Event.data);
                o = SullyJS.Util.Formatting.DeltaBetweenColours(SullyJS.Calendar.ColourPositive, SullyJS.Calendar.ColourNeutral, SullyJS.Calendar.ColourNegative, e);
                t.addClass("ChannelMiniCalendarEvent");
                t.css("background-color", o);
                t.attr("title", SullyJS.Calendar.GetValueTooltip(f.Event.data, r));
                $("<\/br>").insertAfter(t.find(".fc-time"));
                u[0].EventList.push(f)
            },
            eventAfterAllRender: function() {
                window.dispatchEvent(new Event("resize"))
            },
            eventDestroy: function() {
                u[0].EventList = []
            }
        })
    }
}
;
SullyJS.Calendar.CreateChannelStreams = function() {
    var n = $("#channelStreamCalender");
    n[0] != null && (n[0].EventList = [],
    n.fullCalendar({
        header: {
            left: "prev,next today",
            center: "title",
            right: "month"
        },
        editable: !1,
        eventLimit: !1,
        displayEventTime: !0,
        allDayDefault: !1,
        displayEventEnd: !0,
        timeFormat: "h:mma",
        nextDayThreshold: "00:00:01",
        contentHeight: "auto",
        timezone: "UTC",
        events: function(n, t, i, r) {
            $.ajax({
                url: "/api/data/channel/getchannelstreamswithindates/" + n + "/" + t + "/" + PageInfo.id + "/0/0",
                dataType: "json",
                success: function(n) {
                    for (var u, i = [], t = 0; t < n.length; t++)
                        u = {
                            id: t,
                            title: n[t].title,
                            start: n[t].start,
                            end: n[t].end
                        },
                        i.push(u);
                    r(i)
                }
            })
        },
        eventRender: function(t, i) {
            var r = {};
            r.Event = t;
            r.Element = i;
            n[0].EventList.push(r);
            $("<\/br>").insertAfter(i.find(".fc-time"))
        },
        eventAfterAllRender: function() {
            var i = $("#channelStreamCalender").fullCalendar("getDate"), n, t;
            $(".ChannelMiniCalendar").fullCalendar("gotoDate", i);
            n = $("#channelStreamCalender").fullCalendar("getView").start;
            t = $("#channelStreamCalender").fullCalendar("getView").end;
            setTimeout(function() {
                SullyJS.Calendar.UpdateSubCalendars(n._d, t._d)
            }, 10);
            SullyJS.Calendar.AddBroadcastVodsToChannelStreams();
            SullyJS.Calendar.AddHLVodsToChannelStreams();
            window.dispatchEvent(new Event("resize"))
        },
        eventDestroy: function() {
            n[0].EventList = []
        },
        loading: function(n) {
            n
        }
    }))
}
;
SullyJS.Calendar.ColourNegative = [192, 0, 0];
SullyJS.Calendar.ColourNeutral = [225, 237, 242];
SullyJS.Calendar.ColourPositive = [0, 126, 0];
SullyJS.Calendar.UpdateSubCalendars = function(n, t) {
    var i = SullyJS.Util.Dates.CreateParseableDate(n)
      , r = SullyJS.Util.Dates.CreateParseableDate(t);
    $.ajax({
        url: "/api/data/channel/getchannelcalendarsummarydata/" + i + "/" + r + "/" + PageInfo.id + "/0/0",
        dataType: "json",
        ContentType: "application/json; charset=utf-8",
        success: function(n) {
            SullyJS.Calendar.UpdateSubCalendarsDataRetrieved(n)
        }
    })
}
;
SullyJS.Calendar.ValueRange = {};
SullyJS.Calendar.ValueRangeFixedRangeMin = {};
SullyJS.Calendar.ValueRangeFixedRangeMax = {};
SullyJS.Calendar.ValueRangeFixedRangeMin.streamtime = 0;
SullyJS.Calendar.ValueRangeFixedRangeMax.streamtime = 600;
SullyJS.Calendar.GetValueTooltip = function(n, t) {
    return t == "d" ? SullyJS.Util.Formatting.XDecimalPlaces(n, !0) : t == "n" ? SullyJS.Util.Formatting.AddThousandSeperator(n, !1) : t == "n+" ? SullyJS.Util.Formatting.AddThousandSeperator(n, !0) : t == "m" ? SullyJS.Util.Formatting.ConvertToYearsAndDays(n, !1, !1, !0) : n.toString()
}
;
SullyJS.Calendar.SetValueRange = function(n, t) {
    typeof SullyJS.Calendar.ValueRange[n + "min"] == null && (SullyJS.Calendar.ValueRange[n + "min"] = null,
    SullyJS.Calendar.ValueRange[n + "max"] = null);
    SullyJS.Calendar.ValueRange[n + "min"] == null && (SullyJS.Calendar.ValueRange[n + "min"] = t,
    SullyJS.Calendar.ValueRange[n + "max"] = t);
    SullyJS.Calendar.ValueRange[n + "min"] > t && (SullyJS.Calendar.ValueRange[n + "min"] = t);
    SullyJS.Calendar.ValueRange[n + "max"] < t && (SullyJS.Calendar.ValueRange[n + "max"] = t)
}
;
SullyJS.Calendar.GetValueRange = function(n, t) {
    if (typeof SullyJS.Calendar.ValueRange[n + "min"] != null) {
        var i = SullyJS.Calendar.ValueRange[n + "min"]
          , r = SullyJS.Calendar.ValueRange[n + "max"];
        return SullyJS.Calendar.ValueRangeFixedRangeMin[n] != null && (i = SullyJS.Calendar.ValueRangeFixedRangeMin[n]),
        SullyJS.Calendar.ValueRangeFixedRangeMax[n] != null && (r = SullyJS.Calendar.ValueRangeFixedRangeMax[n]),
        t < i && (t = i),
        t > r && (t = r),
        t == 0 ? 0 : t < 0 ? -(-t / -i) : t / r
    }
    return 0
}
;
SullyJS.Calendar.UpdateSubCalendarsDataRetrieved = function(n) {
    var a = n, u = $("[id*=channelStreamTimeCalender]"), f = $("[id*=channelStreamFollowersCalender]"), e = $("[id*=channelStreamMaxViewersCalender]"), o = $("[id*=channelStreamViewersCalender]"), t, i, r;
    u.fullCalendar("removeEvents");
    f.fullCalendar("removeEvents");
    e.fullCalendar("removeEvents");
    o.fullCalendar("removeEvents");
    var s = []
      , h = []
      , c = []
      , l = [];
    for (t = 0; t < n.length; t++)
        i = n[t],
        SullyJS.Calendar.SetValueRange("streamtime", i.streamminutes),
        r = {
            id: t,
            title: "",
            start: i.start,
            end: i.end,
            data: i.streamminutes,
            valuetype: "streamtime"
        },
        s.push(r);
    for (t = 0; t < n.length; t++)
        i = n[t],
        SullyJS.Calendar.SetValueRange("followersgained", i.followersgained),
        r = {
            id: t,
            title: "",
            start: i.start,
            end: i.end,
            data: i.followersgained,
            valuetype: "followersgained"
        },
        h.push(r);
    for (t = 0; t < n.length; t++)
        i = n[t],
        SullyJS.Calendar.SetValueRange("maxviewers", i.maxviewers),
        r = {
            id: t,
            title: "",
            start: i.start,
            end: i.end,
            data: i.maxviewers,
            valuetype: "maxviewers"
        },
        c.push(r);
    for (t = 0; t < n.length; t++)
        i = n[t],
        SullyJS.Calendar.SetValueRange("avgviewers", i.avgviewers),
        r = {
            id: t,
            title: "",
            start: i.start,
            end: i.end,
            data: i.avgviewers,
            valuetype: "avgviewers"
        },
        l.push(r);
    u.fullCalendar("renderEvents", s);
    f.fullCalendar("renderEvents", h);
    e.fullCalendar("renderEvents", c);
    o.fullCalendar("renderEvents", l)
}
;
SullyJS.Calendar.AddHLVodsToChannelStreams = function() {
    var n, t, u, i, f, r;
    if (SullyJS.TwitchAPI.Vods.CachedHLData != null && SullyJS.TwitchAPI.Vods.CachedHLData.videos != null && SullyJS.TwitchAPI.Vods.CachedHLData.videos.length > 0) {
        if ($("#channelStreamCalender")[0] == null)
            return;
        if (n = $("#channelStreamCalender")[0],
        n.EventList != null && n.EventList.length > 0)
            for (t = 0; t < n.EventList.length; t++)
                if (u = n.EventList[t].Event,
                i = SullyJS.TwitchAPI.Vods.FindVodsInDateRange(SullyJS.TwitchAPI.Vods.CachedHLData.videos, SullyJS.Util.Dates.ConvertUTCTimeToLocalTime(u.start._d), SullyJS.Util.Dates.ConvertUTCTimeToLocalTime(u.end._d)),
                i.length > 0)
                    for (n.EventList[t].Element.find(".fc-content").append("<div class='CalendarLinkContainer' id='vodhighlights'><\/div>"),
                    f = n.EventList[t].Element.find("[id*=vodhighlights]"),
                    i.length < 4 && f.addClass("CalendarLinkContainerSpaced"),
                    r = i.length - 1; r >= 0; r--)
                        f.append("<a class='' href='" + i[r].url + "' target='_blank' title='" + i[r].title + "'>HL<\/a>")
    }
}
;
SullyJS.Calendar.AddBroadcastVodsToChannelStreams = function() {
    var n, t, u, i, f, r;
    if (SullyJS.TwitchAPI.Vods.CachedBroadcastData != null && SullyJS.TwitchAPI.Vods.CachedBroadcastData.videos != null && SullyJS.TwitchAPI.Vods.CachedBroadcastData.videos.length > 0) {
        if ($("#channelStreamCalender")[0] == null)
            return;
        if (n = $("#channelStreamCalender")[0],
        n.EventList != null && n.EventList.length > 0)
            for (t = 0; t < n.EventList.length; t++)
                if (u = n.EventList[t].Event,
                i = SullyJS.TwitchAPI.Vods.FindVodsInDateRange(SullyJS.TwitchAPI.Vods.CachedBroadcastData.videos, SullyJS.Util.Dates.ConvertUTCTimeToLocalTime(u.start._d), SullyJS.Util.Dates.ConvertUTCTimeToLocalTime(u.end._d)),
                i.length > 0)
                    for ($(n.EventList[t].Element).find(".fc-content").append("<div class='CalendarLinkContainer' id='vodbroadcasts'><\/div>"),
                    f = n.EventList[t].Element.find("[id*=vodbroadcasts]"),
                    r = i.length - 1; r >= 0; r--)
                        f.append("<a class='' href='" + i[r].url + "' target='_blank' title='" + i[r].title + "'> Vod<\/a>")
    }
}
;
SullyJS.Tools.RaidFinder.CreateRaidFinder = function() {}
;
SullyJS.Tables.CreateRaidFinderTable = function(n, t, i, r, u, f, e, o, s, h, c, l, a) {
    var y = $("[id*=" + n + "]"), p, v, w, b;
    y.DataTable != null && y.DataTable.destroy != null && (y.DataTable.destroy(),
    y.empty());
    t = SullyJS.Util.Url.ValidateRange(t);
    p = null;
    v = SullyJS.Tables.CreateStandardTableSettings("/api/tables/channeltables/raidfinder/" + t + "/" + i.toString() + "/" + r.toString() + "/" + u.toString() + "/" + f.toString() + "/" + e.toString() + "/" + o.toString() + "/" + s.toString() + "/" + h.toString() + "/" + c.toString() + "/" + l.toString(), p);
    v.ordering = !0;
    v.bInfo = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("rownum", ""));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateIconColumn("logo", "", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "", "DataTableChannelIcon", "logo", "displayname"));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateLinkColumn("displayname", "Channel", SullyJS.Tables.AppendDayRange("/channel/{0}", t), "url", "displayname"));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("liveMinutes", "Live for", "MM", 1, null, null, "avgLengthMins", "liveMinutes", {
        tooltip: "Length of time the channel has been live. Percentage progress is an estimate based on previous streams.",
        icon: "info"
    }));
    l && (v.columns[v.columns.length - 1].orderable = !1);
    v.columns.push(SullyJS.Tables.CreateStandardColumn("liveViewers", "Current viewers", "N", 1, null, null, null, null, {
        tooltip: "Current viewership of the channel.",
        icon: "info"
    }));
    l && (v.columns[v.columns.length - 1].orderable = !1);
    v.columns.push(SullyJS.Tables.CreateStandardColumn("avgviewers", "Avg viewers", "N", 1, null, null, null, null, {
        tooltip: "The channels average viewers in recent history.",
        icon: "info"
    }));
    l && (v.columns[v.columns.length - 1].orderable = !1);
    v.columns.push(SullyJS.Tables.CreateStandardColumn("overlappingStreams", "Overlapping streams", "N", 1, null, null, "streams", "overlappingStreams", {
        tooltip: "The number of streams were both channels were live at the same time.",
        icon: "info"
    }));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("overlappingEndedDuring", "Ended during", "N", 1, null, null, null, null, {
        tooltip: "The number of streams where the result channels stream ended during " + a + "s stream.",
        icon: "info"
    }));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("overlappingEndedAfter", "Ended after", "N", 1, null, null, null, null, {
        tooltip: "The number of streams where the result channels stream ended after " + a + "s stream.",
        icon: "info"
    }));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("status", "Status"));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("mature", "Mature", "B", null, null, "Mature"));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateStandardColumn("language", "Language"));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateMultiImageGameLinkColumn("currentGame", "Streaming", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateMultiImageGameLinkColumn("gamesPlayed", "Recently streamed", SullyJS.Tables.AppendDayRange("/game/{0}", t, "game")));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreatePreviewColumn("displayname", "Preview", "preview", "previewLarge", "", ""));
    v.columns[v.columns.length - 1].orderable = !1;
    v.columns.push(SullyJS.Tables.CreateIconColumn("displayname", "", "{0}", "twitchurl", "/images/TwitchIcon.png", null, null, null, null, !0));
    v.columns[v.columns.length - 1].orderable = !1;
    w = 4;
    b = "desc";
    v.order = [[w, b]];
    SullyJS.Tables.InitialiseTable(y, v)
}
;
SullyJS.Users.Lists.CreateFromControl = function(n, t) {
    var i = $("[id=" + t + "]").val();
    return SullyJS.Users.Lists.Create(n, i)
}
;
SullyJS.Users.Lists.Create = function(n, t) {
    $.ajax({
        type: "POST",
        "async": "false",
        url: "/api/users/lists/create",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            name: t,
            typeId: n
        }),
        success: function() {
            setTimeout(function() {
                window.location.reload()
            })
        }
    })
}
;
SullyJS.Users.Lists.DeleteConfirm = function(n, t) {
    var i = !0;
    t != 0 && (i = confirm("Are you sure?"));
    i == !0 && SullyJS.Users.Lists.Delete(n)
}
;
SullyJS.Users.Lists.Delete = function(n) {
    $.ajax({
        type: "DELETE",
        "async": "false",
        url: "/api/users/lists/" + n,
        contentType: "application/json; charset=utf-8",
        success: function() {
            setTimeout(function() {
                window.location.reload()
            })
        }
    })
}
;
SullyJS.Users.Lists.RenameStart = function(n, t) {
    var i = $(t).parent("[id=renamecontainer]").parent();
    i.find("[id=save]").show();
    i.find("[id=rename]").hide();
    i.find("[id=cancel]").show();
    i.find("[id=renameInput]").show()
}
;
SullyJS.Users.Lists.RenameCancel = function(n, t) {
    var i = $(t).parent("[id=renamecontainer]").parent();
    i.find("[id=save]").hide();
    i.find("[id=rename]").show();
    i.find("[id=cancel]").hide();
    i.find("[id=renameInput]").hide()
}
;
SullyJS.Users.Lists.RenameSave = function(n, t) {
    var i = $(t).parent("[id=renamecontainer]").parent()
      , r = i.find("[id*=renameInput]").val();
    i.find("[id=save]").hide();
    i.find("[id=rename]").show();
    i.find("[id=cancel]").hide();
    i.find("[id=renameInput]").hide();
    i.find("[id=listname]").text(r);
    SullyJS.Users.Lists.Save(n, r)
}
;
SullyJS.Users.Lists.Save = function(n, t) {
    $.ajax({
        type: "POST",
        "async": "false",
        url: "/api/users/lists/" + n,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            id: n,
            name: t
        }),
        success: function() {
            setTimeout(function() {
                window.location.reload()
            })
        }
    })
}
;
SullyJS.Users.Lists.RemoveItemFromList = function(n, t) {
    $.ajax({
        type: "DELETE",
        "async": "false",
        url: "/api/users/lists/" + n + "/" + t,
        contentType: "application/json; charset=utf-8",
        success: function() {
            SullyJS.Tables.RefreshTable(SullyJS.Tables.FindStandardTable())
        },
        error: function() {}
    })
}
;
SullyJS.Users.Lists.AddItemFromList = function(n, t) {
    $.ajax({
        type: "PUT",
        "async": "false",
        url: "/api/users/lists/" + n + "/" + t,
        contentType: "application/json; charset=utf-8",
        success: function() {
            SullyJS.Tables.RefreshTable(SullyJS.Tables.FindStandardTable())
        },
        error: function() {}
    })
}
;
SullyJS.Users.Lists.SetListStatus = function(n, t, i, r) {
    $("[id=]").text(i.ToString() + " / " + r.ToString());
    i < r ? $("[id=adderControlId]").show() : $("[id=adderControlId]").hide()
}
;
