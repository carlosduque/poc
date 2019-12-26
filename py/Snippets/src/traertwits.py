import os
import string
import warnings
warnings.simplefilter('ignore', DeprecationWarning)
import twitter

username='cduque'
password='sihhe029lord'

api = twitter.Api(username, password)
status = api.GetFriendsTimeline()
twitterSize = 5;
i = 0;

for s in status:
	i = i+1
	print s.user.name, "(@"+s.user.screen_name+")"
	print s.text
	print
	if i == twitterSize:
		break
