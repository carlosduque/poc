from flask import Flask
from flask import abort
from flask import jsonify
from flask import request
from flask import render_template
from flask import url_for
import random

app = Flask(__name__)

dummy_entries = [
        {'id': 1, 'time': 45678},
        {'id': 2, 'time': 45789},
        {'id': 3, 'time': 44283}
]

@app.route("/taimr")
def home():
    """ Show home page """
    return render_template('home.html')

@app.route("/taimr/entries", methods=['POST'])
def post_entry():
    """ Save a time entry """
    time = request.form['time']
    print "time on request: %s" % (time)
    #save time entry
    return render_template("home.html")

@app.route("/taimr/api/v1.0/entries.json", methods=['POST'])
def api_post_entry():
    """ Save a time entry """
    if not request.json:
        abort(400)
    entry = {
            'id': random.random(),
            'time': request.json.get('time', "")
    }
    dummy_entries.append(entry)
    print "received %s" % (entry)
    return jsonify({'entries': entry}), 201

@app.route("/taimr/api/v1.0/entries.json", methods=['GET'])
def api_get_entries():
    return jsonify({'entries': dummy_entries})

@app.route("/taimr/api/v1.0/<int:id>.json", methods=['GET'])
def api_get_entry(id):
    print "looking for %s" % (id)
    entry = [entry for entry in dummy_entries if entry['id'] == id]
    print "found %s" % (entry)
    if len(entry) == 0:
        abort(404)
    return jsonify({'entries': [entry[0]]})

if __name__ == "__main__":
    app.run(host = "0.0.0.0", port = int("5000"))

