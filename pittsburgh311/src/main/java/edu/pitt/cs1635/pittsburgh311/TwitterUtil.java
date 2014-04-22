package edu.pitt.cs1635.pittsburgh311;

        import twitter4j.Twitter;
        import twitter4j.TwitterException;
        import twitter4j.TwitterFactory;
        import twitter4j.auth.AccessToken;
        import twitter4j.auth.RequestToken;
        import twitter4j.conf.Configuration;
        import twitter4j.conf.ConfigurationBuilder;


public final class TwitterUtil {

    private RequestToken requestToken = null;
    private TwitterFactory twitterFactory = null;
    private Twitter twitter;

    public TwitterUtil() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey("PckTCfTOorRUJ4liti83ryNA9");
        configurationBuilder.setOAuthConsumerSecret("yZgl5sUlQsJmRvD2hRW5HXKPquDQQXom8MSVulXU1ArlZRW4oT");

        Configuration configuration = configurationBuilder.build();
        twitterFactory = new TwitterFactory(configuration);
        twitter = twitterFactory.getInstance();

    }

    public TwitterFactory getTwitterFactory()
    {
        return twitterFactory;
    }

    public void setTwitterFactory(AccessToken accessToken)
    {
        twitter = twitterFactory.getInstance(accessToken);
    }

    public Twitter getTwitter()
    {
        return twitter;
    }

    public RequestToken getRequestToken() {
    //    if (requestToken == null) {
            try {
                requestToken = twitterFactory.getInstance().getOAuthRequestToken("oauth://Pitt");

            } catch (TwitterException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
      //  }
        return requestToken;
    }

    static TwitterUtil instance = new TwitterUtil();

    public static TwitterUtil getInstance() {
        return instance;
    }


    public void reset() {
        instance = new TwitterUtil();
    }
}