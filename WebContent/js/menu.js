$(function(){
    function slideMenu() {
        let activeState = $(".menu-list").hasClass("active");
        $(".menu-list").animate({right: activeState ? "0%" : "-100%"}, 400);
    }

    $(".hamberger").click(function(event){
        event.stopPropagation();
        $(".hamberger").toggleClass("open");
        $(".menu-list").toggleClass("active");
        slideMenu();
    });
    $(".menu-list").find(".menu-contents").click(function(event){
        event.stopPropagation();
        $(this).siblings().slideToggle("fast");
        $(".menu-subcontents").not($(this).next()).slideUp("fast");
    });
});