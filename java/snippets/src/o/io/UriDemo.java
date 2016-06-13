package o.io;

import java.net.URI;

public class UriDemo {

    public static void main(String[] args) {
       URI u = URI.create("hdfs://myhost:9999/myfile.rb?name=wile&lastname=coyote");
        System.out.println(u);
        System.out.println("authority: " + u.getAuthority());
        System.out.println("raw authority: " + u.getRawAuthority());
        System.out.println("fragment: " + u.getFragment());
        System.out.println("raw fragment: " + u.getRawFragment());
        System.out.println("host: " + u.getHost());
        System.out.println("port: " + u.getPort());
        System.out.println("path: " + u.getPath());
        System.out.println("rawpath: " + u.getRawPath());
        System.out.println("query: " + u.getQuery());
        System.out.println("raw query: " + u.getRawQuery());
        System.out.println("scheme: " + u.getScheme());
        System.out.println("raw scheme specific part: " + u.getRawSchemeSpecificPart());
        System.out.println("userinfo: " + u.getUserInfo());
        System.out.println("raw userinfo: " + u.getRawUserInfo());
        System.out.println("ascii string: " + u.toASCIIString());

    }

}
