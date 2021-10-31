$(window).on("load",function() {
    $(".top").each(function() {
        $(this).animate({'opacity':'1','margin-top':'0px'},800);
    });
});

$(document).ready(function(){
    $("#headerSpace").load("header.jsp")
    $("#footerSpace").load("footer.html")
});