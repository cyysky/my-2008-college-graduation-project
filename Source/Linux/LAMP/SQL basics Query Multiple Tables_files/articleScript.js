function MM_openBrWindow(theURL,winName,features) { //v2.0
	window.open(theURL,winName,features);
}


function usrActionReg(which,action) //user engagement
{
   which.innerHTML += '<img src="http://dw.com.com/redir?destUrl=http%3A%2F%2Fi.i.com.com%2Fcnwk.1d%2Fi%2Fb.jpg' + dwInfo + '&usraction=' + action + '" width="1" height="1" border="0">';
      if (action == 20) {
         window.print ();  
      }
      else if (action == 19) {
      	location.href='mailto:?subject='+encodeURIComponent(document.title)+'&body='+encodeURIComponent(location.href);
      }
}

//Get related content
function getrelatedContent() 
{
	try {
		var assets = relassets();
		var impressions = relimpressions();
		if (assets.length > 0) {
			document.writeln('<h2>People who read this, also read...</h2>');
			document.writeln('<ul class="relatedContent">');
			for (var i = 0; i < assets.length; i++) {
				if (i == (assets.length - 1)){
  			 		document.writeln('<li class="last">');
  			 	} else {
  			 		document.writeln('<li>');
  			 	}
  			 		document.writeln('<a href="' + assets[i].url + '">' + assets[i].title + '</a></li>');  			 		
  			 	}
					document.writeln('</ul>');
				}
			if (impressions.length > 0) {
				for (var i = 0; i < impressions.length; i++) {
  			 		document.writeln( impressions[i] );
  			 	}	}			
	} catch(ex) { }
}


//Rubics unit for the gallery
function getgalleryRubics()
{
	try {
		if (rubicsResponse.rubics.response.offerList.length > 0) {
			document.writeln('<h2>Featured Galleries<\/h2>');
			document.writeln('<ul class="rubicContent">');
			for (var i = 0; i < rubicsResponse.rubics.response.offerList.length; i++) {
					document.writeln('<li>');
  			 		document.writeln('<span><a href="' + rubicsResponse.rubics.response.offerList[i].url + '"><img src="' + rubicsResponse.rubics.response.offerList[i].thumbnailUrl + '"><\/a><\/span>');  
  			 		document.writeln('<a href="' + rubicsResponse.rubics.response.offerList[i].url + '">' + rubicsResponse.rubics.response.offerList[i].title + '<\/a>');  		
  			 		document.writeln('<\/li>');
  			 	}
			document.writeln('<\/ul>');
			}
		document.writeln(rubicsResponse.rubics.response.unitImpTracking);		
	} catch(ex) { }

}