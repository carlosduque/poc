from flask import Flask, session, request, redirect, url_for, escape

app = Flask(__name__)

@app.route('/counter', methods=['GET'])
def counter_home():
    number = 0
    if 'count' in session:
        number = int(session.get('count')) + 1
        session['count'] = str(number)
        return session['count']

    print "initializing counter"
    session['count'] = str(0)
    return session['count']

@app.route('/username')
def username_home():
    if 'username' in session:
        return 'Logged in as %s' % escape(session['username'])
    return 'You are not logged in'

@app.route('/username/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        session['username'] = request.form['username']
        return redirect(url_for('username_home'))
    return '''
        <form method="post">
            <p><input type=text name=username>
            <p><input type=submit value=Login>
        </form>
    '''

@app.route('/username/logout')
def logout():
    # remove the username from the session if it's there
    session.pop('username', None)
    return redirect(url_for('username_home'))

app.secret_key = 'notReallyThatS3CR3T!!'
