from flask import Flask, request, render_template
import datetime
import time

app = Flask(__name__)

@app.route("/")
def root():
    return "<h1>Welcome to <em>Flask !</em></h1>"

@app.route("/fibonacci/<n>")
def calculate_fibonacci(n=0):
    return render_template('fibonacci.html', num=fibonacci(int(n)))

@app.route("/factorial/<int:num>")
def calculate_factorial(num):
    return render_template("factorial.html", num=factorial(num))

@app.route("/time/current")
def current_time():
    return render_template("time.html", t=datetime.datetime.now())

@app.route("/info", methods=['POST'])
def info():
    for a in request.args:
        print "- %s" % (a)
    for f in request.form:
        print "* %s" % (f)

    #e = int(round(time.time() * 1000)) - int(request.get_data())
    #s = len(request.get_data())
    #print "::info: elapsed: %s, size: %s" % (elapsed, size)

    return render_template("info.html", size=0, elapsed=0)

def fibonacci(n):
    if (n <= 2):
        return 1
    else:
        return fibonacci(n - 1) + fibonacci(n - 2)

def factorial(n):
    if (n <= 1):
        return 1
    else:
        return n * factorial(n - 1)

if __name__ == "__main__":
    app.run()