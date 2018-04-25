function(feed) {
    if(feed.type === "feed" &&
       feed["current-state"] === true &&
       feed.action !== "delete") {
        emit([feed["default-document-type"],
              feed["language"],
              feed["name"]],
             feed);
    }
}
