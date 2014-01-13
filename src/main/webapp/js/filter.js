var open = false;

$(document).ready(function() {
	
	$("#switch").click(function() {
		
		if(!open) {
			
			$("#filter").animate({left:"-=20px"}, 500);
			$("#filter").animate({left:"+=230px"}, 500);
			
			$("#content").animate({width: "+=20px", marginLeft: "-=20px"}, 500);
		    $("#content").animate({width: "-=230px", marginLeft: "+=230px"}, 500, function() {
		    	
			    $("#switch-img").attr("src", "images/filter/close.gif");
			});
		    
		    open = true;
		}
		
		else if (open) {
			
			$("#filter").animate({left:"-=210px"}, 500);
			
		    $("#content").animate({width: "+=210px", marginLeft: "-=210px"}, 500, function() {
		    	
		    	$("#switch-img").attr("src", "images/filter/open.gif");
		    });
		    
		    open = false;
		}
	});
	
});