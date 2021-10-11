from flask import Flask, render_template
import requests

app = Flask(__name__)

@app.route("/naverRealtime")
def NaverNews():
    response = requests.get("http://localhost:8080/naverRealtime")
    cmRespDto = response.json();
    if cmRespDto["code"] == 1:    
        return render_template("index.html",naverRealtimeList=cmRespDto["data"])
    else:
        return "데이터를 가져올 수 없습니다." 
if __name__ == "__main__":
    app.run(debug=True) 