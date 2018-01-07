var runs = [];
var directionsDisplay = new google.maps.DirectionsRenderer;
var directionsService = new google.maps.DirectionsService;

jQuery(document).ready(function() {
	geocoder = new google.maps.Geocoder();
});

// ***** ADDING SOME VARIABLES *****//
var geocoder = new google.maps.Geocoder();
var map;
var locations = [ [ 'Title A', 3.180967, 101.715546, 1 ],
		[ 'Title B', 3.200848, 101.616669, 2 ],
		[ 'Title C', 3.147372, 101.597443, 3 ],
		[ 'Title D', 3.19125, 101.710052, 4 ] ];

// ***** INITIALIZE FUNCTION *****//
function initialize() {
	geocoder = new google.maps.Geocoder();
	var latlng = new google.maps.LatLng(-34.397, 150.644);
	var mapOptions = {
		zoom : 11,
		center : latlng
	};

	map = new google.maps.Map(document.getElementById('map'), mapOptions);

}

function codeAddress() {

	// jQuery('#map').css("width", "600px");
	// jQuery('#map').css("height", "400px");
	jQuery('#map').css("display", "relative");
	jQuery('#map').addClass('largeone');
	jQuery('#test')
			.html(
					'<img src="http://www.prague-rental-apartments.com/plugins/vdslider/loader.gif"/>');
	initialize();
	directionsDisplay.setMap(map);
	directionsDisplay.setOptions({
		suppressMarkers : true,
		preserveViewport : true
	});
	centralLocation = null;
	var address = document.getElementById('address').value;
	if(address == "Sheffield"){
			address = "S1 2EL";
	}
	geocoder.geocode({
		'address' : address
	}, function(results, status) {
		if (status == 'OK') {

			centralLocation = new google.maps.LatLng(
					results[0].geometry.location.lat(),
					results[0].geometry.location.lng());
			map.setCenter(results[0].geometry.location);
			var marker = new google.maps.Marker({
				map : map,
				position : results[0].geometry.location,
				icon : 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
			});

			outputContent(centralLocation, results[0].geometry.location.lat(),
					results[0].geometry.location.lng(),
					results[0].geometry.location.lat(),
					results[0].geometry.location.lng());
		} else {
			alert('Geocode was not successful for the following reason: '
					+ status);
		}
	});
}

function outputContent(oldCenter, latitude, longitude, oldlat, oldlong) {
	output = "<table class='results-table'>";
	jQuery
			.ajax({
				type : "GET",
				url : "/parkrunsearch",
				data : {
					"lat" : latitude,
					"long" : longitude
				},
				dataType : 'json',
				success : function(data) {

					for (i = 0; i < data.length; i++) {
						var parkrunLocation = new google.maps.LatLng(
								data[i].latitude, data[i].longitude);
						var distance = parseFloat(calcDistance(parkrunLocation,
								oldCenter));
						var myLatLng = {
							lat : parseFloat(data[i].latitude),
							lng : parseFloat(data[i].longitude)
						};

						if (i == 0) {
							document.getElementById('centrallat').innerHTML = oldlat;
							document.getElementById('centrallong').innerHTML = oldlong;
							document.getElementById('chosenlat').innerHTML = data[i].latitude;
							document.getElementById('chosenlong').innerHTML = data[i].longitude;

							calculateAndDisplayRoute(directionsService,
									directionsDisplay, oldlat, oldlong,
									parseFloat(data[i].latitude),
									parseFloat(data[i].longitude));
						}

						if (i < 5) {
							var lowercase = data[i].parkrunName.toLowerCase();
							var finalstring = lowercase.replace(/ /g, '');
							var parkrunURL = "http://www.parkrun.org.uk/"
									+ finalstring + "/";
							var parkrunPageUrl = "";
							output += "<tr class='shadowonhover "
									+ data[i].latitude
									+ " "
									+ data[i].longitude
									+ "' style='margin-top: 20px;' onclick='klikaj("
									+ '"'
									+ data[i].latitude
									+ ','
									+ data[i].longitude
									+ '"'
									+ ")'><td style='padding: 10px;  border-radius: 15px;'><a href='"
									+ parkrunURL
									+ "' target='_blank' style='color: black;'><b><span style='font-family: europeblack;font-weight: 900;'>"
									+ (i + 1)
									+ ". "
									+ data[i].parkrunName
									+ " ("
									+ distance
									+ " miles away)</span></b></a><br><br><a href='"
									+ parkrunURL
									+ "' target='_blank'>View course route</a><br><a href='"
									+ parkrunURL
									+ "course/' target='_blank'>View more information</a></td></tr>";

							var customIcon = '';

							switch (i) {
							case 0:
								customIcon = "./images/red1.png";
								break;
							case 1:
								customIcon = "./images/red2.png";
								break;
							case 2:
								customIcon = "./images/red3.png";
								break;
							case 3:
								customIcon = "./images/red4.png";
								break;
							case 4:
								customIcon = "./images/red5.png";
								break;
							}

							var marker = new google.maps.Marker({
								position : myLatLng,
								map : map,
								icon : customIcon,
								title : data[i].parkrunName
							});

							marker.note = data[i].parkrunName + " Parkrun";

							var infobox = new google.maps.InfoWindow({
								content : 'Hello world'
							});

							google.maps.event
									.addListener(
											marker,
											'click',
											function() {
												var info_window = new google.maps.InfoWindow();
												infobox.setContent(this.note);
												infobox.open(this.getMap(),
														this);
											});
						}

						else {
							var marker = new google.maps.Marker({
								position : myLatLng,
								map : map,
								title : data[i].parkrunName
							});

							var lowercase = data[i].parkrunName.toLowerCase();
							var finalstring = lowercase.replace(/ /g, '');
							var parkrunURL = "http://www.parkrun.org.uk/"
									+ finalstring + "/";
							marker.note = "<p>" + data[i].parkrunName
									+ " Parkrun</p><a href='" + parkrunURL
									+ "' target='_blank'><p>More Info</p></a>";

							var infobox = new google.maps.InfoWindow({
								content : 'Hello world'
							});

							google.maps.event
									.addListener(
											marker,
											'click',
											function() {
												var info_window = new google.maps.InfoWindow();
												infobox.setContent(this.note);
												infobox.open(this.getMap(),
														this);
											});
						}

					}
					output += "</table>";
					jQuery('#test').html(output);
					jQuery('#floating-panel').css('display', 'block');
					jQuery('.mistake').css('display', 'block');
				}
			});
}

// calculates distance between two points in miles
function calcDistance(p1, p2) {
	return (google.maps.geometry.spherical.computeDistanceBetween(p1, p2) / 1000 / 1.6)
			.toFixed(2);
}

function executeInOrder(data) {
}

function klikaj(test) {
	var res = test.split(",");
	document.getElementById('chosenlat').innerHTML = res[0];
	document.getElementById('chosenlong').innerHTML = res[1];
	calculateAndDisplayRoute(directionsService, directionsDisplay,
			parseFloat(document.getElementById('centrallat').textContent),
			parseFloat(document.getElementById('centrallong').textContent),
			parseFloat(res[0]), parseFloat(res[1]));

}