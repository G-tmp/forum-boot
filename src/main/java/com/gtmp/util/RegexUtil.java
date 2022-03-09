package com.gtmp.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * https://stackoverflow.com/questions/15265605/how-to-pull-mentions-out-of-strings-like-twitter-in-javascript
 */
public class RegexUtil {
    private RegexUtil(){}


    public static Set<String> mention(String str){
        final String regex = "\\B@[a-z0-9_-]+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher =pattern.matcher(str);
        Set<String> set = new LinkedHashSet<>();

        while (matcher.find()){
            set.add(matcher.group());
        }

        return set.size()==0 ? null : set;
    }


    public static String mentionAddLink(String str, String path){
        final String regex = "\\B@[a-z0-9_-]+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher =pattern.matcher(str);

        StringBuilder sb = new StringBuilder();
        String username;
        int start, end;
        int pos = 0;

        if (!path.endsWith("/"))
            path = path + "/";

        while (matcher.find()){
            username = matcher.group();
            username = username.substring(1,username.length());
            start = matcher.start();
            end = matcher.end();
            sb.append(str,pos,start);
            sb.append("<a href='").append(path).append(username).append("'>");
            sb.append(str,start,end);
            sb.append("</a>");
            pos = end;
        }
        sb.append(str, pos, str.length());

        return sb.toString();
    }

}
