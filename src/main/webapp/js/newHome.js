$(document).ready(function(){
//     // $("#homemenu").bind('click',home2);
//     // $("#librarymenu").bind('click',library2);
//     // $("#accountmenu").bind('click',account2);
//     // $("#informationmenu").bind('click',information2);
//     // $("#linemenu").bind('click',line);
//     // $("#equipmentmenu").bind('click',equipment2);
//     // $("#documentmenu").bind('click',document12);
//     // $("#recordmenu").bind('click',record2);
//     // $("#othermenu").bind('click',other2);
    $("#homemenu").click(function home2(){
        $("#allcontent").show().siblings("div").hide();$("#footer").show();
        $(this).removeClass();$(this).addClass("active1").siblings().removeClass()
    });

    $("#accountmenu").click(function account2() {
        $("#account").show().siblings("div").hide();$("#footer").show();
        $("#accountSideMenu").removeClass('active2');
        $(this).removeClass();$(this).addClass("active1").siblings().removeClass();
    });

    $("#librarymenu").click(function library2(){
        $("#library").show().siblings("div").hide();$("#footer").show();
        $(this).removeClass();$(this).addClass("active1").siblings().removeClass();

    });


    $("#informationmenu").click(function information2(){
        $("#information").show().siblings("div").hide();$("#footer").show();
        $(this).removeClass();$(this).addClass("active1").siblings().removeClass();
    });


    //主页下点击切换
    $("#linemenu").click(function line2() {
        $("#allcontent").show();
        $("#line").show().siblings().hide();$("#sidemenu").show();
        $(this).removeClass();$(this).addClass("active2").siblings().removeClass();
    });
    $("#equipmentmenu").click(function equipment2() {
        $("#allcontent").show();
        $("#equipment").show().siblings().hide();$("#sidemenu").show();
        $(this).removeClass();$(this).addClass("active2").siblings().removeClass();
    });
    $("#documentmenu").click(function document12() {
        $("#allcontent").show();
        $("#document").show().siblings().hide();$("#sidemenu").show();
        $(this).removeClass();$(this).addClass("active2").siblings().removeClass();
    });
    $("#recordmenu").click(function record2() {
        $("#allcontent").show();
        $("#record").show().siblings().hide();$("#sidemenu").show();
        $(this).removeClass();$(this).addClass("active2").siblings().removeClass();
    });
    $("#othermenu").click(function other2() {
        $("#allcontent").show();
        $("#other").show().siblings().hide();$("#sidemenu").show();
        $(this).removeClass();$(this).addClass("active2").siblings().removeClass();
    });

    // //账号下点击切换
    // $("#teamMenu").click(function teamManagement() {
    //     $("#account").show();
    //     $("#teamManagement").show().siblings().hide();$("#accountSideMenu").show();
    //     $(this).removeClass();$(this).addClass("active2");$(this).siblings().removeClass();
    // });
    // $("#siteAccountMenu").click(function accountNumber() {
    //     $("#account").show();
    //     $("#siteAccount").show().siblings().hide();$("#accountSideMenu").show();
    //     $(this).removeClass();$(this).addClass("active2");$(this).siblings().removeClass();
    // });
});

