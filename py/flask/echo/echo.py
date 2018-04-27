from flask import Flask, request, render_template

app = Flask(__name__)

@app.route("/")
def root():
    return "<h1>Welcome to <em>Flask !</em></h1>"

@app.route('/api/servicio/')
def echo():
    print request.args.get('data', '')
    return request.args.get('data', '')

if __name__ == "__main__":
    app.run()

