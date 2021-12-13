$(document).ready(function() {
	/// here...

	if ($("body").find(".accountManagementPanel").length > 0){

        $('body').css('background-color', '#FFFFFF');
        $('body').css('color', '#000000');
        $('body').css('position', 'relative');
        $('body').css('height', 'calc(100vh)');
        $('body').css('background-size', 'cover');
        $('body').css('background-repeat', 'no-repeat');
        $('body').css('background-position', 'center center');

        $('h4').css('margin-top', '-20px');

        $("form:not(.filter) :input:visible:enabled").eq(0).attr("placeholder", "Ingrese nombre de usuario");
        $("form:not(.filter) :input:visible:enabled").eq(1).attr("placeholder", "Ingrese contraseña");

        $("img[src$='/images/Logo-principal.png']").wrap("<a href='/'> </a>");

        $("button.btn[type=submit]").removeClass("btn-primary").addClass("btn-info");

        $("button.btn[type=reset]").hide();

    	}else{
        $('body').css('background-color', 'white');
        $('body').css('background-repeat', 'repeat-y');
        $('body').css('color', 'black');
        $('body').css('position', 'relative');
        $('body').css('height', 'calc(100vh)');
        $('body').css('background-size', 'cover');
        $('body').css('background-position', 'center center');
    	}
});