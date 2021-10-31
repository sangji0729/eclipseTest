    $(function () {
    let $button = $('#sideMenuButton');
    let $buttonX = $('#sideMenuButton_X');
    let $space = $('#sideMenuSpace');

    $($space).click(function() {
    let $sideMenu = $('#sideMenu').css('left');
    if ($sideMenu == '-300px') {
    $button.addClass('visible');
    $buttonX.addClass('visible');
    $space.addClass('visible');
    $('#sideMenu').css('left', '0px');
} else {
    $('#sideMenu').css('left', '-300px');
    $button.removeClass('visible');
    $buttonX.removeClass('visible');
    $space.removeClass('visible');
}
});
});

    $(function(){
    $(document).ready(function(){
        $('#sideMenuContainer li.active').addClass('open').children('ul').show();
        $('#sideMenuContainer li.sideMenuItem>a').on('click', function(){
            //$(this).removeAttr('href');
            let element = $(this).parent('li');
            if (element.hasClass('open')) {
                element.removeClass('open');
                element.find('li').removeClass('open');
                element.find('ul').slideUp(200);
            }
            else {
                element.addClass('open');
                element.children('ul').slideDown(200);
                element.siblings('li').children('ul').slideUp(200);
                element.siblings('li').removeClass('open');
                element.siblings('li').find('li').removeClass('open');
                element.siblings('li').find('ul').slideUp(200);
            }
        });
    });
});


function openMyInfoLogin() {
    let boxOpened = document.getElementById("myInfoEntrance");
    if(boxOpened.style.display == 'block'){
        boxOpened.style.display = 'none';
    } else{
        boxOpened.style.display = 'block';
    }
}