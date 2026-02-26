function dispHamNavBar() {
  var h = document.getElementById("hamMenu");
  if (h.style.display === "block") {
    h.style.display = "none";
  } else {
    h.style.display = "block";
  }
}