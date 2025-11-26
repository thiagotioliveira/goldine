window.onload = () => {
  "use strict";
  if ("serviceWorker" in navigator) {
    navigator.serviceWorker.register("sw.js", {
      scope: "./",
    });
  }
};

window.addEventListener("load", (event) => {
  var myOffcanvas = document.getElementById("modalpwa");
  var bsOffcanvas = new bootstrap.Modal(myOffcanvas);
  bsOffcanvas.show();
});
