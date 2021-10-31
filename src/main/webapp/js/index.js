$(document).ready(function(){
    $("#headerSpace").load("header.jsp")
    $("#footerSpace").load("footer.html")
});

$(document).ready(function(){
    $('.slider').bxSlider();
    $('.bxslider').bxSlider({
        auto: true,
        speed: 500,
        pause: 5000,
        mode: 'horizontal',
        autoControls: false,
        pager: true,
        captions: false,
        nextSelector:'#next-btn',
        prevSelector:'#prev-btn'
    });
});

function isScrolledIntoView(elem) {
    let $window = $(window),
        docViewTop = $window.scrollTop(),
        docViewBottom = docViewTop + $window.innerHeight(),
        elemTop = $(elem).offset().top,
        elemBottom = elemTop + $(elem).outerHeight();
     return ((elemBottom < docViewBottom) && (elemTop > docViewTop));
    // return ((elemBottom < docViewBottom) && (elemTop <= docViewTop));
    //return elemTop < docViewTop;
}

$(window).on("load",function() {
    $(window).on("scroll", function() {
 //       let windowBottom = $(window).scrollTop() + $(window).innerHeight() ;
        $(".article").each(function() {
            // let objectBottom = $(this).offset().top + $(this).outerHeight();
            // console.log(windowBottom);
            // console.log(objectBottom);
            if (isScrolledIntoView(this)) {
                if ($(this).css("opacity")==0) {
                    $(this).fadeTo(500,1);
                }
            } else {
                if ($(this).css("opacity")==1) {
                    $(this).fadeTo(500,0);
                }
            }
        });
    }).scroll();
});



$(window).on("load",function() {
    $("#mostPopular_Top").each(function() {
        //$(this).fadeTo(1000, 1);
        $(this).animate({'opacity':'1','margin-top':'0px'},650);
    });
});