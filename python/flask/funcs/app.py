from flask import Flask, render_template
import datetime

app = Flask(__name__)

@app.route("/fibonacci/<n>")
def calculate_fibonacci(n=0):
    return render_template('fibonacci.html', num=fibonacci(int(n)))

@app.route("/factorial/<int:num>")
def calculate_factorial(num):
    return render_template("factorial.html", num=factorial(num))

@app.route("/current/time")
def current_time():
    return render_template("time.html", t=datetime.datetime.now())


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

