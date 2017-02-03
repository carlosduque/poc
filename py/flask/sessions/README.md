# Test
The _counter_ is a simple session stored object that gets incremented if you're part of 
the session, otherwise you'll start with a zero counter again.

You can test the counter via curl:

    curl -b cookies.txt -c cookies.txt -D hdr.txt http://127.0.0.1:5000/counter

The _username_ endpoint will let you know that you're not logged in, so, you should send a POST
including your 'username' in order to keep a session, afterwards, you can destroy it by issuing a 'logout'

1. Check the current status:

    curl -b cookies.txt -c cookies.txt -D hdr.txt http://127.0.0.1:5000/username

2. Log in

    curl -b cookies.txt -c cookies.txt -D hdr.txt -d 'username=Carlos' http://127.0.0.1:5000/username/login

3. Log out

    curl -b cookies.txt -c cookies.txt -D hdr.txt http://127.0.0.1:5000/username/logout

